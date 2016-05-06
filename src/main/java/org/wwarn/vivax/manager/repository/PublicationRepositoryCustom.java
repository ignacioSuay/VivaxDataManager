package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.web.rest.dto.FormResourceDTO;

import java.util.List;

/**
 * Created by steven on 03/05/16.
 */
public interface PublicationRepositoryCustom {

    public FormResourceDTO retrievePublicationByPubMedId(Integer pubMedId);
}
