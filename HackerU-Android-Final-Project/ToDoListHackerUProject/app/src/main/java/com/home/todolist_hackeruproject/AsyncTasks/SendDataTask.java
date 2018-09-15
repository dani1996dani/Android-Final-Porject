package com.home.todolist_hackeruproject.AsyncTasks;

import android.os.AsyncTask;

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
import java.net.URL;

public class SendDataTask extends AsyncTask<String, Void, String> {

    private Task task;

    /**
     * Receiving a new task that has only title and content, sending it to the server, and if the server accepts the task,
     * a task_id is returned and the task_id is assigned to the task object, in onPostExecute.
     * @param task the task that needs its task_id populated.
     */
    public SendDataTask(Task task) {
        this.task = task;
    }

    @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            HttpURLConnection connection = null;

            try {

                url = new URL(Consts.HOST + "/Tasks");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);

                JSONObject dataToServer = new JSONObject();
                dataToServer.put("token", strings[0]);
                dataToServer.put("action", strings[1]);
                dataToServer.put("data", new JSONObject(strings[2]));

                outputStream = connection.getOutputStream();

                outputStream.write(dataToServer.toString().getBytes());

                inputStream = connection.getInputStream();

                result = StreamManager.getStreamManager().decodeInputStream(inputStream);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                StreamManager.getStreamManager().closeConnections(inputStream,outputStream,connection);
            }
            return result;
        }

    @Override
    protected void onPostExecute(String s) {
        try {
            task.setId(Integer.valueOf(s)); //In case the response from the server is "fail"
        }
        catch (Exception e){
            return;
        }

    }
}