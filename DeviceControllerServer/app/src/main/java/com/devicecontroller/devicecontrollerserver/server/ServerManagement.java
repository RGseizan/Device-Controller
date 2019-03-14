package com.devicecontroller.devicecontrollerserver.server;

import android.accounts.Account;
import android.util.Log;

import com.devicecontroller.devicecontrollerserver.connection.AppProtocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.devicecontroller.devicecontrollerserver.server.ServerImplementation.verifIfTagExist;

public class ServerManagement implements Runnable {

    private Socket s;
    private BufferedReader brs;
    private BufferedWriter bws;
    public static String LOCATION;
    public static String BATTERY;
    public static String CALL_LIST;
    public static String NETWORK;
    public static String CAMERA;

    private String tag;

    public ServerManagement(Socket s) {
        System.out.println("NOUVELLE CONNEXION IP : " + s.getInetAddress());
        this.s = s;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            brs = br;
            bws = bw;
            requestManagement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestManagement() throws Exception {
        String line = readBuff();
        Log.e("ServerManagement", "LINE RECU : " + line);
        Log.e("ServerManagement", "LINE LENGTH : " + line.length());
        String action = line.split(System.lineSeparator())[0];
        String request = null;
        if (line.split(System.lineSeparator()).length > 1) {
            request = line.substring(line.indexOf(System.lineSeparator()), line.length() - System.lineSeparator().length()+1);
        }
        Log.e("ServerManagement ", "REQUEST : " + request);
        Log.e("ServerManagement ", "REQUEST LENGTH : " + request.length());
        //request = line.split(System.lineSeparator())[1];
        System.out.println(line);
        System.out.println("action : " + action);
        String rep = AppProtocol.KO;

        switch (action) {
            case AppProtocol.USER_TAG:
                verifIfTagExist(request);
                break;
            case AppProtocol.PING:
                break;
            case AppProtocol.SCREEN_STATUS:
                break;
            case AppProtocol.LOCATION:
                LOCATION = request;
                break;
            case AppProtocol.CALL_LIST:
                CALL_LIST = request;
                break;
            case AppProtocol.CAMERA:
                CAMERA = request;
                break;
            case AppProtocol.NETWORK:
                NETWORK = request;
                break;
            case AppProtocol.BATTERY:
                BATTERY = request;
                break;
            default:
                break;
        }

        writeBuff(rep);

    }

    private String getRequestValue(boolean b) {
        return b == true ? AppProtocol.OK : AppProtocol.KO;
    }

    private String readBuff() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = brs.readLine()) != null) {
            if (line.equals(AppProtocol.END_REQUEST)) {
                break;
            }
            System.out.println(line);
            sb.append(line + System.lineSeparator());
        }

        return sb.substring(0, sb.length() - System.lineSeparator().length());
    }

    private void writeBuff(String msg) throws IOException {
        System.out.println("DEBUT REQUEST SEND :");
        bws.write(msg);
        System.out.print(msg);
        bws.newLine();
        System.out.print(System.lineSeparator());
        bws.write(AppProtocol.END_REQUEST);
        System.out.print(AppProtocol.END_REQUEST);
        System.out.print(System.lineSeparator());
        bws.newLine();
        System.out.println("FIN REQUEST SEND.");
        bws.flush();
    }
}