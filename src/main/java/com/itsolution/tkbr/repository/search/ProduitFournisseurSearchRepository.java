/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.ProduitFournisseur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ProduitFournisseur entity.
 */
public interface ProduitFournisseurSearchRepository extends ElasticsearchRepository<ProduitFournisseur, Long> {

    

}




