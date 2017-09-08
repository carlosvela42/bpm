package co.jp.nej.earth.model.ws;

import java.util.ArrayList;
import java.util.List;

import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.Message;

public class GetDocumentResponse extends Response {
    private List<Document> documentResult;

    /**
     *
     */
    public GetDocumentResponse() {
        super();
    }

    /**
    *
    */
    public GetDocumentResponse(String message) {
        super(message);
    }

    public GetDocumentResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param doc
     */
    public GetDocumentResponse(boolean result, Document doc) {
        super(result);
        if (documentResult == null) {
            documentResult = new ArrayList<>();
        }
        documentResult.add(doc);
    }

    /**
     * @param doc
     */
    public GetDocumentResponse(boolean result, List<Document> docs) {
        super();
        this.documentResult = docs;
        this.setResult(result);
    }

    /**
     * @return the documentResult
     */
    public List<Document> getDocumentResult() {
        return documentResult;
    }

    /**
     * @param documentResult the documentResult to set
     */
    public void setDocumentResult(List<Document> documentResult) {
        this.documentResult = documentResult;
    }

    /**
     * @param documentResult the documentResult to set
     */
    public void setDocumentResult(Document document) {
        if (documentResult == null) {
            documentResult = new ArrayList<>();
        }
        documentResult.add(document);
    }
}
