/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.BonLivraison;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the BonLivraison entity.
 */
public interface BonLivraisonSearchRepository extends ElasticsearchRepository<BonLivraison, Long> {

    

}



