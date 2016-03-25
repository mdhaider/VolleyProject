package com.ennjapps.dailyfortune.app.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ennjapps.dailyfortune.R;
import com.ennjapps.dailyfortune.app.app.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FortuneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);
        MyPreferences pref = new MyPreferences(FortuneActivity.this);
        if (pref.isFirstTime()) {
            Toast.makeText(FortuneActivity.this, "Hi" + pref.getUsername(), Toast.LENGTH_LONG);
            pref.setOld(true);
        } else {
            Toast.makeText(FortuneActivity.this, "Welcome back" + pref.getUsername(), Toast.LENGTH_LONG).show();
        }
        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet())
            getFortuneOnline();
        else
            readFortuneFromFile();
    }

    private void getFortuneOnline() {
        final TextView fortuneTxt = (TextView) findViewById(R.id.fortune);
        fortuneTxt.setText("Loading...");

        JsonObjectRequest Request = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                "https://www.reddit.com/r/all.json?limit=2",(String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                String fortune;
                try {
                    fortune = response.getString("quote");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    fortune = "Error";
                }
                fortuneTxt.setText(fortune);
                writeToFile(fortune);
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Response", "Error:" + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(Request);
    }

        private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Fortune.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.e("Message:", "File write failed:" + e.toString());

        }

    }
    private  void readFortuneFromFile()
    {
        String fortune="";
        try{
            InputStream inputStream= openFileInput("Fortune.json");
            if(inputStream!=null)
            {
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
                BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
                String receiveString="";
                StringBuilder stringBuilder=new StringBuilder();
                Log.v("Message:", "reading...");
                while((receiveString=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(receiveString);
                }
                 inputStream.close();
                fortune=stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("Message:","File not found:"+e.toString());
        } catch (IOException e) {
            Log.e("Message:", "Cannot read from file:" + e.toString());
        }
        TextView fortuneTxt =(TextView) findViewById(R.id.fortune);
        fortuneTxt.setText(fortune);
    }


        }
