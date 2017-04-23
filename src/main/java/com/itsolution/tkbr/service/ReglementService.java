/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Decaissement;
import com.itsolution.tkbr.domain.Encaissement;
import com.itsolution.tkbr.domain.Reglement;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.repository.CompteRepository;
import com.itsolution.tkbr.repository.ReglementRepository;
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
public class ReglementService {

    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    CompteService cs;
    @Autowired
    DecaissementService decaissementService;
    @Autowired
    EncaissementService encaissementService;

    public Reglement save(Reglement r) throws Exception {

        if (r.getId() != null) {
            throw new Exception("Mise à jour des reglements interdites");
        }

        //Verifier que la commande n'est pas deja regle
        // if (r.getCommande().getReglements().stream().collect())
        //@todo  crediter la caisse
        BigDecimal totalttc = r.getMontant();

        switch (r.getCommande().getType()) {
            case ACHAT: {
                Compte compteFournisseurs = cs.getCompteFournisseurs();
                Compte compteCaisse = cs.getCompteCaisse();

                compteFournisseurs.setDebit(totalttc.add(compteFournisseurs.getDebit()));
                compteCaisse.setCredit(totalttc.add(compteCaisse.getCredit()));

                compteRepository.save(compteFournisseurs);
                compteRepository.save(compteCaisse);
                
                Decaissement decaissement=new Decaissement();
                decaissement.setMontant(r.getMontant());
                decaissement.setDateVersement(r.getDateVersement());
                decaissement.setModePaiement(r.getMode());
                decaissement.setMotif(CaisseMouvementMotif.ACHAT);
                decaissement.setCommentaires("Mvt commande "+r.getCommande().getId());
                
                decaissementService.save(decaissement);

                break;
            }
            case VENTE: {
                Compte compteClient = cs.getCompteClient();
                Compte compteCaisse = cs.getCompteCaisse();

                compteClient.setCredit(totalttc.add(compteClient.getCredit()));
                compteCaisse.setDebit(totalttc.add(compteCaisse.getDebit()));

                compteRepository.save(compteClient);
                compteRepository.save(compteCaisse);
                
                Encaissement encaissement =new Encaissement();
                encaissement.setMontant(r.getMontant());
                encaissement.setDateVersement(r.getDateVersement());
                encaissement.setModePaiement(r.getMode());
                encaissement.setMotif(CaisseMouvementMotif.VENTE);
                encaissement.setCommentaires("Mvt commande "+r.getCommande().getId());
                
                encaissementService.save(encaissement);
                break;
            }
            default: {
                throw new Exception("Type de commande non spécifié");
            }
        }

        return reglementRepository.save(r);
    }

}
