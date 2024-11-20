package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.List;

/**
 * Resource for managing a user's tweets.
 */
public class TweetsResource extends ServerResource {

    private InMemoryDatabase db_;

    public TweetsResource() {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }

    @Get("json")
    public Representation getTweets() throws Exception {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        User user = db_.getUser(userId);

        if (user == null) {
            return new JsonRepresentation(new JSONObject().put("error", "User not found"));
        }

        List<Tweet> tweets = user.getTweets();
        JSONArray jsonTweets = new JSONArray();

        for (Tweet tweet : tweets) {
            JSONObject jsonTweet = new JSONObject();
            jsonTweet.put("content", tweet.getContent());
            jsonTweet.put("timestamp", tweet.getTimestamp().toString());
            jsonTweets.put(jsonTweet);
        }

        return new JsonRepresentation(jsonTweets);
    }

    @Post("json")
    public Representation addTweet(JsonRepresentation representation) throws Exception {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        User user = db_.getUser(userId);

        if (user == null) {
            return new JsonRepresentation(new JSONObject().put("error", "User not found"));
        }

        JSONObject json = representation.getJsonObject();
        String content = json.getString("content");

        Tweet tweet = new Tweet(content);
        user.addTweet(tweet);

        JSONObject result = new JSONObject();
        result.put("content", tweet.getContent());
        result.put("timestamp", tweet.getTimestamp().toString());

        return new JsonRepresentation(result);
    }
}
