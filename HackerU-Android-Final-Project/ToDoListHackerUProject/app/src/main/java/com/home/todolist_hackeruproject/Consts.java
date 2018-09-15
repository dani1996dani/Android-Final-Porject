package com.home.todolist_hackeruproject;

import android.app.Activity;
import android.content.Intent;

public class Consts {
    //TODO:change back to "http://10.0.2.2:8081"
    public static final String HOST = "http://192.168.1.5:8081";
    public static final int SERVER_TIMEOUT = 5000;

    public static void defaultBackPressed(Activity activity){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
    }

}
