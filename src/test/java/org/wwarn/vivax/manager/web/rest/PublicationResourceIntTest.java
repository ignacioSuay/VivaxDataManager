package org.wwarn.vivax.manager.web.rest;

import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.service.PublicationService;
import org.wwarn.vivax.manager.repository.search.PublicationSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PublicationResource REST controller.
 *
 * @see PublicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class PublicationResourceIntTest {

    private static final String DEFAULT_AUTHORS = "AAAAA";
    private static final String UPDATED_AUTHORS = "BBBBB";
    private static final String DEFAULT_FIRST_AUTHOR = "AAAAA";
    private static final String UPDATED_FIRST_AUTHOR = "BBBBB";
    private static final String DEFAULT_JOURNAL = "AAAAA";
    private static final String UPDATED_JOURNAL = "BBBBB";

    private static final Integer DEFAULT_PUB_MED_ID = 1;
    private static final Integer UPDATED_PUB_MED_ID = 2;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Integer DEFAULT_YEAR_PUBLISH = 1;
    private static final Integer UPDATED_YEAR_PUBLISH = 2;

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
        publication.setAuthors(DEFAULT_AUTHORS);
        publication.setFirstAuthor(DEFAULT_FIRST_AUTHOR);
        publication.setJournal(DEFAULT_JOURNAL);
        publication.setPubMedId(DEFAULT_PUB_MED_ID);
        publication.setTitle(DEFAULT_TITLE);
        publication.setYearPublish(DEFAULT_YEAR_PUBLISH);
    }

    @Test
    @Transactional
    public void createPublication() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // Create the Publication

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeCreate + 1);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getAuthors()).isEqualTo(DEFAULT_AUTHORS);
        assertThat(testPublication.getFirstAuthor()).isEqualTo(DEFAULT_FIRST_AUTHOR);
        assertThat(testPublication.getJournal()).isEqualTo(DEFAULT_JOURNAL);
        assertThat(testPublication.getPubMedId()).isEqualTo(DEFAULT_PUB_MED_ID);
        assertThat(testPublication.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPublication.getYearPublish()).isEqualTo(DEFAULT_YEAR_PUBLISH);

        // Validate the Publication in ElasticSearch
        Publication publicationEs = publicationSearchRepository.findOne(testPublication.getId());
        assertThat(publicationEs).isEqualToComparingFieldByField(testPublication);
    }

    @Test
    @Transactional
    public void getAllPublications() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get all the publications
        restPublicationMockMvc.perform(get("/api/publications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
                .andExpect(jsonPath("$.[*].authors").value(hasItem(DEFAULT_AUTHORS.toString())))
                .andExpect(jsonPath("$.[*].first_author").value(hasItem(DEFAULT_FIRST_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].journal").value(hasItem(DEFAULT_JOURNAL.toString())))
                .andExpect(jsonPath("$.[*].pubMedId").value(hasItem(DEFAULT_PUB_MED_ID)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].year_publish").value(hasItem(DEFAULT_YEAR_PUBLISH)));
    }

    @Test
    @Transactional
    public void getPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(publication.getId().intValue()))
            .andExpect(jsonPath("$.authors").value(DEFAULT_AUTHORS.toString()))
            .andExpect(jsonPath("$.first_author").value(DEFAULT_FIRST_AUTHOR.toString()))
            .andExpect(jsonPath("$.journal").value(DEFAULT_JOURNAL.toString()))
            .andExpect(jsonPath("$.pubMedId").value(DEFAULT_PUB_MED_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.year_publish").value(DEFAULT_YEAR_PUBLISH));
    }

    @Test
    @Transactional
    public void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublication() throws Exception {
        // Initialize the database
        publicationService.save(publication);

        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication
        Publication updatedPublication = new Publication();
        updatedPublication.setId(publication.getId());
        updatedPublication.setAuthors(UPDATED_AUTHORS);
        updatedPublication.setFirstAuthor(UPDATED_FIRST_AUTHOR);
        updatedPublication.setJournal(UPDATED_JOURNAL);
        updatedPublication.setPubMedId(UPDATED_PUB_MED_ID);
        updatedPublication.setTitle(UPDATED_TITLE);
        updatedPublication.setYearPublish(UPDATED_YEAR_PUBLISH);

        restPublicationMockMvc.perform(put("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPublication)))
                .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getAuthors()).isEqualTo(UPDATED_AUTHORS);
        assertThat(testPublication.getFirstAuthor()).isEqualTo(UPDATED_FIRST_AUTHOR);
        assertThat(testPublication.getJournal()).isEqualTo(UPDATED_JOURNAL);
        assertThat(testPublication.getPubMedId()).isEqualTo(UPDATED_PUB_MED_ID);
        assertThat(testPublication.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPublication.getYearPublish()).isEqualTo(UPDATED_YEAR_PUBLISH);

        // Validate the Publication in ElasticSearch
        Publication publicationEs = publicationSearchRepository.findOne(testPublication.getId());
        assertThat(publicationEs).isEqualToComparingFieldByField(testPublication);
    }

    @Test
    @Transactional
    public void deletePublication() throws Exception {
        // Initialize the database
        publicationService.save(publication);

        int databaseSizeBeforeDelete = publicationRepository.findAll().size();

        // Get the publication
        restPublicationMockMvc.perform(delete("/api/publications/{id}", publication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean publicationExistsInEs = publicationSearchRepository.exists(publication.getId());
        assertThat(publicationExistsInEs).isFalse();

        // Validate the database is empty
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPublication() throws Exception {
        // Initialize the database
        publicationService.save(publication);

        // Search the publication
        restPublicationMockMvc.perform(get("/api/_search/publications?query=id:" + publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
            .andExpect(jsonPath("$.[*].authors").value(hasItem(DEFAULT_AUTHORS.toString())))
            .andExpect(jsonPath("$.[*].first_author").value(hasItem(DEFAULT_FIRST_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].journal").value(hasItem(DEFAULT_JOURNAL.toString())))
            .andExpect(jsonPath("$.[*].pubMedId").value(hasItem(DEFAULT_PUB_MED_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].year_publish").value(hasItem(DEFAULT_YEAR_PUBLISH)));
    }
}
