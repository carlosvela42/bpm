package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrMenu;

import java.io.Serializable;
import java.util.List;

/**
 * Created Stucture MgrMenu
 * MgrMenu Parent
 * List MgrMenu
 */
public class MenuStructure implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String menuParentId;
    private String menuParentName;
    private String cssClass;
    private List<MgrMenu> mgrMenus;

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuParentName() {
        return menuParentName;
    }

    public void setMenuParentName(String menuParentName) {
        this.menuParentName = menuParentName;
    }

    public List<MgrMenu> getMgrMenus() {
        return mgrMenus;
    }

    public void setMgrMenus(List<MgrMenu> mgrMenus) {
        this.mgrMenus = mgrMenus;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
