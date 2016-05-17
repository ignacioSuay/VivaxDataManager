package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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

        List<Set<SiteData>> propertyFillerSiteData = new ArrayList<Set<SiteData>>();
        List<Set<Treatment>> propertyFillerTreatment = new ArrayList<Set<Treatment>>();

        FormResourceDTO formResourceDTO = new FormResourceDTO();
        formResourceDTO.setPublication(publication);
        formResourceDTO.setStudies(publication.getStudies());
        formResourceDTO.getStudies().stream().forEach(sg ->{
            propertyFillerSiteData.add(sg.getSiteDatas());
        });
        formResourceDTO.setSiteDatas(propertyFillerSiteData);
        formResourceDTO.getSiteDatas().stream().forEach(sd ->{
            sd.stream().forEach(sf ->{
                propertyFillerTreatment.add(sf.getTreatments());
            });
        });
        formResourceDTO.setTreatments(propertyFillerTreatment);

        return formResourceDTO;
    }

    @Override
    @Transactional
    public FormResourceDTO updatePublicationAndAllCollections(FormResourceDTO formResourceDTO) {
        System.out.println(formResourceDTO.getPublication().getPubMedId());
        formResourceDTO.getSiteDatas().stream().forEach(sd ->{
            sd.stream().forEach(sf ->{
               SiteData siteData = em.find(SiteData.class, sf.getId());
               if(siteData!=null && !siteData.getTreatments().equals(sf.getTreatments())){
                   siteData.setTreatments(sf.getTreatments());
                 }
            });
        });
        formResourceDTO.getStudies().stream().forEach(sf ->{
            Study study = em.find(Study.class, sf.getId());
            if(study!=null && study.equals(study)) {
                System.out.println("PERSISTING");
            }
        });
        return null;
    }
}
