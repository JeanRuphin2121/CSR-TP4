package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.EtatClient;
import org.inria.restlet.mta.database.Restaurant;
import org.inria.restlet.mta.backend.Client;
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
 * @author afassassi
 * @author aruphin
 *
 */
public class ClientResource extends ServerResource
{

    /** database. */
    private Restaurant restaurant;

    /** Utilisateur géré par cette resource. */
    private Client client;

    /**
     * Constructor.
     * Call for every single user request.
     */
    @Override
    public void doInit() {
        restaurant = (Restaurant) getApplication().getContext().getAttributes().get("database");
    }

    @Get("json")
    public Representation getClient() throws Exception
    {
        String clientIdString = (String) getRequest().getAttributes().get("clientId");
        int clientId = Integer.parseInt(clientIdString);
        client = restaurant.getClient(clientId);

        if (client == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Client not found");
        }

        JSONObject clientObject = new JSONObject();
        clientObject.put("Id", client.getId());
        clientObject.put("Etat", client.getEtat());

        return new JsonRepresentation(clientObject);
    }


}
