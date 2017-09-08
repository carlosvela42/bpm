package co.jp.nej.earth.model;

public class DocumentResponse {
    private Document document;
    private int rowNum;
    private int rowCount;

    public DocumentResponse(Document document, int rowNum, int rowCount) {
        super();
        this.document = document;
        this.rowNum = rowNum;
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}
