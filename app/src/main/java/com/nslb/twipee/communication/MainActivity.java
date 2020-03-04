package com.nslb.twipee.communication;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nslb.twipee.R;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ToggleButton Toggle;
    private Button Go_Beacon_Service;
    private TextView Beacon_Text;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    private String Beacon_Condition,DataFromService;
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGUI();
    }


    private void initGUI(){
        Beacon_Text = (TextView)findViewById(R.id.beacon_text);
        Go_Beacon_Service = (Button)findViewById(R.id.go_beacon_service);
        Go_Beacon_Service.setOnClickListener(this);
        Toggle = (ToggleButton)findViewById(R.id.toggleButton);
        Toggle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_beacon_service:
                Intent intent1 = new Intent(this, Beacon_Service.class);
                startActivity(intent1);
                break;
            case R.id.toggleButton:
                if(Toggle.isChecked()){
                   Start_BeaconService();
                    setToast("여행을 시작합니다.");
                }
                else {
                    End_BeaconService();
                    setToast("여행을 마칩니다.");
                }
        }
    }

    //텍스트뷰
    public void setTextView(String text){Beacon_Text.setText(text);}
    //토스트
    public void  setToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    //비콘 서비스 시작 준비
    public void Start_BeaconService(){
        //블루투스 이용 허가 부분
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BeaconService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);
        Beacon_Condition = "TurnON";
        Intent intent = new Intent(getApplication(), BeaconService.class);
        intent.putExtra("Beacon_Condition",Beacon_Condition);
        startService(intent);
    }

    //비콘 서비스 종료
    public void End_BeaconService(){
        Beacon_Text.setText("Hello TWIPEE");
        //Beacon_Condition = "TurnOff";
        Intent intent = new Intent(this, BeaconService.class);
        intent.putExtra("Beacon_Condition",Beacon_Condition);
        stopService(intent);
    }

    //서비스에서 데이터 가져오는 부분
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            DataFromService = arg1.getStringExtra("ServiceData");
            set_UI();
        }
    }

    //서비스에서 가져온 데이터 UI에 표시
    public void set_UI(){
        setToast(DataFromService+"에 오신것을 환영합니다.");
        setTextView(DataFromService);
    }

}
