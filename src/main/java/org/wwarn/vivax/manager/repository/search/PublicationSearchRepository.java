package org.wwarn.vivax.manager.repository.search;

import org.wwarn.vivax.manager.domain.Publication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Publication entity.
 */
public interface PublicationSearchRepository extends ElasticsearchRepository<Publication, Long> {
}
