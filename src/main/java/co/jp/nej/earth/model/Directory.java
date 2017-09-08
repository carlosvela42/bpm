package co.jp.nej.earth.model;

import co.jp.nej.earth.model.sql.QDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author p-tvo-sonta
 */
public class Directory extends BaseModel<Directory> {

    /**
     * serial number
     */
    private static final Logger LOG = LoggerFactory.getLogger(Directory.class);
    private static final long serialVersionUID = 1L;
    private int dataDirectoryId;
    private String folderPath;
    private int newCreateFile;
    private String reservedDiskVolSize;
    private String diskVolSize;
    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Directory() {
        this.setqObj(QDirectory.newInstance());
    }

    public Directory(int dataDirectoryId, String folderPath, int newCreateFile, String reservedDiskVolSize,
                     String diskVolSize, Boolean checked) {
        super();

        LOG.debug("Call to (dataDirectoryId, folderPath, newCreateFile, reservedDiskVolSize," + "diskVolSize");

        this.dataDirectoryId = dataDirectoryId;
        this.folderPath = folderPath;
        this.newCreateFile = newCreateFile;
        this.reservedDiskVolSize = reservedDiskVolSize;
        this.diskVolSize = diskVolSize;
        this.checked = checked;
    }

    /**
     * @return the dataDirectoryId
     */
    public int getDataDirectoryId() {
        return dataDirectoryId;
    }

    /**
     * @param dataDirectoryId the dataDirectoryId to set
     */
    public void setDataDirectoryId(int dataDirectoryId) {
        this.dataDirectoryId = dataDirectoryId;
    }

    /**
     * @return the folderPath
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * @param folderPath the folderPath to set
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * @return the newCreateFile
     */
    public int getNewCreateFile() {
        return newCreateFile;
    }

    /**
     * @param newCreateFile the newCreateFile to set
     */
    public void setNewCreateFile(int newCreateFile) {
        this.newCreateFile = newCreateFile;
    }

    /**
     * @return the reservedDiskVolSize
     */
    public String getReservedDiskVolSize() {
        return reservedDiskVolSize;
    }

    /**
     * @param reservedDiskVolSize the reservedDiskVolSize to set
     */
    public void setReservedDiskVolSize(String reservedDiskVolSize) {
        this.reservedDiskVolSize = reservedDiskVolSize;
    }

    /**
     * @return the diskVolSize
     */
    public String getDiskVolSize() {
        return diskVolSize;
    }

    /**
     * @param diskVolSize the diskVolSize to set
     */
    public void setDiskVolSize(String diskVolSize) {
        this.diskVolSize = diskVolSize;
    }

}