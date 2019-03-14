package com.devicecontroller.devicecontrollerserver;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;


public class OptionActivity extends AppCompatActivity {

    Context context;
     CheckBox map;
     CheckBox screen;
     CheckBox battery;
     CheckBox call;
     CheckBox wifi;
     CheckBox camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt);
        context=getApplicationContext();
         map=findViewById(R.id.mapCheckBox);
         screen=findViewById(R.id.screenCheckBox);
         battery=findViewById(R.id.batteryCheckBox);
         call=findViewById(R.id.callCheckBox);
         wifi=findViewById(R.id.wifiCheckBox);
         camera=findViewById(R.id.cameraCheckBox);

    }



    public void validerOpt(View view){
        map=findViewById(R.id.mapCheckBox);
        screen=findViewById(R.id.screenCheckBox);
        battery=findViewById(R.id.batteryCheckBox);
        call=findViewById(R.id.callCheckBox);
        wifi=findViewById(R.id.wifiCheckBox);
        camera=findViewById(R.id.cameraCheckBox);
        if(map.isChecked()){
        ClientSelectorActivity.gpsVisibility=true;
        }else{
            ClientSelectorActivity.gpsVisibility=false;
        }
        if(screen.isChecked()){
            ClientSelectorActivity.screenVisibility=true;
        }else{
            ClientSelectorActivity.screenVisibility=false;
        }
        if(battery.isChecked()){

            ClientSelectorActivity.batteryVisibility=true;
        }else{
            ClientSelectorActivity.batteryVisibility=false;
        }
        if(call.isChecked()){

            ClientSelectorActivity.callVisibility=true;
        }else{
            ClientSelectorActivity.callVisibility=false;
        }
        if(wifi.isChecked()){

            ClientSelectorActivity.connectiviteVisibility=true;
        }else{
            ClientSelectorActivity.connectiviteVisibility=false;
        }
        if(camera.isChecked()){

            ClientSelectorActivity.cameraVisibility=true;
        }else{
            ClientSelectorActivity.cameraVisibility=false;
        }
        Toast.makeText(getBaseContext(),"Applied",Toast.LENGTH_SHORT).show();
        finish();

    }

    public void annulerOpt(View view){
        Toast.makeText(getBaseContext(),"Canceled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
