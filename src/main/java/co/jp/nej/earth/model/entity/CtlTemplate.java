package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QCtlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public class CtlTemplate extends BaseModel<CtlTemplate> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CtlTemplate.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String userId;
    private String templateId;
    private int accessAuthority;

    public CtlTemplate() {
        LOG.debug("Call to blank contructor");
        this.setqObj(QCtlTemplate.newInstance());
    }

    public CtlTemplate(String userId, String templateId, int accessAuthority) {
        this();
        LOG.debug("Call to (userId, templateId, accessAuthority) " + "contructor");
        this.userId = userId;
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
