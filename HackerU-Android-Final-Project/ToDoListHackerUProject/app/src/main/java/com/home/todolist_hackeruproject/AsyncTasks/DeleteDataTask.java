package com.home.todolist_hackeruproject.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.home.todolist_hackeruproject.Adapters.TaskRemovedListener;
import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.StreamManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DeleteDataTask extends AsyncTask<String, Void, String> {

    private TaskRemovedListener taskRemovedListener;
    private int taskId;

    public DeleteDataTask(TaskRemovedListener taskRemovedListener) {
        this.taskRemovedListener = taskRemovedListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        URL url;
        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            taskId = Integer.valueOf(strings[1]);
            url = new URL(Consts.HOST + "/Tasks?token=" + strings[0] + "&action=delete&task_id=" + strings[1]);
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
        return result;

    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("success"))
            taskRemovedListener.onRemove();
        Log.d("dani", "onPostExecute: " + s);
    }
}
