package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.MouvementStock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MouvementStock entity.
 */
public interface MouvementStockSearchRepository extends ElasticsearchRepository<MouvementStock, Long> {
}
