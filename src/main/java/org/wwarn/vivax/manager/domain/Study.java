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
 * A Study.
 */
@Audited
@Entity
@Table(name = "Study")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "study")
public class Study implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "num_sites")
    private Integer numSites;

    @Column(name = "ref")
    private String ref;

    @Column(name = "study_type")
    private String studyType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "Pub_Study",
               joinColumns = @JoinColumn(name="studies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="publications_id", referencedColumnName="id"))
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(mappedBy = "study")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SiteData> siteDatas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumSites() {
        return numSites;
    }

    public void setNumSites(Integer numSites) {
        this.numSites = numSites;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public Set<Publication> getPublicationss() {
        return publications;
    }

    public void setPublicationss(Set<Publication> publications) {
        this.publications = publications;
    }

    public Set<SiteData> getSiteDatas() {
        return siteDatas;
    }

    public void setSiteDatas(Set<SiteData> siteDatas) {
        this.siteDatas = siteDatas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Study study = (Study) o;
        if(study.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, study.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Study{" +
            "id=" + id +
            ", numSites='" + numSites + "'" +
            ", ref='" + ref + "'" +
            ", studyType='" + studyType + "'" +
            '}';
    }
}
