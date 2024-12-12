package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Client;
import org.inria.restlet.mta.database.Restaurant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the users
 *
 * @author afassassi
 * @author aruphin
 *
 */
public class ClientsResource extends ServerResource
{

    /** Database. */
    private Restaurant restaurant;

    /**
     * Constructor.
     * Call for every single user request.
     */

    @Override
    public void doInit()
    {
        restaurant = (Restaurant) getApplication().getContext().getAttributes().get("database");
    }

    /**
     *
     * Returns the list of all the users
     *
     * @return  JSON representation of the users
     * @throws JSONException
     */
    @Get("json")
    public Representation getClients() throws JSONException
    {
        Collection<Client> clients = restaurant.getClients();
        JSONArray jsonArray = new JSONArray();

        for (Client client : clients)
        {
            JSONObject current = new JSONObject();
            current.put("id", client.getId());
            current.put("state", client.getState());
            jsonArray.put(current);

        }
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createClient(JsonRepresentation representation) throws Exception
    {
        // Creer nouveau user
        Client client = restaurant.createClient();

        // generer resultat
        JSONObject resultObject = new JSONObject();

        resultObject.put("id", client.getId());
        resultObject.put("state", client.getEtat());

        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
