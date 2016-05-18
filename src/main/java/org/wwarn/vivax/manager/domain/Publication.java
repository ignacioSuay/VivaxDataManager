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
 * A Publication.
 */
@Audited
@Entity
@Table(name = "Publication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "publication")
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "authors")
    private String authors;

    @Column(name = "first_author")
    private String firstAuthor;

    @Column(name = "journal")
    private String journal;

    @Column(name = "pubMedId")
    private Integer pubMedId;

    @Column(name = "title")
    private String title;

    @Column(name = "year_publish")
    private Integer yearPublish;

    @Version
    Integer version;

    @ManyToMany(mappedBy = "publications")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Study> studies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getFirstAuthor() {
        return firstAuthor;
    }

    public void setFirstAuthor(String firstAuthor) {
        this.firstAuthor = firstAuthor;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Integer getPubMedId() {
        return pubMedId;
    }

    public void setPubMedId(Integer pubMedId) {
        this.pubMedId = pubMedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYearPublish() {
        return yearPublish;
    }

    public void setYearPublish(Integer yearPublish) {
        this.yearPublish = yearPublish;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studys) {
        this.studies = studys;
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
        Publication publication = (Publication) o;
        if(publication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, publication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Publication{" +
            "id=" + id +
            ", authors='" + authors + "'" +
            ", firstAuthor='" + firstAuthor + "'" +
            ", journal='" + journal + "'" +
            ", pubMedId='" + pubMedId + "'" +
            ", title='" + title + "'" +
            ", yearPublish='" + yearPublish + "'" +
            '}';
    }
}
