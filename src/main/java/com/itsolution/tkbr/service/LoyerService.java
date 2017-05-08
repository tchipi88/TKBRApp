/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Encaissement;
import com.itsolution.tkbr.domain.Loyer;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
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
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;
    @Autowired
    CompteService compteService;

    public Loyer save(Loyer loyer) throws Exception {
        if (loyer.getId() != null) {
            throw new Exception("Mise à jour des loyers interdit");
        }
        ecritureCompteAnalytiqueService.create(loyer.getLocation().getLocataire(), CompteAnalytiqueType.LOCATION, loyer.getMontant(), SensEcritureComptable.C,"Versement Loyer pour location N: "+loyer.getLocation().getId());

        Compte compteLoyer = compteService.getCompteLoyer();
        compteLoyer.setCredit(loyer.getMontant().add(compteLoyer.getCredit()));
        compteService.save(compteLoyer);

        switch (loyer.getModePaiement()) {
            case ESPECES: {

                Compte compteCaisse = compteService.getCompteCaisse();
                compteCaisse.setDebit(loyer.getMontant().add(compteCaisse.getDebit()));
                compteService.save(compteCaisse);

                Encaissement encaissement = new Encaissement();
                encaissement.setMontant(loyer.getMontant());
                encaissement.setDateVersement(loyer.getDateVersement());
                encaissement.setModePaiement(loyer.getModePaiement());
                encaissement.setMotif(CaisseMouvementMotif.LOYER);
                encaissement.setCommentaires("Encaissement pour location " + loyer.getLocation().getId());

                encaissementService.save(encaissement);

                break;
            }
            case CHEQUE: {
                Compte compteCheque = compteService.getCompteCheque();
                compteCheque.setDebit(loyer.getMontant().add(compteCheque.getDebit()));
                compteService.save(compteCheque);
                break;
            }
            case VIREMENT: {
                Compte compteBanque = compteService.getCompteBanque();
                compteBanque.setDebit(loyer.getMontant().add(compteBanque.getDebit()));
                compteService.save(compteBanque);
                break;
            }
        }

        return loyerRepository.save(loyer);
    }

}
