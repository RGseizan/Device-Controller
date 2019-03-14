package com.devicecontroller.devicecontrollerserver;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.components.CameraComponent;
import com.devicecontroller.devicecontrollerserver.components.FileSystemComponent;
import com.devicecontroller.devicecontrollerserver.components.GPSComponent;
import com.devicecontroller.devicecontrollerserver.components.NetDetailsComponent;
import com.devicecontroller.devicecontrollerserver.components.ScreenComponent;
import com.devicecontroller.devicecontrollerserver.components.SoundComponent;
import com.devicecontroller.devicecontrollerserver.connection.AppProtocol;
import com.devicecontroller.devicecontrollerserver.database.DBConstants;
import com.devicecontroller.devicecontrollerserver.database.DBRequest;
import com.devicecontroller.devicecontrollerserver.database.exception.DBException;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionNullValues;
import com.devicecontroller.devicecontrollerserver.server.LaunchServer;
import com.devicecontroller.devicecontrollerserver.server.ServerManagement;

import java.util.ArrayList;

public class ClientSelectorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScreenComponent.OnScreenFragmentInteractionListener, CameraComponent.OnCameraFragmentInteractionListener, FileSystemComponent.OnFileSystemFragmentInteractionListener, GPSComponent.OnGpsFragmentInteractionListener, NetDetailsComponent.OnNetDetailsFragmentInteractionListener, SoundComponent.OnSoundFragmentInteractionListener {
    NavigationView navigationView;
    int nbrItem;
    public Context context;
    TextView clientName;
    TextView clientInformation;
    ImageButton homeButon;
    private LaunchServer ls;
    CameraComponent fragmentCamera;
    FileSystemComponent fragmentFileSysteme;
    GPSComponent fragmentGps;
    NetDetailsComponent fragmentNetDetails;
    ScreenComponent fragmentScreen;
    SoundComponent fragmentSound;
    ImageButton buton_call;
    ImageButton buton_camera;
    ImageButton buton_screen;
    ImageButton buton_connectivite;
    ImageButton buton_gps;
    ImageButton buton_battery;
    public static Boolean callVisibility=true;
    public static Boolean cameraVisibility=true;
    public static Boolean screenVisibility=true;
    public static Boolean connectiviteVisibility=true;
    public static Boolean gpsVisibility=true;
    public static Boolean batteryVisibility=true;
    ArrayList<ImageButton> listeButon;
    ArrayList<String[]> listeClient;
    private DBRequest dbr;
    boolean premierLunch=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nbrItem = navigationView.getMenu().size();
        context = getApplicationContext();
        clientName = findViewById(R.id.textView_name);
        clientInformation = findViewById(R.id.textView_information);
        homeButon = findViewById(R.id.homeButton);
        homeButon.setVisibility(View.INVISIBLE);
        Thread t = new Thread(new LaunchServer());
        t.start();
        listeButon = new ArrayList<>();
        listeClient=new ArrayList<>();
        initialiserfragmentCall();
        initialiserfragmentScreen();
        initialiserfragmentNetDetails();
        initialiserfragmentGPS();
        initialiserfragmentBattery();
        initialiserfragmentCamera();
        for (int i = 0; i < listeButon.size(); i++) {
            listeButon.get(i).setVisibility(View.INVISIBLE);
        }
        dbr = new DBRequest(getApplicationContext());

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbr.stateOnResume();
        refreshNav();
        if(!premierLunch){
            changeVisibility();
        }
        premierLunch=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbr.stateOnPause();
    }

    private void initialiserfragmentCamera() {
        fragmentCamera = (CameraComponent) getSupportFragmentManager().findFragmentById(R.id.cameraFragment);
        buton_camera = findViewById(R.id.buton_camera);
        buton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCamera.doSomething("coucou");
            }
        });
        listeButon.add(buton_camera);

    }

    private void initialiserfragmentBattery() {
        fragmentFileSysteme = (FileSystemComponent) getSupportFragmentManager().findFragmentById(R.id.fileSystemFragment);
        buton_battery = findViewById(R.id.buton_battery);
        buton_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ici quand on clique sur le fragment
                Log.e("lkdkgfd",">je suis rentrer"+ServerManagement.BATTERY);
                fragmentNetDetails.doSomething(ServerManagement.BATTERY,"Battery");
            }
        });
        listeButon.add(buton_battery);
    }

    private void initialiserfragmentGPS() {
        fragmentGps = (GPSComponent) getSupportFragmentManager().findFragmentById(R.id.gpsFragment);
        buton_gps = findViewById(R.id.buton_gps);
        buton_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ici quand on clique sur le fragment
                fragmentGps.doSomething("je suis gps fragment");
            }
        });
        listeButon.add(buton_gps);
    }

    private void initialiserfragmentNetDetails() {
        fragmentNetDetails = (NetDetailsComponent) getSupportFragmentManager().findFragmentById(R.id.netDetailsFragment);
        buton_connectivite = findViewById(R.id.buton_netdetail);
        buton_connectivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ici quand on clique sur le fragment

                fragmentNetDetails.doSomething(ServerManagement.NETWORK,"Network");

            }
        });
        listeButon.add(buton_connectivite);
    }

    private void initialiserfragmentScreen() {
        fragmentScreen = (ScreenComponent) getSupportFragmentManager().findFragmentById(R.id.screenFragment);
        buton_screen = findViewById(R.id.buton_screen);
        buton_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Available soon",Toast.LENGTH_SHORT).show();
            }
        });
        listeButon.add(buton_screen);
    }

    private void initialiserfragmentCall() {
        fragmentSound = (SoundComponent) getSupportFragmentManager().findFragmentById(R.id.soundFragment);
        buton_call = findViewById(R.id.buton_call);
        buton_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLIENT",ServerManagement.CALL_LIST);
                fragmentSound.doSomething(ServerManagement.CALL_LIST,"Call");

            }
        });
        listeButon.add(buton_call);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, OptionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.getTitle();
        System.err.println(item.getTitle());
        if (!item.getTitle().equals("Add client")) {
            String title = "";
            Menu selected = navigationView.getMenu();
            for (int i = 0; i < selected.size(); i++) {
                if (selected.getItem(i).getTitle() == item.getTitle()) {
                    title = selected.getItem(i).getTitle().toString();
                    clientName.setText(title);
                }
            }
            for(int j =0;j<listeClient.size();j++){
                if (listeClient.get(j)[1].equals(item.getTitle())){
                    clientInformation.setText("Tag : "+listeClient.get(j)[0]+" visibility :"+listeClient.get(j)[2]);
                }
            }

            homeButon.setVisibility(View.VISIBLE);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            changeVisibility();
        } else {
            System.err.println("dans le else");
            showAddItemDialog(ClientSelectorActivity.this);

        }

        return true;
    }

    public void showAddItemDialog(final Context c) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cherche_client, null);
        final EditText tagSaisie = view.findViewById(R.id.edittextTag);
        final EditText nomSaisie = view.findViewById(R.id.edittextName);
        dialog.setView(view);
        dialog
                .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tag = tagSaisie.getText().toString();
                        String name = nomSaisie.getText().toString();
                        Log.w("MESSAGE DIALOG", tag);
                        navigationView.getMenu().add(name).setIcon(R.drawable.profil);
                        ContentValues cv = new ContentValues();
                        cv.put(DBConstants.TAG_KEY_COL_NAME, name);
                        cv.put(DBConstants.KEY_COL_TAG, tag);
                        cv.put(DBConstants.TAG_KEY_COL_STATUS, "0");
                        try {
                            Log.e("INSERT", "INS");
                            dbr.insert(DBConstants.TAG_TABLE, cv);
                        } catch (DBException dbException) {
                            dbException.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("annuler", null);
        dialog.show();
    }

    public void reinitialiserClient(View view) {
        for (int i = 0; i < listeButon.size(); i++) {
            listeButon.get(i).setVisibility(View.INVISIBLE);
        }
        homeButon.setVisibility(View.INVISIBLE);
        clientName.setText("Client Name");
        clientInformation.setText("Information client profil");
    }

    public void quitter(View view) {
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void refreshNav() {
        String title = "";
        Menu selected = navigationView.getMenu();
        listeClient.clear();
        selected.clear();
        selected.add("Add client").setIcon(R.drawable.plus);


        String[] liste = null;
        try {
            liste = dbr.getResults(dbr.select(DBConstants.TAG_TABLE, null, null)).split(System.lineSeparator());
        } catch (DBExceptionNullValues dbExceptionNullValues) {
            dbExceptionNullValues.printStackTrace();
        }
        if (liste != null)
            for (int i = 0; i < liste.length; i++) {
                String[] listeSplit = liste[i].split(AppProtocol.DATA_SEPARATOR);
                navigationView.getMenu().add(listeSplit[1]).setIcon(R.drawable.profil);
                listeClient.add(listeSplit);
            }
    }
   public void changeVisibility(){
       if(!cameraVisibility){
           buton_camera.setVisibility(View.INVISIBLE);
       }else{
           buton_camera.setVisibility(View.VISIBLE);
       }
       if(!batteryVisibility){
           buton_battery.setVisibility(View.INVISIBLE);
       }else{
           buton_battery.setVisibility(View.VISIBLE);
       }
       if(!gpsVisibility){
           buton_gps.setVisibility(View.INVISIBLE);
       }else{
           buton_gps.setVisibility(View.VISIBLE);
       }
       if(!connectiviteVisibility){
           buton_connectivite.setVisibility(View.INVISIBLE);
       }else{
           buton_connectivite.setVisibility(View.VISIBLE);
       }
       if(!screenVisibility){
           buton_screen.setVisibility(View.INVISIBLE);
       }else{
           buton_screen.setVisibility(View.VISIBLE);
       }
       if(!callVisibility){
           buton_call.setVisibility(View.INVISIBLE);
       }else{
           buton_call.setVisibility(View.VISIBLE);
       }
   }
}
