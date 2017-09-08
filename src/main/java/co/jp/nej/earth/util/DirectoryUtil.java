package co.jp.nej.earth.util;

import co.jp.nej.earth.model.constant.Constant;

import java.io.File;

public class DirectoryUtil {

    public static long getSizeFolder(String folderPath) {
        if(folderPath == null){
            return 0;
        }

        File file = new File(folderPath);
        long freeSpace = 0L;
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }

        }
        if (file.exists()) {
            freeSpace = file.getFreeSpace(); // free disk space in bytes.
            freeSpace = freeSpace / (Constant.Directory.CONVERT);
        }
        return freeSpace;
    }
}
