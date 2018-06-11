package display.Audit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.EEN.Audit.R;

import java.util.ArrayList;

import display.subclasses.FullScreenImageAdapter;
import display.subclasses.PhotoAdapter;
import fsTablesClasses.SiteMedia;
import display.subclasses.helper.AppConstant;
import display.subclasses.helper.Utils;

/**
 * Created by Steven Wance on 8/15/2015.
 */
public class ShowPhotoFragment extends Fragment {

    private static Utils utils;
    private View view;
    private ArrayList<SiteMedia> imagePaths = new ArrayList<SiteMedia>();
    private PhotoAdapter adapter;
    private ListView listView;
    private int columnWidth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        columnWidth = (int) ((utils.getScreenWidth()* AppConstant.screenRatio ) / (AppConstant.NUM_OF_COLUMNS*2));
        view = inflater.inflate(R.layout.show_photo_fragment_list, container, false);
        imagePaths = MainActivity.db.getBuildingMediaPhotos();//utils.getFilePaths();
        Log.v("ShowPhoto", "Size: " + imagePaths.size());
        adapter = new PhotoAdapter(inflater, imagePaths, this.getActivity().getApplicationContext(),  columnWidth, this.getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }

    public static class FullScreenViewActivity extends Activity {

        private ArrayList<String> imageFiles = new ArrayList<String>();
        private PagerAdapter mPagerAdapter;
        private ViewPager mPager;

        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fullscreen_view);
            // get intent data
            Intent i = getIntent();
            //utils = new Utils(this);
        // loading all image paths from SD card
            imageFiles = MainActivity.db.getBuildingPhotos();

        // Selected image id
        //int position = i.getExtras().getInt("id");
        mPagerAdapter = new FullScreenImageAdapter(this, imageFiles);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(i.getExtras().getInt("position"));
        // ImageView imageView = (ImageView) findViewById(R.id.imgDisplay);
        // imageView.setImageResource(imageAdapter.mThumbIds[position]);

        }

}
}
