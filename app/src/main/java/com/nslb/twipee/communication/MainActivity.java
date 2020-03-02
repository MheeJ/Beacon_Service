package com.nslb.twipee.communication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;
import com.minew.beacon.MinewBeaconValue;
import com.nslb.twipee.R;

import android.os.Handler;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MinewBeaconManager mMinewBeaconManager;
    private boolean bClick = false;
    private Button mBtn;
    private TextView mTV;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    private Handler mHandler = new Handler();
    private String[] beacon_name = new String[10];
    private String ip = "202.31.137.137";
    private int port = 65000;
    private String b_name;
    byte[] data;
   // int MainSize;

    DataPacketStore.header Header1 = new DataPacketStore.header();
    DataPacketStore.header hHeaderPacket = new DataPacketStore.header();
    DataPacketStore.MainPacket mMainPacket = new DataPacketStore.MainPacket();
    DataPacketStore.LoginSubPacket lLoginPacket = new DataPacketStore.LoginSubPacket();
    DataPacketStore.TripTalkSubPacket tTripTalkSubPacket = new DataPacketStore.TripTalkSubPacket();
    DataPacketStore.FollowSubPacket fFollowSubPacket = new DataPacketStore.FollowSubPacket();
    DataPacketStore.PageSubPacket pPageSubPacket = new DataPacketStore.PageSubPacket();
    DataPacketStore.PostSubPacket pPostSubPacket = new DataPacketStore.PostSubPacket();
    int MainSize;
    int SubSize;

    byte[] HeaderByte ;

    byte[] HeaderByteArray = new byte[8];
    byte[] MainByteArray;
    byte[] SubByteArray;

    byte[] HeaderReset = new byte[8];
    byte[] MainReset;
    byte[] SubReset;

    byte[] TotalyPacket = new byte[700];
       //private class Connect{};

    String ID = "no";

    private MinewBeaconValue mMinewBeaconValue;
   // public MinewBeaconValue getBeaconValue(NS_ENUM MinewBeaconValueIndex_Name).getStringValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button)findViewById(R.id.start);
        mTV = (TextView)findViewById(R.id.textview);
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

                                        /*mTV.setText(b_name);*/
                                        ID = "yes";
                                       // makeID();
                                        try {
                                            Serialize();


                                            //data = SerializationUtils.serialize((Serializable)d);
                                            // switch (view.getId()
                                            //new Connect().start();
                                              /*  try {
                                                    Socket socket = new Socket(ip, port);
                                                    OutputStream os = socket.getOutputStream();
                                                    ObjectOutputStream oos = new ObjectOutputStream(os);
                                                    oos.writeObject(b_name);

                                                    oos.close();
                                                    os.close();
                                                    socket.close();


                                                } catch (Exception e) {
                                                    final String recvInput = "연결에 실패하였습니다.";
                                                    mHandler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            setToast(recvInput);
                                                        }
                                                    });
                                                }
                                                */
                                        }  catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        makeID();
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

    public void makeID(){
    if(ID.equals("yes")){
        mTV.setText(b_name);
       // ID = "no";
    }
    else{
        mTV.setText("없떵");
    }
    }

    private byte[] integersToBytes1(int[] values) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        for(int i=0; i < values.length; ++i)
        {
            dos.writeInt(values[i]);
        }
        return baos.toByteArray();
    }

    private void Serialize() throws IOException
    {
        mMainPacket.DataType = 2;
        tTripTalkSubPacket.ChatRoomTitle = b_name;
        byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
        MainSize = MainByte.length;
        Header1.header[0] = MainSize;


        byte[] SubByte = SerializationUtils.serialize((Serializable) tTripTalkSubPacket);
        SubSize = SubByte.length;
        Header1.header[1] = SubSize;

        HeaderByte = integersToBytes1(Header1.header);

        System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
        System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length , MainByte.length);
        System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length );
    }

    /*class Connect extends Thread {
        public void run() {

            try {
                //서버에 접속합니다.
                Socket socket = new Socket(ip, port);
                //소켓으로부터 OutputStream을 설정합니다.
                OutputStream os = socket.getOutputStream();
                //OutputStream을 Object 방식으로 전송하도록 설정합니다.
                ObjectOutputStream oos = new ObjectOutputStream(os);

                //Object를 Socket을 통해 값을 전송합니다.
                oos.writeObject(TotalyPacket);

                oos.close();
                os.close();
                socket.close();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                final String recvInput = "연결에 실패하였습니다.";
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }
        }
    }
*/
    void  setToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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

    public void onBtnClick(View view)  throws Exception
    {
       // if (view.getId() == R.id.start)
        switch (view.getId())
        {
            case R.id.start:
            if (!bClick)
            {
                mBtn.setText("Stop");
                try {
                    mMinewBeaconManager.startScan();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                bClick = true;
            }
            else
            {
                mBtn.setText("Start");
                mTV.setText("Hello TWIPEE");
                if (mMinewBeaconManager != null) {
                    mMinewBeaconManager.stopScan();
                }
                bClick = false;
            }
        }
    }




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
