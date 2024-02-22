package org.example;

public class Page {
    private String pageid;
    private String title;
    public String getPageId() { return pageid; }
    public String getTitle() { return title; }

    public void setPageID(String pageid){
        this.pageid = pageid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Page(){
        pageid = "";
        title = "";
    }
}

