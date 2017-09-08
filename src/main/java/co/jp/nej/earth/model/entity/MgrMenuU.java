package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrMenuU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by minhtv on 3/29/2017.
 */
public class MgrMenuU extends BaseModel<MgrMenuU> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(MgrMenuU.class);

    private static final long serialVersionUID = 1L;
    private String functionId;
    private String userId;
    private int accessAuthority;

    public MgrMenuU() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrMenuU.newInstance());
    }

    public MgrMenuU(String functionId, String userId, int accessAuthority) {
        this();
        this.functionId = functionId;
        this.userId = userId;
        this.accessAuthority = accessAuthority;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAccessAuthority() {
        return accessAuthority;
    }

    public void setAccessAuthority(int accessAuthority) {
        this.accessAuthority = accessAuthority;
    }
}
