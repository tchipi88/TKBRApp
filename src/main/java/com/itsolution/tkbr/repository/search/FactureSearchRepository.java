/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Facture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Facture entity.
 */
public interface FactureSearchRepository extends ElasticsearchRepository<Facture, Long> {

    

}




