package com.itsolution.tkbr.repository.search;

import com.itsolution.tkbr.domain.CommandeLigne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommandeLigne entity.
 */
public interface CommandeLigneSearchRepository extends ElasticsearchRepository<CommandeLigne, Long> {
}
