package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.Location;

import org.springframework.data.jpa.repository.*;
import org.wwarn.vivax.manager.domain.SiteData;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Location entity.
 */
public interface LocationRepository extends JpaRepository<Location,Long> {

    @Query("select distinct l.country from Location l order by l.country")
    List<String> findAllDistinctCountries();

    @Query("select distinct l.location from Location l order by l.location")
    List<String> findAllDistinctLocations();

}
