/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.config.ApplicationProperties;
import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.Commande;
import com.itsolution.tkbr.domain.CommandeLigne;
import com.itsolution.tkbr.domain.Fournisseur;
import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.domain.ProduitFournisseur;
import com.itsolution.tkbr.domain.enumeration.EntrepotType;
import com.itsolution.tkbr.domain.enumeration.EtatCommande;
import com.itsolution.tkbr.repository.ClientRepository;
import com.itsolution.tkbr.repository.CommandeLigneRepository;
import com.itsolution.tkbr.repository.CommandeRepository;
import com.itsolution.tkbr.repository.FournisseurRepository;
import com.itsolution.tkbr.repository.ProduitFournisseurRepository;
import java.time.LocalTime;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class CommandeService {

    @Autowired
    ProduitFournisseurRepository produitFournisseurRepository;
    @Autowired
    MouvementStockService mouvementStockService;

    @Autowired
    EntrepotService entrepotService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ProduitFournisseurRepository ProduitFournisseurRepository;

    @Autowired
    FournisseurRepository fournisseurRepository;
    
    @Autowired
    CommandeLigneRepository commandeLigneRepository;

    public Commande create(Commande commande) throws Exception {

        switch (commande.getType()) {
            case ACHAT: {
                if (EtatCommande.BON_LIVRAISON.equals(commande.getEtat())) {
                    //mouvement stock
                    for (CommandeLigne cl : commandeLigneRepository.findByCommandeId(commande.getId())) {
                        MouvementStock ms = new MouvementStock();
                        ms.setDateTransaction(commande.getDateEmission().atTime(LocalTime.now()).atZone(ZoneId.systemDefault()));
                        ms.setQuantite(cl.getQuantite());
                        ms.setProduit(cl.getProduit());

                        ms.setMotifTransaction(EntrepotType.ACHAT.name());
                        //set price
                        ProduitFournisseur pf = produitFournisseurRepository.findByFournisseurAndProduit(commande.getFournisseur(), cl.getProduit());
                        cl.setPrixUnitaire(pf == null ? cl.getProduit().getPrix() : pf.getPrixVente());
                        ms.setEntrepotDepart(entrepotService.findByType(EntrepotType.FOURNISSEURS));
                        ms.setEntrepotDestination(entrepotService.findByType(EntrepotType.ACHAT));

                        mouvementStockService.save(ms, false);
                    }
                }

                //set client 
                Client c = clientRepository.findByNom(applicationProperties.getTkbr().getNom());
                if (c == null) {
                    c = new Client();
                    c.setNom(applicationProperties.getTkbr().getNom());
                    c.setTelephonePortable(applicationProperties.getTkbr().getTelephonePortable());
                    c.setAdresse(applicationProperties.getTkbr().getAdresse());
                    clientRepository.save(c);
                }

                commande.setClient(c);

                break;
            }
            case VENTE: {

                if (EtatCommande.BON_COMMANDE.equals(commande.getEtat())) {
                    //mouvement stock
                    for (CommandeLigne cl : commandeLigneRepository.findByCommandeId(commande.getId())) {
                        MouvementStock ms = new MouvementStock();
                        ms.setDateTransaction(commande.getDateEmission().atTime(LocalTime.now()).atZone(ZoneId.systemDefault()));
                        ms.setQuantite(cl.getQuantite());
                        ms.setProduit(cl.getProduit());

                        ms.setEntrepotDepart(entrepotService.findByType(EntrepotType.ACHAT));
                        ms.setEntrepotDestination(entrepotService.findByType(EntrepotType.VENTE));
                        ms.setMotifTransaction(EntrepotType.VENTE.name());

                        mouvementStockService.save(ms, true);
                    }
                }

                //set client 
                Fournisseur c = fournisseurRepository.findByNom(applicationProperties.getTkbr().getNom());
                if (c == null) {
                    c = new Fournisseur();
                    c.setNom(applicationProperties.getTkbr().getNom());
                    c.setTelephonePortable(applicationProperties.getTkbr().getTelephonePortable());
                    c.setAdresse(applicationProperties.getTkbr().getAdresse());
                    fournisseurRepository.save(c);
                }

                commande.setFournisseur(c);
                break;
            }
            default: {

            }
        }

        return commandeRepository.save(commande);
    }
    public Commande update(Commande commande) throws Exception {

        switch (commande.getType()) {
            case ACHAT: {
                if (EtatCommande.BON_LIVRAISON.equals(commande.getEtat())) {
                    //mouvement stock
                    for (CommandeLigne cl : commande.getCommandeLignes()) {
                        MouvementStock ms = new MouvementStock();
                        ms.setDateTransaction(commande.getDateEmission().atTime(LocalTime.now()).atZone(ZoneId.systemDefault()));
                        ms.setQuantite(cl.getQuantite());
                        ms.setProduit(cl.getProduit());

                        ms.setMotifTransaction(EntrepotType.ACHAT.name());
                        //set price
                        ProduitFournisseur pf = produitFournisseurRepository.findByFournisseurAndProduit(commande.getFournisseur(), cl.getProduit());
                        cl.setPrixUnitaire(pf == null ? cl.getProduit().getPrix() : pf.getPrixVente());
                        ms.setEntrepotDepart(entrepotService.findByType(EntrepotType.FOURNISSEURS));
                        ms.setEntrepotDestination(entrepotService.findByType(EntrepotType.ACHAT));

                        mouvementStockService.save(ms, false);
                    }
                }

              
                break;
            }
            case VENTE: {

                if (EtatCommande.BON_COMMANDE.equals(commande.getEtat())) {
                    //mouvement stock
                    for (CommandeLigne cl : commande.getCommandeLignes()) {
                        MouvementStock ms = new MouvementStock();
                        ms.setDateTransaction(commande.getDateEmission().atTime(LocalTime.now()).atZone(ZoneId.systemDefault()));
                        ms.setQuantite(cl.getQuantite());
                        ms.setProduit(cl.getProduit());

                        ms.setEntrepotDepart(entrepotService.findByType(EntrepotType.ACHAT));
                        ms.setEntrepotDestination(entrepotService.findByType(EntrepotType.VENTE));
                        ms.setMotifTransaction(EntrepotType.VENTE.name());

                        mouvementStockService.save(ms, true);
                    }
                }

               
                break;
            }
            default: {

            }
        }

        return commandeRepository.save(commande);
    }
}
