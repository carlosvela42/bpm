package co.jp.nej.earth.model;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DocumentDataSavePath;
import co.jp.nej.earth.model.sql.QMgrProcess;

/**
 * @author p-tvo-sonta
 */
public class MgrProcess extends BaseModel<MgrProcess> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer processId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,process.name",
        messageLengthMax = "E0026,process.name,255")
    private String processName;

    private Float processVersion;

    @NotEmptyAndValidSize(max = Constant.Regexp.MAX_LENGTH,
        messageLengthMax = "E0026,process.description,255")
    private String description;
    private String processDefinition;
    private String documentDataSavePath;

    public MgrProcess() {
        this.setqObj(QMgrProcess.newInstance());
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
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @return the processVersion
     */
    public Float getProcessVersion() {
        return processVersion;
    }

    /**
     * @param processVersion the processVersion to set
     */
    public void setProcessVersion(Float processVersion) {
        this.processVersion = processVersion;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the processDefinition
     */
    public String getProcessDefinition() {
        return processDefinition;
    }

    /**
     * @param processDefinition the processDefinition to set
     */
    public void setProcessDefinition(String processDefinition) {
        this.processDefinition = processDefinition;
    }

    /**
     * @return the documentDataSavePath
     */
    public String getDocumentDataSavePath() {
        return documentDataSavePath;
    }

    /**
     * @param documentDataSavePath the documentDataSavePath to set
     */
    public void setDocumentDataSavePath(String documentDataSavePath) {
        this.documentDataSavePath = documentDataSavePath;
    }

//    Custom method
    public boolean isSavePathDatabaseType() {
        return String.valueOf(DocumentDataSavePath.DATABASE.getId()).equals(this.getDocumentDataSavePath());
    }
}
