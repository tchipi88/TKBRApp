/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Decaissement;
import com.itsolution.tkbr.domain.Encaissement;
import com.itsolution.tkbr.domain.TerrainCommande;
import com.itsolution.tkbr.domain.TerrainReglement;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
import com.itsolution.tkbr.repository.TerrainCommandeRepository;
import com.itsolution.tkbr.repository.TerrainReglementRepository;
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
public class TerrainReglementService {

    @Autowired
    TerrainReglementRepository reglementRepository;

    @Autowired
    CompteService cs;
    @Autowired
    DecaissementService decaissementService;
    @Autowired
    EncaissementService encaissementService;
    @Autowired
    TerrainCommandeRepository commandeRepository;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;

    public TerrainReglement save(TerrainReglement r) throws Exception {

        if (r.getId() != null) {
            throw new Exception("Mise à jour des reglements interdites");
        }

        //http://www.compta-facile.com/comptabilisation-operations-bancaires/
        //Verifier que la commande n'est pas deja regle
        // if (r.getCommande().getReglements().stream().collect())
        //@todo  crediter la caisse
        BigDecimal totalttc = r.getMontant();

        switch (r.getCommande().getType()) {
            case ACHAT: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getFournisseur(), CompteAnalytiqueType.TERRAIN, totalttc, SensEcritureComptable.C,"Versement pour Achat Terrain "+r.getCommande().getTerrain().getDenomination());

                //comptabilite globalle
                Compte compteFournisseurs = cs.getCompteFournisseurs();
                compteFournisseurs.setDebit(totalttc.add(compteFournisseurs.getDebit()));
                cs.save(compteFournisseurs);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setCredit(totalttc.add(compteCaisse.getCredit()));
                        cs.save(compteCaisse);

                        Decaissement decaissement = new Decaissement();
                        decaissement.setMontant(r.getMontant());
                        decaissement.setDateVersement(r.getDateVersement());
                        decaissement.setModePaiement(r.getMode());
                        decaissement.setMotif(CaisseMouvementMotif.ACHAT);
                        decaissement.setCommentaires("Terrain Achat  N: " + r.getCommande().getId());

                        decaissementService.save(decaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }

                }

                break;
            }
            case VENTE: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getClient(), CompteAnalytiqueType.TERRAIN, totalttc, SensEcritureComptable.C,"Versement pour Vente  Terrain "+r.getCommande().getTerrain().getDenomination());

                Compte compteClient = cs.getCompteClient();
                compteClient.setCredit(totalttc.add(compteClient.getCredit()));
                cs.save(compteClient);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setDebit(totalttc.add(compteCaisse.getDebit()));
                        cs.save(compteCaisse);

                        Encaissement encaissement = new Encaissement();
                        encaissement.setMontant(r.getMontant());
                        encaissement.setDateVersement(r.getDateVersement());
                        encaissement.setModePaiement(r.getMode());
                        encaissement.setMotif(CaisseMouvementMotif.VENTE);
                        encaissement.setCommentaires("Terrain Vente N: " + r.getCommande().getId());

                        encaissementService.save(encaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteCheque = cs.getCompteCheque();
                        compteCheque.setDebit(totalttc.add(compteCheque.getDebit()));
                        cs.save(compteCheque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setDebit(totalttc.add(compteBanque.getDebit()));
                        cs.save(compteBanque);
                        break;
                    }
                }
                break;
            }
            default: {
                throw new Exception("Type de commande non spécifié");
            }
        }

        TerrainCommande commande = commandeRepository.findOne(r.getCommande().getId());
        commande.setMontantPaye(commande.getMontantPaye() == null ? r.getMontant() : commande.getMontantPaye().add(r.getMontant()));

        commandeRepository.save(commande);

        return reglementRepository.save(r);
    }

}
