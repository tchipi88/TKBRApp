/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Achat;
import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Reglement;
import com.itsolution.tkbr.domain.Vente;
import com.itsolution.tkbr.repository.CompteRepository;
import com.itsolution.tkbr.repository.ReglementRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
public class ReglementService {


    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    CompteService  cs;

    @Transactional
    public Reglement save(Reglement r) throws Exception {
        
        if(r.getId()!=null) throw new Exception("Mise à jour des reglements interdites");
        
        BigDecimal totalttc = r.getMontant();
        BigDecimal totalht = r.getMontant().divide(new BigDecimal(1.1925),2,RoundingMode.HALF_UP);
        BigDecimal tva = totalttc.subtract(totalht);
        
        if (r.getCommande() instanceof Vente) {

            Compte compteClient = cs.getCompteClient();
            Compte compteVente = cs.getCompteVente();
            Compte compteTVACollecte = cs.getCompteTVACollecte();

            compteClient.setDebit(totalttc.add(compteClient.getDebit()));
            compteVente.setCredit(totalht.add(compteVente.getCredit()));
            compteTVACollecte.setCredit(tva.add(compteTVACollecte.getCredit()));

            compteRepository.save(compteClient);
            compteRepository.save(compteVente);
            compteRepository.save(compteTVACollecte);

        }
        if (r.getCommande() instanceof Achat) {

            Compte compteAchat = cs.getCompteAchat();
            Compte compteFournisseurs = cs.getCompteFournisseurs();
            Compte compteTVADeductible = cs.getCompteTVADeductible();

            compteAchat.setDebit(totalht.add(compteAchat.getDebit()));
            compteFournisseurs.setCredit(totalttc.add(compteFournisseurs.getCredit()));
            compteTVADeductible.setDebit(tva.add(compteTVADeductible.getDebit()));

            compteRepository.save(compteAchat);
            compteRepository.save(compteFournisseurs);
            compteRepository.save(compteTVADeductible);
        }

        return reglementRepository.save(r);
    }

}
