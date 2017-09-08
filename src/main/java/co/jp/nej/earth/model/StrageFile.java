package co.jp.nej.earth.model;

import co.jp.nej.earth.model.enums.DocumentSavingType;
import co.jp.nej.earth.model.sql.QStrageFile;

import javax.validation.constraints.NotNull;

/**
 * @author p-tvo-sonta
 */
public class StrageFile extends BaseModel<StrageFile> {

    /**
     * serial number
     */
    private static final long serialVersionUID = 1L;

    private Integer processId;
    @NotNull(message = "E0001,process.SiteID")
    private Integer siteId;
    private String siteManagementType = DocumentSavingType.FILE_UNTIL_FULL.getValue();

    public StrageFile() {
        super();
        this.setqObj(QStrageFile.newInstance());
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    /**
     * @return the siteId
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the siteManagementType
     */
    public String getSiteManagementType() {
        return siteManagementType;
    }

    /**
     * @param siteManagementType the siteManagementType to set
     */
    public void setSiteManagementType(String siteManagementType) {
        //todo: siteManagementType is only 1 or 2
        if(siteManagementType != null){
            this.siteManagementType = siteManagementType;
        }
    }

}