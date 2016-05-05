package org.wwarn.vivax.manager.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.repository.search.PublicationSearchRepository;
import org.wwarn.vivax.manager.service.PublicationService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by steven on 05/05/16.
 */
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class formResourceTest {

    private final Logger log = LoggerFactory.getLogger(org.wwarn.vivax.manager.web.rest.PublicationServiceSmallTest.class);


        private static final Integer PUB_MED_ID = 8882190;

        @Inject
        private PublicationRepository publicationRepository;

        @Inject
        private PublicationService publicationService;

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
            ReflectionTestUtils.setField(publicationResource, "publicationService", publicationService);
            this.restPublicationMockMvc = MockMvcBuilders.standaloneSetup(publicationResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
        }

        @Before
        public void initTest() {
            publicationSearchRepository.deleteAll();
            publication = new Publication();

        }

        @Test
        @Transactional
        public void findPublicationByPubMedId() throws Exception {
            System.out.println("REST request");
            Publication pub = new Publication();
            pub = publicationRepository.retrievePublicationByPubMedId(PUB_MED_ID);

            assertThat(pub!=null);
        }


    }


