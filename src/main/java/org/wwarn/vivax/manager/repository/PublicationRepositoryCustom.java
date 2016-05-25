package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;

import java.util.List;

/**
 * Created by steven on 03/05/16.
 *
 * Custom repository interface created for being able to implement
 * the method for collecting the data from a Publication and all
 * it's collections and childs
 */

public interface PublicationRepositoryCustom {

    FormResourceDTO retrievePublicationByPubMedId(Integer pubMedId);
}
