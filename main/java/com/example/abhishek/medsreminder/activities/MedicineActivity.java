package com.example.abhishek.medsreminder.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.abhishek.medsreminder.R;
import com.example.abhishek.medsreminder.constants.GeneralConstants;
import com.example.abhishek.medsreminder.dbManager.MedsContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MedicineActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etDosage;
    private LinearLayout mParentLayout;

    private Button mDatePicker;
    private Button mTimePicker;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_medicine);

        mParentLayout = (LinearLayout) findViewById(R.id.ll_parent);

        etName = (EditText) findViewById(R.id.et_name);
        etDosage = (EditText) findViewById(R.id.et_dosage);

        mDatePicker = (Button) findViewById(R.id.bt_date_picker);
        mTimePicker = (Button) findViewById(R.id.bt_time_picker);
        Button saveButton = (Button) findViewById(R.id.bt_save);

        if (mDatePicker != null) {
            mDatePicker.setOnClickListener(this);
        }

        if (mTimePicker != null) {
            mTimePicker.setOnClickListener(this);
        }

        if (saveButton != null) {
            saveButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_date_picker:
                showDateDialog();
                break;

            case R.id.bt_time_picker:
                showTimeDialog();
                break;

            case R.id.bt_save:
                String name = etName.getText().toString();
                String dosage = etDosage.getText().toString();

                if(name.isEmpty() || dosage.isEmpty() || mYear == 0 || mMonth == 0 || mDay == 0 || mHour == 0 || mMinute == 0){
                    Snackbar.make(mParentLayout, "Please fill all fields!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = getCalendar();

                ContentValues values = new ContentValues();
                values.put(MedsContract.MedsEntry.COLUMN_NAME, name);
                values.put(MedsContract.MedsEntry.COLUMN_DOSAGE, dosage);
                values.put(MedsContract.MedsEntry.COLUMN_DATE, mDay + "-" + (mMonth + 1) + "-" + mYear);
                values.put(MedsContract.MedsEntry.COLUMN_TIME, mHour + ":" + mMinute);
                values.put(MedsContract.MedsEntry.COLUMN_TIME_STAMP, String.valueOf(calendar.getTimeInMillis()));
                values.put(MedsContract.MedsEntry.COLUMN_STATUS, GeneralConstants.STATUS_IN_PROCESS);
                Uri uri = getContentResolver().insert(MedsContract.MedsEntry.MEDS_URI, values);
                String id = MedsContract.MedsEntry.getMessageIdFromUri(uri);
                Toast.makeText(this, "Successfully added medicine!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, AlarmActivity.class);
                intent.putExtra(MedsContract.MedsEntry.COLUMN_ID, id);
                intent.putExtra(MedsContract.MedsEntry.COLUMN_NAME, name);
                intent.putExtra(MedsContract.MedsEntry.COLUMN_DOSAGE, dosage);
                intent.putExtra(MedsContract.MedsEntry.COLUMN_DATE, mDay + "-" + (mMonth + 1) + "-" + mYear);
                intent.putExtra(MedsContract.MedsEntry.COLUMN_TIME, mHour + ":" + mMinute);

                int requestCode = new Random().nextInt();
                PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
                ((AlarmManager) getSystemService(ALARM_SERVICE)).setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                finish();
                break;
        }
    }

    private void showDateDialog(){
        //Get current date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //Launch Date dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimeDialog(){
        // Get Current Time
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        mTimePicker.setText(hourOfDay + ":" + minute);
                        mHour = hourOfDay;
                        mMinute = minute;
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private Calendar getCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
