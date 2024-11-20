package org.inria.restlet.mta.internals;

public class Tweet {

    //tweet ID
    private int id_;

    //Tweet content
    private String text_;

    //Tweet Author
    private User author_;



    public Tweet(String text_, User author_) {
        this.id_ = id_;
        this.text_ = text_;
        this.author_ = author_;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public String getText_() {
        return text_;
    }

    public void setText_(String text_) {
        this.text_ = text_;
    }

    public User getAuthor_() {
        return author_;
    }

    public void setAuthor_(User author_) {
        this.author_ = author_;
    }
}
