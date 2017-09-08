package co.jp.nej.earth.model;

import co.jp.nej.earth.model.enums.AccessRight;

import java.io.Serializable;

public class ProfileAccessRight implements Serializable {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;
    private String profileId;
    private AccessRight accessRight;
    private Integer accessRightValue;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public Integer getAccessRightValue() {
        return accessRightValue;
    }

    public void setAccessRightValue(Integer accessRightValue) {
        this.accessRightValue = accessRightValue;
    }
}
