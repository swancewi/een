package display.Audit;

import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import display.subclasses.helper.Utils;

public class AddAudioFragment extends AddPhotoFragment {
	
	private boolean mRecording = false;
	private static final String START_RECORDING = "Start Recording";
	private static final String STOP_RECORDING = "Stop Recording";
	private String mFileName;
	private MediaRecorder mRecorder;
	private static final String TAG = "AddAudioFragment";
	private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		submitButton.setText(START_RECORDING);
		
		File directory = new File(Utils.AUDIO_FILE_DIRECTORY);
		if (!directory.isDirectory()) {
            directory.mkdirs();
            //Log.v(TAG, Utils.AUDIO_FILE_DIRECTORY + "Directory exists");
        }
		else {
			//Log.v(TAG, Utils.AUDIO_FILE_DIRECTORY + "Directory created");
		}

		this.view = view;
		
		return view;
		
	}
	
	@Override
	protected void submitButtonPressed() {
			
		if(mRecording) {
			submitButton.setText(START_RECORDING);
			stopRecording();
		}
		else {
			submitButton.setText(STOP_RECORDING);
			startRecording();
		}
		
		mRecording = !mRecording;
		
	}
	
	private void startRecording() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		
        mFileName = Utils.AUDIO_FILE_DIRECTORY;
        mFileName += File.separator + "EANOTES_" + calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH)+1) + "_" + calendar.get(Calendar.DAY_OF_MONTH) +"_" +calendar.get(Calendar.HOUR_OF_DAY)+ "_" + calendar.get(Calendar.MINUTE) +calendar.get(Calendar.SECOND) +".mp4";
        //Log.v("AddAudioFragment", "fileName: " + mFileName);
        
        try {
	        mRecorder = new MediaRecorder();
	        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //MediaRecorder.OutputFormat.MPEG_4);
	        
	        mRecorder.setOutputFile(mFileName);
	        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); //.AAC);
        
            mRecorder.prepare();
            mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    //Log.v(TAG, "error while recording audio, what: " + what);
                }
            });

            MainActivity.db.insertAudio(mFileName);
            this.view.setBackgroundColor(Color.RED);

            mRecorder.start();

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Sadly, you ain't got no microphone");
			builder.setTitle(":-(");
			builder.setPositiveButton("OKAY DOKAY2", null);
			builder.show();
			mRecorder.release();
			mRecorder = null;
			mRecording = true;
			submitButton.setText(START_RECORDING);
        }
	}
	
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        this.view.setBackgroundColor(Color.WHITE);

    }

    @Override
    protected void updatePhotoCountText() {

    }
	
}
