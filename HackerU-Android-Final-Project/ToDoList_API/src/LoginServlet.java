import DatabaseIO.UsersDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String action = request.getParameter("action");

        String result = "";

        if (email == null || password == null || action == null || email.isEmpty() || password.isEmpty()
                || action.isEmpty())
            return;

        switch (action) {
            case "register":
                result = register(email, password);
                break;
            case "login":
                result = login(email, password);
                break;

            default:
                break;
        }

        String token = "";
        if (result == "success") {
            token = UsersDatabase.getUserToken(email, password);
        }

        JSONObject object = new JSONObject();
        try {
            object.put("response", result);
            object.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().append(object.toString());
    }


    public String register(String email, String password) {
        String result;
        boolean emailAvailable = UsersDatabase.emailAvailableInDatabase(email);

        if (emailAvailable) {
            result = UsersDatabase.addUserToDatabase(email, password) ? "success" : "There was a problem. Try Again";
        } else {
            result = "Email Unavailable";
        }
        return result;
    }

    public String login(String email, String password) {
        boolean isUserValid = UsersDatabase.isUserValid(email, password);
        String result = isUserValid ? "success" : "Wrong Credentials";
        return result;
    }
}
