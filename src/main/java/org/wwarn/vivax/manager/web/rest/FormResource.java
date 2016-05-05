package org.wwarn.vivax.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.service.PublicationService;

import javax.inject.Inject;

/**
 * Created by steven on 05/05/16.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    private FormResource formResource;

    @Inject
    private PublicationService publicationService;

    @RequestMapping(value = "/studyUpload/retrievePublicationByPubMedId/{pubMedId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Publication retrievePublicationByPubMedId(@PathVariable Integer pubMedId) {
        log.debug("REST request to get Publication : {}", pubMedId);
        //Publication publication = formResource.retrievePublicationByPubMedId(pubMedId);
        return new  Publication();
    }
}
