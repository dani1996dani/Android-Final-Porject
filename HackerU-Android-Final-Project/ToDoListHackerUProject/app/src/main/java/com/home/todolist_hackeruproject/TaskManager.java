package com.home.todolist_hackeruproject;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import com.home.todolist_hackeruproject.Activities.ListActivity;

import java.util.regex.Pattern;

public class TaskManager {

    /**
     * Shows a new alert dialog.
     * @param context The context that will show the new alert dialog.
     */
    public static void showInvalidTaskAlert(Context context){
        new AlertDialog.Builder(context).setTitle("Invalid Task Entered").setMessage("Please Try Again.\n\n" +
                "Only Alaphabet and Numeric Characters are Allowed.\n\nTitle can not be empty, and can not exceed 50 characters." +
                "\n\nContent can not be empty, and can not exceed 255 characters.").show();
    }

    /**
     * Extracts the title and content that are present in the Task alert dialog.
     * @return <P>1. A new Task object, if the title and content are acceptable.</P>
     * <P>2. null - if the title and content are <B>NOT</B> acceptable.</P>
     */
    public static Task extractTaskFromDialog(EditText newTaskTitle,EditText newTaskContent) {
        String title = newTaskTitle.getText().toString();
        String content = newTaskContent.getText().toString();

        if (!isValidTaskParams(title, content))
            return null;

        return new Task(title, content);
    }

    /**
     *
     * @param title Title to be checked.
     * @param content Content to be checked.
     * @return True if the parameters are valid, false otherwise.
     */
    public static boolean isValidTaskParams(String title, String content) {
        if (!isSentence(title) || !isSentence(content) || isTitleToLong(title) || isContentToLong(content))
            return false;
        return true;
    }

    public static boolean isTitleToLong(String title) {
        return title.length() > 50;
    }

    public static boolean isContentToLong(String content) {
        return content.length() > 255;
    }

    public static boolean isSentence(String testedString) {
        boolean result = false;
        String sentenceRegex = "^[A-Za-z\\d.,;'\\\"\\s!@#$%^&*()+?_]+([.?!])*$";
        Pattern pattern = Pattern.compile(sentenceRegex);
        result = pattern.matcher(testedString).matches();

        return result;
    }
}
