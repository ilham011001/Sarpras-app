package com.hamz.sistemsarpras;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hamz on 02/03/2018.
 */

public class PrefManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final int PREF_MODE = 0;
    private static final String PREF_NAME = "Session_Manager";
    private static final String IS_FIRST_TIME = "isFirstTime";

    private static final String IS_LOGGED_IN = "isLoggedIn";

    private static final String ID_USER = "idUser";
    private static final String NAME_USER = "nameUser";
    private static final String LEVEL_USER = "levelUser";

    private static final String IP_ADDRESS = "ipAdress";
    private static final String FOLDER_PROJECT = "folderProject";


    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PREF_MODE);
        editor = preferences.edit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn() {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setNameUser(String nameUser) {
        editor.putString(NAME_USER, nameUser);
        editor.commit();
    }

    public String getNameUser() {
        return preferences.getString(NAME_USER, "Empty");
    }

    public void setIdUser(Integer idUser) {
        editor.putInt(ID_USER, idUser);
        editor.commit();
    }

    public Integer getIdUser() {
        return preferences.getInt(ID_USER, 0);
    }

    public String getIpAddress() {
        return preferences.getString(IP_ADDRESS, "empty");
    }

    public void setIpAddress(String ipAddress) {
        editor.putString(IP_ADDRESS, ipAddress);
        editor.commit();
    }

    public String getFolderProject() {
        return preferences.getString(FOLDER_PROJECT, "sarpras");
    }

    public void setFolderProject(String folderProject) {
        editor.putString(FOLDER_PROJECT, folderProject);
        editor.commit();
    }

    public Integer getLevelUser() {
        return preferences.getInt(LEVEL_USER, 0);
    }

    public void setLevelUser(Integer levelUser) {
        editor.putInt(LEVEL_USER, levelUser);
        editor.commit();
    }
}
