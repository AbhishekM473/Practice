package com.example.abhishek.safetyapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class PopUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private LocalBroadcastManager mManager;
    private TextView mQuestionText;
    private Context mContext;
    private String mAnswerString;
    private String mQuestionString;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        mContext = this;
        mManager = LocalBroadcastManager.getInstance(this);
        mEditText = (EditText) findViewById(R.id.et_answer);
        mQuestionText = (TextView) findViewById(R.id.tv_enter);

        SharedPreferences preferences = getSharedPreferences("shared", MODE_PRIVATE);
        mQuestionString = preferences.getString("question", "");
        mAnswerString = preferences.getString("answer", "");


        final String url = "http://my-url";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mQuestionString = response.optString("question", "");
                        mAnswerString = response.optString("answer", "");
                        mQuestionText.setText(mQuestionString);
                        pDialog.hide();

                        SharedPreferences.Editor editor = getSharedPreferences("shared", MODE_PRIVATE).edit();
                        editor.putString("question", mQuestionString);
                        editor.putString("answer", mAnswerString);
                        editor.apply();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Can use caching here but SharedPreferences would work better!
//                        Cache cache = MySingleton.getInstance(mContext).getRequestQueue().getCache();
//                        Cache.Entry entry = cache.get(url);
//                        if(entry != null){
//                            try {
//                                String data = new String(entry.data, "UTF-8");
//                                // handle data, like converting it to xml, json, bitmap etc.,
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        } else{
//                            Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_SHORT).show();
//                        }
                        Toast.makeText(mContext, "No internet connection!", Toast.LENGTH_SHORT).show();
                        mQuestionText.setText(mQuestionString);
                        pDialog.hide();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

        Button submitButton = (Button) findViewById(R.id.bt_submit);
        if (submitButton != null) {
            submitButton.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mManager.registerReceiver(mReceiver, new IntentFilter("login"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mManager.unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                String text = mEditText.getText().toString();
                if (text.equalsIgnoreCase(mAnswerString)) {
                    sendMsgToService(true);
                    finish();
                } else {
                    switchToHomeScreen();
                    finish();
                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switchToHomeScreen();
    }

    private void sendMsgToService(boolean status) {
        Intent intent = new Intent("login");
        intent.putExtra("login_status", status);
        mManager.sendBroadcast(intent);
    }

    private void switchToHomeScreen() {
        sendMsgToService(false);
        Toast.makeText(this, "Answer is incorrect", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
