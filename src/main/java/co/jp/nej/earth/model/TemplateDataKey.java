package co.jp.nej.earth.model;

import java.io.Serializable;

import co.jp.nej.earth.model.enums.TemplateType;

public class TemplateDataKey implements Serializable {
    public static final int NUM_PROCESS_DATA_KEY = 5;
    public static final int NUM_WORKITEM_DATA_KEY = 4;
    public static final int NUM_FOLDERITEM_DATA_KEY = 5;
    public static final int NUM_DOCUMENT_DATA_KEY = 6;
    public static final int NUM_LAYER_DATA_KEY = 7;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String processId;
    private String workItemId;
    private String folderItemNo;
    private String documentNo;
    private String layerNo;

    private String templateId;
    private String templateType;
    public TemplateDataKey() {
    }


    /**
     * @param processId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @param layerNo
     */
    public TemplateDataKey(String processId, String workItemId, String folderItemNo, String documentNo,
            String layerNo) {
        super();
        this.processId = processId;
        this.workItemId = workItemId;
        this.folderItemNo = folderItemNo;
        this.documentNo = documentNo;
        this.layerNo = layerNo;
    }

    public static int getNumTemplateKeys(String templateType) {
        if (TemplateType.isProcess(Integer.parseInt(templateType))) {
            return NUM_PROCESS_DATA_KEY;
        }
        if (TemplateType.isDocument(Integer.parseInt(templateType))) {
            return NUM_DOCUMENT_DATA_KEY;
        }
        if (TemplateType.isFolderItem(Integer.parseInt(templateType))) {
            return NUM_FOLDERITEM_DATA_KEY;
        }
        if (TemplateType.isLayer(Integer.parseInt(templateType))) {
            return NUM_LAYER_DATA_KEY;
        }
        return NUM_WORKITEM_DATA_KEY;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }


    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }


    /**
     * @return the workItemId
     */
    public String getWorkItemId() {
        return workItemId;
    }


    /**
     * @param workItemId the workItemId to set
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
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


    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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
     * @return the templateType
     */
    public String getTemplateType() {
        return templateType;
    }


    /**
     * @param templateType the templateType to set
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}
