package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.enums.AccessRight;

import java.io.Serializable;

public class MenuAccessRight implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MgrMenu mgrMenu;
    private AccessRight accessRight;

    public MgrMenu getMgrMenu() {
        return mgrMenu;
    }

    public void setMgrMenu(MgrMenu mgrMenu) {
        this.mgrMenu = mgrMenu;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}
