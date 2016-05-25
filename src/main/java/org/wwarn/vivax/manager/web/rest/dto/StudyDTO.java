package org.wwarn.vivax.manager.web.rest.dto;

import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;

import java.util.Set;

/**
 * Created by steven on 19/05/16.
 * Class created to contain all studies and child collections
 * (SiteDatas and SiteDatas Treatments collection)
 */
public class StudyDTO {

    private Study studyDetails;
    private Set<SiteData> siteDatas;

    public Study getStudyDetails() {
        return studyDetails;
    }

    public void setStudyDetails(Study studyDetails) {
        this.studyDetails = studyDetails;
    }

    public Set<SiteData> getSiteDatas() {
        return siteDatas;
    }

    public void setSiteDatas(Set<SiteData> siteDatas) {
        this.siteDatas = siteDatas;
    }

    public StudyDTO() {
    }

    public StudyDTO(Study studyDetails, Set<SiteData> siteDatas) {
        this.studyDetails = studyDetails;
        this.siteDatas = siteDatas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyDTO studyDTO = (StudyDTO) o;

        if (studyDetails != null ? !studyDetails.equals(studyDTO.studyDetails) : studyDTO.studyDetails != null) return false;
        return siteDatas != null ? siteDatas.equals(studyDTO.siteDatas) : studyDTO.siteDatas == null;

    }

    @Override
    public int hashCode() {
        int result = studyDetails != null ? studyDetails.hashCode() : 0;
        result = 31 * result + (siteDatas != null ? siteDatas.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "StudyDTO{" +
            "studyDetails=" + studyDetails +
            ", siteDatas=" + siteDatas +
            '}';
    }
}
