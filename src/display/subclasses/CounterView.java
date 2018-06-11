package display.subclasses;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EEN.Audit.R;

import constants.GeneralFunctions;

/**
 * Created by Steven Wance on 5/25/2015.
 */

public class CounterView extends LinearLayout {
    Button decreaseButton;
    Button increaseButton;
    EditText currentNumber;
    TextView nameTextView;

    public CounterView(Context context) {
        super(context);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        View.inflate(context, R.layout.add_water_item, this);
        decreaseButton =  (Button) findViewById(R.id.subtractButton);
        increaseButton = (Button) findViewById(R.id.addButton);
        currentNumber = (EditText) findViewById(R.id.quantityEditText);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        increaseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                increaseCount();
            }
        });

        decreaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseCount();
            }
        });

    }

    public int getCurrentNumber() {

        int value = GeneralFunctions.StringToInt(currentNumber.getText().toString(), "Water Fixture Quantity");
        return value;

    }

    public void setCurrentName(String nameText) {
        nameTextView.setText(nameText);
    }

    public void setCurrentNumber(int number) {
        currentNumber.setText("" + number);
    }

    private void increaseCount() {
        int value = getCurrentNumber();
        value +=1;
        currentNumber.setText(""+value);
    }

    private void decreaseCount() {
        int value = getCurrentNumber();
        value = Math.max(value-1,0);
        currentNumber.setText(""+value);
    }

}
