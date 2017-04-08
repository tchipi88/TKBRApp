package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Unite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Unite entity.
 */
public interface UniteSearchRepository extends ElasticsearchRepository<Unite, Long> {
}
