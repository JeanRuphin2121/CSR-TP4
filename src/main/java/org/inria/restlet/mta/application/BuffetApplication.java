package org.inria.restlet.mta.application;

import org.inria.restlet.mta.resources.BuffetResource;
import org.inria.restlet.mta.resources.ClientResource;
import org.inria.restlet.mta.resources.ClientsResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * Application.
 *
 * @author msimonin
 *
 */
public class BuffetApplication extends Application
{

    public BuffetApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/users", ClientsResource.class);
        router.attach("/users/{userId}", ClientResource.class);
        router.attach("/users/{userId}/tweets", BuffetResource.class);
        return router;
    }

}
