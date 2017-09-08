package co.jp.nej.earth.model.ws;

import java.util.ArrayList;
import java.util.List;

import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Message;

public class GetFolderItemResponse extends Response {
    private List<FolderItem> folderItemResult;

    /**
     *
     */
    public GetFolderItemResponse() {
        super();
    }

    /**
    *
    */
    public GetFolderItemResponse(String message) {
        super(message);
    }

    public GetFolderItemResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param doc
     */
    public GetFolderItemResponse(boolean result, FolderItem folderItem) {
        super();
        if (folderItemResult == null) {
            folderItemResult = new ArrayList<>();
        }
        folderItemResult.add(folderItem);
        this.setResult(result);
    }

    /**
     * @return the folderItemResult
     */
    public List<FolderItem> getFolderItemResult() {
        return folderItemResult;
    }

    /**
     * @param folderItemResult the folderItemResult to set
     */
    public void setFolderItemResult(List<FolderItem> folderItemResult) {
        this.folderItemResult = folderItemResult;
    }
}
