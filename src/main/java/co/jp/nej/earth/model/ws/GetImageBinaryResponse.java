package co.jp.nej.earth.model.ws;

import java.util.List;

import co.jp.nej.earth.model.Message;

public class GetImageBinaryResponse extends Response {
    private byte[] binaryData;
    /**
     *
     */
    public GetImageBinaryResponse() {
        super();
    }

    /**
    *
    */
    public GetImageBinaryResponse(String message) {
        super(message);
    }

    public GetImageBinaryResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param binaryData
     */
    public GetImageBinaryResponse(boolean result, byte[] binaryData) {
        super(result);
        this.binaryData = binaryData;
    }

    /**
     * @return the binaryData
     */
    public byte[] getBinaryData() {
        return binaryData;
    }

    /**
     * @param binaryData the binaryData to set
     */
    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
