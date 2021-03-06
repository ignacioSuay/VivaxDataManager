package org.wwarn.vivax.manager.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.repository.TreatmentRepository;
import org.wwarn.vivax.manager.repository.search.SiteDataSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.wwarn.vivax.manager.repository.search.TreatmentSearchRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Treatments.
 */
@Service
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    @Inject
    private SiteDataRepository siteDataRepository;

    @Inject
    private SiteDataSearchRepository siteDataSearchRepository;

    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;

    /**
     * Transactional method which will delete the treatment entity
     * from the database, but will also enter the collections
     * of any siteData containing this treatment and delete it
     *
     * @param id the id of the treatment we are going to delete
     */
    @Transactional
    public void deleteTreatment(Long id){
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
    }

    /**
     * Transactional method which will save the treatment entity
     * in the database, but will also enter the collections
     * of any particular siteData linked to this treatment and it
     * will save it there too
     * @param treatment the Treatment object that we want to save
     * @param siteDataId if the method is called from the upload Study form, the
     *                   treatment will be directly linked to a particular siteData
     */
    @Transactional
    public Treatment saveTreatment(Treatment treatment, Long siteDataId){

        if (siteDataId != null){
            SiteData siteData = siteDataRepository.findOne(siteDataId);
            treatment.getSiteDatas().add(siteData);
            siteData.getTreatments().add(treatment);
        }

        treatmentRepository.flush();
        treatmentRepository.save(treatment);
        treatmentSearchRepository.save(treatment);

        return treatment;
    }
}
