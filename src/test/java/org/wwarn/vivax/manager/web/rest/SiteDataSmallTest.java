package org.wwarn.vivax.manager.web.rest;

import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.util.Filter;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.service.SiteDataService;
import org.wwarn.vivax.manager.web.rest.dto.SiteDataViewDTO;

import com.codahale.metrics.annotation.Timed;

import org.wwarn.vivax.manager.repository.search.SiteDataSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SiteDataResource REST controller.
 *
 * @see SiteDataResource
 */
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class SiteDataSmallTest {
	
	 private final Logger log = LoggerFactory.getLogger(SiteDataSmallTest.class);

    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";
    private static final String DEFAULT_DAY_28_RECURRENCE = "AAAAA";
    private static final String UPDATED_DAY_28_RECURRENCE = "BBBBB";
    private static final String DEFAULT_LOWEST_95_CI = "AAAAA";
    private static final String UPDATED_LOWEST_95_CI = "BBBBB";

    private static final Integer DEFAULT_NUM_ENROLED = 1;
    private static final Integer UPDATED_NUM_ENROLED = 2;

    private static final Integer DEFAULT_NUM_PATIENTS_TREAT_CQ = 1;
    private static final Integer UPDATED_NUM_PATIENTS_TREAT_CQ = 2;
    private static final String DEFAULT_PRIMAQUINE = "AAAAA";
    private static final String UPDATED_PRIMAQUINE = "BBBBB";
    private static final String DEFAULT_TIME_PRIMAQUINE = "AAAAA";
    private static final String UPDATED_TIME_PRIMAQUINE = "BBBBB";
    private static final String DEFAULT_TYPE_STUDY = "AAAAA";
    private static final String UPDATED_TYPE_STUDY = "BBBBB";
    private static final String DEFAULT_UPPER_95_CI = "AAAAA";
    private static final String UPDATED_UPPER_95_CI = "BBBBB";

    private static final Integer DEFAULT_YEAR_END = 1;
    private static final Integer UPDATED_YEAR_END = 2;

    private static final Integer DEFAULT_YEAR_START = 1;
    private static final Integer UPDATED_YEAR_START = 2;
    
    private static final String COUNTRY = "China";
    private static final String CATEGORY = "Case Report of CQR";
    private static final String STUDY_REF = "Heidari-2012";
    private static final String PUB_MED_ID = "8882190";
    
    
    

    @Inject
    private SiteDataRepository siteDataRepository;
    
    @Inject
    private SiteDataService siteDataService;

    @Inject
    private SiteDataSearchRepository siteDataSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSiteDataMockMvc;

    private SiteData siteData;
    
    private List<Filter>listFilters=new ArrayList<Filter>();

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SiteDataResource siteDataResource = new SiteDataResource();
        ReflectionTestUtils.setField(siteDataResource, "siteDataService", siteDataService);
        this.restSiteDataMockMvc = MockMvcBuilders.standaloneSetup(siteDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        siteDataSearchRepository.deleteAll();
        siteData = new SiteData();
        siteData.setComments(DEFAULT_COMMENTS);
        siteData.setDay28Recurrence(DEFAULT_DAY_28_RECURRENCE);
        siteData.setLowest95CI(DEFAULT_LOWEST_95_CI);
        siteData.setNumEnroled(DEFAULT_NUM_ENROLED);
        siteData.setNumPatientsTreatCQ(DEFAULT_NUM_PATIENTS_TREAT_CQ);
        siteData.setPrimaquine(DEFAULT_PRIMAQUINE);
        siteData.setTimePrimaquine(DEFAULT_TIME_PRIMAQUINE);
        siteData.setTypeStudy(DEFAULT_TYPE_STUDY);
        siteData.setUpper95CI(DEFAULT_UPPER_95_CI);
        siteData.setYearEnd(DEFAULT_YEAR_END);
        siteData.setYearStart(DEFAULT_YEAR_START);
        
        listFilters.add(new Filter("country", COUNTRY));
    	///listFilters.add(new Filter("category", CATEGORY));
    	//listFilters.add(new Filter("studyRef", STUDY_REF));
    	//listFilters.add(new Filter("pubMedId", PUB_MED_ID));
    }
    
    /*@Test
    @Transactional
    public void tryStudyCollection() throws Exception {
    	System.out.println("REST request");
    	 Study studyList = new Study();
    	 Set<Publication>pubList = studyList.getPublicationss();
    	 
    	 //assertThat(listSiteData!=null);
    }*/

   /*@Test
    @Transactional
    public void getAllSiteDataAndRelatedClasses() throws Exception {
    	System.out.println("REST request to search Categories for query {}");
    	 List<SiteData>listSiteData = siteDataRepository.showAllSiteDataAndRelatedClasses();
    	 assertThat(listSiteData!=null);
    }
    */
   /* @Test
    @Transactional
    public void getAllSiteDataByCountry(String country) throws Exception {
    	System.out.println("REST request to search SiteDataByCountries");
    	 Set<SiteData>setData = siteDataRepository.findAllSiteDatasByCountry(country);
    	 setData.stream().forEach(sd->{
    		 System.out.println("Site Data id: "+sd.getId());
    		 System.out.print(" Location: "+country);
    	 });
    }*/
    
  /* @Test
    @Transactional
    public void getAllSiteDataFiltered() throws Exception {
    	System.out.println("REST request to search SiteDataByCountries");
    	List<SiteData>listSiteData = siteDataRepository.searchSiteDataByFilter(listFilters);
    	SiteData testSiteData = listSiteData.get(0);
    	assertThat(testSiteData.getLocation().getCountry()).isEqualTo(COUNTRY);
    	assertThat(testSiteData.getCategory().getName()).isEqualTo(CATEGORY);
    	assertThat(testSiteData.getStudy().getRef()).isEqualTo(STUDY_REF);
    	assertEquals(Long.valueOf(9), testSiteData.getId());
    }*/
    
   /* @Test
    @Transactional
    public void getAllSiteDataFilteredResource() throws Exception {
	    	
	    siteDataService.save(siteData);
	    
	    restSiteDataMockMvc.perform(get("/siteData/" + listFilters));
	    
        	log.debug("REST request to get all SiteData filtered");
    		List<SiteData> listSiteData = siteDataRepository.searchSiteDataByFilter(listFilters); 
    		SiteData testSiteData = listSiteData.get(0);
    		assertThat(testSiteData.getLocation().getCountry()).isEqualTo(COUNTRY);
        	assertThat(testSiteData.getCategory().getName()).isEqualTo(CATEGORY);
        	assertThat(testSiteData.getStudy().getRef()).isEqualTo(STUDY_REF);
    		
    	        //return siteData;
        }*/
   
    @Test
   @Transactional
   public void getAllSiteDataInDTO() throws Exception {
   	System.out.println("REST request to create collection SiteDataViewDTO");
   	 List<SiteDataViewDTO>setDataViewDTO = siteDataRepository.searchSiteDataByFilter(listFilters);
   	 setDataViewDTO.stream().forEach(sd->{
   		 System.out.println(sd.toString());
   	 });
   }
}
