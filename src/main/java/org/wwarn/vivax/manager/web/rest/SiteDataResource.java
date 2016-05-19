package org.wwarn.vivax.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.data.jpa.repository.Modifying;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.domain.util.Filter;
import org.wwarn.vivax.manager.repository.SiteDataRepository;
import org.wwarn.vivax.manager.service.SiteDataService;
import org.wwarn.vivax.manager.web.rest.dto.SiteDataViewDTO;
import org.wwarn.vivax.manager.web.rest.util.HeaderUtil;
import org.wwarn.vivax.manager.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SiteData.
 */
@RestController
@RequestMapping("/api")
public class SiteDataResource {

    private final Logger log = LoggerFactory.getLogger(SiteDataResource.class);

    @Inject
    private SiteDataService siteDataService;

    @Inject
    private SiteDataRepository siteDataRepository;

     /**
     * POST  /site-data : Create a new siteData.
     *
     * @param siteData the siteData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siteData, or with status 400 (Bad Request) if the siteData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/site-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SiteData> createSiteData(@RequestBody SiteData siteData) throws URISyntaxException {
        log.debug("REST request to save SiteData : {} ", siteData);
        if (siteData.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("siteData", "idexists", "A new siteData cannot already have an ID")).body(null);
        }
        SiteData result = siteDataService.save(siteData);
        return ResponseEntity.created(new URI("/api/site-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("siteData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /site-data : Updates an existing siteData.
     *
     * @param siteData the siteData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siteData,
     * or with status 400 (Bad Request) if the siteData is not valid,
     * or with status 500 (Internal Server Error) if the siteData couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/site-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SiteData> updateSiteData(@RequestBody SiteData siteData) throws URISyntaxException {
        log.debug("REST request to update SiteData : {}", siteData);
        if (siteData.getId() == null) {
            return createSiteData(siteData);
        }
        SiteData result = siteDataService.save(siteData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("siteData", siteData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /site-data : get all the siteData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siteData in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/site-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SiteData>> getAllSiteData(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SiteData");
        Page<SiteData> page = siteDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/site-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /site-data/:id : get the "id" siteData.
     *
     * @param id the id of the siteData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siteData, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/site-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SiteData> getSiteData(@PathVariable Long id) {
        log.debug("REST request to get SiteData : {}", id);
        SiteData siteData = siteDataService.findOne(id);
        return Optional.ofNullable(siteData)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /site-data/:id : delete the "id" siteData.
     *
     * @param id the id of the siteData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/site-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSiteData(@PathVariable Long id) {
        log.debug("REST request to delete SiteData : {}", id);
        siteDataService.deleteSiteData(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("siteData", id.toString())).build();
    }

    /**
     * SEARCH  /_search/site-data?query=:query : search for the siteData corresponding
     * to the query.
     *
     * @param query the query of the siteData search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/site-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SiteData>> searchSiteData(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SiteData for query {}", query);
        Page<SiteData> page = siteDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/site-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/site-data/siteData",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public List<SiteData> getAllSiteDataAndRelatedClasses() {
    		log.debug("REST request to get all SiteData by Category");
    		 List<SiteData> siteData = siteDataRepository.showAllSiteDataAndRelatedClasses();
    	        return siteData;
        }


    @RequestMapping(value = "/categories/siteData",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public Set<SiteData> getAllSiteDataByCategory() {
    		log.debug("REST request to get all SiteData by Category");
    		 Set<SiteData> siteData = siteDataRepository.findAllSiteDatasByCategory();
    	        return siteData;
        }

    @RequestMapping(value = "/siteData/{country}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public Set<SiteData> getAllSiteDataByCountry(@PathVariable String country) {
    		log.debug("REST request to get all SiteData by Country");
    		Set<SiteData> siteData = siteDataRepository.findAllSiteDatasByCountry(country);
    	        return siteData;
        }

    @RequestMapping(value = "/siteData/searchByFilters",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public List<SiteDataViewDTO> getAllSiteDataFiltered(@RequestBody List<Filter>listFilters) {
    		log.debug("request to get all SiteData filtered"+listFilters.get(0).toString());
    		List<SiteDataViewDTO> siteData = siteDataRepository.searchSiteDataByFilter(listFilters);
                return siteData;
        }

    @RequestMapping(value = "/siteData/updateSiteData",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Modifying
    public SiteData updateSiteDataTreatmentList(@RequestBody SiteData siteData) {
        log.debug("request update siteData");
        siteDataRepository.updateSiteData(siteData);
        return siteData;
    }
}
