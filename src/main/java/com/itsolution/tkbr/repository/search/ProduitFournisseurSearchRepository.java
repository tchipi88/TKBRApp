package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.ProduitFournisseur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProduitFournisseur entity.
 */
public interface ProduitFournisseurSearchRepository extends ElasticsearchRepository<ProduitFournisseur, Long> {
}
