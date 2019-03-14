package com.devicecontroller.devicecontrollerclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerclient.connection.AppProtocole;
import com.devicecontroller.devicecontrollerclient.connection.SocketManagement;
import com.devicecontroller.devicecontrollerclient.connection.SocketTools;

import java.util.regex.Pattern;

public class UserLogin extends Activity {
    private EditText tag;
    private CheckBox remember;
    private Button validate;
    public static String IP;
    private ImageView config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_user_login);
        addListenerOnButton();
    }

    public void addListenerOnButton() {


        tag = findViewById (R.id.tag);
        IP = tag.getText ().toString ();

        validate = findViewById (R.id.validate);
        validate.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {
                //Log.e ("SOCKET REP : ", "REP : " + SocketManagement.postRequest (AppProtocole.USER_TAG, tag.getText ().toString ()));
                Intent it = new Intent (getApplicationContext (), UserPage.class);
                startActivity (it);
            }
        });

        remember = findViewById (R.id.remember);
        remember.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {

            }
        });
        config = findViewById (R.id.config);
        config.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {
                showAddItemDialog (v.getContext ());
            }
        });
    }

    private void showAddItemDialog(final Context c) {
        final EditText ip_serveur = new EditText (c);
        AlertDialog dialog = new AlertDialog.Builder (c)
                .setTitle ("config_server_popup")
                .setMessage ("127.0.0.1:8888")
                .setView (ip_serveur)
                .setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (verifIpPort (ip_serveur.getText ().toString ())) {
                            SocketTools.IP_Server = ip_serveur.getText ().toString ().split (":")[0];
                            SocketTools.PORT_Server = Integer.parseInt (ip_serveur.getText ().toString ().split (":")[1]);
                            /*if (!SocketManagement.pingServer ().equals (ProtocoleServer.OK))
                                Toast.makeText (c, getResources ().getString (R.string.error_server_unknow), Toast.LENGTH_SHORT).show ();
                            else {
                                Toast.makeText (c, getResources ().getString (R.string.server_connexion_success), Toast.LENGTH_SHORT).show ();
                                loginPrefsEditor.putString ("ip", SocketTools.IP_Server);
                                loginPrefsEditor.putInt ("port", SocketTools.PORT_Server);
                            }*/
                        } else {
                            Toast.makeText (c, "IP incorrect ...", Toast.LENGTH_SHORT).show ();
                        }
                    }
                })
                .setNegativeButton ("Cancel", null)
                .create ();
        dialog.show ();
    }

    public boolean verifIpPort(String val) {
        if (!val.contains (":"))
            return false;
        String moinsDeCent = "([0-9]{1,2}";
        String moinsDeDeuxCent = "(1[0-9]{1,2}";
        String moinsDeDeuxCentCinquante = "(2[0-4]{1,2}";
        String moinsDeDeuxCentCinquanteCinq = "(25[0-5]";
        String ipPoint = "(" + moinsDeCent + "\\.)|" + moinsDeDeuxCent + "\\.)|" + moinsDeDeuxCentCinquante + "\\.)|"
                + moinsDeDeuxCentCinquanteCinq + "\\.))";
        String ip = "(" + moinsDeCent + ")|" + moinsDeDeuxCent + ")|" + moinsDeDeuxCentCinquante + ")|"
                + moinsDeDeuxCentCinquanteCinq + "))";

        Log.e ("REGEX 1", "test");
        int port;
        try {
            port = Integer.valueOf (val.split (":")[1]);
            if (!(port >= 1 && port <= 65536))
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        Log.e ("REGEX 1", "test");
        return Pattern.matches (ipPoint + "{3}" + ip, val.split (":")[0]);
    }
}
