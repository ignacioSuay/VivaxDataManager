package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Created by steven on 03/05/16.
 */
public class PublicationRepositoryImpl implements PublicationRepositoryCustom{

    Publication publication = new Publication();

    @PersistenceContext
    private EntityManager em;


    @Override
    public Publication findPublicationByPubMedId(Integer pubMedId) {
        
        final String QUERY= "from Publication p where p.pubMedId = "+pubMedId;
        Query q1 = em.createQuery(QUERY);

        publication = (Publication)q1.getSingleResult();

        return publication;
    }
}
