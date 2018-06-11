/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package display.Audit;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.EEN.Audit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.DatabaseHandler;
import export.MakeCSV;
import fsTablesEnums.FragmentOrder;
import display.subclasses.helper.Utils;

//implements GestureOverlayView.OnGesturePerformedListener

public class MainActivity extends FragmentActivity  {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int currentFragment;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] menuOptions;
    private ArrayAdapter<String> buildingAdapter;

    private static final String GESTURE_PHOTO = "photoGesture";
    private static final String GESTURE_MEASURE = "measureGesture";

    private GestureLibrary mLibrary;

	public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.v(this.toString(),"line60");
        setContentView(R.layout.activity_main);
        try {
			db = new DatabaseHandler(this.getApplication());
	
	        mTitle = mDrawerTitle = getTitle();
	        menuOptions = FragmentOrder.getStringArray();
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	
	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, menuOptions));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	
	        // enable ActionBar app icon to behave as action to toggle nav drawer
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	        
			//setup navigation adapter
	        updateBuildingList();
			//try {
	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer  /* nav drawer image to replace 'Up' caret */,
	                R.string.app_name,
	                R.string.app_name
	        		) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	
	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);

            //TODO Gesture options
            mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
            //GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestures_overlay);
            //gestureView.addOnGesturePerformedListener(this);

	        if (savedInstanceState == null) {
	            selectItem(0);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }

        Utils.setContext(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

/*    public boolean dispatchTouchEvent(MotionEvent e)
    {
        detector.onTouchEvent(e);

        return super.dispatchTouchEvent(e);
    } */

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_email:
        	MakeCSV makeCSV = new MakeCSV();
        	makeCSV.sendCSV(this);
        	break;
        /*case R.id.action_speak:
        	Translator.ListenThenRespond();
        	break;*/
        default:
        }
        return super.onOptionsItemSelected(item);

    }
/*
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        Log.v("onGesturePerformed", "entered function");
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        Log.v("gesture", "" + gesture.describeContents());
        if(predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            Log.v("prediction score", "Score: " + prediction.score);
            if(prediction.score > 2.0) {
                if(prediction.name.equals(GESTURE_PHOTO)) {
                    //TODO, save fragment instance and start camera
                    GeneralFunctions.DisplayToast(getApplicationContext(), "Photo Gesture Detected.");
                }
                if(prediction.name.equals(GESTURE_MEASURE)) {
                    GeneralFunctions.DisplayToast(getApplicationContext(), "Measure Gesture Detected.");
                }
            }
        }
    }
*/
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int id) {
        // update the main content by replacing fragments
		Date current = new Date();
		String myFormatString = "dd/MM/yy";
		SimpleDateFormat df = new SimpleDateFormat(myFormatString);
		Date givenDate = new Date();

		try {
			givenDate = df.parse("31/12/17");
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

			FragmentManager fragmentManager = getSupportFragmentManager();
			this.currentFragment = id;

		if (current.before(givenDate)) {

			if (id == FragmentOrder.ADD_BUILDING.getOrderNumber()) {
				AddBuildingFragment fragment2 = new AddBuildingFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();
			} else if (id == FragmentOrder.EDIT_BUILDING.getOrderNumber()) {
				EditBuildingFragment fragment2 = new EditBuildingFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();
			} else if (id == FragmentOrder.ADD_EQUIPMENT.getOrderNumber()) {
				AddEquipmentFragment fragment2 = new AddEquipmentFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();
				//Toast.makeText(this, "item list activity", Toast.LENGTH_SHORT).show();
			} else if (id == FragmentOrder.EDIT_EQUIPMENT.getOrderNumber()) {
				EditEquipmentFragment fragment2 = new EditEquipmentFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();
			}
			/*else if (id == FragmentOrder.SETTINGS.getOrderNumber()) {
				SettingsFragment fragment2 = new SettingsFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment2).commit();				
			}*/
			else if (id == FragmentOrder.SHOW_EQUIPMENT.getOrderNumber()) {
				EquipmentTableFragment fragment2 = new EquipmentTableFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();
			} else if (id == FragmentOrder.ADD_PHOTOS.getOrderNumber()) {
				AddPhotoFragment addPhotoFragment = new AddPhotoFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, addPhotoFragment).commit();
			} else if (id == FragmentOrder.DISPLAY_PHOTOS.getOrderNumber()) {
				ShowPhotoFragment photoFragment = new ShowPhotoFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, photoFragment).commit();
			} else if (id == FragmentOrder.FACILITY.getOrderNumber()) {
				FacilityFragment facilityFragment = new FacilityFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, facilityFragment).commit();
			} else if (id == FragmentOrder.ADD_MEASURE_TAG.getOrderNumber()) {
				AddMeasureTagFragment measureTagFragment = new AddMeasureTagFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, measureTagFragment).commit();
			} else if (id == FragmentOrder.ADD_CUSTOM_MEASURE.getOrderNumber()) {
				AddCustomMeasureFragment ACMFragment = new AddCustomMeasureFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, ACMFragment).commit();
			} else if (id == FragmentOrder.ADD_AUDIO.getOrderNumber()) {
				AddAudioFragment gridViewActivity = new AddAudioFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, gridViewActivity).commit();
			} else if (id == FragmentOrder.SHOW_MEASURES.getOrderNumber()) {
				ShowECMFragment ECMFragment = new ShowECMFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, ECMFragment).commit();
			}
		}	else {
				TrialExpiredFragment trialExpiredFragment = new TrialExpiredFragment();
				fragmentManager.beginTransaction().replace(R.id.content_frame, trialExpiredFragment).commit();
		}

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(id, true);
        setTitle(menuOptions[id]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	private void DisplayToast(String unitID) {
		Toast.makeText(this, unitID, Toast.LENGTH_SHORT).show();
	}

	public void updateBuildingList() {
		buildingAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, db.getBuildingList());			
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayShowTitleEnabled(true);
		OnNavigationListener navigationListener = new OnNavigationListener() {
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            	DisplayToast("Building Selected");
            	db.setActiveBuildingID(itemPosition);
            	if(currentFragment==FragmentOrder.DISPLAY_PHOTOS.getOrderNumber()) {
    				ShowPhotoFragment showPhotoFragment = new ShowPhotoFragment();
    				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, showPhotoFragment).commit();
            	}
    			else if (currentFragment == FragmentOrder.SHOW_EQUIPMENT.getOrderNumber()) {
    				EquipmentTableFragment fragment2 = new EquipmentTableFragment();
    				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();				
    			}
    			else if (currentFragment == FragmentOrder.ADD_PHOTOS.getOrderNumber()) {
    				AddPhotoFragment fragment2 = new AddPhotoFragment();
    				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();
    			}
    			else if (currentFragment == FragmentOrder.EDIT_BUILDING.getOrderNumber()) {
    				EditBuildingFragment fragment2 = new EditBuildingFragment();
    				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();
    			}
    			else if (currentFragment == FragmentOrder.ADD_AUDIO.getOrderNumber()) {
    				AddAudioFragment gridViewActivity = new AddAudioFragment();
    				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, gridViewActivity).commit();
    			}		
                return false;
            }
        };
		getActionBar().setListNavigationCallbacks(buildingAdapter, navigationListener);
		try {
			getActionBar().setSelectedNavigationItem(db.getActiveBuildingIndex()); //
		} catch (NullPointerException e) {
			// do nothing (means no building has been added)
		}
		
		//Log.v("MainActivity",buildingAdapter.getItem(0).toString());
		
	}

	public void showAddEquipment() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.noEquipment);
        alertDialogBuilder.setMessage(R.string.moveToAddEquipment);
        alertDialogBuilder.setPositiveButton("OK",null);
// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        this.selectItem(FragmentOrder.ADD_EQUIPMENT.getOrderNumber());
	}

	public void showAddBuilding() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.noBuilding);
        alertDialogBuilder.setMessage(R.string.moveToAddBuilding);
        alertDialogBuilder.setPositiveButton("OK",null);
// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        this.selectItem(FragmentOrder.ADD_BUILDING.getOrderNumber());
    }

}