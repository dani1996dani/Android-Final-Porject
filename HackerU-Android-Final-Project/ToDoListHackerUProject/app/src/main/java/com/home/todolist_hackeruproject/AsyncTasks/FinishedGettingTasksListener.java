package com.home.todolist_hackeruproject.AsyncTasks;

import org.json.JSONArray;

public interface FinishedGettingTasksListener {
    void onFinishedGettingTasks(JSONArray data);
}
