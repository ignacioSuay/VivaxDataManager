package org.wwarn.vivax.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.service.PublicationService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Publication.
 */
@RestController
@RequestMapping("/api")
public class PublicationResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    @Inject
    private PublicationService publicationService;

    /**
     * POST  /publications : Create a new publication.
     *
     * @param publication the publication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publication, or with status 400 (Bad Request) if the publication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to save Publication : {}", publication);
        if (publication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("publication", "idexists", "A new publication cannot already have an ID")).body(null);
        }
        Publication result = publicationService.save(publication);
        return ResponseEntity.created(new URI("/api/publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("publication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publications : Updates an existing publication.
     *
     * @param publication the publication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publication,
     * or with status 400 (Bad Request) if the publication is not valid,
     * or with status 500 (Internal Server Error) if the publication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> updatePublication(@RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to update Publication : {}", publication);
        if (publication.getId() == null) {
            return createPublication(publication);
        }
        Publication result = publicationService.save(publication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("publication", publication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publications : get all the publications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of publications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Publication>> getAllPublications(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Publications");
        Page<Publication> page = publicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/publications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /publications/:id : get the "id" publication.
     *
     * @param id the id of the publication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publication, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/publications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        Publication publication = publicationService.findOne(id);
        return Optional.ofNullable(publication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publications/:id : delete the "id" publication.
     *
     * @param id the id of the publication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/publications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication : {}", id);
        publicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/publications?query=:query : search for the publication corresponding
     * to the query.
     *
     * @param query the query of the publication search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/publications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Publication>> searchPublications(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Publications for query {}", query);
        Page<Publication> page = publicationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/publications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
