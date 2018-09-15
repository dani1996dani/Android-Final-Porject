import Consts.Consts;
import DatabaseIO.DatabaseManager;
import DatabaseIO.TasksDatabase;
import DatabaseIO.UsersDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@WebServlet(name = "TasksServlet")
public class TasksServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = request.getInputStream();
        String requestString = "";
        byte[] buffer = new byte[1024];
        int actuallyRead;
        while ((actuallyRead = inputStream.read(buffer)) != -1) {
            requestString += new String(buffer, 0, actuallyRead);
        }

        String action = "";
        String token = "";
        JSONObject data = null;

        try {
            JSONObject requestJSON = new JSONObject(requestString);
            action = requestJSON.getString("action");
            token = requestJSON.getString("token");
            data = requestJSON.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

        String result = "";

        switch (action) {
            case "add":
                int taskId = TasksDatabase.addTaskToDatabase(token, data);
                result = taskId > -1 ? String.valueOf(taskId) : "fail";
                break;
            case "update":
                TasksDatabase.updateTaskInDatabase(token, data);
                break;

            default:
                break;
        }

        response.getWriter().append(result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String action = request.getParameter("action");

        if (token == null || action == null || token.isEmpty() || action.isEmpty())
            return;

        String result;

        switch (action) {
            case "get":
                result = TasksDatabase.getTasksFromDatabase(token);
                response.setContentType("application/json");
                response.getWriter().append(result);
                break;

            case "delete":
                int taskId;
                try {
                    taskId = Integer.valueOf(request.getParameter("task_id"));
                } catch (Exception e) {
                    return;
                }
                result = TasksDatabase.deleteTaskFromDatabase(token, taskId) ? "success" : "fail"; //if deleted successfully
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().append(result);
                break;

            default:
                break;
        }


    }


}
