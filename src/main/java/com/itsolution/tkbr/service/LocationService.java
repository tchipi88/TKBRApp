/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import com.itsolution.tkbr.domain.Location;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueClientType;
import com.itsolution.tkbr.repository.ClientRepository;
import com.itsolution.tkbr.repository.CompteAnalytiqueClientRepository;
import com.itsolution.tkbr.repository.LocationRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CompteAnalytiqueClientRepository compteAnalytiqueClientRepository;
    @Autowired
    CompteService cs;
    @Autowired
    ClientRepository  clientRepository;

    public Location create(Location location) throws Exception {
        location.setDateFin(location.getDateDebut().plusMonths(location.getDuree()));
        BigDecimal montant = location.getMontantLoyer().multiply(new BigDecimal(location.getDuree()));

        CompteAnalytiqueClient compteClient = compteAnalytiqueClientRepository.findByIntituleAndType(location.getLocataire().getNom(),CompteAnalytiqueClientType.LOCATION);
        if (compteClient == null) {
            compteClient = new CompteAnalytiqueClient();
            compteClient.setIntitule(location.getLocataire().getNom());
            compteClient.setClient(location.getLocataire());
            compteClient.setType(CompteAnalytiqueClientType.LOCATION);
            compteClient.setCredit(BigDecimal.ZERO);
            compteClient.setDebit(BigDecimal.ZERO);
        }
        compteClient.setDebit(compteClient.getDebit().add(montant));

        compteAnalytiqueClientRepository.save(compteClient);

        Compte compteLoyer = cs.getCompteLoyer();
        Compte compteVente = cs.getCompteVente();
        Compte compteTVACollecte = cs.getCompteTVACollecte();

        compteLoyer.setDebit(montant.add(compteClient.getDebit()));
        compteVente.setCredit(montant.add(compteVente.getCredit()));
        compteTVACollecte.setCredit(montant.add(compteTVACollecte.getCredit()));
        
        if(!location.getLocataire().isLocataire()) {
            Client locataire=location.getLocataire();
            locataire.setLocataire(true);
            clientRepository.save(locataire);
        }

        return locationRepository.save(location);
    }

    public Location update(Location location) throws Exception {
        //changer la durée
        Location locationOld = locationRepository.findOne(location.getId());

        CompteAnalytiqueClient compteClient = compteAnalytiqueClientRepository.findByIntitule(locationOld.getLocataire().getNom());
        compteClient.setCredit(compteClient.getCredit().add(locationOld.getMontantLoyer().multiply(new BigDecimal(locationOld.getDuree()))));

        compteAnalytiqueClientRepository.save(compteClient);

        return create(location);
    }

}
