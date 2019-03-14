package com.devicecontroller.devicecontrollerclient.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import lombok.Getter;

public class SocketTools implements Callable<String> {

    public static String IP_Server = "localhost";
    public static int PORT_Server = 8888;

    private static Socket s;
    private static BufferedReader br;
    public static BufferedWriter bw;
    private String params;
    private String action;
    private String serverAction;

    public SocketTools(String params, String serverAction, String action) {
        initSocket();

            //Log.w(" params", params);

        Log.w(" serverAction", serverAction);
        Log.w(" action", action);
        this.params = params;
        this.action = action;
        this.serverAction = serverAction;
    }

    @Override
    public String call() {
        switch (action) {
            case AppProtocole.PING:
                return writePing(serverAction);

            case AppProtocole.POST:
                writeBuff(serverAction, params);
                break;

            default:
                break;
        }
        String s = readBuff();
        return s;
    }

    public static void initSocket() {
        s = new Socket();
        try {
            s.connect(new InetSocketAddress(IP_Server, PORT_Server), 2000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String writePing(String action) {
        String line;
        try {
            bw.write(action);
            bw.newLine();
            bw.write(AppProtocole.END_REQUEST);
            bw.newLine();
            bw.flush();

            while ((line = br.readLine()) != null) {
                if (line.contains(AppProtocole.END_REQUEST)) {
                    break;
                }
            }
        } catch (SocketTimeoutException ste) {
            Log.e("TIMEOUT SOCKET", "TIMEOUT");
            return AppProtocole.CON_SERVER_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return AppProtocole.CON_SERVER_ERROR;
        }
        Log.e("OK SOCKET", "OK");
        return AppProtocole.OK;
    }

    public void writeBuff(String action, String msg) {

        try {
            bw.write(action);
            bw.newLine();
            bw.write(msg);
            bw.newLine();
            bw.write(AppProtocole.END_REQUEST);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readBuff() {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains(AppProtocole.END_REQUEST)) {
                    break;
                }
                sb.append(line + System.lineSeparator());
            }
        } catch (SocketTimeoutException ste) {
            Log.e("TIMEOUT SOCKET", "TIMEOUT");
            return AppProtocole.CON_SERVER_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.substring(0, sb.length() - System.lineSeparator().length());
    }

}