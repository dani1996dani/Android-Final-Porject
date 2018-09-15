package com.home.todolist_hackeruproject.AsyncTasks;

import android.os.AsyncTask;

import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.Credentials.SuccessfulLoginListener;
import com.home.todolist_hackeruproject.StreamManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginSignupTask extends AsyncTask<String, String, JSONObject> {

    private SuccessfulLoginListener loginListener;

    public LoginSignupTask(SuccessfulLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String result = "none";
        URL url;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {

            url = new URL(Consts.HOST + "/Login?email=" + strings[0] + "&password=" + strings[1] + "&action=" + strings[2]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(Consts.SERVER_TIMEOUT);

            inputStream = connection.getInputStream();

            result = StreamManager.getStreamManager().decodeInputStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamManager.getStreamManager().closeConnections(inputStream, connection);
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        loginListener.onLoginAttempt(s);


    }
}