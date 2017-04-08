package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.EmployeFonction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmployeFonction entity.
 */
public interface EmployeFonctionSearchRepository extends ElasticsearchRepository<EmployeFonction, Long> {
}
