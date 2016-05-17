package org.wwarn.vivax.manager.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.repository.search.PublicationSearchRepository;
import org.wwarn.vivax.manager.service.PublicationService;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by steven on 05/05/16.
 */
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class FormResourceTest {

    private final Logger log = LoggerFactory.getLogger(org.wwarn.vivax.manager.web.rest.PublicationServiceSmallTest.class);


    private static final Integer PUB_MED_ID = 15486831;

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationSearchRepository publicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPublicationMockMvc;

    private Publication publication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublicationResource publicationResource = new PublicationResource();
        this.restPublicationMockMvc = MockMvcBuilders.standaloneSetup(publicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        publicationSearchRepository.deleteAll();
        publication = new Publication();
    }

    /*@Test
    @Transactional
    public void retPublicationByPubMedId() throws Exception {
        log.debug("REST request to get Publication : {}", PUB_MED_ID);
        FormResourceDTO formResourceDTO = new FormResourceDTO();
        formResourceDTO = publicationRepository.retrievePublicationByPubMedId(PUB_MED_ID);
        assertNotNull(formResourceDTO);
    }

    @Test
    @Transactional
    public void updateSiteData(SiteData siteData) {
        EntityManager em = Mockito.mock(EntityManager.class);
        Set<Treatment> listTreatments = new HashSet<>();
        Treatment trea = new Treatment();
        trea.setId((long)178);
        trea.setTreatmentName("x");
        trea.setTreatmentArmCode("xcv");
        listTreatments.add(trea);
        System.out.println("@@@@@@@@@@@@@@@@@@@ " +listTreatments);
        *//*em.createQuery(
            "update SiteData set treatments = '"+listTreatments+"' where id = 33")
            *//**//*.setParameter("treats", listTreatments)*//**//*
            *//**//*.setParameter("id", siteData.getId())*//**//*
           .executeUpdate();*//*
        em.merge(siteData);
    }*/
    @Test
    @Transactional
    public void testUpdateFormResourceDTO() {
        EntityManager em = Mockito.mock(EntityManager.class);
     /*   Publication pub = em.find(Publication.class, 1);*/
        final String QUERY= "SELECT FROM Publication p" +
            " where p.pubMedId = 7769318";
        Query q1 = em.createQuery(QUERY);
        /*publication = (Publication)q1.getSingleResult();*/
        System.out.println(q1.getSingleResult());
    }
}


