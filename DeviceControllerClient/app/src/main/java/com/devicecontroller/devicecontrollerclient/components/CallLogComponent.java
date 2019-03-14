package com.devicecontroller.devicecontrollerclient.components;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.devicecontroller.devicecontrollerclient.connection.AppProtocole;

import java.util.Date;

public class CallLogComponent extends CComponent {

    private Context context;

    public CallLogComponent(Context context){
        this.context = context;
    }


    public String getCallDetails() {

            StringBuilder stringBuilder = new StringBuilder();
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null, null, null, CallLog.Calls.DATE + " DESC");
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                stringBuilder.append("{Phone Number: " + phNumber+ "/ Call Type: "
                        + dir + "/ Call Date: " + callDayTime
                        + "/ Call duration in sec : " + callDuration+"}"+ AppProtocole.DATA_SEPARATOR);
            }
            cursor.close();
            return stringBuilder.toString();
        }
    }

