/**
 *
 */
package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.util.EStringUtil;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-tvo-khanhnv
 */
public class FolderItem extends BaseModel<FolderItem> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String workitemId;
    @NotEmpty(message = "E0001,folderItemNo")
    private String folderItemNo;
    private Integer folderItemOrder;
    private MgrTemplate mgrTemplate;
    private Integer action;
    private TemplateData folderItemData;
    private String templateId;
    private List<Document> documents = new ArrayList<Document>();
    private AccessRight accessRight;

    public FolderItem() {
        this.setqObj(QFolderItem.newInstance());
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

    public MgrTemplate getMgrTemplate() {
        return mgrTemplate;
    }

    public void setMgrTemplate(MgrTemplate mgrTemplate) {
        this.mgrTemplate = mgrTemplate;
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
     * @return the folderItemData
     */
    public TemplateData getFolderItemData() {
        return folderItemData;
    }

    /**
     * @param folderItemData the folderItemData to set
     */
    public void setFolderItemData(TemplateData folderItemData) {
        this.folderItemData = folderItemData;
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
     * @return the documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        if (document != null && (!EStringUtil.isEmpty(document.getDocumentNo())) && (!documents.contains(document))) {
            documents.add(document);
        }
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public Integer getFolderItemOrder() {
        return folderItemOrder;
    }

    public void setFolderItemOrder(Integer folderItemOrder) {
        this.folderItemOrder = folderItemOrder;
    }
}
