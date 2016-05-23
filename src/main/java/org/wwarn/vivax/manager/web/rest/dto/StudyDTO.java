package org.wwarn.vivax.manager.web.rest.dto;

import org.wwarn.vivax.manager.domain.Publication;
import org.wwarn.vivax.manager.domain.SiteData;
import org.wwarn.vivax.manager.domain.Study;
import org.wwarn.vivax.manager.domain.Treatment;

import java.util.List;
import java.util.Set;

/**
 * Created by steven on 19/05/16.
 */
public class StudyDTO {

    private Study studies;
    private Set<SiteData> siteDatas;

    public Study getStudies() {
        return studies;
    }

    public void setStudies(Study studies) {
        this.studies = studies;
    }

    public Set<SiteData> getSiteDatas() {
        return siteDatas;
    }

    public void setSiteDatas(Set<SiteData> siteDatas) {
        this.siteDatas = siteDatas;
    }

    public StudyDTO() {
    }

    public StudyDTO(Study studies, Set<SiteData> siteDatas) {
        this.studies = studies;
        this.siteDatas = siteDatas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyDTO studyDTO = (StudyDTO) o;

        if (studies != null ? !studies.equals(studyDTO.studies) : studyDTO.studies != null) return false;
        return siteDatas != null ? siteDatas.equals(studyDTO.siteDatas) : studyDTO.siteDatas == null;

    }

    @Override
    public int hashCode() {
        int result = studies != null ? studies.hashCode() : 0;
        result = 31 * result + (siteDatas != null ? siteDatas.hashCode() : 0);
        return result;
    }


}
