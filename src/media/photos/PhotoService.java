package media.photos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhotoService extends Activity {
	

	public static final String CAMERA_IMAGE_BUCKET_NAME =
	        Environment.getExternalStorageDirectory().toString()
	        + "/DCIM/Camera";
	public static final String CAMERA_IMAGE_BUCKET_ID =
	        getBucketId(CAMERA_IMAGE_BUCKET_NAME);
	
	public static String getBucketId(String path) {
	    return String.valueOf(path.toLowerCase(Locale.US).hashCode());
	}
	private static int beforeCount;
	private static int afterCount;
	private static boolean checkForPhotos;

	private static List<String> getCameraImages(Context context) {
	    final String[] projection = { MediaStore.Images.Media.DATA };
	    final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
	    final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
	    final Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, 
	            projection, 
	            selection, 
	            selectionArgs, 
	            null);
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
	
	public static void startCamera(Activity activity) {
			
		Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
		//beforeCount = getCameraImages(activity).size();
		//checkForPhotos = true;
		activity.startActivityForResult(cameraIntent,1);
		//checkForPhotos = true;
	}
	
	/*public static void checkPhotos(Activity activity) {
		checkPhotos(activity, MainActivity.db.getActiveEquipmentType(), true);
	}*/
	
	/*public static void checkPhotos(Activity activity, EquipmentType eType, boolean isReal) {
	
		if(checkForPhotos) {
			
			Log.v("AddPhotoFragment", "Looking for photos");
			List<String> photoNames = getCameraImages(activity);
			afterCount = photoNames.size();
			if((afterCount - beforeCount)> 0) {
				Toast.makeText(activity, afterCount - beforeCount + " Photo(s) taken", Toast.LENGTH_SHORT).show();
				//It should really be taking an array of photo names
				for(int i=1; i<= (afterCount - beforeCount); i++) {
					//insert into database w/ building tag + equipment or light tag
					MainActivity.db.insertPhoto(photoNames.get(photoNames.size()-i));
				}
			}
			checkForPhotos = false;
		}
	}*/
}
