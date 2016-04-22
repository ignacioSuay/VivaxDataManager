package org.wwarn.vivax.manager.web.rest;

import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.repository.StudyRepository;
import org.wwarn.vivax.manager.service.StudyService;
import org.wwarn.vivax.manager.repository.search.StudySearchRepository;

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
 * Test class for the StudyResource REST controller.
 *
 * @see StudyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class StudyResourceIntTest {


    private static final Integer DEFAULT_NUM_SITES = 1;
    private static final Integer UPDATED_NUM_SITES = 2;
    private static final String DEFAULT_REF = "AAAAA";
    private static final String UPDATED_REF = "BBBBB";
    private static final String DEFAULT_STUDY_TYPE = "AAAAA";
    private static final String UPDATED_STUDY_TYPE = "BBBBB";

    @Inject
    private StudyRepository studyRepository;

    @Inject
    private StudyService studyService;

    @Inject
    private StudySearchRepository studySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudyMockMvc;

    private Study study;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudyResource studyResource = new StudyResource();
        ReflectionTestUtils.setField(studyResource, "studyService", studyService);
        this.restStudyMockMvc = MockMvcBuilders.standaloneSetup(studyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        studySearchRepository.deleteAll();
        study = new Study();
        study.setNumSites(DEFAULT_NUM_SITES);
        study.setRef(DEFAULT_REF);
        study.setStudyType(DEFAULT_STUDY_TYPE);
    }

    @Test
    @Transactional
    public void createStudy() throws Exception {
        int databaseSizeBeforeCreate = studyRepository.findAll().size();

        // Create the Study

        restStudyMockMvc.perform(post("/api/studies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(study)))
                .andExpect(status().isCreated());

        // Validate the Study in the database
        List<Study> studies = studyRepository.findAll();
        assertThat(studies).hasSize(databaseSizeBeforeCreate + 1);
        Study testStudy = studies.get(studies.size() - 1);
        assertThat(testStudy.getNumSites()).isEqualTo(DEFAULT_NUM_SITES);
        assertThat(testStudy.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testStudy.getStudyType()).isEqualTo(DEFAULT_STUDY_TYPE);

        // Validate the Study in ElasticSearch
        Study studyEs = studySearchRepository.findOne(testStudy.getId());
        assertThat(studyEs).isEqualToComparingFieldByField(testStudy);
    }

    @Test
    @Transactional
    public void getAllStudies() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studies
        restStudyMockMvc.perform(get("/api/studies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(study.getId().intValue())))
                .andExpect(jsonPath("$.[*].numSites").value(hasItem(DEFAULT_NUM_SITES)))
                .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
                .andExpect(jsonPath("$.[*].studyType").value(hasItem(DEFAULT_STUDY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get the study
        restStudyMockMvc.perform(get("/api/studies/{id}", study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(study.getId().intValue()))
            .andExpect(jsonPath("$.numSites").value(DEFAULT_NUM_SITES))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.studyType").value(DEFAULT_STUDY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudy() throws Exception {
        // Get the study
        restStudyMockMvc.perform(get("/api/studies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudy() throws Exception {
        // Initialize the database
        studyService.save(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study
        Study updatedStudy = new Study();
        updatedStudy.setId(study.getId());
        updatedStudy.setNumSites(UPDATED_NUM_SITES);
        updatedStudy.setRef(UPDATED_REF);
        updatedStudy.setStudyType(UPDATED_STUDY_TYPE);

        restStudyMockMvc.perform(put("/api/studies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStudy)))
                .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studies = studyRepository.findAll();
        assertThat(studies).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studies.get(studies.size() - 1);
        assertThat(testStudy.getNumSites()).isEqualTo(UPDATED_NUM_SITES);
        assertThat(testStudy.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testStudy.getStudyType()).isEqualTo(UPDATED_STUDY_TYPE);

        // Validate the Study in ElasticSearch
        Study studyEs = studySearchRepository.findOne(testStudy.getId());
        assertThat(studyEs).isEqualToComparingFieldByField(testStudy);
    }

    @Test
    @Transactional
    public void deleteStudy() throws Exception {
        // Initialize the database
        studyService.save(study);

        int databaseSizeBeforeDelete = studyRepository.findAll().size();

        // Get the study
        restStudyMockMvc.perform(delete("/api/studies/{id}", study.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean studyExistsInEs = studySearchRepository.exists(study.getId());
        assertThat(studyExistsInEs).isFalse();

        // Validate the database is empty
        List<Study> studies = studyRepository.findAll();
        assertThat(studies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStudy() throws Exception {
        // Initialize the database
        studyService.save(study);

        // Search the study
        restStudyMockMvc.perform(get("/api/_search/studies?query=id:" + study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(study.getId().intValue())))
            .andExpect(jsonPath("$.[*].numSites").value(hasItem(DEFAULT_NUM_SITES)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].studyType").value(hasItem(DEFAULT_STUDY_TYPE.toString())));
    }
}
