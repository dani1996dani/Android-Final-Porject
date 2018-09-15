package com.home.todolist_hackeruproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class StreamManager {

    private static StreamManager streamManager;

    private StreamManager() {

    }

    public static StreamManager getStreamManager() {
        if (streamManager == null)
            streamManager = new StreamManager();
        return streamManager;
    }

    /**
     * @param inputStream The InputStream to be decoded.
     * @return encoded data in String format.
     * @throws IOException
     */
    public String decodeInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        String result = "";

        int actuallyRead;
        while ((actuallyRead = inputStream.read(buffer)) != -1)
            result += new String(buffer, 0, actuallyRead);

        return result;
    }

    /**
     * Close http connection and its streams
     **/
    public void closeConnections(InputStream inputStream, OutputStream outputStream, HttpURLConnection connection) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (connection != null)
            connection.disconnect();
    }

    /**
     * For when you dont have an OutputStream to close
     **/
    public void closeConnections(InputStream inputStream, HttpURLConnection connection) {
        closeConnections(inputStream, null, connection);
    }
}


