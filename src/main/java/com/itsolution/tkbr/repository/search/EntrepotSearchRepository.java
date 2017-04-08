package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Entrepot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entrepot entity.
 */
public interface EntrepotSearchRepository extends ElasticsearchRepository<Entrepot, Long> {
}
