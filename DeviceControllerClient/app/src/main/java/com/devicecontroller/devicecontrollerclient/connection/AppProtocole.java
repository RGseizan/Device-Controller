package com.devicecontroller.devicecontrollerclient.connection;

public interface AppProtocole {

    String USER_TAG = "[_USER_TAG_]";

    String SCREEN_STATUS = "[_SCREEN_STATUS_]";
    String LOCATION = "[_LOCATION_STATUS_]";
    String CALL_LIST = "[_CALL_LIST_STATUS_]";
    String CAMERA = "[_CAMERA_STATUS_]";
    String NETWORK = "[_NETWORK_STATUS_]";
    String BATTERY = "[_BATTERY_STATUS_]";

    String PING = "[_PING_]";
    String POST = "[_POST_]";
    String END_REQUEST = "[_END_REQUEST_]";
    String OK = "[_OK_]";
    String KO = "[_KO_]";
    String DATA_SEPARATOR = "_;_";

    String INFO_SERVEUR = "[_INFO_SERVEUR_]";
    String CON_SERVER_ERROR = "[_CON_SERVER_ERROR_]";
}
