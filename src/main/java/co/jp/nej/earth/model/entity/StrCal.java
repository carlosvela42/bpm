package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QStrCal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class StrCal extends BaseModel<StrCal> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(CtlLogin.class);
    /**
     * Serial number
     */
    private static final long serialVersionUID = 1L;
    private String division;
    private String processTime;
    private String profileId;
    private Integer availableLicenseCount;
    private Integer useLicenseCount = 0;
    private String lastUpdateTime;

    public StrCal() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QStrCal.newInstance());
    }

    public StrCal(String division, String processTime, String profileId, Integer availableLicenseCount,
                  Integer useLicenseCount) {
        this();
        LOG.debug("Call to (division, processTime, profileId, availableLicenseCount,"
                + "useLicenseCount) constructor");
        this.division = division;
        this.processTime = processTime;
        this.profileId = profileId;
        this.availableLicenseCount = availableLicenseCount;
        this.useLicenseCount = useLicenseCount;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Integer getAvailableLicenseCount() {
        return availableLicenseCount;
    }

    public void setAvailableLicenseCount(Integer availableLicenseCount) {
        this.availableLicenseCount = availableLicenseCount;
    }

    public Integer getUseLicenseCount() {
        return useLicenseCount;
    }

    public void setUseLicenseCount(Integer useLicenseCount) {
        this.useLicenseCount = useLicenseCount;
    }
    @Override
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
