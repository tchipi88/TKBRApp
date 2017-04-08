package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Reglement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reglement entity.
 */
public interface ReglementSearchRepository extends ElasticsearchRepository<Reglement, Long> {
}
