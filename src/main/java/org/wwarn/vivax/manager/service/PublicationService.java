package org.wwarn.vivax.manager.service;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.repository.StudyRepository;
import org.wwarn.vivax.manager.repository.search.PublicationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.wwarn.vivax.manager.repository.search.SiteDataSearchRepository;
import org.wwarn.vivax.manager.repository.search.StudySearchRepository;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;

import javax.inject.Inject;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Publication.
 */
@Service
@Transactional
public class PublicationService {

    private final Logger log = LoggerFactory.getLogger(PublicationService.class);

    @Inject
    private StudyRepository studyRepository;

    @Inject
    private StudySearchRepository studySearchRepository;

    @Inject
    private SiteDataRepository siteDataRepository;

    @Inject
    private SiteDataSearchRepository siteDataSearchRepository;

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

    @Transactional
    public FormResourceDTO updatePublicationAndAllCollections(FormResourceDTO formResourceDTO) {
        formResourceDTO.getStudyDTOList().stream().forEach(sd ->{
            if(sd!=null){
                Study study = sd.getStudyDetails();
                studyRepository.save(study);
                studySearchRepository.save(study);
                studyRepository.flush();
                sd.getSiteDatas().stream().forEach(sf ->{
                    sf.setStudy(study);
                    siteDataRepository.save(sf);
                    siteDataSearchRepository.save(sf);
                    siteDataRepository.flush();
                });
            }
        });
        return formResourceDTO;
    }

    /*public void deleteTreatment(Long id){
        Treatment treatment = treatmentRepository.findOne(id);
        for (SiteData siteData : treatment.getSiteDatas()) {
            Set<Treatment> setTre = siteData.getTreatments();
            setTre.remove(treatment);
            siteData.setTreatments(setTre);
        }
        treatment.setSiteDatas(null);
        treatmentRepository.flush();
        treatmentRepository.delete(id);
        treatmentSearchRepository.delete(id);
    }*/
}
