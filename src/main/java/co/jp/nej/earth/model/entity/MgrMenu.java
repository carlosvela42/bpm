package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.MenuLink;
import co.jp.nej.earth.model.sql.QMgrMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class MgrMenu extends BaseModel<MgrMenu> implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(MgrMenu.class);

    private static final long serialVersionUID = 1L;
    private String functionId;
    private String functionName;
    private String functionCategoryId;
    private String functionCategoryName;
    private int functionSortNo;
    private String functionInformation;
    private MenuLink menuInformation;

    public MgrMenu() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrMenu.newInstance());
    }

    public MgrMenu(String functionId, String functionName, String functionCategoryId, String functionCategoryName,
                   int functionSortNo, String functionInformation) {
        this();
        LOG.debug(
                "Call to (functionId, functionName, functionCategoryId, functionCategoryName, functionSortNo, "
                        + "functionInformation, menuInformation) constructor");
        this.functionId = functionId;
        this.functionName = functionName;
        this.functionCategoryId = functionCategoryId;
        this.functionCategoryName = functionCategoryName;
        this.functionSortNo = functionSortNo;
        this.functionInformation = functionInformation;
        this.menuInformation = menuInformation;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCategoryId() {
        return functionCategoryId;
    }

    public void setFunctionCategoryId(String functionCategoryId) {
        this.functionCategoryId = functionCategoryId;
    }

    public String getFunctionCategoryName() {
        return functionCategoryName;
    }

    public void setFunctionCategoryName(String functionCategoryName) {
        this.functionCategoryName = functionCategoryName;
    }

    public int getFunctionSortNo() {
        return functionSortNo;
    }

    public void setFunctionSortNo(int functionSortNo) {
        this.functionSortNo = functionSortNo;
    }

    public String getFunctionInformation() {
        return functionInformation;
    }

    public void setFunctionInformation(String functionInformation) {
        this.functionInformation = functionInformation;
    }

    public MenuLink getMenuInformation() {
        return menuInformation;
    }

    public void setMenuInformation(MenuLink menuInformation) {
        this.menuInformation = menuInformation;
    }
}
