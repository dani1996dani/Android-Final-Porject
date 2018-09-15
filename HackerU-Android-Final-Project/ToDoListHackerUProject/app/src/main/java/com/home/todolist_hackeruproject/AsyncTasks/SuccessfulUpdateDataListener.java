package com.home.todolist_hackeruproject.AsyncTasks;

import com.home.todolist_hackeruproject.Task;

public interface SuccessfulUpdateDataListener {
    void onSuccessfulTaskUpdate(int taskPosition, Task task, String newTitle, String newContent);
    void onFailedTaskUpdate();
}
