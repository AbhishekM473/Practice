package com.example.abhishek.medsreminder.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abhishek.medsreminder.R;
import com.example.abhishek.medsreminder.constants.GeneralConstants;
import com.example.abhishek.medsreminder.dbManager.MedsContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addMedButton = (Button) findViewById(R.id.bt_add_med);
        Button takenListButton = (Button) findViewById(R.id.bt_show_taken);
        Button skippedListButton = (Button) findViewById(R.id.bt_show_skipped);
        Button dumpButton = (Button) findViewById(R.id.bt_dump);

        if (addMedButton != null) {
            addMedButton.setOnClickListener(this);
        }
        if (takenListButton != null) {
            takenListButton.setOnClickListener(this);
        }
        if (skippedListButton != null) {
            skippedListButton.setOnClickListener(this);
        }
        if (dumpButton != null) {
            dumpButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.bt_add_med:
                intent = new Intent(this, MedicineActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_show_taken:
                intent = new Intent(this, MedsListActivity.class);
                intent.putExtra(GeneralConstants.STATUS, GeneralConstants.STATUS_TAKEN);
                startActivity(intent);
                break;

            case R.id.bt_show_skipped:
                intent = new Intent(this, MedsListActivity.class);
                intent.putExtra(GeneralConstants.STATUS, GeneralConstants.STATUS_SKIPPED);
                startActivity(intent);
                break;

            case R.id.bt_dump:
                Cursor cursor = getContentResolver().query(MedsContract.MedsEntry.MEDS_URI, null, null, null, null);
                if (cursor != null){
                    Log.d("golu",DatabaseUtils.dumpCursorToString(cursor));
                    Toast.makeText(this, "Successfully dumped to logcat", Toast.LENGTH_SHORT).show();
                    cursor.close();
                }
                break;
        }
    }
}
