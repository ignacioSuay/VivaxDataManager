package org.wwarn.vivax.manager.web.rest.dto;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.Treatment;

import java.util.List;
import java.util.Set;

/**
 * Created by steven on 06/05/16.
 */
public class FormResourceDTO {

    private Publication publication;
    private Set<Study> studies;
    private List<Set<Treatment>> treatments;
    private List<Set<SiteData>> siteDatas;
    private List<StudyDTO> studyDTOList;

    public FormResourceDTO() {
    }

    public FormResourceDTO(Publication publication, Set<Study> studies, List<Set<Treatment>> treatments, List<Set<SiteData>> siteDatas, List<StudyDTO> studyDTOList) {
        this.publication = publication;
        this.studies = studies;
        this.treatments = treatments;
        this.siteDatas = siteDatas;
        this.studyDTOList = studyDTOList;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    public List<Set<Treatment>> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Set<Treatment>> treatments) {
        this.treatments = treatments;
    }

    public List<Set<SiteData>> getSiteDatas() {
        return siteDatas;
    }

    public void setSiteDatas(List<Set<SiteData>> siteDatas) {
        this.siteDatas = siteDatas;
    }

    public List<StudyDTO> getStudyDTOList() {
        return studyDTOList;
    }

    public void setStudyDTOList(List<StudyDTO> studyDTOList) {
        this.studyDTOList = studyDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormResourceDTO that = (FormResourceDTO) o;

        if (publication != null ? !publication.equals(that.publication) : that.publication != null) return false;
        if (studies != null ? !studies.equals(that.studies) : that.studies != null) return false;
        if (treatments != null ? !treatments.equals(that.treatments) : that.treatments != null) return false;
        if (siteDatas != null ? !siteDatas.equals(that.siteDatas) : that.siteDatas != null) return false;
        return studyDTOList != null ? studyDTOList.equals(that.studyDTOList) : that.studyDTOList == null;

    }

    @Override
    public int hashCode() {
        int result = publication != null ? publication.hashCode() : 0;
        result = 31 * result + (studies != null ? studies.hashCode() : 0);
        result = 31 * result + (treatments != null ? treatments.hashCode() : 0);
        result = 31 * result + (siteDatas != null ? siteDatas.hashCode() : 0);
        result = 31 * result + (studyDTOList != null ? studyDTOList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormResourceDTO{" +
            "publication=" + publication +
            ", studies=" + studies +
            ", treatments=" + treatments +
            ", siteDatas=" + siteDatas +
            ", studyDTOList=" + studyDTOList +
            '}';
    }
}
