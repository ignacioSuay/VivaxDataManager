package org.wwarn.vivax.manager.web.rest.dto;

import java.io.Serializable;
import java.util.Set;

import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.domain.Location;
import org.wwarn.vivax.manager.domain.Publication;

public class SiteDataViewDTO implements Serializable{
	
	private long id;
	private String typeStudy;
	private String upper95CI;
	private Integer yearStart;
	private Integer yearEnd;
	private String category;
	private String ref;
	private Location location;
	private Set <Treatment> listTreatments;
	private Set <Publication> listPublications;
	
	public SiteDataViewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SiteDataViewDTO(long id, String typeStudy, String upper95ci, Integer yearStart, Integer yearEnd,
			String category, String ref, Set<Treatment> listTreatments, Set<Publication> listPublications, Location location) {
		super();
		this.location = location;
		this.id = id;
		this.typeStudy = typeStudy;
		this.upper95CI = upper95ci;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
		this.category = category;
		this.ref = ref;
		this.listTreatments = listTreatments;
		this.listPublications = listPublications;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTypeStudy() {
		return typeStudy;
	}
	public void setTypeStudy(String typeStudy) {
		this.typeStudy = typeStudy;
	}
	public String getUpper95CI() {
		return upper95CI;
	}
	public void setUpper95CI(String upper95ci) {
		upper95CI = upper95ci;
	}
	public Integer getYearStart() {
		return yearStart;
	}
	public void setYearStart(Integer yearStart) {
		this.yearStart = yearStart;
	}
	public Integer getYearEnd() {
		return yearEnd;
	}
	public void setYearEnd(Integer yearEnd) {
		this.yearEnd = yearEnd;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Set<Treatment> getListTreatments() {
		return listTreatments;
	}
	public void setListTreatments(Set<Treatment> listTreatments) {
		this.listTreatments = listTreatments;
	}
	public Set<Publication> getListPublications() {
		return listPublications;
	}
	public void setListPublications(Set<Publication> listPublications) {
		this.listPublications = listPublications;
	}

	@Override
	public String toString() {
		return "SiteDataViewDTO [id=" + id + ", typeStudy=" + typeStudy + ", upper95CI=" + upper95CI + ", yearStart="
				+ yearStart + ", yearEnd=" + yearEnd + ", category=" + category + ", ref=" + ref + ", location="
				+ location + ", listTreatments=" + listTreatments + ", listPublications=" + listPublications + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((listPublications == null) ? 0 : listPublications.hashCode());
		result = prime * result + ((listTreatments == null) ? 0 : listTreatments.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((typeStudy == null) ? 0 : typeStudy.hashCode());
		result = prime * result + ((upper95CI == null) ? 0 : upper95CI.hashCode());
		result = prime * result + ((yearEnd == null) ? 0 : yearEnd.hashCode());
		result = prime * result + ((yearStart == null) ? 0 : yearStart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteDataViewDTO other = (SiteDataViewDTO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id != other.id)
			return false;
		if (listPublications == null) {
			if (other.listPublications != null)
				return false;
		} else if (!listPublications.equals(other.listPublications))
			return false;
		if (listTreatments == null) {
			if (other.listTreatments != null)
				return false;
		} else if (!listTreatments.equals(other.listTreatments))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (typeStudy == null) {
			if (other.typeStudy != null)
				return false;
		} else if (!typeStudy.equals(other.typeStudy))
			return false;
		if (upper95CI == null) {
			if (other.upper95CI != null)
				return false;
		} else if (!upper95CI.equals(other.upper95CI))
			return false;
		if (yearEnd == null) {
			if (other.yearEnd != null)
				return false;
		} else if (!yearEnd.equals(other.yearEnd))
			return false;
		if (yearStart == null) {
			if (other.yearStart != null)
				return false;
		} else if (!yearStart.equals(other.yearStart))
			return false;
		return true;
	}
	
}

	