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
 * Service Implementation for managing SiteData.
 */
@Service
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;

    @Transactional(readOnly = true)
    public Treatment findOneWithSiteDatas(Long id) {
        log.debug("Request to get SiteData : {}", id);
        Treatment treatment = treatmentRepository.findOneTreatmentWithSiteDataRelationships(id);
        return treatment;
    }

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
}
