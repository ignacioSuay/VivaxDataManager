package org.wwarn.vivax.manager.service;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.repository.search.PublicationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Publication.
 */
@Service
@Transactional
public class PublicationService {

    private final Logger log = LoggerFactory.getLogger(PublicationService.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationSearchRepository publicationSearchRepository;

    /**
     * Save a publication.
     *
     * @param publication the entity to save
     * @return the persisted entity
     */
    public Publication save(Publication publication) {
        log.debug("Request to save Publication : {}", publication);
        Publication result = publicationRepository.save(publication);
        publicationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the publications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Publication> findAll(Pageable pageable) {
        log.debug("Request to get all Publications");
        Page<Publication> result = publicationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one publication by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Publication findOne(Long id) {
        log.debug("Request to get Publication : {}", id);
        Publication publication = publicationRepository.findOne(id);
        return publication;
    }

    /**
     *  Delete the  publication by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Publication : {}", id);
        publicationRepository.delete(id);
        publicationSearchRepository.delete(id);
    }

    /**
     * Search for the publication corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Publication> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Publications for query {}", query);
        return publicationSearchRepository.search(queryStringQuery(query), pageable);
    }

    @Transactional(readOnly = true)
    public Publication findPublicationByPubMedId(Integer pubMedId) {
        log.debug("Request to get Publication : {}", pubMedId);
        Publication publication = publicationRepository.findPublicationByPubMedId(pubMedId);
        return publication;
    }
}
