package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QDatProcess;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DatProcess extends BaseModel<DatProcess> {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "E0001,processId")
    private Integer processId;
    @NotEmpty(message = "E0001,workitemId")
    private String workitemId;
    private MgrTemplate mgrTemplate;
    private TemplateData processData;
    private String templateId;
    private AccessRight accessRight;

    public DatProcess() {
        this.setqObj(QDatProcess.newInstance());
    }

    /**
     * @param processId
     * @param workitemId
     * @param lastUpdateTime
     * @param templateId
     */
    public DatProcess(Integer processId, String workitemId, String templateId, String lastUpdateTime) {
        super();
        this.processId = processId;
        this.workitemId = workitemId;
        this.setLastUpdateTime(lastUpdateTime);
        this.templateId = templateId;
        this.setqObj(QDatProcess.newInstance());
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
     * @return the workitemId
     */
    public String getWorkitemId() {
        return workitemId;
    }

    /**
     * @param workitemId the workitemId to set
     */
    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    public MgrTemplate getMgrTemplate() {
        return mgrTemplate;
    }

    public void setMgrTemplate(MgrTemplate mgrTemplate) {
        this.mgrTemplate = mgrTemplate;
    }

    /**
     * @return the processData
     */
    public TemplateData getProcessData() {
        return processData;
    }

    /**
     * @param processData the processData to set
     */
    public void setProcessData(TemplateData processData) {
        this.processData = processData;
    }

    /**
     * @return the templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}
