package display.subclasses.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Utils {
	 
	
    private static Context _context;
    public static final String CAMERA_IMAGE_BUCKET_ID =
            AppConstant.getBucketId(AppConstant.PHOTO_ALBUM);
    public static final String AUDIO_FILE_DIRECTORY = Environment.getExternalStorageDirectory().toString() + File.separator + "EAAudio";
    
    // constructor
    public static void setContext(Context context) {
        Utils._context = context;
    }
    
    //get all camera images
    public static ArrayList<String> getCameraImages() {
    	final String[] projection = {MediaStore.Images.Media.DATA};
    	final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
    	final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
    	final Cursor cursor = _context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI,projection,selection, selectionArgs,null);
    	ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
    	return result;
    }
    
    public static ArrayList<String> getAudioFiles() {
    	
    	File f = new File(AUDIO_FILE_DIRECTORY);        
    	File file[] = f.listFiles();
    	ArrayList<String> audioFiles = new ArrayList<String>();

    	for (int i=0; i < file.length; i++)
    	{
    	    audioFiles.add(file[i].getName());
    	}
              
        return audioFiles;
        
    }
 
    /*
     * getting screen width
     */
    @SuppressLint("NewApi")
	public static int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        display.getSize(point);
        columnWidth = point.x;
        return columnWidth;
    }
}
