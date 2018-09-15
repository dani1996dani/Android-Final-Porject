package com.home.todolist_hackeruproject.Credentials;

import org.json.JSONObject;

public interface SuccessfulLoginListener {
    void onLoginAttempt(JSONObject resultFromServer);
}
