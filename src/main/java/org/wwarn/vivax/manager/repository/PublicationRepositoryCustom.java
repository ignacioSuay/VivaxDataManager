package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;

import java.util.List;

/**
 * Created by steven on 03/05/16.
 */
public interface PublicationRepositoryCustom {

    public Publication findPublicationByPubMedId(Integer pubMedId);
}
