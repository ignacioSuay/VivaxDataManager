package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Publication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Publication entity.
 */
public interface PublicationRepository extends JpaRepository<Publication,Long> {

}
