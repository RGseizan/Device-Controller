package com.devicecontroller.devicecontrollerclient.components;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.devicecontroller.devicecontrollerclient.connection.AppProtocole;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Classe permettant de recuperer les informations reseaux de l'appareil et de les renvoyer sous forme de String.
 *
 * @author Damien SAILLY
 */

public class NetInfosComponent extends CComponent {


    private Context context;

    /**
     * Constructeur de la classe.
     *
     * @param context: on donne le context de l'activite en parametre. Permet d'acceder aux services de l'application.
     */
    public NetInfosComponent(Context context) {
        this.context = context;
    }


    /**
     * Methode permettant d'envoyer les donnees reseaux via le serveur.
     * TYPE_;_IP_;_FAI
     * @return String. (ex : MOBILE_;_10.144.153.103-10.129.251.163_;_orange)
     */
    public String networkInfo() {

        StringBuilder sb = new StringBuilder();

        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressWarnings("deprecation")
        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        //On recupere uniquement les informations de la connexion active (WIFI ou MOBILE).

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (netInfo.isConnected()) {
                    /*sb.append("Connection type: " + netInfo.getTypeName() + System.lineSeparator()
                            + "IP:" + GetDeviceipWiFiData(context) + System.lineSeparator() +
                            "ISP: " + CM.getActiveNetworkInfo().getExtraInfo());*/
                    sb.append(netInfo.getTypeName() + AppProtocole.DATA_SEPARATOR +
                            GetDeviceipWiFiData(context) + AppProtocole.DATA_SEPARATOR +
                            CM.getActiveNetworkInfo().getExtraInfo());
                }
            }


            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {

                if (netInfo.isConnected()) {
                    /*sb.append("Connection type: " + netInfo.getTypeName() + System.getProperty("line.separator") +
                            GetDeviceipMobileData() + System.getProperty("line.separator") +
                            "ISP: " + CM.getActiveNetworkInfo().getExtraInfo());*/
                    sb.append(netInfo.getTypeName() + AppProtocole.DATA_SEPARATOR +
                            GetDeviceipMobileData() + AppProtocole.DATA_SEPARATOR +
                            CM.getActiveNetworkInfo().getExtraInfo());
                }
            }
        }

        return sb.toString();
    }


    /**
     * Methode permettant de recuperer l'IPv4 de l'appareil lors d'une connexion MOBILE.
     *
     * @return String: l'adresse IPv4.
     */
    public String GetDeviceipMobileData() {
        StringBuilder sb = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    // on recupere l'IPv4.
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        sb.append(inetAddress.getHostAddress());
                        sb.append("-");
                        // on recupere l'IPv6.
                    } /*if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        sb.append("IPv6: "+inetAddress.getHostAddress().toString());
                    }*/
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Methode permettant de recuperer l'IP de l'appareil lors d'une connexion WIFI.
     *
     * @param context: permet d'acceder aux services de l'application et creer un WifiManager
     * @return String: l'IP de l'appareil.
     */

    public String GetDeviceipWiFiData(Context context) {

        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

}
