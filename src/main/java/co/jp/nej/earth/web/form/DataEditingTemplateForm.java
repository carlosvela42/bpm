package co.jp.nej.earth.web.form;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.util.EStringUtil;
import org.springframework.util.StringUtils;

/**
 * Form handle request param of Process
 *
 * @author DaoPQ
 * @version 1.0
 */
public class DataEditingTemplateForm {

    private String workspaceId;

    private String workItemId;

    private String templateId;

    private String templateName;

    private String templateTableName;

    private String templateType;

    private String templateField;

    private String templateData;

    private String eventType;

    private String accessRight;

    private String documentNo;

    private String folderItemNo;

    private String processId;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = StringUtils.trimWhitespace(processId);
    }

    public String getFolderItemNo() {
        return folderItemNo;
    }

    public void setFolderItemNo(String folderItemNo) {
        this.folderItemNo = folderItemNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = StringUtils.trimWhitespace(workspaceId);
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = StringUtils.trimWhitespace(workItemId);
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = StringUtils.trimWhitespace(templateId);
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = StringUtils.trimWhitespace(templateName);
    }

    public String getTemplateTableName() {
        return templateTableName;
    }

    public void setTemplateTableName(String templateTableName) {
        this.templateTableName = StringUtils.trimWhitespace(templateTableName);
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = StringUtils.trimWhitespace(templateType);
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = StringUtils.trimWhitespace(templateField);
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = StringUtils.trimWhitespace(templateData);
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(String accessRight) {
        this.accessRight = accessRight;
    }

    /**
     * Validate form
     *
     * @throws EarthException
     */
    public void validateForm() throws EarthException {
        validateEmpty(workspaceId, "workspaceId is null");
        validateEmpty(workItemId, "workItemId is null");
        if (!StringUtils.isEmpty(templateId)) {
            validateEmpty(templateName, "templateName is null");
            validateEmpty(templateTableName, "templateTableName is null");
            validateEmpty(templateType, "templateType is null");
            validateEmpty(templateField, "templateField is null");
            validateEmpty(templateData, "templateData is null");

            TemplateType templateTypeEnum = TemplateType.getByValue(EStringUtil.parseInt(templateType));
            if (templateTypeEnum == null) {
                throw new EarthException("invalid type. Must be in [PROCESS, WORKITEM, FORLDERITEM, DOCUMENT]");
            }
            switch (templateTypeEnum) {
                case PROCESS:
                    validateEmpty(processId, "processId is null");
                    break;
                case FOLDERITEM:
                    validateEmpty(folderItemNo, "processId is null");
                    break;
                case DOCUMENT:
                    validateEmpty(folderItemNo, "processId is null");
                    validateEmpty(documentNo, "documentNo is null");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Validate empty for item
     *
     * @param validateItem
     * @param message
     * @throws EarthException
     */
    protected void validateEmpty(String validateItem, String message) throws EarthException {
        if (StringUtils.isEmpty(validateItem)) {
            throw new EarthException(message);
        }
    }
}