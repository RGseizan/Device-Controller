package com.devicecontroller.devicecontrollerserver.database;

import android.provider.BaseColumns;

// @goals This class aims to show the constant to use for the DBOpenHelper */
public class DBConstants implements BaseColumns {
    // The database name
    public static final String DATABASE_NAME = "db_DeviceController.db";

    // The database version
    public static final int DATABASE_VERSION = 1;

    // The tables Names
    public static final String GUEST_TABLE = "guest";
    public static final String TAG_TABLE = "tag";

    // Noms de colonnes
    //Generic keys
    public static final String KEY_COL_TAG = "tag";// Mandatory
    public static final String KEY_COL_NAME = "name";// Mandatory

    // GUEST Table
    public static final String GUEST_KEY_COL_TAG = "tag";// Mandatory
    public static final String GUEST_KEY_COL_URL_ICON = "url_icon";// Mandatory
    public static final String GUEST_KEY_COL_LAST_IP = "last_ip";// Mandatory
    public static final String GUEST_KEY_COL_RIGHTS = "rights";// Mandatory

    // TAG Table
    public static final String TAG_KEY_COL_NAME = "name";// Mandatory
    public static final String TAG_KEY_COL_STATUS = "status";// Mandatory

    // Index des colonnes
    // GUEST Table
    public static final int GUEST_TAG_COLUMN = 1;
    public static final int GUEST_NAME_COLUMN = 2;
    public static final int GUEST_URL_ICON_COLUMN = 3;
    public static final int GUEST_LAST_IP_COLUMN = 4;
    public static final int GUEST_RIGHTS_COLUMN = 5;

    // TAG Table
    public static final int TAG_TAG_COLUMN = 1;
    public static final int TAG_TAG_NAME = 2;
    public static final int TAG_STATUS_COLUMN = 3;
}
