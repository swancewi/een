package display.Audit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.EEN.Audit.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import constants.GeneralFunctions;
import constants.Strings;
import display.subclasses.OnCustomEventListener;
import display.subclasses.PhotoAdapter;
import display.subclasses.SelectActiveEquipmentView;
import display.subclasses.helper.AppConstant;
import display.subclasses.helper.Utils;
import fsTablesClasses.Measure;
import fsTablesClasses.SiteMedia;
import fsTablesEnums.EquipmentType;
import service.CameraObserverService;

public class AddPhotoFragment extends Fragment {

	private View view;
	private ArrayAdapter<String> photoTypeTagAdapter;
	private ArrayAdapter<String> measureTagAdapter;
	protected Spinner photoTypeTagSpinner;
	protected Spinner measureTagSpinner;
	protected EditText notesEditText;
    protected TextView countTextView;
	protected ImageButton addMeasureButton;
    private Intent fileSaverIntent;
    private static Utils utils;
    private BroadcastReceiver receiver;
    //private Context mContext;


    private int beforeCount;
//	private int afterCount;
	private List<String> photoNames;
    protected Timer timer;
	protected EquipmentType equipmentType;
	protected Button submitButton;
	protected SelectActiveEquipmentView equipmentPicker;
    private static final int MULTIPLE_PHOTOS = 111;
 //   private static final String PREFS_NAME = "myPhotoFragmentSettings";
 //   private static final String BEFORE_COUNT_SETTING = "beforePhotoCount";
	private final String TAG = "AddPhotoFragment";

	private boolean itemUpdated = false;

	public static final String CAMERA_IMAGE_BUCKET_NAME =
	        Environment.getExternalStorageDirectory().toString()
	        + "/DCIM/camera/";
	public static final String CAMERA_IMAGE_BUCKET_ID =
	        getBucketId(CAMERA_IMAGE_BUCKET_NAME);

	public AddPhotoFragment() {

    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.add_photo, container, false);
		setRetainInstance(true);

        Log.v(TAG, "just starting");
		equipmentPicker = (SelectActiveEquipmentView) view.findViewById(R.id.selectActEquipment);
		equipmentPicker.setFirstLineAddEditSpinner(Strings.GENERAL_TYPE, true);
		equipmentPicker.setActiveEquipmentListener(new OnCustomEventListener() {
            @Override
            public void onEvent() {
                if (itemUpdated) {
                    Log.v(TAG, "updating photocount");
                    updatePhotoCountText();
                    Log.v(TAG, "updated photocount");
                    updateMeasureSpinner();
                    Log.v(TAG, "finished updating text");
                }

                //Log.v(this.toString(), "Equipment Type:" + equipmentPicker.getActiveEquipmentType());
                itemUpdated = true;
            }
        });
		equipmentPicker.setAllowAddSimpleEquipment(true);
        equipmentPicker.setTrackActiveEquipment(true);
        equipmentPicker.setAllowFakeAssets(true);

        Log.v(TAG, "setup equipmentPicker");
        //setup equipment spinner
		photoTypeTagSpinner = (Spinner) view.findViewById(R.id.spinnerPhotoTagType);
		measureTagSpinner = (Spinner) view.findViewById(R.id.spinnerMeasureTagType);
		notesEditText = (EditText) view.findViewById(R.id.photoNotesEditText);

		photoTypeTagAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, Strings.PHOTO_TAG_TYPES);
		photoTypeTagSpinner.setAdapter(photoTypeTagAdapter);

        Log.v(TAG, "setup internal stuff");

		//updateMeasureSpinner();

        Log.v(TAG, "setup measure spinner");

		submitButton = (Button) view.findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.v(TAG, "submit button pressed");
                submitButtonPressed();
            }
        });

        countTextView = (TextView) view.findViewById(R.id.photoCountText);

		addMeasureButton = (ImageButton) view.findViewById(R.id.addMeasureButton);
		addMeasureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AddMeasurePopup();
			}
		});

        timer = new Timer();

        /*IntentFilter filter = new IntentFilter();
        filter.addAction("TEST_PHOTO_ADDED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(TAG, "broadcast received");
                //adapter.notifyChange();
            }
        };

        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(receiver, filter);*/
        //mContext = this.getActivity();

        return view;
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        updatePhotoCountText();
        WaitForPhotosPopup();

        //Log.v(TAG, "onActivityResult, requestCode: " + requestCode + " resultCode: " + resultCode);
        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // run AsyncTask here.
                getActivity().stopService(fileSaverIntent);
                Log.v(TAG, "service stopped");
                //updatePhotoCountText();
            }
        }, 15000);*/
        /*if(requestCode == MULTIPLE_PHOTOS) {

            //retrieve photo settings
            //SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            //beforeCount = settings.getInt(BEFORE_COUNT_SETTING,0);
            //Log.v(TAG, "onResume, # of prePhotos: " + beforeCount);

            //Log.v(TAG, "onActivityResult, Looking for photos");
            //photoNames = getCameraImages(getActivity());
            //afterCount = photoNames.size();
            //Log.v(TAG, "onResume, # of postPhotos: " + afterCount);
            //Log.v(TAG, "lastPhoto: " + photoNames.get(photoNames.size()-1));
            /*if((afterCount - beforeCount)> 0) {
                Toast.makeText(getActivity(), afterCount - beforeCount + " Photo(s) taken", Toast.LENGTH_SHORT).show();
                //It should really be taking an array of photo names
                for(int i=1; i<= (afterCount - beforeCount); i++) {
                    //insert into database w/ building tag + equipment or light tag
                    //Log.v(this.toString(), "EquipmentType" + equipmentType);
                    MainActivity.db.insertPhoto(photoNames.get(photoNames.size()-i));
                }
                this.updatePhotoCountText();
            }
        }*/
    }

	/**
	 * Matches code in MediaProvider.computeBucketValues. Should be a common
	 * function.
	 */
	public static String getBucketId(String path) {
	    return String.valueOf(path.toLowerCase().hashCode());
	}
	
	public static List<String> getCameraImages(Context context) {
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
	
	private void updateMeasureSpinner() {

		ArrayList<Measure> measures = MainActivity.db.getMeasuresByActiveEquipment();

		if(measures != null) {
			String[] measureStrings = new String[measures.size()];

			for (int i = 0; i < measures.size(); i++) {
				measureStrings[i] = measures.get(i).getDescription();
			}

			measureTagAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, measureStrings);
		}
		else {
			measureTagAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, new String[] {""});
		}
			this.measureTagSpinner.setAdapter(measureTagAdapter);
	}
	

	protected void submitButtonPressed() {

		MainActivity.db.setActivePhotoProperties(getMediaInfo());

		Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        File out = new File(CAMERA_IMAGE_BUCKET_NAME);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
		beforeCount = getCameraImages(getActivity()).size();
		Log.v(TAG, "submitButtonPressed, # of photos before: " + beforeCount);
        //store beforeCount in shared preferences
        //SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt(BEFORE_COUNT_SETTING, beforeCount);
        //editor.commit();

        fileSaverIntent = new Intent(this.getActivity(), CameraObserverService.class);
        timer.cancel();
        timer.purge();
        this.getActivity().startService(fileSaverIntent);

        startActivityForResult(cameraIntent, MULTIPLE_PHOTOS);
	}

	protected SiteMedia getMediaInfo() {
		SiteMedia siteMedia = new SiteMedia();

		siteMedia.setMeasureTag((String) measureTagSpinner.getSelectedItem());
		siteMedia.setNotes(notesEditText.getText().toString());
		siteMedia.setPhotoTag((String) photoTypeTagSpinner.getSelectedItem());

		return siteMedia;
	}
	
    /**updates UI to show information relating to photos taken
     *
     */
    protected void updatePhotoCountText() {
        Log.v(TAG, "getting photo count");
        int numEquipmentPhotos = MainActivity.db.getEquipmentPhotosCount();  //MainActivity.db.getEquipmentPhotos().size();
        Log.v(TAG, "got photocount.  getting building photos");
        int numBuildingPhotos = MainActivity.db.getBuildingPhotosCount(); //MainActivity.db.getBuildingPhotos().size();
        Log.v(TAG, "got buidling photos count");

        String equipmentString = "# of Equipment Photos: " + numEquipmentPhotos;
        String buildingString = "# of Building Photos: " + numBuildingPhotos;
		//Log.v(TAG, "photo text added");
        countTextView.setText(buildingString + "\n" + equipmentString);
    }

	protected void AddMeasurePopup() {
		//Log.v(TAG, "addMeasurePopup");
		final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View spinner = inflater.inflate(R.layout.edit_equipment_type, null);

		final Spinner addMeasureSpinner = (Spinner) spinner.findViewById(R.id.editSpinner);
		final ArrayAdapter<String> buildingItemAdapter = new ArrayAdapter<String> (getActivity(),android.R.layout.simple_dropdown_item_1line, MainActivity.db.getGeneralEquipmentMeasures(equipmentPicker.getActiveEquipmentType()));
		addMeasureSpinner.setAdapter(buildingItemAdapter);

		builder.setView(spinner);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//add measure
				MainActivity.db.addMeasure(addMeasureSpinner.getSelectedItemPosition(), equipmentPicker.getActiveEquipmentType(), null);
				GeneralFunctions.DisplayToast(getActivity(), "Measure Tag Added!");
				updateMeasureSpinner();
			}
		});
		builder.setNegativeButton("Cancel", null);
		builder.setTitle("Add Measure");

		builder.show();
	}

    protected void WaitForPhotosPopup() {

        final Context mContext = this.getActivity();

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        final Activity activity = this.getActivity();
        View view = inflater.inflate(R.layout.show_new_photo, null);

        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final int columnWidth = 20;
        final ArrayList<SiteMedia> imagePaths = MainActivity.db.getNewPhotos();
        final PhotoAdapter adapter = new PhotoAdapter(inflater, imagePaths, mContext,  columnWidth, activity);

        Button button = (Button) view.findViewById(R.id.refreshButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyChange();
            }
        });


        /*IntentFilter filter = new IntentFilter();
        filter.addAction("TEST_PHOTO_ADDED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(TAG,"broadcast received");
                adapter.notifyChange();
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);*/

        listView.setAdapter(adapter);

        builder.setView(view);
        builder.setPositiveButton("OK", null);

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updatePhotoCountText();
                MainActivity.db.setNewPhotosOld();
            }
        });

        builder.setTitle("New Photos");
        builder.show();


        //LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);

    }

    public void onPause() {
        super.onPause();
        //LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(receiver);
    }


}
