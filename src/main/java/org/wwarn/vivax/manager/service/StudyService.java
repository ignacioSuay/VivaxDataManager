package org.wwarn.vivax.manager.service;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.repository.StudyRepository;
import org.wwarn.vivax.manager.repository.search.StudySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.wwarn.vivax.manager.web.rest.dto.StudyDTO;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Study.
 */
@Service
@Transactional
public class StudyService {

    private final Logger log = LoggerFactory.getLogger(StudyService.class);

    @Inject
    private StudyRepository studyRepository;

    @Inject
    private StudySearchRepository studySearchRepository;

    /**
     * Save a study.
     *
     * @param study the entity to save
     * @return the persisted entity
     */
    public Study save(Study study) {
        log.debug("Request to save Study : {}", study);
        Study result = studyRepository.save(study);
        studySearchRepository.save(result);
        return result;
    }

    /**
     * Save a study returning a studyDTO entity.
     *
     * @param study the entity to save
     * @return the persisted entity
     */
    public StudyDTO saveReturnDTO(Study study) {
        log.debug("Request to save Study : {}", study);
        Study result = studyRepository.save(study);
        Set<SiteData>siteDatas = new HashSet<>();
        studySearchRepository.save(result);
        StudyDTO studyDTO = new StudyDTO();
        studyDTO.setStudyDetails(study);
        studyDTO.setSiteDatas(siteDatas);
        return studyDTO;
    }

    /**
     *  Get all the studies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Study> findAll(Pageable pageable) {
        log.debug("Request to get all Studies");
        Page<Study> result = studyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one study by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Study findOne(Long id) {
        log.debug("Request to get Study : {}", id);
        Study study = studyRepository.findOneWithEagerRelationships(id);
        return study;
    }

    /**
     *  Delete the  study by id.
     *  This method also removes all references to it
     *  in any publications studies collection
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Study : {}", id);
        Study study = studyRepository.findOne(id);
        for (Publication publication : study.getPublicationss()) {
            Set<Study> setStu = publication.getStudies();
            setStu.remove(study);
            publication.setStudies(setStu);
        }
        study.setPublicationss(null);
        studyRepository.flush();
        studyRepository.delete(id);
        studySearchRepository.delete(id);
    }

    /**
     * Search for the study corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Study> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Studies for query {}", query);
        return studySearchRepository.search(queryStringQuery(query), pageable);
    }

    /**
     * Search for the study corresponding to the query.
     *  @return the list of types
     */
    @Transactional(readOnly = true)
    public List<String> findStudyTypes() {
        log.debug("Request to search for all study types");
        return studyRepository.findStudyTypes();
    }
}
