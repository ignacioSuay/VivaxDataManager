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
    private List<StudyDTO> studyDTOList;

    public FormResourceDTO() {
    }

    public FormResourceDTO(Publication publication, List<StudyDTO> studyDTOList) {
        this.publication = publication;
        this.studyDTOList = studyDTOList;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public List<StudyDTO> getStudyDTOList() {
        return studyDTOList;
    }

    public void setStudyDTOList(List<StudyDTO> studyDTOList) {
        this.studyDTOList = studyDTOList;
    }

    @Override
    public String toString() {
        return "FormResourceDTO{" +
            "publication=" + publication +
            ", studyDTOList=" + studyDTOList +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormResourceDTO that = (FormResourceDTO) o;

        if (publication != null ? !publication.equals(that.publication) : that.publication != null) return false;
        return studyDTOList != null ? studyDTOList.equals(that.studyDTOList) : that.studyDTOList == null;

    }

    @Override
    public int hashCode() {
        int result = publication != null ? publication.hashCode() : 0;
        result = 31 * result + (studyDTOList != null ? studyDTOList.hashCode() : 0);
        return result;
    }
}
