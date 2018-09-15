package com.home.todolist_hackeruproject.Credentials;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.home.todolist_hackeruproject.Activities.LoginActivity;
import com.home.todolist_hackeruproject.AsyncTasks.LoginSignupTask;

import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class CredentialsManager {

    private static CredentialsManager credentialsManager;
    SuccessfulLoginListener loginListener;

    private CredentialsManager(){

    }

    public static CredentialsManager getCredentialsManager(){
        if(credentialsManager == null)
            credentialsManager = new CredentialsManager();
        return credentialsManager;
    }

    /*public CredentialsManager(SuccessfulLoginListener loginListener) {
        this.loginListener = loginListener;
    }*/

    public void setLoginListener(SuccessfulLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public boolean isValidEmail(String email) {
        boolean result = false;

        if (email == null || email.isEmpty() || email.length() > 32)
            return result;

        String emailRegex = "^[a-z0-9]+((\\.)?[a-zA-Z0-9-])*@([a-zA-Z0-9]+\\.)+[a-z]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        result = pattern.matcher(email).matches();


        return result;
    }

    public boolean isValidPassword(String password){
        boolean result = false;

        if(password == null || password.isEmpty() || password.length() > 32)
            return result;

        String passwordRegex = "^(?=.*\\d).{4,16}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        result = pattern.matcher(password).matches();

        return result;
    }

    public String loginOrRegister(String email, String password, String action) {
        String result = "";

        if (!isValidEmail(email)) {
            result = "Invalid Email";
            return result;
        }

        if(!isValidPassword(password)){
            result = " Password must be between 4 and 16 characters long and include at least one numeric digit.";
            return result;
        }

        new LoginSignupTask(loginListener).execute(email, password,action);
        return result;
    }

    /**
     * Logs out the current user, and transitions back to the Login Activity.
     * @param activity The Activity from where you wish to logout and go to the login activity.
     */
    public void logout(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("prefs", MODE_PRIVATE);
        sharedPreferences.edit().putString("token", null).commit();
        Intent i = new Intent(activity, LoginActivity.class);
        i.putExtra("login", true);
        activity.startActivity(i);
        activity.finish();
    }
}