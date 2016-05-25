package org.wwarn.vivax.manager.web.rest.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.wwarn.vivax.manager.domain.Treatment;
import org.wwarn.vivax.manager.domain.Location;
import org.wwarn.vivax.manager.domain.Publication;

/**
 * Created by steven on 15/04/16.
 * Class created to contain all properties we want to collect to show on
 * the view option
 */
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
        private List <String> listTreatmentArmCodes;
        private List <Integer> listPubMedIds;

        public List <String> getListTreatmentArmCodes() {
            return listTreatmentArmCodes;
        }

        public void setListTreatmentArmCodes(List <String> listTreatmentArmCodes) {
            this.listTreatmentArmCodes = listTreatmentArmCodes;
        }

        public List <Integer> getListPubMedIds() {
            return listPubMedIds;
        }

        public void setListPubMedIds(List <Integer> listPubMedIds) {
            this.listPubMedIds = listPubMedIds;
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

    public SiteDataViewDTO(long id, String typeStudy, String upper95CI, Integer yearStart, Integer yearEnd, String category, String ref, Location location, Set<Treatment> listTreatments, Set<Publication> listPublications, List <String> listTreatmentArmCodes, List <Integer> listPubMedIds) {
        this.id = id;
        this.typeStudy = typeStudy;
        this.upper95CI = upper95CI;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.category = category;
        this.ref = ref;
        this.location = location;
        this.listTreatments = listTreatments;
        this.listPublications = listPublications;
        this.listTreatmentArmCodes = listTreatmentArmCodes;
        this.listPubMedIds = listPubMedIds;
    }

    public SiteDataViewDTO() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + Objects.hashCode(this.typeStudy);
        hash = 53 * hash + Objects.hashCode(this.upper95CI);
        hash = 53 * hash + Objects.hashCode(this.yearStart);
        hash = 53 * hash + Objects.hashCode(this.yearEnd);
        hash = 53 * hash + Objects.hashCode(this.category);
        hash = 53 * hash + Objects.hashCode(this.ref);
        hash = 53 * hash + Objects.hashCode(this.location);
        hash = 53 * hash + Objects.hashCode(this.listTreatments);
        hash = 53 * hash + Objects.hashCode(this.listPublications);
        hash = 53 * hash + Objects.hashCode(this.listTreatmentArmCodes);
        hash = 53 * hash + Objects.hashCode(this.listPubMedIds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SiteDataViewDTO other = (SiteDataViewDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.typeStudy, other.typeStudy)) {
            return false;
        }
        if (!Objects.equals(this.upper95CI, other.upper95CI)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.ref, other.ref)) {
            return false;
        }
        if (!Objects.equals(this.yearStart, other.yearStart)) {
            return false;
        }
        if (!Objects.equals(this.yearEnd, other.yearEnd)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.listTreatments, other.listTreatments)) {
            return false;
        }
        if (!Objects.equals(this.listPublications, other.listPublications)) {
            return false;
        }
        if (!Objects.equals(this.listTreatmentArmCodes, other.listTreatmentArmCodes)) {
            return false;
        }
        if (!Objects.equals(this.listPubMedIds, other.listPubMedIds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SiteDataViewDTO{" + "id=" + id + ", typeStudy=" + typeStudy + ", upper95CI=" + upper95CI + ", yearStart=" + yearStart + ", yearEnd=" + yearEnd + ", category=" + category + ", ref=" + ref + ", location=" + location + ", listTreatments=" + listTreatments + ", listPublications=" + listPublications + ", listTreatmentArmCodes=" + listTreatmentArmCodes + ", listPubMedIds=" + listPubMedIds + '}';
    }
}

