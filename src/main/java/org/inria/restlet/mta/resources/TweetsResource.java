package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;

public class TweetsResource extends ServerResource {
    /** database. */
    private InMemoryDatabase db_;

    /** Utilisateur géré par cette resource. */
    private User user_;

    public TweetsResource()
    {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }
    @Get("json")
    public Representation userTweets(JsonRepresentation representation)
            throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("name",user_.getName());
        resultObject.put("tweet_content",user_.getTweets());
//        resultObject.put("url", getReference() + "/" + user_.getId()+"/"+"tweets");
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
