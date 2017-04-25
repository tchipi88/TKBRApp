/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import com.itsolution.tkbr.repository.CompteAnalytiqueClientRepository;
import com.itsolution.tkbr.repository.search.CompteAnalytiqueClientSearchRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CompteAnalytiqueClientService {

    @Autowired
    CompteAnalytiqueClientRepository compteAnalytiqueClientRepository;
    @Autowired
    CompteAnalytiqueClientSearchRepository compteAnalytiqueClientSearchRepository;

    public CompteAnalytiqueClient getCompteClient(Client client) throws Exception {
        CompteAnalytiqueClient compteClient = compteAnalytiqueClientRepository.findByIntitule(client.getNom());
        if (compteClient == null) {
            compteClient = new CompteAnalytiqueClient();
            compteClient.setIntitule(client.getNom());
            compteClient.setClient(client);
            compteClient.setCredit(BigDecimal.ZERO);
            compteClient.setDebit(BigDecimal.ZERO);
        }

        return compteClient;
    }

    public CompteAnalytiqueClient save(CompteAnalytiqueClient compteAnalytiqueClient) throws Exception {
        compteAnalytiqueClient = compteAnalytiqueClientRepository.save(compteAnalytiqueClient);
        compteAnalytiqueClientSearchRepository.save(compteAnalytiqueClient);
        return compteAnalytiqueClient;
    }
}
