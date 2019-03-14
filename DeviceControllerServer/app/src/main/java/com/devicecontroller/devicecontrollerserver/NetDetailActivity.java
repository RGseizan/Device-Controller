package com.devicecontroller.devicecontrollerserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.connection.AppProtocol;

import java.text.ParseException;

import androidx.versionedparcelable.VersionedParcel;

public class NetDetailActivity extends AppCompatActivity {
    String infoClient;
    String source;
    TextView theText;
    ProgressBar prog;
    TextView percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_detail);
        Log.e("fdffd","je suis rentrer dans la classe");
        infoClient = "infoClient_error";
        source ="source_error";
        percent=(TextView)findViewById(R.id.percentageView);
        prog =(ProgressBar) findViewById(R.id.progressBar);
        theText=findViewById(R.id.textView_information);
        Intent intent = getIntent();

            infoClient = intent.getStringExtra("infoClient");

        Log.e("infc","infoclient>"+infoClient);

            source = intent.getStringExtra("source");

        Log.e("src","source>"+source);

        switch (source){
           case "Battery":
               int value=50;
               try{
                   value = Integer.parseInt(infoClient.trim ().substring(0,infoClient.indexOf("%")-1));
                   Log.e("BATTERY","TEST"+value);
               }catch (Exception e){
                   Log.e("BATTERYfail","TESTi"+value);
                  value =50;
                  e.printStackTrace ();
               }
               theText.setVisibility(View.INVISIBLE);
               percent.setText(value+"%");
               percent.setVisibility(View.VISIBLE);
               prog.setVisibility(View.VISIBLE);
               prog.setProgress(value);
               break;
            default:
                cacherBar();
                theText.setVisibility(View.VISIBLE);
                   theText.setText(infoClient);
               break;
        }
    }

    public void annulerOpt(View view){
        Toast.makeText(getBaseContext(),"Return",Toast.LENGTH_SHORT).show();
        finish();
    }
    public void cacherBar(){
        percent.setVisibility(View.INVISIBLE);
        prog.setVisibility(View.INVISIBLE);
    }
}
