package com.devicecontroller.devicecontrollerclient.components;

import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;

import static android.content.Context.BATTERY_SERVICE;

public class BatteryComponent extends CComponent {

    private Context context;

    public BatteryComponent(Context context) {
        this.context = context;
    }

    public String batteryLevel() {
        BatteryManager bm = (BatteryManager) context.getSystemService (BATTERY_SERVICE);
        int batLevel = bm.getIntProperty (BatteryManager.BATTERY_PROPERTY_CAPACITY);
        Log.e ("BatteryComponent", batLevel + "%");
        return batLevel + "%";
    }
}
