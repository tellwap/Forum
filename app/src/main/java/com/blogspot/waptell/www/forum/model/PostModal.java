package com.blogspot.waptell.www.forum.model;

/**
 * Created b y chami on 7/9/18.
 */

public class PostModal {

    private String post_title;
    private String post_description;
    private String time;
    private String image;


    public PostModal(String post_title, String post_description, String time, String image) {
        this.post_title = post_title;
        this.post_description = post_description;
        this.time=time;
        this.image=image;


    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
