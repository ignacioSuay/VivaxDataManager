package org.wwarn.vivax.manager.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.wwarn.vivax.manager.domain.Category;
import org.wwarn.vivax.manager.domain.Location;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.domain.util.Filter;
import org.wwarn.vivax.manager.web.rest.dto.SiteDataViewDTO;

public class SiteDataRepositoryImpl implements SiteDataRepositoryCustom{
	
	Study s = new Study();
	long id=0;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List <SiteDataViewDTO> searchSiteDataByFilter(List<Filter> listFilters) {
		TypedQuery<SiteData> query = buildQuery(listFilters);
		Hibernate.initialize(s.getPublicationss());
		int count=0;
		for (Filter filter: listFilters){
			if("country".equals(filter.getName()))
                query.setParameter(filter.getName(), filter.getQuery());
            else if("category".equals(filter.getName()))
                query.setParameter(filter.getName(), filter.getQuery());
            else if("studyRef".equals(filter.getName()))
            	query.setParameter(filter.getName(), filter.getQuery());
            else if("studyType".equals(filter.getName()))
            	query.setParameter(filter.getName(), filter.getQuery());
            else if("pubMedId".equals(filter.getName()))
            	query.setParameter(filter.getName(), Integer.valueOf(filter.getQuery()));
            count++;
        }
		
        List<SiteData> siteDataList = query.getResultList();
     
        List<SiteDataViewDTO> siteDataViewDTOList=new ArrayList<SiteDataViewDTO>();
        
        //filling SiteDataViewDTO list
        for (SiteData siteData : siteDataList) {
            
            SiteDataViewDTO temp = new SiteDataViewDTO();
            List<Integer> tempPubMedList = new ArrayList<Integer>();
            List<String> tempTreatmentList = new ArrayList<String>();    
            temp.setId(id);
            temp.setTypeStudy(siteData.getTypeStudy());
            temp.setRef(siteData.getStudy().getRef());
            temp.setLocation(siteData.getLocation());
            temp.setCategory(siteData.getCategory().getName());
            temp.setUpper95CI(siteData.getUpper95CI());
            temp.setYearStart(siteData.getYearStart());
            temp.setYearEnd(siteData.getYearEnd());
            temp.setListPublications(siteData.getStudy().getPublicationss());
            temp.setListTreatments(siteData.getTreatments());
            siteData.getStudy().getPublicationss().stream().forEach(sg ->{
                tempPubMedList.add(sg.getPubMedId());
            });
            temp.setListPubMedIds(tempPubMedList);
            siteData.getTreatments().stream().forEach(sh ->{
                tempTreatmentList.add(sh.getTreatmentName());
            });
            temp.setListTreatmentArmCodes(tempTreatmentList);

            siteDataViewDTOList.add(temp);
            id++;
     	};
        return siteDataViewDTOList;
	}
	
	private TypedQuery<SiteData> buildQuery(List<Filter> listFilters){
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SiteData> cq = cb.createQuery(SiteData.class);
        Root<SiteData> siteData = cq.from(SiteData.class);
        Join<SiteData, Category> cat = siteData.join("category", JoinType.LEFT);
        Join<SiteData, Location> loc = siteData.join("location",JoinType.LEFT);
        Join<SiteData, Treatment> tre = siteData.join("treatments",JoinType.LEFT);
        Join<SiteData, Study> stu = siteData.join("study",JoinType.LEFT);
        Join<Study, Publication> pub = stu.join("publications",JoinType.LEFT);
        siteData.fetch("study").fetch("publications");
        
        List<Predicate> predicates = new ArrayList<>();
        int index = 0;
        
        for(Filter filter : listFilters){
            if("country".equals(filter.getName()))
                predicates.add(cb.equal(loc.get("country"), cb.parameter(String.class, filter.getName())));
            else if("category".equals(filter.getName()))
                predicates.add(cb.equal(cat.get("name"), cb.parameter(String.class, filter.getName())));
            else if("studyRef".equals(filter.getName()))
                predicates.add(cb.equal(stu.get("ref"), cb.parameter(String.class, filter.getName())));
            else if("studyType".equals(filter.getName()))
                predicates.add(cb.equal(stu.get("studyType"), cb.parameter(String.class, filter.getName())));
            else if("pubMedId".equals(filter.getName()))
                predicates.add(cb.equal(pub.get("pubMedId"), cb.parameter(Integer.class, filter.getName())));
            index++;
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cq);
	}
}	