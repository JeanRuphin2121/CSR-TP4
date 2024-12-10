package org.inria.restlet.mta.main;

import org.inria.restlet.mta.application.BuffetApplication;
import org.inria.restlet.mta.database.Restaurant;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

/**
 * Main RESTlet minimal example
 *
 * @author msimonin
 */
public final class Main
{

    /** Hide constructor. */
    private Main()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method.
     *
     * @param args  The arguments of the commande line
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);

        // Create an application
        Application application = new BuffetApplication(context);

        // Add the db into component's context
        Restaurant db = new Restaurant();
        context.getAttributes().put("database", db);
        component.getDefaultHost().attach(application);

        // Start the component
        component.start();
    }

}
