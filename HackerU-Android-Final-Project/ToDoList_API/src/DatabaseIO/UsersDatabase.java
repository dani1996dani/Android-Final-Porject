package DatabaseIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UsersDatabase {

    public static boolean addUserToDatabase(String email, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = DatabaseManager.getDatabaseManager().getConnection();

            String sql = "INSERT INTO users (email,password,user_token) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            String random_token = UUID.randomUUID().toString();
            preparedStatement.setString(3, random_token);

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(preparedStatement, connection);
        }

        return rowsAffected > 0 ? true : false;
    }

    public static boolean isUserValid(String email, String password) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.getDatabaseManager().getConnection();

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                result = true;
            else
                result = false;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }

        return result;
    }

    public static boolean emailAvailableInDatabase(String email) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.getDatabaseManager().getConnection();

            String sql = "SELECT email FROM users WHERE email=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return true; // the email is available
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }


        return false;
    }

    public static String getUserToken(String email, String password) {
        String result = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.getDatabaseManager().getConnection();

            String sql = "SELECT user_token FROM users WHERE email=? AND password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                result = resultSet.getString("user_token");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }

        return result;
    }

//    public static int getUserIdFromDatabase(String email, String password) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        int result = -1;
//
//        try {
//            connection = DatabaseManager.getDatabaseManager().getConnection();
//
//            String sql = "SELECT user_id FROM users WHERE email=? AND password=?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, email);
//            preparedStatement.setString(2, password);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next())
//                result = resultSet.getInt("user_id");
//            return result;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//
//    }

    /**
     * @param token The token which is searched for in the database. Used to find the corresponding user ID.
     * @return Returns an int that has 2 meanings
     * <P>1). The ID of an existing user</P>
     * <P>2). -1 if the token does not match an existing user</P>
     */
    public static int getUserIdFromDatabase(String token) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = -1;

        try {
            connection = DatabaseManager.getDatabaseManager().getConnection();

            String sql = "SELECT user_id FROM users WHERE user_token=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                result = resultSet.getInt("user_id");
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getDatabaseManager().closeConnection(resultSet, preparedStatement, connection);
        }

        return result;

    }
}
