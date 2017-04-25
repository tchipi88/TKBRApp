/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CompteAnalytiqueFournisseur;
import com.itsolution.tkbr.domain.Fournisseur;
import com.itsolution.tkbr.repository.CompteAnalytiqueFournisseurRepository;
import com.itsolution.tkbr.repository.search.CompteAnalytiqueFournisseurSearchRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CompteAnalytiqueFournisseurService {

    @Autowired
    CompteAnalytiqueFournisseurRepository compteAnalytiqueFournisseurRepository;
    @Autowired
    CompteAnalytiqueFournisseurSearchRepository compteAnalytiqueFournisseurSearchRepository;

    public CompteAnalytiqueFournisseur getCompteFournisseur(Fournisseur fournisseur) throws Exception {
        CompteAnalytiqueFournisseur compteFournisseur = compteAnalytiqueFournisseurRepository.findByIntitule(fournisseur.getNom());
        if (compteFournisseur == null) {
            compteFournisseur = new CompteAnalytiqueFournisseur();
            compteFournisseur.setIntitule(fournisseur.getNom());
            compteFournisseur.setFournisseur(fournisseur);
            compteFournisseur.setCredit(BigDecimal.ZERO);
            compteFournisseur.setDebit(BigDecimal.ZERO);
        }

        return compteFournisseur;
    }

    public CompteAnalytiqueFournisseur save(CompteAnalytiqueFournisseur compteAnalytiqueFournisseur) throws Exception {
        compteAnalytiqueFournisseur = compteAnalytiqueFournisseurRepository.save(compteAnalytiqueFournisseur);
        compteAnalytiqueFournisseurSearchRepository.save(compteAnalytiqueFournisseur);
        return compteAnalytiqueFournisseur;
    }
}
