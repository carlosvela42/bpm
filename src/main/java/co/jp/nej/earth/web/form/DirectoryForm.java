package co.jp.nej.earth.web.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.form.BaseForm;
import org.hibernate.validator.constraints.NotEmpty;

public class DirectoryForm extends BaseForm<Directory> {

    private static final long serialVersionUID = 1L;

    private String dataDirectoryId;

    @NotEmpty(message = "E0001,disk.vol")
    private String diskVolSize;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_9,
        messageNotEmpty = "E0001,reserved.disk.vol",
        messageLengthMax = "E0026,reserved.disk.vol," + Constant.Regexp.MAX_9)
    private String reservedDiskVolSize;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,folder.path",
        messageLengthMax = "E0026,folder.path," + Constant.Regexp.MAX_LENGTH)
    private String folderPath;

    @NotEmpty(message = "E0001,create.new.file")
    private String newCreateFile;

    public String getDataDirectoryId() {
        return dataDirectoryId;
    }

    public void setDataDirectoryId(String dataDirectoryId) {
        this.dataDirectoryId = dataDirectoryId;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getNewCreateFile() {
        return newCreateFile;
    }

    public void setNewCreateFile(String newCreateFile) {
        this.newCreateFile = newCreateFile;
    }

    public String getReservedDiskVolSize() {
        return reservedDiskVolSize;
    }

    public void setReservedDiskVolSize(String reservedDiskVolSize) {
        this.reservedDiskVolSize = reservedDiskVolSize;
    }

    public String getDiskVolSize() {
        return diskVolSize;
    }

    public void setDiskVolSize(String diskVolSize) {
        this.diskVolSize = diskVolSize;
    }

}
