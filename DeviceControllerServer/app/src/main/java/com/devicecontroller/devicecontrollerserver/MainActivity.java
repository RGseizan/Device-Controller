package com.devicecontroller.devicecontrollerserver;

import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.database.DBConstants;
import com.devicecontroller.devicecontrollerserver.database.DBRequest;
import com.devicecontroller.devicecontrollerserver.database.exception.DBException;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private CheckBox rememberMe;
    private EditText login;
    private EditText psw;
    private DBRequest dbr;
    public static Context mainActivityContext;
    public String trueLogin = "user";
    public String truePsw = "motdepasse";
    private static String savedLogin = "";
    private static String savedPsw = "";
    public static boolean remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        rememberMe = findViewById(R.id.checkBox);
        login = findViewById(R.id.login_text);
        psw = findViewById(R.id.psw_text);
        if (remember) {
            rememberMe.setChecked(true);
        } else {
            rememberMe.setChecked(false);
        }
        if (rememberMe.isChecked()) {
            login.setText(savedLogin);
            psw.setText(savedPsw);
        } else {
            login.setText("user");
            psw.setText("motdepasse");
        }
        mainActivityContext = getApplicationContext();
        dbr = new DBRequest(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbr.stateOnResume();
        if (rememberMe.isChecked()) {
            login.setText(savedLogin);
            psw.setText(savedPsw);
        } else {
            login.setText("user");
            psw.setText("motdepasse");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbr.stateOnPause();
    }

    public void affichOption(final View view) {
        Log.e("DEBUT REQUET : ", "DEB");

        Intent intent = new Intent(context, OptionActivity.class);
        //startActivity(intent);

        // TEST BDD
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.TAG_KEY_COL_NAME, "ossama");
        cv.put(DBConstants.KEY_COL_TAG, "13246589");
        cv.put(DBConstants.TAG_KEY_COL_STATUS, "13246589");
        try {
            Log.e("INSERT", "INS");
            dbr.insert(DBConstants.TAG_TABLE, cv);
            Log.e("SELECT VAL : ", dbr.getResults(dbr.select(DBConstants.TAG_TABLE, null, null)));

            ContentValues cv2 = new ContentValues();
            cv2.put(DBConstants.TAG_KEY_COL_NAME, "UPDATED_NAME");
            cv2.put(DBConstants.KEY_COL_TAG, "13246589");
            cv2.put(DBConstants.TAG_KEY_COL_STATUS, "13246589");
            Log.e("UPDATE", "UPD");
            dbr.update(DBConstants.TAG_TABLE, cv2, 13246589);
            Log.e("SELECT VAL : ", dbr.getResults(dbr.select(DBConstants.TAG_TABLE, null, null)));

            Log.e("DELETE", "DEL");
            dbr.delete(DBConstants.TAG_TABLE, 13246589);
        } catch (DBException dbException) {
            dbException.printStackTrace();
        }
        //FIN TEST BDD
    }

    public void quitter(final View view) {
        finish();
    }

    public void cleanLogin(View view) {
        login.setText("");

    }

    public void cleanPsw(View view) {
        psw = findViewById(R.id.psw_text);
        psw.setText("");
    }

    public void valider(final View view) {
        rememberMe = findViewById(R.id.checkBox);
        login = findViewById(R.id.login_text);
        psw = findViewById(R.id.psw_text);
        if (rememberMe.isChecked()) {
            savedLogin = login.getText().toString();
            savedPsw = psw.getText().toString();
        }
        if (login.getText().toString().toLowerCase().equals(trueLogin)) {
            if (psw.getText().toString().toLowerCase().equals(truePsw)) {
                Intent intent = new Intent(context, ClientSelectorActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Your password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Unknown user", Toast.LENGTH_SHORT).show();
        }

    }
}