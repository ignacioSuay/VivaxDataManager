package org.wwarn.vivax.manager.repository;

import org.wwarn.vivax.manager.domain.SiteData;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

import javax.persistence.NamedQuery;

/**
 * Spring Data JPA repository for the SiteData entity.
 */
public interface SiteDataRepository extends JpaRepository<SiteData,Long>, SiteDataRepositoryCustom{

    @Query("select distinct siteData from SiteData siteData left join fetch siteData.treatments")
    List<SiteData> findAllWithEagerRelationships();

    @Query("select siteData from SiteData siteData left join fetch siteData.treatments where siteData.id =:id")
    SiteData findOneWithEagerRelationships(@Param("id") Long id);

    @Query("from SiteData")
    List<SiteData> showAllSiteDataAndRelatedClasses();

    @Query("select c.siteDatas from Category c")
    Set<SiteData> findAllSiteDatasByCategory();

    @Query("select siteData from SiteData siteData left join fetch siteData.location lo where lo.country=:country")
    Set<SiteData> findAllSiteDatasByCountry(String country);
}
