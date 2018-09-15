package DatabaseIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class TasksDatabase {

    /**
     * @param token Represents the token the client has passed in Http.
     * @return A JSONArray that contains the client's tasks, in <B>String</B> format.
     */
    public static String getTasksFromDatabase(String token) {
        String result;
        int userId = UsersDatabase.getUserIdFromDatabase(token);
        if (userId == -1)
            return new JSONArray().toString();
        JSONArray userTasks = getTasksFromDatabase(userId);

        result = userTasks.toString();
        return result;
    }

    public static JSONArray getTasksFromDatabase(int id) {

        Connection connection = DatabaseManager.getDatabaseManager().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JSONArray result = new JSONArray();


        String sql = "SELECT * FROM tasks WHERE user_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String taskTitle = resultSet.getString("task_title");
                String taskContent = resultSet.getString("task_content");
                int taskId = resultSet.getInt("task_id");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title", taskTitle);
                    jsonObject.put("content", taskContent);
                    jsonObject.put("task_id", taskId);
                    result.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }

        return result;
    }

    public static int addTaskToDatabase(String token, JSONObject task) {
        int userId = UsersDatabase.getUserIdFromDatabase(token);
        if (userId == -1)
            return -1;

        Connection connection = DatabaseManager.getDatabaseManager().getConnection();
        //PreparedStatement preparedStatement;
        String title = task.optString("title");
        String content = task.optString("content");


        //String sql = "INSERT INTO tasks (user_id,task_title,task_content) VALUES (?,?,?)";
        String sql = "{call to_do_list.insertTaskAndGetTaskId(?,?,?,?)}";
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, userId);
            callableStatement.setString(2, title);
            callableStatement.setString(3, content);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();


            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, callableStatement, connection);
        }

        return -1;

    }

    public static boolean updateTaskInDatabase(String token, JSONObject task) {

        int userId = UsersDatabase.getUserIdFromDatabase(token);

        if (userId == -1)
            return false;

        Connection connection = DatabaseManager.getDatabaseManager().getConnection();
        PreparedStatement preparedStatement = null;


        String sql = "UPDATE to_do_list.tasks SET task_title=?, task_content=? WHERE task_id=?";

        try {
            String title = task.optString("title");
            String content = task.optString("content");
            int taskId = task.getInt("task_id");

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, taskId);


            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(preparedStatement, connection);
        }


        return false;
    }

    public static boolean deleteTaskFromDatabase(String token, int taskId) {
        int userId = UsersDatabase.getUserIdFromDatabase(token);
        if (userId == -1)
            return false;

        Connection connection = DatabaseManager.getDatabaseManager().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM to_do_list.tasks WHERE task_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) //checks if resultSet is empty
                return false;
            if (resultSet.getInt("user_id") != userId)//the token that was sent as a parameter was of another user! this task doesnt belong to this user.
                return false;

            sql = "DELETE FROM tasks WHERE task_id=?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, taskId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }


        return false;
    }
}
