package com.home.todolist_hackeruproject;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {

    private String title;
    private String content;
    private String shortTitle;
    private String shortContent;
    private int id;

    /**
     * To be used ONLY when task_id is unavailable.
     * @param title Task title.
     * @param content Task Content.
     */
    public Task(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public Task(String title, String content, int id) {
        setTitle(title);
        setContent(content);
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        int maxLength = 9;
        this.title = title;
        if(this.title.length() > maxLength)
            setShortTitle(this.title.substring(0,maxLength-1).concat("..."));
        else
            setShortTitle(this.title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        int maxLength = 25;
        this.content = content;
        if(this.content.length() > maxLength)
            setShortContent(this.content.substring(0,maxLength-1).concat("..."));
        else
            setShortContent(this.content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("title", this.title);
        result.put("content", this.content);
        result.put("short_title",this.shortTitle);
        result.put("short_content",this.shortContent);
        result.put("task_id",this.id);
        return result;
    }
}
