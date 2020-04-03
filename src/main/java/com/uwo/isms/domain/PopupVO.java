package com.uwo.isms.domain;

public class PopupVO {
    private int popup_id;
    private int brd_key;
    private String status;
    private String start_date;
    private String finish_date;
    private int width;
    private int height;
    private int top;
    private int left;
    private String title;
    private String content;

    public int getPopup_id() { return popup_id; }
    public void setPopup_id(int popup_id) { this.popup_id = popup_id; }

    public int getBrd_key() {
        return brd_key;
    }
    public void setBrd_key(int brd_key) {
        this.brd_key = brd_key;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() { return start_date; }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }
    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getTop() {
        return top;
    }
    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
