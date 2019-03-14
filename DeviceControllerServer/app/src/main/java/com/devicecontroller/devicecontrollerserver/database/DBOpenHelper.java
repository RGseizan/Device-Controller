package com.devicecontroller.devicecontrollerserver.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import static com.devicecontroller.devicecontrollerserver.database.DBConstants.*;

public class DBOpenHelper extends SQLiteOpenHelper {


    // The static string to create the database.
    private static final String CREATE_GUEST_TABLE = "CREATE TABLE "
            + GUEST_TABLE + "("
            + GUEST_KEY_COL_TAG + " INTEGER PRIMARY KEY, "
            + KEY_COL_NAME + " VARCHAR(20) NOT NULL, "
            + GUEST_KEY_COL_URL_ICON + " VARCHAR(255) NOT NULL, "
            + GUEST_KEY_COL_LAST_IP + " VARCHAR(255) NOT NULL, "
            + GUEST_KEY_COL_RIGHTS + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + GUEST_KEY_COL_TAG + ") REFERENCES " + TAG_TABLE + "(" + KEY_COL_TAG + ") ON UPDATE CASCADE ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_COL_NAME + ") REFERENCES " + TAG_TABLE + "(" + TAG_KEY_COL_NAME + ") ON UPDATE CASCADE ON DELETE CASCADE )";

    private static final String CREATE_TAG_TABLE = "CREATE TABLE "
            + TAG_TABLE + "("
            + KEY_COL_TAG + " INTEGER PRIMARY KEY, "
            + TAG_KEY_COL_NAME + " VARCHAR(20) NOT NULL, "
            + TAG_KEY_COL_STATUS + " INTEGER NOT NULL)";

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // permet d'activer les cles etrangeres
        db.execSQL("PRAGMA foreign_keys = ON");
        // Create the new database using the SQL string Database_create
        db.execSQL(CREATE_GUEST_TABLE);
        db.execSQL(CREATE_TAG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DBOpenHelper", "Mise à jour de la version " + oldVersion
                + " vers la version " + newVersion
                + ", les anciennes données seront détruites ");
        // Drop the old database
        db.execSQL("DROP TABLE IF EXISTS " + GUEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAG_TABLE);
        // Create the new one
        onCreate(db);
        // or do a smartest stuff
    }
}
