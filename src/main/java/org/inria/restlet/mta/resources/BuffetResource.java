package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.Restaurant;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * Resource for managing a user's tweets.
 */
public class BuffetResource extends ServerResource {

    private Restaurant db_;

    public BuffetResource() {
        db_ = (Restaurant) getApplication().getContext().getAttributes()
                .get("database");
    }

    @Get("json")
    public Representation getBuffetState() throws Exception {
        JSONObject json = new JSONObject();
        json.put("state", db_.getBuffetState());
        return new JsonRepresentation(json);
    }
    @Put("json")
    public Representation updateBuffetState(JsonRepresentation representation) throws Exception {
        JSONObject json = representation.getJsonObject();
        String newState = json.getString("state");

        db_.setBuffetState(newState);

        JSONObject result = new JSONObject();
        result.put("state", db_.getBuffetState());
        return new JsonRepresentation(result);
    }
}
