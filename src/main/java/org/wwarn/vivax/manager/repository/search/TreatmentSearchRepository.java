package org.wwarn.vivax.manager.repository.search;

import org.wwarn.vivax.manager.domain.Treatment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Treatment entity.
 */
public interface TreatmentSearchRepository extends ElasticsearchRepository<Treatment, Long> {
}
