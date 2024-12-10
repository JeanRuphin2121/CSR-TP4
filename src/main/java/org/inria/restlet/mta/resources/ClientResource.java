package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.Restaurant;
import org.inria.restlet.mta.backend.ClientThread;
import org.restlet.data.Status;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 *
 * Resource exposing a user.
 *
 * @author msimonin
 * @author ctedeschi
 *
 */
public class ClientResource extends ServerResource
{

    /** database. */
    private Restaurant db_;

    /** Utilisateur géré par cette resource. */
    private ClientThread client_Thread_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ClientResource()
    {
        db_ = (Restaurant) getApplication().getContext().getAttributes()
                .get("database");
    }


    @Get("json")
    public Representation getClient() throws Exception
    {
        String clientIdString = (String) getRequest().getAttributes().get("clientId");
        int clientId = Integer.valueOf(clientIdString);
        client_Thread_ = db_.getClient(clientId);

        if (client_Thread_ == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Client not found");
        }

        JSONObject userObject = new JSONObject();
        userObject.put("id", client_Thread_.getId());
        userObject.put("name", client_Thread_.getName());
        userObject.put("state", client_Thread_.getState());

        return new JsonRepresentation(userObject);
    }

    @Put("json")
    public Representation updateClient(JsonRepresentation representation) throws Exception {
        String clientIdString = (String) getRequest().getAttributes().get("clientId");
        int clientId = Integer.valueOf(clientIdString);
        client_Thread_ = db_.getClient(clientId);

        JSONObject json = representation.getJsonObject();
        String newState = json.getString("state");
        client_Thread_.setState(newState);

        JSONObject result = new JSONObject();
        result.put("id", client_Thread_.getId());
        result.put("name", client_Thread_.getName());
        result.put("state", client_Thread_.getState());

        return new JsonRepresentation(result);
    }

}
