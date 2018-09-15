package com.home.todolist_hackeruproject.AsyncTasks;

import android.os.AsyncTask;

import com.home.todolist_hackeruproject.Adapters.TaskAdapter;
import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.StreamManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDataTask extends AsyncTask<String, String, JSONArray> {


    FinishedGettingTasksListener finishedGettingTasksListener;

    public GetDataTask(FinishedGettingTasksListener finishedGettingTasksListener) {
        this.finishedGettingTasksListener = finishedGettingTasksListener;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String result;
        URL url;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        JSONArray jsonArray = null;
        try {

            url = new URL(Consts.HOST + "/Tasks?token=" + strings[0] + "&action=" + strings[1]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            inputStream = connection.getInputStream();

            result = StreamManager.getStreamManager().decodeInputStream(inputStream);

            jsonArray = new JSONArray(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            StreamManager.getStreamManager().closeConnections(inputStream, connection);
        }
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        finishedGettingTasksListener.onFinishedGettingTasks(jsonArray);
    }
}