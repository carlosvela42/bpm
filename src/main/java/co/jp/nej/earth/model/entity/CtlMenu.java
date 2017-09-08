package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QCtlMenu;

import java.io.Serializable;

public class CtlMenu extends BaseModel<CtlMenu> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String functionId;
    private String userId;
    private int accessAuthority;

    public CtlMenu() {
        this.setqObj(QCtlMenu.newInstance());
    }

    public CtlMenu(String functionId, String userId, int accessAuthority) {
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
