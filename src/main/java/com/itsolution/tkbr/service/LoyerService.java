/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import com.itsolution.tkbr.domain.Encaissement;
import com.itsolution.tkbr.domain.Loyer;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.repository.CompteAnalytiqueClientRepository;
import com.itsolution.tkbr.repository.LoyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class LoyerService {

    @Autowired
    LoyerRepository loyerRepository;
    @Autowired
    EncaissementService encaissementService;
    @Autowired
    CompteAnalytiqueClientRepository compteAnalytiqueClientRepository;

    public Loyer save(Loyer loyer) throws Exception {
        if (loyer.getId() != null) {
            throw new Exception("Mise à jour des loyers interdit");
        }
        CompteAnalytiqueClient compteClient = compteAnalytiqueClientRepository.findByIntitule(loyer.getLocation().getLocataire().getNom());
        if (compteClient == null) {
            compteClient = new CompteAnalytiqueClient();
            compteClient.setIntitule(loyer.getLocation().getLocataire().getNom());
            compteClient.setClient(loyer.getLocation().getLocataire());
        }
        compteClient.setCredit(loyer.getMontant());

        compteAnalytiqueClientRepository.save(compteClient);

        Encaissement encaissement = new Encaissement();
        encaissement.setMontant(loyer.getMontant());
        encaissement.setDateVersement(loyer.getDateVersement());
        encaissement.setModePaiement(loyer.getModePaiement());
        encaissement.setMotif(CaisseMouvementMotif.LOYER);
        encaissement.setCommentaires("Mvt Loyer " + loyer.getId());

        encaissementService.save(encaissement);

        return loyerRepository.save(loyer);
    }

}
