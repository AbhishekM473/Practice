package com.example.abhishek.medsreminder.activities;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abhishek.medsreminder.R;
import com.example.abhishek.medsreminder.adapters.MedListAdapter;
import com.example.abhishek.medsreminder.constants.GeneralConstants;
import com.example.abhishek.medsreminder.dbManager.MedsContract;

public class MedsListActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private MedListAdapter mMedListAdapter;
    private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_list);

        Intent intent = getIntent();
        mStatus = intent.getStringExtra(GeneralConstants.STATUS);

        RecyclerView rvList = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        if (rvList != null) {
            rvList.setLayoutManager(manager);
        }

        mMedListAdapter = new MedListAdapter(this);
        if (rvList != null) {
            rvList.setAdapter(mMedListAdapter);
        }

        getLoaderManager().initLoader(213, null, this);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{
                MedsContract.MedsEntry.COLUMN_ID,
                MedsContract.MedsEntry.COLUMN_NAME,
                MedsContract.MedsEntry.COLUMN_DOSAGE,
                MedsContract.MedsEntry.COLUMN_DATE,
                MedsContract.MedsEntry.COLUMN_TIME,
                MedsContract.MedsEntry.COLUMN_TIME_STAMP,
                MedsContract.MedsEntry.COLUMN_STATUS
        };
        return new CursorLoader(this, MedsContract.MedsEntry.MEDS_URI, projection, "status =?", new String[]{mStatus}, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mMedListAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}
