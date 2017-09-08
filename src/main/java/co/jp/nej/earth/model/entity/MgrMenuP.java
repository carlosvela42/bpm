package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrMenuP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by minhtv on 4/3/2017.
 */
public class MgrMenuP extends BaseModel<MgrMenuP> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(MgrMenuP.class);

    private static final long serialVersionUID = 1L;
    private String functionId;
    private String profileId;
    private int accessAuthority;

    public MgrMenuP() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrMenuP.newInstance());
    }

    public MgrMenuP(String functionId, String profileId, int accessAuthority) {
        this();
        this.functionId = functionId;
        this.profileId = profileId;
        this.accessAuthority = accessAuthority;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getAccessAuthority() {
        return accessAuthority;
    }

    public void setAccessAuthority(int accessAuthority) {
        this.accessAuthority = accessAuthority;
    }
}
