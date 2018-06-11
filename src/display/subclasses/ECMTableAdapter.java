package display.subclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EEN.Audit.R;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.io.IOException;
import java.util.ArrayList;

import display.Audit.MainActivity;
import fsTablesClasses.MeasureSummary;

/**
 * Created by Steven Wance on 4/28/2015.
 */
public class ECMTableAdapter extends BaseTableAdapter {
    private LayoutInflater inflater;
    private final float density;
    private ArrayList<String> headers = new ArrayList<String>();
    private int numColumns;
    private ArrayList<MeasureSummary> measures = new ArrayList<MeasureSummary>();

    private static final int COLUMN_ECM_NUMBER = -1;
    private static final int COLUMN_ECM_NAME = 0;
    private static final int COLUMN_ECM_BUILDING = 1;
    private static final int COLUMN_ECM_QTY = 2;
    private static final int COLUMN_ECM_EQUIPMENT = 3;

    public ECMTableAdapter(Context context, LayoutInflater inflater) throws IOException, Exception {
        //this.context = context;
        density = context.getResources().getDisplayMetrics().density;
        this.inflater = inflater;
        this.setupColumns();
    }

    public void setupColumns() {
        headers.add("ECM #");
        headers.add("ECM Name");
        headers.add("Building(s)");
        headers.add("Qty");
        headers.add("Equipment");

        numColumns = 4;
        measures = MainActivity.db.getMeasureSummaryList();
    }


    @Override
    public int getRowCount() {
        try {
            //return # of unique measures
            return MainActivity.db.getMeasureSummaryList().size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getColumnCount() {

        return this.numColumns;
    }

    @Override
    public View getView(int row, int column, View convertView,
                        ViewGroup parent) {
        final View view;
        //Log.v("EquipmentTableFragment", "asked for view for " + row + "," + column);
        switch (getItemViewType(row, column)) {
            case 0:
                view = getFirstHeader(row, column, convertView, parent);
                break;
            case 1:
                view = getHeader(row, column, convertView, parent);
                break;
            case 2:
                view = getFirstBody(row, column, convertView, parent);
                break;
            case 3:
                view = getBody(row, column, convertView, parent);
                break;
            case 4:
                view = getFamilyView(row, column, convertView, parent);
                break;
            default:
                throw new RuntimeException("wtf?");
        }
        return view;
    }


    private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_table_header_first, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers.get(column+1));
        return convertView;
    }

    private View getHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_table_header, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers.get(column+1));
        return convertView;
    }

    private View getFirstBody(int row, int column, View convertView,
                              ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_table_first, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getMeasureInfo(row, column));
        return convertView;
    }

    private View getBody(int row, int column, View convertView,
                         ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_table, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getMeasureInfo(row, column));
        return convertView;
    }

    private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_table_family, parent, false);
        }
        final String string;
        if (column == -1) {
            string = "";
        } else {
            string = "";
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
        return convertView;
    }

    @Override
    public int getItemViewType(int row, int column) {
        final int itemViewType;
        if (row == -1 && column == -1) {
            itemViewType = 0;
        } else if (row == -1) {
            itemViewType = 1;
        } else if (column == -1) {
            itemViewType = 2;
        } else {
            itemViewType = 3;
        }
        return itemViewType;
    }

    @Override
    public int getViewTypeCount() {
        //Log.v("EquipmentTableFragment","Is it here?");
        return 5;
    }

    @Override
    public int getWidth(int column) {
        // TODO adjust based on how it looks, but later
        int width = 100;
        if (column == COLUMN_ECM_EQUIPMENT  || column == COLUMN_ECM_NAME)
            width = 200;
        else if (column == COLUMN_ECM_QTY || column == COLUMN_ECM_NUMBER)
            width = 50;
        return (int) (width*density);
    }

    @Override
    public int getHeight(int row) {

        final int height;
        if (row == -1) {
            height = 45;
        } else {
            height = 35;
        }
        return Math.round(height * density);
    }

    private String getMeasureInfo(int row, int column) {
        if(column==  COLUMN_ECM_NUMBER) return "" + measures.get(row).getMeasureNumber();
        if(column==  COLUMN_ECM_NAME) return measures.get(row).getMeasureName();
        if(column==  COLUMN_ECM_EQUIPMENT) return "" + measures.get(row).getMeasureEquipment();
        if(column==  COLUMN_ECM_BUILDING) return measures.get(row).getMeasureBuildings();
        if(column==  COLUMN_ECM_QTY) return "" + measures.get(row).getNumEquipment();


        return "ERROR";
    }
}
