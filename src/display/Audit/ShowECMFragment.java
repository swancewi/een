package display.Audit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EEN.Audit.R;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import java.io.IOException;

import display.subclasses.ECMTableAdapter;

/**
 * Created by Steven Wance on 4/28/2015.
 */
public class ShowECMFragment extends Fragment {
    View view;
    LayoutInflater inflater;
    ECMTableAdapter baseTableAdapter;
    TableFixHeaders tableFixHeaders;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.table_master, container, false);

        tableFixHeaders = (TableFixHeaders) view.findViewById(R.id.table);

        try {
            baseTableAdapter = new ECMTableAdapter(this.getActivity(), inflater);
            tableFixHeaders.setAdapter(baseTableAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}


