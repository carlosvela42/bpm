package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrProfile;

import java.io.Serializable;

public class MgrProfile extends BaseModel<MgrProfile> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String profileId;
    private int availableLicenceCount;
    private String description;
    private String ldapIdentifier;

    public MgrProfile() {
        this.setqObj(QMgrProfile.newInstance());
    }

    public MgrProfile(String profileId, int availableLicenceCount, String description, String ldapIdentifier) {
        this();
        this.profileId = profileId;
        this.availableLicenceCount = availableLicenceCount;
        this.description = description;
        this.ldapIdentifier = ldapIdentifier;
    }

    public MgrProfile(String profileId, int availableLicenceCount, String description, String ldapIdentifier,
                      String lastUpdateTime) {
        this();
        this.profileId = profileId;
        this.availableLicenceCount = availableLicenceCount;
        this.description = description;
        this.ldapIdentifier = ldapIdentifier;
        this.setLastUpdateTime(lastUpdateTime);
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getAvailableLicenceCount() {
        return availableLicenceCount;
    }

    public void setAvailableLicenceCount(int availableLicenceCount) {
        this.availableLicenceCount = availableLicenceCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLdapIdentifier() {
        return ldapIdentifier;
    }

    public void setLdapIdentifier(String ldapIdentifier) {
        this.ldapIdentifier = ldapIdentifier;
    }

}
