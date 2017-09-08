package co.jp.nej.earth.model.entity;

import java.util.List;

public class TemplateBusinessData {
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<String> getListItem() {
        return listItem;
    }

    public void setListItem(List<String> listItem) {
        this.listItem = listItem;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    private String templateName;
    private String createdDate;
    private List<String> listItem;
    private String pageNumber;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnknowName() {
        return unknowName;
    }

    public void setUnknowName(String unknowName) {
        this.unknowName = unknowName;
    }

    private String unknowName;


    // Main attribute
    private String formTypeCode;
    private String formTypeName;
    private String receiptNumber;
    private String documentType;
    private String documentNumber;
    private String userChangeCode;
    private String applicationMatter;
    private String documentStatus;

    public String getFormTypeCode() {
        return formTypeCode;
    }

    public void setFormTypeCode(String formTypeCode) {
        this.formTypeCode = formTypeCode;
    }

    public String getFormTypeName() {
        return formTypeName;
    }

    public void setFormTypeName(String formTypeName) {
        this.formTypeName = formTypeName;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getUserChangeCode() {
        return userChangeCode;
    }

    public void setUserChangeCode(String userChangeCode) {
        this.userChangeCode = userChangeCode;
    }

    public String getApplicationMatter() {
        return applicationMatter;
    }

    public void setApplicationMatter(String applicationMatter) {
        this.applicationMatter = applicationMatter;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    // ComboxItem
    private  String statusId;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private String statusName;
}
