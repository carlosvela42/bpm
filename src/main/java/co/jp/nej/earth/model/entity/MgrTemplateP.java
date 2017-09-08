package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrTemplateP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class MgrTemplateP extends BaseModel<MgrTemplateP> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MgrTemplateP.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String profileId;
    private String templateId;
    private int accessAuthority;

    public MgrTemplateP() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrTemplateP.newInstance());
    }

    public MgrTemplateP(String profileId, String templateId, int accessAuthority) {
        this();
        LOG.debug("Call to (profileId, templateId, accessAuthority) constructor");
        this.profileId = profileId;
        this.templateId = templateId;
        this.accessAuthority = accessAuthority;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getAccessAuthority() {
        return accessAuthority;
    }

    public void setAccessAuthority(int accessAuthority) {
        this.accessAuthority = accessAuthority;
    }
}
