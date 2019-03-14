package com.devicecontroller.devicecontrollerclient;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.devicecontroller.devicecontrollerclient.components.BatteryComponent;
import com.devicecontroller.devicecontrollerclient.components.CallLogComponent;
import com.devicecontroller.devicecontrollerclient.components.CameraComponent;
import com.devicecontroller.devicecontrollerclient.components.GPSComponent;
import com.devicecontroller.devicecontrollerclient.components.NetInfosComponent;
import com.devicecontroller.devicecontrollerclient.connection.AppProtocole;
import com.devicecontroller.devicecontrollerclient.connection.SocketManagement;

public class UserPage extends Activity {
    private Button on;
    private Button off;
    private ImageView exit;
    private Switch switchNetwork;
    private Switch switchGPS;
    private Switch switchCall;
    private Switch switchBattery;
    private Switch switchCamera;
    private UserPage instance;

    private NetInfosComponent netInfosComponent;
    private CameraComponent cameraComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_page);

        if (ContextCompat.checkSelfPermission (this,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale (this,
                    Manifest.permission.READ_CALL_LOG)) {
            } else {
                ActivityCompat.requestPermissions (this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        1);
            }
        }
        instance = this;
        addListenerOnButton ();
        initComponents ();
    }

    @Override
    protected void onResume() {
        super.onResume ();
        cameraComponent.stateOnResume ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        cameraComponent.stateOnPause ();
    }

    private void initComponents() {
        netInfosComponent = new NetInfosComponent (getApplicationContext ());
        cameraComponent = new CameraComponent (this);
    }

    public void addListenerOnButton() {

        exit = findViewById (R.id.exit);
        exit.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {
                Intent it = new Intent (getApplicationContext (), MainActivity.class);
            }
        });

        switchNetwork = findViewById (R.id.switchReseau);
        switchNetwork.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SocketManagement.postRequest (AppProtocole.NETWORK, netInfosComponent.networkInfo ());
                }
            }
        });


        switchGPS = findViewById (R.id.switchGPS);
        switchGPS.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GPSComponent.getPosition (instance);
                    try {
                        Thread.sleep (1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                    String s = GPSComponent.GPSLoc;
                    Log.e ("GPS VAL", "VAL : " + s);
                    SocketManagement.postRequest (AppProtocole.LOCATION, s);
                }
            }
        });

        switchCall = findViewById (R.id.switchCallList);
        switchCall.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SocketManagement.postRequest (AppProtocole.CALL_LIST, new CallLogComponent (getApplicationContext ()).getCallDetails ());
                    Log.e ("CALL", new CallLogComponent (getApplicationContext ()).getCallDetails ());
                }
            }
        });

        switchBattery = findViewById (R.id.switchBattery);
        switchBattery.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SocketManagement.postRequest (AppProtocole.BATTERY, new BatteryComponent (getApplicationContext ()).batteryLevel ());
                }
            }
        });

        switchCamera = findViewById (R.id.switchCamera);
        switchCamera.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String s = cameraComponent.takePicture ();
                    Log.e ("S VAL", "test : " + s);
                    Log.e ("S LENGTH", "test : " + s.length ());
                    SocketManagement.postRequest (AppProtocole.CAMERA, s);
                }
            }
        });

    }
}
