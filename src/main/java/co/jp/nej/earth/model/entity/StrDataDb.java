package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QStrDataDb;

public class StrDataDb extends BaseModel<StrDataDb> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String workitemId;
    private String folderItemNo;
    private String documentNo;
    private String documentData;

    public StrDataDb() {
        this.setqObj(QStrDataDb.newInstance());
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
     * @return the documentData
     */
    public String getDocumentData() {
        return documentData;
    }

    /**
     * @param documentData the documentData to set
     */
    public void setDocumentData(String documentData) {
        this.documentData = documentData;
    }

}
