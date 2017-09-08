package co.jp.nej.earth.web.form;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Site;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class SiteForm extends BaseModel<Site> {
    @NotEmpty(message = "E0001,siteId")
    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDirectoryIds() {
        return directoryIds;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public void setDirectoryIds(String directoryIds) {
        this.directoryIds = directoryIds;
    }

    @NotEmpty(message = "E0005,directory")
    private String directoryIds;

    private List<Directory> directories;
}
