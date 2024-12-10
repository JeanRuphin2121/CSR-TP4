package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.ClientThread;
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
 * @author ctedeschi
 * @author msimonin
 *
 */
public class ClientsResource extends ServerResource
{

    /** Database. */
    private Restaurant db_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ClientsResource()
    {
        super();
        db_ = (Restaurant) getApplication().getContext().getAttributes()
            .get("database");
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
        Collection<ClientThread> clientThreads = db_.getClients();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (ClientThread clientThread : clientThreads)
        {
            JSONObject current = new JSONObject();
            current.put("id", clientThread.getId());
            current.put("name", clientThread.getName());
            current.put("state", clientThread.getState());
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createClient(JsonRepresentation representation) throws Exception
    {
        JSONObject object = representation.getJsonObject();
        String name = object.getString("name");
        //int age = object.getInt("age");

        // Save the user
        ClientThread clientThread = db_.createClient(name);

        // generate result
        JSONObject resultObject = new JSONObject();

        resultObject.put("id", clientThread.getId());
        resultObject.put("name", clientThread.getName());
        resultObject.put("state", clientThread.getState());

        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
