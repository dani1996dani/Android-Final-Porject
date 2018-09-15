package com.home.todolist_hackeruproject.Adapters;

import com.home.todolist_hackeruproject.Task;

public interface ListItemClickedListener {
    void onItemClick(Task currentTask, int position);
}