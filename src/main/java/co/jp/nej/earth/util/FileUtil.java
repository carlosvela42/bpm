package co.jp.nej.earth.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.jp.nej.earth.batch.AgentBatch;
import co.jp.nej.earth.model.Directory;

public class FileUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AgentBatch.class);

    /** get file size
     *
     * @param file
     * @return */
    public static long getFileSize(File file) {
        return file.length();
    }

    /** get directory size
     *
     * @param directory
     * @return */
    public static long getDirectorySize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += getDirectorySize(file);
            }
        }
        return length;
    }

    /** get directory used minimum storage
     *
     * @param directories
     * @return */
    public static Directory getDirectoryUsedStorageMin(List<Directory> directories) {
        Directory result = null;
        File fileResult = null;
        for (Directory directory : directories) {
            File fileDirectory = new File(directory.getFolderPath());
            if (result == null) {
                result = directory;
                fileResult = fileDirectory;
            } else if (fileResult.getTotalSpace() > fileDirectory.getTotalSpace()) {
                result = directory;
                fileResult = fileDirectory;
            }
        }
        return result;
    }

    public static byte[] convertFileToBinary(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(path);

    }

    /* delete list of file
     *
     * @param fileList
     * @throws IOException */
    public static void deleteFiles(List<String> fileList) throws IOException {
        for (String file : fileList) {
            Files.delete(Paths.get(file));
        }
    }

    /*
     * Rename the file to newFileName
     */
    public static boolean rename(String filename, String newFilename) {
        boolean success = true;
        Path source = Paths.get(filename);
        try {
            Files.move(source, source.resolveSibling(newFilename));
        } catch (IOException e) {
            success = false;
            LOG.error(e.getMessage(),e);
        }
        return success;
    }

}
