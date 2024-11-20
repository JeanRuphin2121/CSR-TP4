package org.inria.restlet.mta.internals;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User {

    private int id_;
    private String name_;
    private int age_;
    private List<Tweet> tweets_;

    public User(String name, int age) {
        this.name_ = name;
        this.age_ = age;
        this.tweets_ = new ArrayList<>();
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public int getAge() {
        return age_;
    }

    public void setAge(int age) {
        this.age_ = age;
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        this.id_ = id;
    }

    public List<Tweet> getTweets() {
        return tweets_;
    }

    public void addTweet(Tweet tweet) {
        this.tweets_.add(tweet);
    }
}
