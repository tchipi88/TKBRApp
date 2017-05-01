/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Location;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
import com.itsolution.tkbr.repository.ClientRepository;
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
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;
    @Autowired
    CompteService cs;
    @Autowired
    ClientRepository clientRepository;

    public Location create(Location location) throws Exception {
        location.setDateFin(location.getDateDebut().plusMonths(location.getDuree()));
        if(location.getMontantLoyer()==null) location.setMontantLoyer(location.getLocal().getPrix());
        BigDecimal montant = location.getMontantLoyer().multiply(new BigDecimal(location.getDuree()));

        ecritureCompteAnalytiqueService.create(location.getLocataire(), CompteAnalytiqueType.LOCATION, montant, SensEcritureComptable.D,"Location pour Local :"+location.getLocal().getDenomination());

        Compte compteLoyer = cs.getCompteLoyer();
        Compte compteVente = cs.getCompteVente();
        Compte compteTVACollecte = cs.getCompteTVACollecte();

        compteLoyer.setDebit(montant.add(compteLoyer.getDebit()));
        compteVente.setCredit(montant.add(compteVente.getCredit()));
        compteTVACollecte.setCredit(montant.add(compteTVACollecte.getCredit()));

        if (!location.getLocataire().isLocataire()) {
            Client locataire = location.getLocataire();
            locataire.setLocataire(true);
            clientRepository.save(locataire);
        }

        return locationRepository.save(location);
    }

    public Location update(Location location) throws Exception {
        //changer la durée
        Location locationOld = locationRepository.findOne(location.getId());

        ecritureCompteAnalytiqueService.create(locationOld.getLocataire(), CompteAnalytiqueType.LOCATION, locationOld.getMontantLoyer().multiply(new BigDecimal(locationOld.getDuree())), SensEcritureComptable.C,"Mise à jour Location N:"+location.getId());

        return create(location);
    }

}
