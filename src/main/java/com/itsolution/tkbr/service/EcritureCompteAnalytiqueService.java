/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CompteAnalytique;
import com.itsolution.tkbr.domain.EcritureCompteAnalytique;
import com.itsolution.tkbr.domain.Tiers;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
import com.itsolution.tkbr.repository.EcritureCompteAnalytiqueRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class EcritureCompteAnalytiqueService {

    @Autowired
    EcritureCompteAnalytiqueRepository ecritureCompteAnalytiqueRepository;
    @Autowired
    CompteAnalytiqueService compteAnalytiqueService;

    public EcritureCompteAnalytique create(Tiers tiers, CompteAnalytiqueType typeCompte, BigDecimal montant, SensEcritureComptable sens, String operation) throws Exception {
        CompteAnalytique compte = compteAnalytiqueService.getCompteTiers(tiers, typeCompte);

        EcritureCompteAnalytique ecriture = new EcritureCompteAnalytique();
        ecriture.setCompte(compte);
        ecriture.setMontant(montant);
        ecriture.setDateEcriture(LocalDateTime.now());
        ecriture.setSensEcriture(sens);
        ecriture.setLibelleOperation(operation);

        if (sens.equals(SensEcritureComptable.C)) {
            compte.setCredit(compte.getCredit().add(montant));
        } else {
            compte.setDebit(compte.getDebit().add(montant));
        }
        compteAnalytiqueService.save(compte);
        return ecritureCompteAnalytiqueRepository.save(ecriture);
    }

}
