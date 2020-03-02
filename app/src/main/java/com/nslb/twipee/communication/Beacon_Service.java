package com.nslb.twipee.communication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;
import com.minew.beacon.MinewBeaconValue;
import com.nslb.twipee.R;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Beacon_Service extends AppCompatActivity implements View.OnClickListener {
    private MinewBeaconManager mMinewBeaconManager;
    private boolean bClick = false;
    private Button BeaconBtn,Go_BeaconMethod;
    private TextView BeaconText;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    private String[] beacon_name = new String[10];
    private String[] location_name = new String[10];
    private String b_name;
    String Toast_Show = "no";
    private String myLocation=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_service);

        initObject();
        initManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            @Override
            public void onAppearBeacons(List<MinewBeacon> list) {
            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> list) {
            }

            @Override
            public void onRangeBeacons(final List<MinewBeacon> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() > 0) {
                            for (int i = 0; i<list.size(); i++)
                            {
                                MinewBeacon item = list.get(i);
                                b_name = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue();
                                for (int j = 0; j < 10 ; j ++)
                                {
                                    //makeID();
                                    if (b_name.equals(beacon_name[j])) {
                                       // ID = "yes";
                                        BeaconText.setText(b_name);
                                        //내 위치 정보
                                        myLocation = location_name[j];
                                        if(Toast_Show.equals("no")){
                                            setToast(location_name[j] + "에 오신것을 환영합니다.");
                                            Toast_Show="yes";
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
            @Override
            public void onUpdateState(BluetoothState bluetoothState) {
            }
        });
    }

    public void  setToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void initObject(){
        BeaconBtn = (Button)findViewById(R.id.start);
        BeaconBtn.setOnClickListener(this);
        BeaconText = (TextView)findViewById(R.id.textview);
        Go_BeaconMethod = (Button)findViewById(R.id.go_beacon_method);
        Go_BeaconMethod.setOnClickListener(this);

        beacon_name[0] = "mini";
        beacon_name[1] = "Gangwon";
        beacon_name[2] = "Chungbuk";
        beacon_name[3] = "Chungnam";
        beacon_name[4] = "Seoul29250";
        beacon_name[5] = "Jeonnam";
        beacon_name[6] = "Gyeongnam";
        beacon_name[7] = "Gyeongbuk";
        beacon_name[8] = "Ulleung";
        beacon_name[9] = "Jeju0000";

        location_name[0] = "mini";
        location_name[1] = "강원도";
        location_name[2] = "충청북도";
        location_name[3] = "충청남도";
        location_name[4] = "서울";
        location_name[5] = "전라남도";
        location_name[6] = "경상남도";
        location_name[7] = "경상북도";
        location_name[8] = "울릉도";
        location_name[9] = "제주도";
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, yay! Start the Bluetooth device scan.

                    checkBluetooth();
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (!bClick)
                {
                    BeaconBtn.setText("여행끝");
                    try {
                        mMinewBeaconManager.startScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bClick = true;
                }
                else
                {
                    BeaconBtn.setText("여행시작");
                    BeaconText.setText("Hello TWIPEE");
                    Toast_Show="no";
                    myLocation = null;
                    if (mMinewBeaconManager != null) {
                        mMinewBeaconManager.stopScan();
                    }
                    bClick = false;
                }
                break;

            case R.id.go_beacon_method:
                Intent intent = new Intent(this,Beacon_Method.class);
                intent.putExtra("myLocation",myLocation);
                startActivity(intent);
                break;
        }
    }
   /* public void onBtnClick(View view)  throws Exception
    {
        // if (view.getId() == R.id.start)
        switch (view.getId())
        {
            case R.id.start:
                if (!bClick)
                {
                    BeaconBtn.setText("여행끝");
                    try {
                        mMinewBeaconManager.startScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bClick = true;
                }
                else
                {
                    BeaconBtn.setText("여행시작");
                    BeaconText.setText("Hello TWIPEE");
                    Toast_Show="no";
                        if (mMinewBeaconManager != null) {
                        mMinewBeaconManager.stopScan();
                        }
                    bClick = false;
                }
                break;


        }
    }*/


    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }

    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                //showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }

}