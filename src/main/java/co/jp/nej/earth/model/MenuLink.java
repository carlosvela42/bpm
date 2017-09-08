package co.jp.nej.earth.model;

import co.jp.nej.earth.model.enums.LinkType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuLink implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SerializedName("linkType")
    private LinkType linkType;
    private String url;
    private String cssClass;

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

}
