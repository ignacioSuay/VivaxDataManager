package org.wwarn.vivax.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.hibernate.Hibernate;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.repository.TreatmentRepository;
import org.wwarn.vivax.manager.repository.search.TreatmentSearchRepository;
import org.wwarn.vivax.manager.service.TreatmentService;
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
import javax.persistence.PersistenceContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);

    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;

    @Inject
    private TreatmentService treatmentService;


    /**
     * POST  /treatments : Create a new treatment.
     *
     * @param treatment the treatment to create
     * @param siteDataId if the call comes from the upload Study Form, the treatment will
     *                   be linked to a particular SiteData
     * @return the ResponseEntity with status 201 (Created) and with body the new treatment, or with status 400 (Bad Request) if the treatment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment, Long siteDataId) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatment);
        if (treatment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("treatment", "idexists", "A new treatment cannot already have an ID")).body(null);
        }
        Treatment result = treatmentService.saveTreatment(treatment, siteDataId);
        treatmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("treatment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments : Updates an existing treatment.
     *
     * @param treatment the treatment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatment,
     * or with status 400 (Bad Request) if the treatment is not valid,
     * or with status 500 (Internal Server Error) if the treatment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> updateTreatment(@RequestBody Treatment treatment, Long siteDataId) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatment);
        if (treatment.getId() == null) {
            return createTreatment(treatment, siteDataId);
        }
        Treatment result = treatmentRepository.save(treatment);
        treatmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("treatment", treatment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments : get all the treatments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of treatments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Treatment>> getAllTreatments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Treatments");
        Page<Treatment> page = treatmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /treatments/:id : get the "id" treatment.
     *
     * @param id the id of the treatment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Treatment treatment = treatmentRepository.findOne(id);
        return Optional.ofNullable(treatment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /treatments/:id : delete the "id" treatment.
     *
     * @param id the id of the treatment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentService.deleteTreatment(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("treatment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/treatments?query=:query : search for the treatment corresponding
     * to the query.
     *
     * @param query the query of the treatment search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/treatments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Treatment>> searchTreatments(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Treatments for query {}", query);
        Page<Treatment> page = treatmentSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/treatments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
