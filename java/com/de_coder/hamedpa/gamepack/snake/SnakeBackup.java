package com.de_coder.hamedpa.gamepack.snake;
//Developed by HamedPa

import android.annotation.TargetApi;
import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.os.Build;



@TargetApi(Build.VERSION_CODES.FROYO)
public class SnakeBackup extends BackupAgentHelper {

    static final String PREFS = "settings";


    static final String PREFS_BACKUP_KEY = "settings";


    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS);
        addHelper(PREFS_BACKUP_KEY, helper);
    }
}