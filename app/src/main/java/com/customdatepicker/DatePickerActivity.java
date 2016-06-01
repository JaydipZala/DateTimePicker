package com.customdatepicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Jaydipsinh Zala on 1/6/16.
 */
public class DatePickerActivity extends AppCompatActivity {

    private NumberPicker np;
    private android.widget.TimePicker tp;
    private TextView txtSelDate;
    private Calendar selDate;
    ArrayList<Calendar> arrActualDates = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("E MMM, dd HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        selDate = Calendar.getInstance();
        initUI();
        updateUI();
    }

    private void initUI() {
        np = (NumberPicker) findViewById(R.id.np);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Calendar date = arrActualDates.get(picker.getValue());
                selDate.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
                selDate.set(Calendar.MONTH, date.get(Calendar.MONTH));
                selDate.set(Calendar.YEAR, date.get(Calendar.YEAR));
                String formatedDate = sdf.format(date.getTimeInMillis());
                txtSelDate.setText(formatedDate);
            }
        });
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        tp = (android.widget.TimePicker) findViewById(R.id.tp);
        tp.setIs24HourView(DateFormat.is24HourFormat(this));
        tp.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                selDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selDate.set(Calendar.MINUTE, minute);
                String formatedDate = sdf.format(selDate.getTimeInMillis());
                txtSelDate.setText(formatedDate);
            }
        });
        tp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        txtSelDate = (TextView) findViewById(R.id.txtSelDate);
    }

    private void updateUI() {
        ArrayList<String> arrDates = new ArrayList<>();
        arrDates.add("Today");
        arrDates.add("Tomorrow");
        for (int i = 0; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            arrActualDates.add(c);
            if (i > 1) {
                arrDates.add(sdf.format(c.getTimeInMillis()));
            }
        }
        np.setMinValue(0);
        np.setMaxValue(arrDates.size() - 1);
        np.setWrapSelectorWheel(false);
        String[] stringArray = arrDates.toArray(new String[arrDates.size()]);
        np.setDisplayedValues(stringArray);
    }
}
