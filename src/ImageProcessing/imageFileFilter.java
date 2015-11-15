package ImageProcessing;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Roman on 29.09.2015.
 */
public class imageFileFilter extends FileFilter {
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = imageFilterUtils.getExtension(f);
            if (extension != null) {
                if (extension.equals(imageFilterUtils.bmp_1) ||
                        extension.equals(imageFilterUtils.bmp_2)||
                        extension.equals(imageFilterUtils.jpg_1)||
                        extension.equals(imageFilterUtils.jpg_2)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "*.bmp, *.jpg";
        }
}
