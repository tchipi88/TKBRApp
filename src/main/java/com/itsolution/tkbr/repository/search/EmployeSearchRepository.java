package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.Employe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Employe entity.
 */
public interface EmployeSearchRepository extends ElasticsearchRepository<Employe, Long> {
}
