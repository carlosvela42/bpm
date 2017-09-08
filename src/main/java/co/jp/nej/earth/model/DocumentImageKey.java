package co.jp.nej.earth.model;

public class DocumentImageKey {
    private String workitemId;
    private Integer folderItemNo;
    private Integer documentNo;

    public DocumentImageKey() {
    }

    public DocumentImageKey(String workitemId, Integer folderItemNo, Integer documentNo) {
        this.workitemId = workitemId;
        this.folderItemNo = folderItemNo;
        this.documentNo = documentNo;
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
    public Integer getFolderItemNo() {
        return folderItemNo;
    }

    /**
     * @param folderItemNo the folderItemNo to set
     */
    public void setFolderItemNo(Integer folderItemNo) {
        this.folderItemNo = folderItemNo;
    }

    /**
     * @return the documentNo
     */
    public Integer getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(Integer documentNo) {
        this.documentNo = documentNo;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((documentNo == null) ? 0 : documentNo.hashCode());
        result = prime * result + ((folderItemNo == null) ? 0 : folderItemNo.hashCode());
        result = prime * result + ((workitemId == null) ? 0 : workitemId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DocumentImageKey other = (DocumentImageKey) obj;
        if (documentNo == null) {
            if (other.documentNo != null) {
                return false;
            }
        } else if (!documentNo.equals(other.documentNo)) {
            return false;
        }
        if (folderItemNo == null) {
            if (other.folderItemNo != null) {
                return false;
            }
        } else if (!folderItemNo.equals(other.folderItemNo)) {
            return false;
        }
        if (workitemId == null) {
            if (other.workitemId != null) {
                return false;
            }
        } else if (!workitemId.equals(other.workitemId)) {
            return false;
        }
        return true;
    }

    ;

}
