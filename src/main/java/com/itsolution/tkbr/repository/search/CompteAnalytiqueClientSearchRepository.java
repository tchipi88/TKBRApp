/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Client entity.
 */
public interface CompteAnalytiqueClientSearchRepository extends ElasticsearchRepository<CompteAnalytiqueClient, Long> {

    

}



