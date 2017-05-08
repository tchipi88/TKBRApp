/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Decaissement;
import com.itsolution.tkbr.domain.Paie;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import static com.itsolution.tkbr.domain.enumeration.PaymentMode.CHEQUE;
import static com.itsolution.tkbr.domain.enumeration.PaymentMode.ESPECES;
import static com.itsolution.tkbr.domain.enumeration.PaymentMode.VIREMENT;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
import com.itsolution.tkbr.repository.PaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class PaieService {

    @Autowired
    PaieRepository paieRepository;
    @Autowired
    DecaissementService decaissementService;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;
    @Autowired
    CompteService compteService;

    public Paie save(Paie paie) throws Exception {
        if (paie.getId() != null) {
            throw new Exception("Mise à jour de la paie interdite");
        }
      //  ecritureCompteAnalytiqueService.create(loyer.getLocation().getLocataire(), CompteAnalytiqueType.LOCATION, loyer.getMontant(), SensEcritureComptable.C,"Versement Loyer pour location N: "+loyer.getLocation().getId());

        Compte comptePersonnel = compteService.getComptePersonnel();
        comptePersonnel.setDebit(paie.getMontant().add(comptePersonnel.getDebit()));
        compteService.save(comptePersonnel);

        switch (paie.getModePaiement()) {
            case ESPECES: {

                Compte compteCaisse = compteService.getCompteCaisse();
                compteCaisse.setCredit(paie.getMontant().add(compteCaisse.getCredit()));
                compteService.save(compteCaisse);

                Decaissement decaissement = new Decaissement();
                decaissement.setMontant(paie.getMontant());
                decaissement.setDateVersement(paie.getDateVersement());
                decaissement.setModePaiement(paie.getModePaiement());
                decaissement.setMotif(CaisseMouvementMotif.PAIE);
                decaissement.setCommentaires("Renumeration Employe :" + paie.getEmploye().getNom());

                decaissementService.save(decaissement);

                break;
            }
            case CHEQUE: {
                Compte compteCheque = compteService.getCompteCheque();
                compteCheque.setCredit(paie.getMontant().add(compteCheque.getCredit()));
                compteService.save(compteCheque);
                break;
            }
            case VIREMENT: {
                Compte compteBanque = compteService.getCompteBanque();
                compteBanque.setCredit(paie.getMontant().add(compteBanque.getCredit()));
                compteService.save(compteBanque);
                break;
            }
        }

        return paieRepository.save(paie);
    }

}
