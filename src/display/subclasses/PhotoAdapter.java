package display.subclasses;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.EEN.Audit.R;

import java.util.ArrayList;

import display.Audit.MainActivity;
import display.Audit.ShowPhotoFragment;
import fsTablesClasses.SiteMedia;
import media.photos.BitmapWorkerTask;

/**
 * Created by Steven Wance on 8/15/2015.
 */
public class PhotoAdapter extends BaseAdapter {

    private static final String TAG = "PhotoAdapter";
    private ArrayList<SiteMedia> mPhotos;
    private Context mContext;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private int mImageWidth;

    //http://www.androidhive.info/2014/07/android-custom-listview-with-image-and-text-using-volley/

    public PhotoAdapter(LayoutInflater inflater, ArrayList<SiteMedia> photos, Context context, int imageWidth, Activity activity)
    {
        this.mPhotos = photos;
        this.mInflater = inflater;
        this.mImageWidth = imageWidth;
        this.mContext = context;
        this.mActivity = activity;


    }

    public void notifyChange() {
        mPhotos = MainActivity.db.getNewPhotos();
        this.notifyDataSetChanged();
    }

    public int getCount() { return mPhotos.size(); }

    public Object getItem(int arg0) { return mPhotos.get(arg0); }

    public long getItemId(int arg0) { return  arg0; }

    public View getView(int arg0, View convertView, ViewGroup arg2) {

        PhotoViewHolder viewHolder;

        if(convertView==null) {
            convertView = mInflater.inflate(R.layout.show_photo_fragment_listview, null);

            viewHolder = new PhotoViewHolder();
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.photoImageView);
            viewHolder.notes = (TextView) convertView.findViewById((R.id.photoNotesTextView));
            viewHolder.building = (TextView) convertView.findViewById(R.id.photoBuildingTextView);
            viewHolder.equipment = (TextView) convertView.findViewById(R.id.photoEquipmentTextView);
            viewHolder.measure = (TextView) convertView.findViewById(R.id.photoMeasureTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PhotoViewHolder) convertView.getTag();
        }

        SiteMedia photo = mPhotos.get(arg0);

        //Log.v(TAG, photo.getEquipmentName());
        viewHolder.notes.setText("Notes: " + photo.getNotes());
        viewHolder.building.setText("Building: " +  photo.getBuildingName());
        viewHolder.equipment.setText("Equipment: " + photo.getEquipmentName());
        viewHolder.measure.setText("Measure Name: " + photo.getMeasureTag());
        viewHolder.photo.setOnClickListener(new OnImageClickListener(arg0));
        BitmapWorkerTask.loadBitmap(arg0, viewHolder.photo, this.mContext, photo);

        return convertView;
    }

    static class PhotoViewHolder {
        ImageView photo;
        TextView notes;
        TextView building;
        TextView equipment;
        TextView measure;
    }


    private class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(mActivity, ShowPhotoFragment.FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            mActivity.startActivity(i);
        }

    }

}
