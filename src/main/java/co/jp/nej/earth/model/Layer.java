package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QLayer;
import org.hibernate.validator.constraints.NotEmpty;

public class Layer extends BaseModel<Layer> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String workitemId;
    private String folderItemNo;
    private String documentNo;
    @NotEmpty(message = "E0001,layerNo")
    private String layerNo;
    private Integer layerOrder;
    private String layerName;
    private MgrTemplate mgrTemplate;
    private String ownerId;
    private Integer action;
    private TemplateData layerData;
    private String templateId;
    private String annotations;
    private String insertDateTime;
    private AccessRight accessRight;

    public Layer() {
        this.setqObj(QLayer.newInstance());
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

    /**
     * @return the folderItemNo
     */
    public String getFolderItemNo() {
        return folderItemNo;
    }

    /**
     * @param folderItemNo the folderItemNo to set
     */
    public void setFolderItemNo(String folderItemNo) {
        this.folderItemNo = folderItemNo;
    }

    /**
     * @return the documentNo
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    /**
     * @return the layerNo
     */
    public String getLayerNo() {
        return layerNo;
    }

    /**
     * @param layerNo the layerNo to set
     */
    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }

    public MgrTemplate getMgrTemplate() {
        return mgrTemplate;
    }

    public void setMgrTemplate(MgrTemplate mgrTemplate) {
        this.mgrTemplate = mgrTemplate;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the action
     */
    public Integer getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * @return the layerData
     */
    public TemplateData getLayerData() {
        return layerData;
    }

    /**
     * @param layerData the layerData to set
     */
    public void setLayerData(TemplateData layerData) {
        this.layerData = layerData;
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

    /**
     * @return the annotations
     */
    public String getAnnotations() {
        return annotations;
    }

    /**
     * @param annotations the annotations to set
     */
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    /**
     * @return the layerName
     */
    public String getLayerName() {
        return layerName;
    }

    /**
     * @param layerName the layerName to set
     */
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    /**
     * @return the insertDateTime
     */
    public String getInsertDateTime() {
        return insertDateTime;
    }

    /**
     * @param insertDateTime the insertDateTime to set
     */
    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public Integer getLayerOrder() {
        return layerOrder;
    }

    public void setLayerOrder(Integer layerOrder) {
        this.layerOrder = layerOrder;
    }

}
