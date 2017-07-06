package mineward.core.common.utils;

import java.io.File;

/**
 * Created by alexc on 28/07/2016.
 */
public class UtilFile {

    public static float getFileSizeInMB(File fileName) {
        float ret = getFileSizeInBytes(fileName);
        ret = ret / (float) (1024 * 1024);
        return ret;
    }

    public static long getFileSizeInBytes(File fileName) {
        long ret = 0;
        File f = fileName;
        if (f.isFile()) {
            return f.length();
        } else if (f.isDirectory()) {
            File[] contents = f.listFiles();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i].isFile()) {
                    ret += contents[i].length();
                } else if (contents[i].isDirectory())
                    ret += getFileSizeInBytes(contents[i]);
            }
        }
        return ret;
    }

}
