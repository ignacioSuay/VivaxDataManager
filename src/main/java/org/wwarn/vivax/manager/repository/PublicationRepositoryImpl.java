package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;
import org.wwarn.vivax.manager.web.rest.dto.StudyDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by steven on 03/05/16.
 * Implementation of the custom repository
 */
public class PublicationRepositoryImpl implements PublicationRepositoryCustom {

    Publication publication = new Publication();

    @PersistenceContext
    private EntityManager em;

    /**
     * This method creates a Typed Query which will collect a publication
     * and all its eager relationships and their one collections
     * after that it will put all data in the DTOs created for such purpose and send
     * them to the client side
     * @param pubMedId
     * @return
     */
    @Override
    public FormResourceDTO retrievePublicationByPubMedId(Integer pubMedId) {

        final String QUERY = " SELECT p FROM Publication p" +
            " LEFT JOIN FETCH p.studies stu" +
            " LEFT JOIN FETCH stu.publications pub" +
            " LEFT JOIN FETCH stu.siteDatas site" +
            " LEFT JOIN FETCH site.treatments tre" +
            " where p.pubMedId = " + pubMedId;

        Query q1 = em.createQuery(QUERY);

        publication = (Publication) q1.getSingleResult();

        List<StudyDTO> studyDTOList = new ArrayList<>();
        Set<Study> studyList;
        FormResourceDTO formResourceDTO = new FormResourceDTO();

        formResourceDTO.setPublication(publication);
        studyList = publication.getStudies();

        studyList.stream().forEach(sg -> {
            StudyDTO studyDTO = new StudyDTO();
            studyDTO.setStudyDetails(sg);
            studyDTO.setSiteDatas(sg.getSiteDatas());
            studyDTOList.add(studyDTO);
        });
        formResourceDTO.setStudyDTOList(studyDTOList);

        return formResourceDTO;
    }
}
