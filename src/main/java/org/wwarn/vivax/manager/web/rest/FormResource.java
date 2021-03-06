package org.wwarn.vivax.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.repository.PublicationRepository;
import org.wwarn.vivax.manager.service.PublicationService;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;
import org.wwarn.vivax.manager.web.rest.util.HeaderUtil;

import javax.inject.Inject;

/**
 * Created by steven on 05/05/16.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationService publicationService;

    @RequestMapping(value = "/studyUpload/retrievePublicationByPubMedId/{pubMedId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public FormResourceDTO retrievePublicationByPubMedId(@PathVariable Integer pubMedId) {
        log.debug("REST request to get Publication : {}", pubMedId);
        FormResourceDTO formResourceDTO = publicationRepository.retrievePublicationByPubMedId(pubMedId);
        return formResourceDTO;
    }

    @RequestMapping(value = "/studyUpload/updatePublicationDTOAndAllEagerRelationships",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FormResourceDTO> updatePublicationDTOAndAllEagerRelationships(@RequestBody FormResourceDTO formResourceDTO) {
        log.debug("REST request to get Publication : {}", formResourceDTO);
        FormResourceDTO formResDTO = publicationService.updatePublicationAndAllCollections(formResourceDTO);
        FormResourceDTO updatedFormResourceDTO = publicationRepository.retrievePublicationByPubMedId(formResourceDTO.getPublication().getPubMedId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("formDTO", formResourceDTO.getPublication().getId().toString()))
            .body(updatedFormResourceDTO);
    }
}
