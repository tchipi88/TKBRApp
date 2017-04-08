package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.PvReception;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PvReception entity.
 */
public interface PvReceptionSearchRepository extends ElasticsearchRepository<PvReception, Long> {
}
