package org.wwarn.vivax.manager.repository;

import java.util.List;
import java.util.Set;

import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.util.Filter;
import org.wwarn.vivax.manager.web.rest.dto.SiteDataViewDTO;

/**
 * Custom repository interface created for being able to implement
 * the methods for filtering the data on the views and for
 * updating a SiteData and its eager relationships
 */
public interface SiteDataRepositoryCustom {

	public List<SiteDataViewDTO> searchSiteDataByFilter(List<Filter>listFilters);

    public void updateSiteData(SiteData siteData);


}
