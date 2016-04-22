package org.wwarn.vivax.manager.web.rest;

import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.repository.TreatmentRepository;
import org.wwarn.vivax.manager.repository.search.TreatmentSearchRepository;

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
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class TreatmentResourceIntTest {

    private static final String DEFAULT_TREATMENT_NAME = "AAAAA";
    private static final String UPDATED_TREATMENT_NAME = "BBBBB";
    private static final String DEFAULT_TREATMENT_ARM_CODE = "AAAAA";
    private static final String UPDATED_TREATMENT_ARM_CODE = "BBBBB";

    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TreatmentResource treatmentResource = new TreatmentResource();
        ReflectionTestUtils.setField(treatmentResource, "treatmentSearchRepository", treatmentSearchRepository);
        ReflectionTestUtils.setField(treatmentResource, "treatmentRepository", treatmentRepository);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        treatmentSearchRepository.deleteAll();
        treatment = new Treatment();
        treatment.setTreatmentName(DEFAULT_TREATMENT_NAME);
        treatment.setTreatmentArmCode(DEFAULT_TREATMENT_ARM_CODE);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment

        restTreatmentMockMvc.perform(post("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(treatment)))
                .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
        assertThat(testTreatment.getTreatmentName()).isEqualTo(DEFAULT_TREATMENT_NAME);
        assertThat(testTreatment.getTreatmentArmCode()).isEqualTo(DEFAULT_TREATMENT_ARM_CODE);

        // Validate the Treatment in ElasticSearch
        Treatment treatmentEs = treatmentSearchRepository.findOne(testTreatment.getId());
        assertThat(treatmentEs).isEqualToComparingFieldByField(testTreatment);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatments
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
                .andExpect(jsonPath("$.[*].treatmentName").value(hasItem(DEFAULT_TREATMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].treatmentArmCode").value(hasItem(DEFAULT_TREATMENT_ARM_CODE.toString())));
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.treatmentName").value(DEFAULT_TREATMENT_NAME.toString()))
            .andExpect(jsonPath("$.treatmentArmCode").value(DEFAULT_TREATMENT_ARM_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        treatmentSearchRepository.save(treatment);
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = new Treatment();
        updatedTreatment.setId(treatment.getId());
        updatedTreatment.setTreatmentName(UPDATED_TREATMENT_NAME);
        updatedTreatment.setTreatmentArmCode(UPDATED_TREATMENT_ARM_CODE);

        restTreatmentMockMvc.perform(put("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTreatment)))
                .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
        assertThat(testTreatment.getTreatmentName()).isEqualTo(UPDATED_TREATMENT_NAME);
        assertThat(testTreatment.getTreatmentArmCode()).isEqualTo(UPDATED_TREATMENT_ARM_CODE);

        // Validate the Treatment in ElasticSearch
        Treatment treatmentEs = treatmentSearchRepository.findOne(testTreatment.getId());
        assertThat(treatmentEs).isEqualToComparingFieldByField(testTreatment);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        treatmentSearchRepository.save(treatment);
        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean treatmentExistsInEs = treatmentSearchRepository.exists(treatment.getId());
        assertThat(treatmentExistsInEs).isFalse();

        // Validate the database is empty
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        treatmentSearchRepository.save(treatment);

        // Search the treatment
        restTreatmentMockMvc.perform(get("/api/_search/treatments?query=id:" + treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].treatmentName").value(hasItem(DEFAULT_TREATMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].treatmentArmCode").value(hasItem(DEFAULT_TREATMENT_ARM_CODE.toString())));
    }
}
