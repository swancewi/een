package service;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

import display.Audit.AddPhotoFragment;
import display.Audit.MainActivity;
import display.subclasses.helper.AppConstant;

/**
 * Created by Steven on 3/16/2016.
 */
public class CameraObserverService extends Service {

    public final String TAG = "CameraObserver";
    public static FileObserver observer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        Log.v(TAG, "onStart");

        final String pathToWatch = AppConstant.PHOTO_ALBUM;

        Log.v(TAG,pathToWatch);
        observer = new FileObserver(pathToWatch) {
            @Override
            public void onEvent(int event, String path) {
                /*if(event != 1) {
                    Log.v(TAG, "onEvent fired: " + event);
                }*/
                if(event == CREATE) {
                    Log.v(TAG, "onEvent fired: " + event + "path: " + path);
                    MainActivity.db.insertPhoto(pathToWatch + "/" + path, getApplicationContext());
                }
                //Log.v(TAG, "onEvent fired: " + event + "path: " + path);

            }
        };
        observer.startWatching();

        Log.v(TAG,"OnStart complete");
        return START_STICKY;
    }


    public IBinder onBind(Intent intent) {

        return null;
    }

    public void onDestroy() {
        observer.stopWatching();
        Toast.makeText(this, "service destroyyyyyeedd", Toast.LENGTH_SHORT).show();
    }
}
