package ImageProcessing;

import java.io.File;

/**
 * Created by Roman on 29.09.2015.
 */
public class imageFilterUtils {
    public final static String bmp_1 = "bmp";
    public final static String bmp_2 = "BMP";
    public final static String jpg_1 = "jpg";
    public final static String jpg_2 = "JPG";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
