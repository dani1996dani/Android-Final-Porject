package com.home.todolist_hackeruproject.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.Fragments.Alertable;
import com.home.todolist_hackeruproject.Fragments.LoginFragment;
import com.home.todolist_hackeruproject.R;
import com.home.todolist_hackeruproject.Fragments.RegisterFragment;


import org.json.JSONObject;

import com.home.todolist_hackeruproject.Credentials.*;

public class LoginActivity extends Activity implements SuccessfulLoginListener, AlertableSwapListener {

    FrameLayout loginFragmentContainer;
    SharedPreferences sharedPreferences;
    Alertable currentAlertable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null) {
            goToListActivity(token);
            return;
        }

        boolean shouldLoadLogin = getIntent().getBooleanExtra("login", false);
        loginFragmentContainer = findViewById(R.id.loginFragmentContainer);

        if (shouldLoadLogin) {
            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setLoginListener(this);
            loginFragment.setAlertableSwapListener(this);
            currentAlertable = loginFragment;

            getFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, loginFragment).commit();
        } else {


            RegisterFragment registerFragment = new RegisterFragment();
            registerFragment.setLoginListener(this);
            registerFragment.setAlertableSwapListener(this);
            currentAlertable = registerFragment;

            getFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, registerFragment).commit();
        }

    }

    @Override
    public void onLoginAttempt(JSONObject resultFromServer) {
        String response = resultFromServer.optString("response");
        String userToken = resultFromServer.optString("token");

        if (response.equals("success")) {
            saveTokenToSharedPreferences(userToken);
            goToListActivity(userToken);
        } else {
            currentAlertable.toggleAlertMessage(true, response);
        }
    }

    @Override
    public void onBackPressed() {
        Consts.defaultBackPressed(this);
    }

    public void saveTokenToSharedPreferences(String token) {
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        sharedPreferences.edit().putString("token", token).commit();
    }

    public void goToListActivity(String token) {
        Intent i = new Intent(this, ListActivity.class);
        i.putExtra("token", token);
        startActivity(i);
        finish();
    }

    @Override
    public void onAlertableSwapped(Alertable alertable) {
        this.currentAlertable = alertable;
    }
}


