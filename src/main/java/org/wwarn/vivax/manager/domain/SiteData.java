package org.wwarn.vivax.manager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SiteData.
 */
@Audited
@Entity
@Table(name = "Vivax_Site_Data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sitedata")
public class SiteData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "day_28_recurrence")
    private String day28Recurrence;

    @Column(name = "lowest_95_CI")
    private String lowest95CI;

    @Column(name = "num_enroled")
    private Integer numEnroled;

    @Column(name = "num_patients_treat_CQ")
    private Integer numPatientsTreatCQ;

    @Column(name = "primaquine")
    private String primaquine;

    @Column(name = "time_primaquine")
    private String timePrimaquine;

    @Column(name = "type_study")
    private String typeStudy;

    @Column(name = "upper_95_CI")
    private String upper95CI;

    @Column(name = "year_end")
    private Integer yearEnd;

    @Column(name = "year_start")
    private Integer yearStart;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
 
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name="study_id")
    private Study study;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "SiteData_Treatment",
               joinColumns = @JoinColumn(name="vivaxSiteDataList_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="treatments_id", referencedColumnName="id"))
    private Set<Treatment> treatments = new HashSet<>();
    
    @Version
    Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDay28Recurrence() {
        return day28Recurrence;
    }

    public void setDay28Recurrence(String day28Recurrence) {
        this.day28Recurrence = day28Recurrence;
    }

    public String getLowest95CI() {
        return lowest95CI;
    }

    public void setLowest95CI(String lowest95CI) {
        this.lowest95CI = lowest95CI;
    }

    public Integer getNumEnroled() {
        return numEnroled;
    }

    public void setNumEnroled(Integer numEnroled) {
        this.numEnroled = numEnroled;
    }

    public Integer getNumPatientsTreatCQ() {
        return numPatientsTreatCQ;
    }

    public void setNumPatientsTreatCQ(Integer numPatientsTreatCQ) {
        this.numPatientsTreatCQ = numPatientsTreatCQ;
    }

    public String getPrimaquine() {
        return primaquine;
    }

    public void setPrimaquine(String primaquine) {
        this.primaquine = primaquine;
    }

    public String getTimePrimaquine() {
        return timePrimaquine;
    }

    public void setTimePrimaquine(String timePrimaquine) {
        this.timePrimaquine = timePrimaquine;
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

    public void setUpper95CI(String upper95CI) {
        this.upper95CI = upper95CI;
    }

    public Integer getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(Integer yearEnd) {
        this.yearEnd = yearEnd;
    }

    public Integer getYearStart() {
        return yearStart;
    }

    public void setYearStart(Integer yearStart) {
        this.yearStart = yearStart;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }
    
    public Integer getVersion(){
    	return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SiteData siteData = (SiteData) o;
        if(siteData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, siteData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SiteData{" +
            "id=" + id +
            ", comments='" + comments + "'" +
            ", day28Recurrence='" + day28Recurrence + "'" +
            ", lowest95CI='" + lowest95CI + "'" +
            ", numEnroled='" + numEnroled + "'" +
            ", numPatientsTreatCQ='" + numPatientsTreatCQ + "'" +
            ", primaquine='" + primaquine + "'" +
            ", timePrimaquine='" + timePrimaquine + "'" +
            ", typeStudy='" + typeStudy + "'" +
            ", upper95CI='" + upper95CI + "'" +
            ", yearEnd='" + yearEnd + "'" +
            ", yearStart='" + yearStart + "'" +
            '}';
    }
}
