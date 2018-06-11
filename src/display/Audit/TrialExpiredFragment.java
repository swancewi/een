package display.Audit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EEN.Audit.R;

/**
 * Created by Steven Wance on 9/7/2015.
 */
public class TrialExpiredFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trial_expired, container, false);

        return view;

    }
}
