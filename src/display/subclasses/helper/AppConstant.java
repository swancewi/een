package display.subclasses.helper;

import java.util.Arrays;
import java.util.List;

import android.os.Environment;
 
public class AppConstant {
 
    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;
 
    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp
 
    // SD card image directory
    public static final String PHOTO_ALBUM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM ).toString() +"/Camera";
 
    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
    
    public static final double screenRatio = 1;
    
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

}