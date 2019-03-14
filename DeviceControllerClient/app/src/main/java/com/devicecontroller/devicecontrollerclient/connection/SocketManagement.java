package com.devicecontroller.devicecontrollerclient.connection;

import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocketManagement {

    private static ExecutorService es = Executors.newFixedThreadPool (4);

    public static String pingServer() {
        Future<String> res = es.submit (new SocketTools ("", AppProtocole.PING, AppProtocole.PING));

        try {
            return res.get ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return AppProtocole.CON_SERVER_ERROR;
    }

    public static String postRequest(String action, String params) {
        Log.e ("SocketManagement", "PARAMS : " + params);
        Future<String> res = es.submit (new SocketTools (params, action, AppProtocole.POST));
        try {
            return res.get ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return AppProtocole.CON_SERVER_ERROR;
    }

}
