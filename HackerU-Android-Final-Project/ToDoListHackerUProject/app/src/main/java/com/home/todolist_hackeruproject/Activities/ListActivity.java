package com.home.todolist_hackeruproject.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.home.todolist_hackeruproject.Adapters.ListItemClickedListener;
import com.home.todolist_hackeruproject.AsyncTasks.DeleteDataTask;
import com.home.todolist_hackeruproject.AsyncTasks.FinishedGettingTasksListener;
import com.home.todolist_hackeruproject.AsyncTasks.GetDataTask;
import com.home.todolist_hackeruproject.AsyncTasks.SendDataTask;
import com.home.todolist_hackeruproject.AsyncTasks.SuccessfulUpdateDataListener;
import com.home.todolist_hackeruproject.AsyncTasks.UpdateDataTask;
import com.home.todolist_hackeruproject.Consts;
import com.home.todolist_hackeruproject.Credentials.CredentialsManager;
import com.home.todolist_hackeruproject.R;
import com.home.todolist_hackeruproject.Task;
import com.home.todolist_hackeruproject.Adapters.TaskAdapter;
import com.home.todolist_hackeruproject.TaskManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.regex.Pattern;

public class ListActivity extends Activity implements ListItemClickedListener, FinishedGettingTasksListener,SuccessfulUpdateDataListener {

    RecyclerView taskList;
    String userToken;
    EditText newTaskTitle, newTaskContent;
    TaskAdapter taskAdapter;
    View alertTaskView;
    Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        taskList = findViewById(R.id.listTasks);
        userToken = getIntent().getStringExtra("token");
        taskList.setLayoutManager(new GridLayoutManager(this, 2));
        new GetDataTask(this).execute(userToken, "get");
    }

    //Inflate the menu that has the "logout" option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    //Handles the "logout" item being clicked. If it was clicked, the client will logout.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                CredentialsManager.getCredentialsManager().logout(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Consts.defaultBackPressed(this);
    }

    public void btnAdd(View view) {
        //inflating custom view for the alert dialog.
        alertTaskView = getLayoutInflater().inflate(R.layout.alert_task_input, null, false);

        //finding the ok button and the cancel button.
        btnOk = alertTaskView.findViewById(R.id.btnOkDialog);
        btnCancel = alertTaskView.findViewById(R.id.btnCancelDialog);

        //creating a new alert dialog and set its view to the custom view that was inflated previously.
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(alertTaskView).create();

        //finding the title and content EditTexts.
        newTaskTitle = alertTaskView.findViewById(R.id.txtNewTitle);
        newTaskContent = alertTaskView.findViewById(R.id.txtNewContent);

        //setting onClickListener on the ok button.
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = TaskManager.extractTaskFromDialog(newTaskTitle, newTaskContent);
                if (newTask != null) {
                    try {
                        new SendDataTask(newTask).execute(userToken, "add", newTask.toJSONObject().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //if the taskAdapter never got assigned (if never got tasks from the server because it is offline)
                    if (taskAdapter != null) {
                        taskAdapter.addTaskToList(newTask);
                    }
                    alertDialog.dismiss();
                } else {
                    TaskManager.showInvalidTaskAlert(ListActivity.this);
                }
            }
        });

        //setting onClickListener on the cancel button.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        //showing the finished alert dialog.
        alertDialog.show();

    }

    @Override
    public void onItemClick(final Task currentTask, final int position) {

        //inflating a custom view for the edit task alert dialog.
        View taskEditorDialog = getLayoutInflater().inflate(R.layout.alert_task_editor, null, false);

        //finding the title and content EditTexts on the custom view.
        final EditText currentTitle = taskEditorDialog.findViewById(R.id.edittextCurrentTitle);
        final EditText currentContent = taskEditorDialog.findViewById(R.id.edittextCurrentContent);

        //setting the title and content EditTexts text to the current Task text.
        currentTitle.setText(currentTask.getTitle());
        currentContent.setText(currentTask.getContent());

        //finding the edit and delete buttons on the custom view.
        Button editButton = taskEditorDialog.findViewById(R.id.btnEditDoneDialog);
        Button deleteButton = taskEditorDialog.findViewById(R.id.btnDeleteTaskDialog);

        //creating a new alert dialog.
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(taskEditorDialog).create();

        //setting onClickListener for the edit button.
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = currentTitle.getText().toString();
                String newContent = currentContent.getText().toString();

                if (!TaskManager.isValidTaskParams(newTitle, newContent)) {
                    TaskManager.showInvalidTaskAlert(ListActivity.this);
                    return;
                }

                //sets new title and content values in the currentTask object, that is present in the list of the taskAdapter.
//                currentTask.setTitle(newTitle);
//                currentTask.setContent(newContent);

                //send the new task data to the server.
                try {
                    new UpdateDataTask(ListActivity.this,position,currentTask,newTitle,newContent).execute(userToken, "update", currentTask.toJSONObject().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
                //TODO:notifyItemChanged should be called when you get the response "success" from the server, and not here. (move this to postExecute of UpdateAsyncTask).

            }
        });

        //setting onClickListener for the delete button.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteDataTask(taskAdapter).execute(userToken, String.valueOf(currentTask.getId()));
                alertDialog.dismiss();
            }
        });

        //show the complete alert dialog.
        alertDialog.show();
    }

    //Called when the server sent the tasks of the client.
    @Override
    public void onFinishedGettingTasks(JSONArray data) {
        taskAdapter = new TaskAdapter(data, this);
        taskList.setAdapter(taskAdapter);
    }

    @Override
    public void onSuccessfulTaskUpdate(int taskPosition, Task task, String newTitle, String newContent) {
        taskAdapter.notifyItemChanged(taskPosition);
        task.setTitle(newTitle);
        task.setContent(newContent);
    }

    @Override
    public void onFailedTaskUpdate() {
        new AlertDialog.Builder(this).setTitle("Could not update task.").setMessage("Please try again.").show();
    }
}