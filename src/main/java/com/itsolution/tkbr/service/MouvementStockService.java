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
import com.itsolution.tkbr.service.dto.MouvementStockSearch;
import java.time.LocalDate;
import java.util.List;
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
    
    @Transactional
    public MouvementStock save(MouvementStock ms, boolean verifstockfournisseur) throws Exception {
        if (ms.getId() != null) {
            throw new Exception("Mise à jour des mouvements stocks interdites");
        }
        EntrepotProduit ep = entrepotProduitRepository.findByProduitAndEntrepot(ms.getProduit(), ms.getEntrepotDepart());
        if (ep == null) {
            ep = new EntrepotProduit();
            ep.setProduit(ms.getProduit());
            ep.setEntrepot(ms.getEntrepotDepart());
            ep.setStockTheorique(Float.valueOf("0"));
            ep.setSeuilAlerte(Float.valueOf("0"));
            ep.setSeuilSurStockage(Float.valueOf("0"));
            
        } else if (verifstockfournisseur && ep.getStockTheorique() < ms.getQuantite()) {
            throw new Exception("Qte " + ms.getProduit().getDenomination() + " à sortir excède la quantité disponible dans le stock!!!");
        }
        
        ms.setStockEntrepotDepart(ep.getStockTheorique());
        ep.setStockTheorique(ep.getStockTheorique() - ms.getQuantite());
        entrepotProduitRepository.save(ep);
        
        EntrepotProduit ep1 = entrepotProduitRepository.findByProduitAndEntrepot(ms.getProduit(), ms.getEntrepotDestination());
        if (ep1 == null) {
            ep1 = new EntrepotProduit();
            ep1.setProduit(ms.getProduit());
            ep1.setEntrepot(ms.getEntrepotDestination());
            ep1.setStockTheorique(Float.valueOf("0"));
            ep1.setSeuilAlerte(Float.valueOf("0"));
            ep1.setSeuilSurStockage(Float.valueOf("0"));
            
        }
        
        ms.setStockEntrepotDestination(ep1.getStockTheorique());
        ep1.setStockTheorique(ep.getStockTheorique()+ms.getQuantite());
        entrepotProduitRepository.save(ep1);
        
        return mouvementStockRepository.save(ms);
    }
    
    public List<MouvementStock> search(MouvementStockSearch mouvementStockSearch) throws Exception {

        //  if (mouvementStockSearch.getProduit() == null) 
        {
            return mouvementStockRepository.findByDateTransactionAfterAndDateTransactionBefore(mouvementStockSearch.getDateDebut(), mouvementStockSearch.getDateFin());
        }
        
    }
}
