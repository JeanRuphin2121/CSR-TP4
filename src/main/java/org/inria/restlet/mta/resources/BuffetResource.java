package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Compartiment;
import org.inria.restlet.mta.database.Restaurant;
import org.json.JSONArray;
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

    private Restaurant restaurant;

   @Override
    public void doInit() {
        restaurant = (Restaurant) getApplication().getContext().getAttributes().get("database");
    }

    @Get("json")
    public Representation getBuffetState() throws Exception {
        JSONArray buffetCompartiment = new JSONArray();

        for(Compartiment compartiment : restaurant.getCompartiments()){
            JSONObject jsonbuffetCompartiment = new JSONObject();
            jsonbuffetCompartiment.put("Nom", compartiment.getName());
            jsonbuffetCompartiment.put("Reste", compartiment.getCurrent_stock());
            buffetCompartiment.put(jsonbuffetCompartiment);
        }
        return new JsonRepresentation(buffetCompartiment);
    }
}
