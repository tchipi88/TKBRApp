/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CompteAnalytique;
import com.itsolution.tkbr.domain.Tiers;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itsolution.tkbr.repository.CompteAnalytiqueRepository;
import com.itsolution.tkbr.repository.search.CompteAnalytiqueSearchRepository;

/**
 *
 * @author tchipi
 */
@Service
public class CompteAnalytiqueService {

    @Autowired
    CompteAnalytiqueRepository compteAnalytiqueClientRepository;
    @Autowired
    CompteAnalytiqueSearchRepository compteAnalytiqueClientSearchRepository;

    public CompteAnalytique getCompteTiers(Tiers tiers,CompteAnalytiqueType type) throws Exception {
        CompteAnalytique compteClient = compteAnalytiqueClientRepository.findByIntituleAndType(tiers.getNom(),type);
        if (compteClient == null) {
            compteClient = new CompteAnalytique();
            compteClient.setIntitule(tiers.getNom());
            compteClient.setType(type);
            compteClient.setTiers(tiers);
            compteClient.setCredit(BigDecimal.ZERO);
            compteClient.setDebit(BigDecimal.ZERO);
        }

        return compteClient;
    }

    public CompteAnalytique save(CompteAnalytique compteAnalytiqueClient) throws Exception {
        compteAnalytiqueClient = compteAnalytiqueClientRepository.save(compteAnalytiqueClient);
        compteAnalytiqueClientSearchRepository.save(compteAnalytiqueClient);
        return compteAnalytiqueClient;
    }
}
