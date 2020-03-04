package com.nslb.twipee.communication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
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
import com.nslb.twipee.R;

import java.util.List;

public class BeaconService extends Service {

    private MinewBeaconManager mMinewBeaconManager;
    private String[] beacon_name = new String[10];
    private String[] location_name = new String[10];
    private String b_name;
    String Toast_Show = "no";
    private String myLocation = null;
    public String Beacon_Main;
    final static String MY_ACTION = "MY_ACTION";
    private String Previous_Beacon = "previous";
    private String Latest_Beacon = "last";



    public BeaconService(){}

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initObject();
        initManager();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        processCommand(intent);
        if(Beacon_Main.equals("TurnON")){
            try {
                mMinewBeaconManager.startScan();
            } catch (Exception e) {
                e.printStackTrace();
            }
            beacon_start();

        }
        else if(Beacon_Main.equals("TurnOff")){
            if (mMinewBeaconManager != null) {
                mMinewBeaconManager.stopScan();
            }
        }

        return super.onStartCommand(intent,flags,startId);
    }

    private void processCommand(Intent intent){
        String hello = intent.getStringExtra("Beacon_Condition");
        Beacon_Main = hello;
        intent.setAction(MY_ACTION);
    }

    public void beacon_start(){
        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            @Override
            public void onAppearBeacons(List<MinewBeacon> list) {
            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> list) {
            }

            @Override
            public void onRangeBeacons(final List<MinewBeacon> list) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() > 0) {
                            for (int i = 0; i<list.size(); i++)
                            {
                                MinewBeacon item = list.get(i);
                                b_name = item.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue();
                                for (int j = 0; j < 10 ; j ++)
                                {
                                    if (b_name.equals(beacon_name[j])) {
                                        myLocation = location_name[j];
                                        Latest_Beacon = myLocation;
                                        if(!Previous_Beacon.equals(Latest_Beacon)){
                                            Toast_Show = "no";
                                        }
                                        if(Toast_Show.equals("no")){
                                            Previous_Beacon = myLocation;
                                            Intent intent = new Intent();
                                            intent.setAction(MY_ACTION);
                                            intent.putExtra("DATAPASSED",myLocation);
                                            sendBroadcast(intent);
                                            Toast_Show="yes";
                                        }

                                    }
                                }
                            }
                        }
                    }
                }).start();
            }
            @Override
            public void onUpdateState(BluetoothState bluetoothState) {
            }
        });
    }


    public void initObject(){

        beacon_name[0] = "Seoul29250";
        beacon_name[1] = "Gangwon";
        beacon_name[2] = "Chungbuk";
        beacon_name[3] = "Chungnam";
        beacon_name[4] = "Jeonbuk00000";
        beacon_name[5] = "Jeonnam29247";
        beacon_name[6] = "Gyeongnam";
        beacon_name[7] = "Gyeongbuk";
        beacon_name[8] = "Ulleung";
        beacon_name[9] = "Jeju0000";

        location_name[0] = "서울";
        location_name[1] = "강원도";
        location_name[2] = "충청북도";
        location_name[3] = "충청남도";
        location_name[4] = "전라북도";
        location_name[5] = "전라남도";
        location_name[6] = "경상남도";
        location_name[7] = "경상북도";
        location_name[8] = "울릉도";
        location_name[9] = "제주도";
    }



    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }

    public void SendToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(myLocation,"myLocation");
    }

    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                break;
            case BluetoothStatePowerOff:
                //showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }


}