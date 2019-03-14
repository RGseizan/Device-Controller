package com.devicecontroller.devicecontrollerclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DeviceControllerService extends Service {
    public DeviceControllerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
