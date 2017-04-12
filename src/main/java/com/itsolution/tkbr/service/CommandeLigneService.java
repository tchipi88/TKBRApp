/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Achat;
import com.itsolution.tkbr.domain.CommandeLigne;
import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.domain.ProduitFournisseur;
import com.itsolution.tkbr.domain.Vente;
import com.itsolution.tkbr.domain.enumeration.EntrepotType;
import com.itsolution.tkbr.repository.CommandeLigneRepository;
import com.itsolution.tkbr.repository.EntrepotRepository;
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
public class CommandeLigneService {

    @Autowired
    ProduitFournisseurRepository ProduitFournisseurRepository;
    @Autowired
    MouvementStockService MouvementStockService;

    @Autowired
    EntrepotRepository EntrepotRepository;
    @Autowired
    CommandeLigneRepository CommandeLigneRepository;

    @Transactional
    public CommandeLigne save(CommandeLigne cl) throws Exception {

        //mouvement stock
        MouvementStock ms = new MouvementStock();
        ms.setDateTransaction(cl.getCommande().getDateEmission().atTime(LocalTime.now()).atZone(ZoneId.systemDefault()));
        ms.setQuantite(cl.getQuantite());
        ms.setProduit(cl.getProduit());

        if (cl.getCommande() instanceof Vente) {
            ms.setEntrepotDepart(EntrepotRepository.findByType(EntrepotType.PRODUCTION));
            ms.setEntrepotDestination(EntrepotRepository.findByType(EntrepotType.VENTE));
            ms.setMotifTransaction(EntrepotType.VENTE.name());
        }
        if (cl.getCommande() instanceof Achat) {
            ms.setMotifTransaction(EntrepotType.ACHAT.name());
            //set price
            ProduitFournisseur pf = ProduitFournisseurRepository.findByFournisseurAndProduit(((Achat)cl.getCommande()).getFournisseur(), cl.getProduit());
            cl.setPrixUnitaire(pf == null ? cl.getProduit().getPrix() : pf.getPrixVente());
            ms.setEntrepotDepart(EntrepotRepository.findByType(EntrepotType.FOURNISSEURS));
            ms.setEntrepotDestination(EntrepotRepository.findByType(EntrepotType.ACHAT));
        }

        MouvementStockService.createMvtStock(ms, cl.getCommande() instanceof Vente);
        CommandeLigneRepository.save(cl);
        return cl;
    }

}
