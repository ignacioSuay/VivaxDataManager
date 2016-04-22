package org.wwarn.vivax.manager.repository.search;

import org.wwarn.vivax.manager.domain.Study;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Study entity.
 */
public interface StudySearchRepository extends ElasticsearchRepository<Study, Long> {
}
