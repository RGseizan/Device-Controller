package com.devicecontroller.devicecontrollerserver.server;

import android.util.Log;

import com.devicecontroller.devicecontrollerserver.MainActivity;
import com.devicecontroller.devicecontrollerserver.database.DBConstants;
import com.devicecontroller.devicecontrollerserver.database.DBRequest;
import com.devicecontroller.devicecontrollerserver.database.exception.DBExceptionNullValues;

import static com.devicecontroller.devicecontrollerserver.database.DBConstants.KEY_COL_TAG;

public class ServerImplementation {

    private static DBRequest dbr;

    public static boolean verifIfTagExist(String tag) {
        dbr = new DBRequest(MainActivity.mainActivityContext);
        try {
            Log.e("TAG VAL ", "TAG : " + tag);
            String res = dbr.getResults(dbr.select(DBConstants.TAG_TABLE, new String[]{KEY_COL_TAG}, KEY_COL_TAG + "=?", new String[]{tag}, null, null, null));
            Log.e("RESULT TAG SELECT ", "RES : " + res);
        } catch (DBExceptionNullValues dbExceptionNullValues) {
            dbExceptionNullValues.printStackTrace();
            return false;
        }
        return true;
    }
}
