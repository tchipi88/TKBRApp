/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.EntrepotProduit;
import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.repository.EntrepotProduitRepository;
import com.itsolution.tkbr.repository.MouvementStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
public class MouvementStockService {

    @Autowired
    MouvementStockRepository mouvementStockRepository;
    @Autowired
    EntrepotProduitRepository entrepotProduitRepository;

    public MouvementStock save( MouvementStock ms) throws Exception {
        createMvtStock(ms, true);
        return ms;
    }

    public MouvementStock update( MouvementStock ms) throws Exception {
        throw new Exception("Mise à jour des mouvements stocks interdites");
    }

    @Transactional
    public void createMvtStock(MouvementStock ms, boolean verifstockfournisseur) throws Exception {
        EntrepotProduit ep = entrepotProduitRepository.findByProduitAndEntrepot(ms.getProduit(), ms.getEntrepotDepart());
        if (ep == null) {
            ep = new EntrepotProduit();
            ep.setProduit(ms.getProduit());
            ep.setEntrepot(ms.getEntrepotDepart());
            ep.setStockTheorique(Float.valueOf("0"));
            ep.setSeuilAlerte(Float.valueOf("0"));
            entrepotProduitRepository.save(ep);
        } else if (verifstockfournisseur && ep.getStockTheorique() < ms.getQuantite()) {
            throw new Exception("Quantité sortie excède la quantité disponible dans le stock!!!");
        }

        ms.setStockEntrepotDepart(ep.getStockTheorique());

        EntrepotProduit ep1 = entrepotProduitRepository.findByProduitAndEntrepot(ms.getProduit(), ms.getEntrepotDestination());
        if (ep1 == null) {
            ep1 = new EntrepotProduit();
            ep1.setProduit(ms.getProduit());
            ep1.setEntrepot(ms.getEntrepotDestination());
            ep1.setStockTheorique(Float.valueOf("0"));
            ep1.setSeuilAlerte(Float.valueOf("0"));
            entrepotProduitRepository.save(ep1);
        }

        ms.setStockEntrepotDestination(ep1.getStockTheorique());
        mouvementStockRepository.save(ms);
    }

}
