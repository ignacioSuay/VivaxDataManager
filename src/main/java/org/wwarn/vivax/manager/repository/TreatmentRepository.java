package org.wwarn.vivax.manager.repository;

import org.springframework.data.repository.query.Param;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Treatment;

import org.springframework.data.jpa.repository.*;

import javax.persistence.NamedAttributeNode;
import java.util.List;

/**
 * Spring Data JPA repository for the Treatment entity.
 */
public interface TreatmentRepository extends JpaRepository<Treatment,Long> {

    @Query("select tre from Treatment tre left join fetch tre.siteDatas where tre.id = :id")
    Treatment findOneTreatmentWithSiteDataRelationships(@Param("id") Long id);

}
