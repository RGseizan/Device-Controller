package com.devicecontroller.devicecontrollerserver.server;

import android.util.Log;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchServer implements Runnable {

    private static int PORT = 8888;
    public static List<ServerManagement> listClient;

    @Override
    public void run() {
        ExecutorService es = Executors.newFixedThreadPool(4);
        listClient = new ArrayList<>();
        try {
            try (ServerSocket ss = new ServerSocket(PORT)) {
                displayIp();
                while (true) {
                    ServerManagement si = new ServerManagement(ss.accept());
                    listClient.add(si);
                    es.execute(si);
                }
            }
        } catch (BindException e) {
            System.err.println("Adresse deja utilisee ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayIp() {
        Log.i("Serveur IP ", "               ***************************");
        Log.i("Serveur IP ", "               *    Serveur demarre !   *");
        Log.i("Serveur IP ", "               *       Port : " + PORT + "       *");
        Log.i("Serveur IP ", "               ***************************");
    }
}
