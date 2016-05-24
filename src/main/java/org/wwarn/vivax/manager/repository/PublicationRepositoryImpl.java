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
 */
public class PublicationRepositoryImpl implements PublicationRepositoryCustom{

    Publication publication = new Publication();

    @PersistenceContext
    private EntityManager em;

    @Override
    public FormResourceDTO retrievePublicationByPubMedId(Integer pubMedId) {

        final String QUERY= " SELECT p FROM Publication p" +
            " LEFT JOIN FETCH p.studies stu" +
            " LEFT JOIN FETCH stu.publications pub" +
            " LEFT JOIN FETCH stu.siteDatas site" +
            " LEFT JOIN FETCH site.treatments tre" +
            " where p.pubMedId = "+pubMedId;

        Query q1 = em.createQuery(QUERY);

        publication = (Publication)q1.getSingleResult();

        List <StudyDTO>studyDTOList = new ArrayList<>();
        Set<Study>studyList;
        FormResourceDTO formResourceDTO = new FormResourceDTO();

        formResourceDTO.setPublication(publication);
        studyList = publication.getStudies();

        studyList.stream().forEach(sg ->{
            StudyDTO studyDTO = new StudyDTO();
            studyDTO.setStudyDetails(sg);
            studyDTO.setSiteDatas(sg.getSiteDatas());
            studyDTOList.add(studyDTO);
        });
        formResourceDTO.setStudyDTOList(studyDTOList);

        return formResourceDTO;
    }

    /*@Override
    @Transactional
    public FormResourceDTO updatePublicationAndAllCollections(FormResourceDTO formResourceDTO) {
        formResourceDTO.getSiteDatas().stream().forEach(sd ->{
            sd.stream().forEach(sf ->{
               if(sf!=null){
                  em.merge(sf);
                 }
            });
        });
        formResourceDTO.getStudyDetails().stream().forEach(sf ->{
            System.out.println("@@@@@@@@@@@@ "+sf.toString());
            if(sf!=null) {
                em.merge(sf);
            }
        });
        return formResourceDTO;
    }*/
}
