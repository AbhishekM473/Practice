package com.example.abhishek.medsreminder.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.abhishek.medsreminder.R;
import com.example.abhishek.medsreminder.constants.GeneralConstants;
import com.example.abhishek.medsreminder.dbManager.MedsContract;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    String mMedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_alarm);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MedsContract.MedsEntry.COLUMN_NAME);
        String dosage = intent.getStringExtra(MedsContract.MedsEntry.COLUMN_DOSAGE);
        String date = intent.getStringExtra(MedsContract.MedsEntry.COLUMN_DATE);
        String time = intent.getStringExtra(MedsContract.MedsEntry.COLUMN_TIME);
        mMedId = intent.getStringExtra(MedsContract.MedsEntry.COLUMN_ID);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvDosage = (TextView) findViewById(R.id.tv_dosage);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);

        Button btnSkip = (Button) findViewById(R.id.bt_skip);
        Button btnTaken = (Button) findViewById(R.id.bt_taken);

        if (tvName != null) {
            tvName.setText(name);
        }
        if (tvDosage != null) {
            tvDosage.setText(dosage);
        }
        if (tvDate != null) {
            tvDate.setText(date);
        }
        if (tvTime != null) {
            tvTime.setText(time);
        }
        if (btnSkip != null) {
            btnSkip.setOnClickListener(this);
        }
        if (btnTaken != null) {
            btnTaken.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        switch (v.getId()){
            case R.id.bt_skip:
                values.put(MedsContract.MedsEntry.COLUMN_STATUS, GeneralConstants.STATUS_SKIPPED);
                break;

            case R.id.bt_taken:
                values.put(MedsContract.MedsEntry.COLUMN_STATUS, GeneralConstants.STATUS_TAKEN);
                break;
        }
        getContentResolver().update(MedsContract.MedsEntry.MEDS_URI, values, "med_id = ?", new String[]{mMedId});
        finish();
    }
}
