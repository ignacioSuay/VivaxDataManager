package org.wwarn.vivax.manager.web.rest;

import org.wwarn.vivax.manager.VivaxDataManagerApp;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.service.SiteDataService;
import org.wwarn.vivax.manager.repository.search.SiteDataSearchRepository;

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
import org.springframework.test.context.ActiveProfiles;
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
 * Test class for the SiteDataResource REST controller.
 *
 * @see SiteDataResource
 */
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VivaxDataManagerApp.class)
@WebAppConfiguration
@IntegrationTest
public class SiteDataResourceIntTest {

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
    }

    @Test
    @Transactional
    public void createSiteData() throws Exception {
        int databaseSizeBeforeCreate = siteDataRepository.findAll().size();

        // Create the SiteData

        restSiteDataMockMvc.perform(post("/api/site-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(siteData)))
                .andExpect(status().isCreated());

        // Validate the SiteData in the database
        List<SiteData> siteData = siteDataRepository.findAll();
        assertThat(siteData).hasSize(databaseSizeBeforeCreate + 1);
        SiteData testSiteData = siteData.get(siteData.size() - 1);
        assertThat(testSiteData.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testSiteData.getDay28Recurrence()).isEqualTo(DEFAULT_DAY_28_RECURRENCE);
        assertThat(testSiteData.getLowest95CI()).isEqualTo(DEFAULT_LOWEST_95_CI);
        assertThat(testSiteData.getNumEnroled()).isEqualTo(DEFAULT_NUM_ENROLED);
        assertThat(testSiteData.getNumPatientsTreatCQ()).isEqualTo(DEFAULT_NUM_PATIENTS_TREAT_CQ);
        assertThat(testSiteData.getPrimaquine()).isEqualTo(DEFAULT_PRIMAQUINE);
        assertThat(testSiteData.getTimePrimaquine()).isEqualTo(DEFAULT_TIME_PRIMAQUINE);
        assertThat(testSiteData.getTypeStudy()).isEqualTo(DEFAULT_TYPE_STUDY);
        assertThat(testSiteData.getUpper95CI()).isEqualTo(DEFAULT_UPPER_95_CI);
        assertThat(testSiteData.getYearEnd()).isEqualTo(DEFAULT_YEAR_END);
        assertThat(testSiteData.getYearStart()).isEqualTo(DEFAULT_YEAR_START);

        // Validate the SiteData in ElasticSearch
        SiteData siteDataEs = siteDataSearchRepository.findOne(testSiteData.getId());
        assertThat(siteDataEs).isEqualToComparingFieldByField(testSiteData);
    }

    @Test
    @Transactional
    public void getAllSiteData() throws Exception {
        // Initialize the database
        siteDataRepository.saveAndFlush(siteData);

        // Get all the siteData
        restSiteDataMockMvc.perform(get("/api/site-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(siteData.getId().intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].day28Recurrence").value(hasItem(DEFAULT_DAY_28_RECURRENCE.toString())))
                .andExpect(jsonPath("$.[*].lowest95CI").value(hasItem(DEFAULT_LOWEST_95_CI.toString())))
                .andExpect(jsonPath("$.[*].numEnroled").value(hasItem(DEFAULT_NUM_ENROLED)))
                .andExpect(jsonPath("$.[*].numPatientsTreatCQ").value(hasItem(DEFAULT_NUM_PATIENTS_TREAT_CQ)))
                .andExpect(jsonPath("$.[*].primaquine").value(hasItem(DEFAULT_PRIMAQUINE.toString())))
                .andExpect(jsonPath("$.[*].timePrimaquine").value(hasItem(DEFAULT_TIME_PRIMAQUINE.toString())))
                .andExpect(jsonPath("$.[*].typeStudy").value(hasItem(DEFAULT_TYPE_STUDY.toString())))
                .andExpect(jsonPath("$.[*].upper95CI").value(hasItem(DEFAULT_UPPER_95_CI.toString())))
                .andExpect(jsonPath("$.[*].yearEnd").value(hasItem(DEFAULT_YEAR_END)))
                .andExpect(jsonPath("$.[*].yearStart").value(hasItem(DEFAULT_YEAR_START)));
    }

    @Test
    @Transactional
    public void getSiteData() throws Exception {
        // Initialize the database
        siteDataRepository.saveAndFlush(siteData);

        // Get the siteData
        restSiteDataMockMvc.perform(get("/api/site-data/{id}", siteData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(siteData.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.day28Recurrence").value(DEFAULT_DAY_28_RECURRENCE.toString()))
            .andExpect(jsonPath("$.lowest95CI").value(DEFAULT_LOWEST_95_CI.toString()))
            .andExpect(jsonPath("$.numEnroled").value(DEFAULT_NUM_ENROLED))
            .andExpect(jsonPath("$.numPatientsTreatCQ").value(DEFAULT_NUM_PATIENTS_TREAT_CQ))
            .andExpect(jsonPath("$.primaquine").value(DEFAULT_PRIMAQUINE.toString()))
            .andExpect(jsonPath("$.timePrimaquine").value(DEFAULT_TIME_PRIMAQUINE.toString()))
            .andExpect(jsonPath("$.typeStudy").value(DEFAULT_TYPE_STUDY.toString()))
            .andExpect(jsonPath("$.upper95CI").value(DEFAULT_UPPER_95_CI.toString()))
            .andExpect(jsonPath("$.yearEnd").value(DEFAULT_YEAR_END))
            .andExpect(jsonPath("$.yearStart").value(DEFAULT_YEAR_START));
    }

    @Test
    @Transactional
    public void getNonExistingSiteData() throws Exception {
        // Get the siteData
        restSiteDataMockMvc.perform(get("/api/site-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteData() throws Exception {
        // Initialize the database
        siteDataService.save(siteData);

        int databaseSizeBeforeUpdate = siteDataRepository.findAll().size();

        // Update the siteData
        SiteData updatedSiteData = new SiteData();
        updatedSiteData.setId(siteData.getId());
        updatedSiteData.setComments(UPDATED_COMMENTS);
        updatedSiteData.setDay28Recurrence(UPDATED_DAY_28_RECURRENCE);
        updatedSiteData.setLowest95CI(UPDATED_LOWEST_95_CI);
        updatedSiteData.setNumEnroled(UPDATED_NUM_ENROLED);
        updatedSiteData.setNumPatientsTreatCQ(UPDATED_NUM_PATIENTS_TREAT_CQ);
        updatedSiteData.setPrimaquine(UPDATED_PRIMAQUINE);
        updatedSiteData.setTimePrimaquine(UPDATED_TIME_PRIMAQUINE);
        updatedSiteData.setTypeStudy(UPDATED_TYPE_STUDY);
        updatedSiteData.setUpper95CI(UPDATED_UPPER_95_CI);
        updatedSiteData.setYearEnd(UPDATED_YEAR_END);
        updatedSiteData.setYearStart(UPDATED_YEAR_START);

        restSiteDataMockMvc.perform(put("/api/site-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSiteData)))
                .andExpect(status().isOk());

        // Validate the SiteData in the database
        List<SiteData> siteData = siteDataRepository.findAll();
        assertThat(siteData).hasSize(databaseSizeBeforeUpdate);
        SiteData testSiteData = siteData.get(siteData.size() - 1);
        assertThat(testSiteData.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testSiteData.getDay28Recurrence()).isEqualTo(UPDATED_DAY_28_RECURRENCE);
        assertThat(testSiteData.getLowest95CI()).isEqualTo(UPDATED_LOWEST_95_CI);
        assertThat(testSiteData.getNumEnroled()).isEqualTo(UPDATED_NUM_ENROLED);
        assertThat(testSiteData.getNumPatientsTreatCQ()).isEqualTo(UPDATED_NUM_PATIENTS_TREAT_CQ);
        assertThat(testSiteData.getPrimaquine()).isEqualTo(UPDATED_PRIMAQUINE);
        assertThat(testSiteData.getTimePrimaquine()).isEqualTo(UPDATED_TIME_PRIMAQUINE);
        assertThat(testSiteData.getTypeStudy()).isEqualTo(UPDATED_TYPE_STUDY);
        assertThat(testSiteData.getUpper95CI()).isEqualTo(UPDATED_UPPER_95_CI);
        assertThat(testSiteData.getYearEnd()).isEqualTo(UPDATED_YEAR_END);
        assertThat(testSiteData.getYearStart()).isEqualTo(UPDATED_YEAR_START);

        // Validate the SiteData in ElasticSearch
        SiteData siteDataEs = siteDataSearchRepository.findOne(testSiteData.getId());
        assertThat(siteDataEs).isEqualToComparingFieldByField(testSiteData);
    }

    @Test
    @Transactional
    public void deleteSiteData() throws Exception {
        // Initialize the database
        siteDataService.save(siteData);

        int databaseSizeBeforeDelete = siteDataRepository.findAll().size();

        // Get the siteData
        restSiteDataMockMvc.perform(delete("/api/site-data/{id}", siteData.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean siteDataExistsInEs = siteDataSearchRepository.exists(siteData.getId());
        assertThat(siteDataExistsInEs).isFalse();

        // Validate the database is empty
        List<SiteData> siteData = siteDataRepository.findAll();
        assertThat(siteData).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSiteData() throws Exception {
        // Initialize the database
        siteDataService.save(siteData);

        // Search the siteData
        restSiteDataMockMvc.perform(get("/api/_search/site-data?query=id:" + siteData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteData.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].day28Recurrence").value(hasItem(DEFAULT_DAY_28_RECURRENCE.toString())))
            .andExpect(jsonPath("$.[*].lowest95CI").value(hasItem(DEFAULT_LOWEST_95_CI.toString())))
            .andExpect(jsonPath("$.[*].numEnroled").value(hasItem(DEFAULT_NUM_ENROLED)))
            .andExpect(jsonPath("$.[*].numPatientsTreatCQ").value(hasItem(DEFAULT_NUM_PATIENTS_TREAT_CQ)))
            .andExpect(jsonPath("$.[*].primaquine").value(hasItem(DEFAULT_PRIMAQUINE.toString())))
            .andExpect(jsonPath("$.[*].timePrimaquine").value(hasItem(DEFAULT_TIME_PRIMAQUINE.toString())))
            .andExpect(jsonPath("$.[*].typeStudy").value(hasItem(DEFAULT_TYPE_STUDY.toString())))
            .andExpect(jsonPath("$.[*].upper95CI").value(hasItem(DEFAULT_UPPER_95_CI.toString())))
            .andExpect(jsonPath("$.[*].yearEnd").value(hasItem(DEFAULT_YEAR_END)))
            .andExpect(jsonPath("$.[*].yearStart").value(hasItem(DEFAULT_YEAR_START)));
    }
}
