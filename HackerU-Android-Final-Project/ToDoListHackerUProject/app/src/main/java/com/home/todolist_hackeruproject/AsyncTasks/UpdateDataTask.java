package com.home.todolist_hackeruproject.AsyncTasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.StreamManager;
import com.home.todolist_hackeruproject.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UpdateDataTask extends AsyncTask<String, Void, String> {

    private SuccessfulUpdateDataListener successfulUpdateDataListener;
    int taskPosition;
    Task task;
    String newTitle,newContent;


    public UpdateDataTask(SuccessfulUpdateDataListener successfulUpdateDataListener, int taskPosition, Task task, String newTitle, String newContent) {
        this.successfulUpdateDataListener = successfulUpdateDataListener;
        this.taskPosition = taskPosition;
        this.task = task;
        this.newTitle = newTitle;
        this.newContent = newContent;
    }


    /**
     * Sending POST to server - sending the task to update in JSON format
     **/


    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection connection = null;


        try {
            JSONObject dataToServer = new JSONObject();
            dataToServer.put("token", strings[0]);
            dataToServer.put("action", strings[1]);
            dataToServer.put("data", new JSONObject(strings[2]));

            url = new URL(Consts.HOST + "/Tasks");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(Consts.SERVER_TIMEOUT);

            outputStream = connection.getOutputStream();
            outputStream.write(dataToServer.toString().getBytes());

            inputStream = connection.getInputStream();

            result = StreamManager.getStreamManager().decodeInputStream(inputStream);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            StreamManager.getStreamManager().closeConnections(inputStream, connection);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("success")){
            successfulUpdateDataListener.onSuccessfulTaskUpdate(taskPosition,task,newTitle,newContent);
        }
        else
            successfulUpdateDataListener.onFailedTaskUpdate();
    }
}
