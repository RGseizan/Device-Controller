package com.devicecontroller.devicecontrollerserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.components.CallListAdapter;
import com.devicecontroller.devicecontrollerserver.connection.AppProtocol;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CallListActivity extends AppCompatActivity {

   private RecyclerView listCall;
    String infoClient;
    String source;
    Button cancel;
    private ArrayList<String> allCall = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_call_list_component);
        listCall = findViewById(R.id.list_call);
        infoClient = "infoClient_error";
        source ="source_error";
        cancel = findViewById(R.id.Button_cancel_call);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Return",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("infoClient")){
            infoClient = intent.getStringExtra("infoClient");
            System.err.println("infoclient>"+infoClient);
        }
        if (intent.hasExtra("source")){
            source = intent.getStringExtra("source");
            System.err.println("source>"+source);
        }
        listCall.setLayoutManager(new LinearLayoutManager(this));
        addList();
        listCall.setAdapter(new CallListAdapter(this,allCall));
    }

    private void addList(){
        String[]data = infoClient.split(AppProtocol.DATA_SEPARATOR);
        for(int i = 0; i < data.length;i++){

            allCall.add(data[i]);

        }
    }
}
