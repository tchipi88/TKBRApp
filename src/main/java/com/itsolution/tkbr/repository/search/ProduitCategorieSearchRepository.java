package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.ProduitCategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProduitCategorie entity.
 */
public interface ProduitCategorieSearchRepository extends ElasticsearchRepository<ProduitCategorie, Long> {
}
