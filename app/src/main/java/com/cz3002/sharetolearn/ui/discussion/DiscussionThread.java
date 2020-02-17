package com.cz3002.sharetolearn.ui.discussion;

import java.util.ArrayList;
import java.util.List;

public class DiscussionThread {
    private String topic;
    private List<String> comments;
    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public DiscussionThread(String topic, List<String> comments, int likes) {
        this.topic = topic;
        this.comments = comments;
        this.likes = likes;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment){
        this.comments.add(comment);
    }

    public void editComment(int index, String comment){
        this.comments.set(index, comment);
    }

}
