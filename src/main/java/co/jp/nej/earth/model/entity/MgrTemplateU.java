package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrTemplateU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by minhtv on 3/29/2017.
 */
public class MgrTemplateU extends BaseModel<MgrTemplateU> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MgrTemplateU.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String userId;
    private String templateId;
    private int accessAuthority;

    public MgrTemplateU() {

        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrTemplateU.newInstance());
    }

    public MgrTemplateU(String userId, String templateId, int accessAuthority) {
        this();
        LOG.debug("Call to (userId, templateId, accessAuthority) constructor");
        this.userId = userId;
        this.templateId = templateId;
        this.accessAuthority = accessAuthority;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
