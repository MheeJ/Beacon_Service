package com.nslb.twipee.communication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;

public class Beacon_Method extends AppCompatActivity {
    TextView Show_MyLocation;
    Intent intent;
    private String myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_method);

        initObject();
        intent=getIntent();
        get_BeconService();

    }

    public void get_BeconService(){
        myLocation = intent.getStringExtra("myLocation");
        Show_MyLocation.setText(myLocation);
    }

    public void initObject(){
       Show_MyLocation = (TextView)findViewById(R.id.show_mylocation);
    }
}
