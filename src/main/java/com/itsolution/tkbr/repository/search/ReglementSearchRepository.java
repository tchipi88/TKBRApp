/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Reglement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Reglement entity.
 */
public interface ReglementSearchRepository extends ElasticsearchRepository<Reglement, Long> {

    

}




