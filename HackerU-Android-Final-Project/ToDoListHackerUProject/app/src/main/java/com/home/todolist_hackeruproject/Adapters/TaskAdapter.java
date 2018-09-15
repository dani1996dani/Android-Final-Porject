package com.home.todolist_hackeruproject.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.todolist_hackeruproject.R;
import com.home.todolist_hackeruproject.Task;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> implements TaskRemovedListener {

    JSONArray jsonArray;
    List<Task> tasks = new ArrayList<>();
    private ListItemClickedListener listItemClickedListener;
    private int selectedTaskPosition;

    public TaskAdapter(JSONArray jsonArray, ListItemClickedListener listItemClickedListener) {
        this.jsonArray = jsonArray;
        this.listItemClickedListener = listItemClickedListener;
        String title, content;

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    title = jsonArray.getJSONObject(i).getString("title");
                    content = jsonArray.getJSONObject(i).getString("content");
                    int taskId = jsonArray.getJSONObject(i).getInt("task_id");
                    Task task = new Task(title, content, taskId);
                    tasks.add(task);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addTaskToList(Task newTask) {
        this.tasks.add(newTask);
        notifyItemChanged(tasks.size() - 1);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        final Task currTask = tasks.get(i);
        taskViewHolder.title.setText(currTask.getTitle());
        taskViewHolder.content.setText(currTask.getShortContent());

        final int j = i;
        taskViewHolder.taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClickedListener.onItemClick(currTask, j);
                selectedTaskPosition = j;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onRemove() {
        tasks.remove(selectedTaskPosition);
        notifyItemRemoved(selectedTaskPosition);
        notifyItemRangeChanged(selectedTaskPosition, getItemCount());
        Log.w("dani", "onRemove: postion" + selectedTaskPosition + "size" + getItemCount());
    }

}

class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView title, content;
    LinearLayout taskLayout;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskLayout = (LinearLayout) itemView;
        title = itemView.findViewById(R.id.txtTitle);
        content = itemView.findViewById(R.id.txtContent);
    }
}




