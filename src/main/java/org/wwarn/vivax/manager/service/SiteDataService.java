package org.wwarn.vivax.manager.service;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.repository.search.SiteDataSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiteData.
 */
@Service
@Transactional
public class SiteDataService {

    private final Logger log = LoggerFactory.getLogger(SiteDataService.class);

    @Inject
    private SiteDataRepository siteDataRepository;

    @Inject
    private SiteDataSearchRepository siteDataSearchRepository;

    /**
     * Save a siteData.
     *
     * @param siteData the entity to save
     * @return the persisted entity
     */
    public SiteData save(SiteData siteData) {
        log.debug("Request to save SiteData : {}", siteData);
        SiteData result = siteDataRepository.save(siteData);
        siteDataSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the siteData.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SiteData> findAll(Pageable pageable) {
        log.debug("Request to get all SiteData");
        Page<SiteData> result = siteDataRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one siteData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SiteData findOne(Long id) {
        log.debug("Request to get SiteData : {}", id);
        SiteData siteData = siteDataRepository.findOneWithEagerRelationships(id);
        return siteData;
    }

    /**
     *  Delete the  siteData by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteData : {}", id);
        siteDataRepository.delete(id);
        siteDataSearchRepository.delete(id);
    }

    /**
     * Search for the siteData corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SiteData> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiteData for query {}", query);
        return siteDataSearchRepository.search(queryStringQuery(query), pageable);
    }

    @Transactional
    public void deleteSiteData(Long id){
        SiteData siteData = siteDataRepository.findOne(id);
        for (Treatment treatment : siteData.getTreatments()) {
            Set<SiteData> setSite = treatment.getSiteDatas();
            setSite.remove(siteData);
            treatment.setSiteDatas(setSite);
        }
        siteData.setTreatments(null);
        siteDataRepository.flush();
        siteDataRepository.delete(id);
        siteDataSearchRepository.delete(id);
    }
}
