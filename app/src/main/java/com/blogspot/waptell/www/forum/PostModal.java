package com.blogspot.waptell.www.forum;

/**
 * Created by chami on 7/9/18.
 */

public class PostModal {

    public String post_title;
    public String post_description;

    public PostModal(String post_title, String post_description) {
        this.post_title = post_title;
        this.post_description = post_description;
    }

    public PostModal(){

    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }
}
