package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class MgrUser extends BaseModel<MgrUser> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MgrUser.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String userId;
    private String name;
    private String password;
    private String confirmPassword;
    private boolean changePassword;

    public MgrUser() {
        LOG.debug("Call to blank contructor");
        this.setqObj(QMgrUser.newInstance());
    }


    public MgrUser(String userId, String name, String password, String confirmPassword, boolean changePassword,
                   String lastUpdateTime) {
        this();
        LOG.debug("Call to (userId, name, password, confirmPassword,changePassword) " + "contructor");
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.changePassword = changePassword;
        this.confirmPassword = confirmPassword;
        this.setLastUpdateTime(lastUpdateTime);
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }
}
