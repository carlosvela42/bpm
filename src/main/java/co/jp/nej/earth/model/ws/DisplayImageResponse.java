package co.jp.nej.earth.model.ws;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.Message;

public class DisplayImageResponse extends Response {
    private Integer currentDocument;

    // Key is number documentNo, Value is Document tree.
    private Map<String, Document> documentMap = new LinkedHashMap<>();

    /**
     *
     */
    public DisplayImageResponse() {
        super();
    }

    /**
    *
    */
    public DisplayImageResponse(String message) {
        super(message);
    }

    public DisplayImageResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param currentDocument
     * @param documentMap
     */
    public DisplayImageResponse(Integer currentDocument, Map<String, Document> documentMap) {
        super();
        this.currentDocument = currentDocument;
        this.documentMap = documentMap;
    }

    /**
     * @return the currentDocument
     */
    public Integer getCurrentDocument() {
        return currentDocument;
    }

    /**
     * @param currentDocument the currentDocument to set
     */
    public void setCurrentDocument(Integer currentDocument) {
        this.currentDocument = currentDocument;
    }

    /**
     * @return the documentMap
     */
    public Map<String, Document> getDocumentMap() {
        return documentMap;
    }

    /**
     * @param documentMap the documentMap to set
     */
    public void setDocumentMap(Map<String, Document> documentMap) {
        this.documentMap = documentMap;
    }
}
