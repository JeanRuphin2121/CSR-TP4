package org.inria.restlet.mta.internals;

import java.time.LocalDateTime;

/**
 * Represents a Tweet.
 */
public class Tweet {

    /** Content of the tweet */
    private String content_;

    /** Timestamp of the tweet */
    private LocalDateTime timestamp_;

    public Tweet(String content) {
        this.content_ = content;
        this.timestamp_ = LocalDateTime.now();
    }

    public String getContent() {
        return content_;
    }

    public void setContent(String content) {
        this.content_ = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp_;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp_ = timestamp;
    }
}
