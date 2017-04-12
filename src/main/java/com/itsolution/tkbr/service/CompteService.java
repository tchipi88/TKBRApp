/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.repository.CompteRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CompteService {

    @Autowired
    CompteRepository compteRepository;

    public Compte getCompte(Integer numcompte, String intitule) throws Exception {
        Compte c = compteRepository.findOne(numcompte);
        if (c == null) {
            c = new Compte();
            c.setId(numcompte);
            c.setIntitule(intitule);
            c.setCredit(BigDecimal.ZERO);
            c.setDebit(BigDecimal.ZERO);
            return compteRepository.save(c);
        }
        return c;
    }

    public Compte getCompteClient() throws Exception {
        return getCompte(411, "Client");
    }

    public Compte getCompteVente() throws Exception {
        return getCompte(70, "Vente");
    }

    public Compte getCompteTVACollecte() throws Exception {
        return getCompte(44571, "TVA collectée");
    }

    public Compte getCompteAchat() throws Exception {
        return getCompte(60, "Achat");
    }

    public Compte getCompteTVADeductible() throws Exception {
        return getCompte(44566, "TVA déductible");
    }

    public Compte getCompteFournisseurs() throws Exception {
        return getCompte(401, "Fournisseurs");
    }

}
