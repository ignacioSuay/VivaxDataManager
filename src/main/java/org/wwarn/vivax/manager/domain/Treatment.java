package org.wwarn.vivax.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Treatment.
 */
@Audited
@Entity
@Table(name = "Treatment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "treatment")
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "treatment_name")
    private String treatmentName;

    @Column(name = "treatment_arm_code")
    private String treatmentArmCode;

    @ManyToMany(mappedBy = "treatments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SiteData> siteDatas = new HashSet<>();

    @Version
    Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentArmCode() {
        return treatmentArmCode;
    }

    public void setTreatmentArmCode(String treatmentArmCode) {
        this.treatmentArmCode = treatmentArmCode;
    }

    public Set<SiteData> getSiteDatas() {
        return siteDatas;
    }

    public void setSiteDatas(Set<SiteData> siteDatas) {
        this.siteDatas = siteDatas;
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
        Treatment treatment = (Treatment) o;
        if(treatment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + id +
            ", treatmentName='" + treatmentName + "'" +
            ", treatmentArmCode='" + treatmentArmCode + "'" +
            '}';
    }
}
