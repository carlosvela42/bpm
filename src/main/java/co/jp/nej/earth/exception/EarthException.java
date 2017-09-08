package co.jp.nej.earth.exception;

import co.jp.nej.earth.model.Message;

public class EarthException extends Exception {
    private String errorCode;
    private Message errorMessage;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EarthException() {
        super();
    }

    public EarthException(String message) {
        super(message);
    }

    public EarthException(Message errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public EarthException(Exception e) {
        super(e);
    }

    public EarthException(String code, Exception e) {
        super(e);
        this.errorCode = code;
    }

    public Message getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(Message errorMessage) {
        this.errorMessage = errorMessage;
    }
}