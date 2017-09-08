package co.jp.nej.earth.exception;

/**
 * Created by cuongtm on 2017/08/17.
 */
public class ConnectionFailedException extends EarthException {
    private String workspaceId;
    public ConnectionFailedException() {
        super();
    }

    public ConnectionFailedException(Exception ex, String workspaceId) {
        super(ex);
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }
}
