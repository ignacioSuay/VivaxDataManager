package org.wwarn.vivax.manager.repository.search;

import org.wwarn.vivax.manager.domain.SiteData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SiteData entity.
 */
public interface SiteDataSearchRepository extends ElasticsearchRepository<SiteData, Long> {
}
