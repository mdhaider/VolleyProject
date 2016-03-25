package com.ennjapps.dailyfortune.app.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ennjapps.dailyfortune.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPreferences pref= new MyPreferences(MainActivity.this);
        if(!pref.isFirstTime()){
            Intent i=new Intent(getApplicationContext(),FortuneActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
        }
    }
    public void SaveUserName(View v){
        EditText usrName=(EditText) findViewById(R.id.editText);
        MyPreferences pref= new MyPreferences(MainActivity.this);
        pref.setUsername(usrName.getText().toString().trim());
        Intent i=new Intent(getApplicationContext(),FortuneActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
