package org.inria.restlet.mta.database;

import java.util.*;

import org.inria.restlet.mta.backend.ClientThread;

/**
 *
 * In-memory database
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class Restaurant {


    private String name;
    private String buffetState = "open"; // Buffet ouvert par défaut
    private List<String> dishes = new ArrayList<>(); // Liste des plats disponibles
    private Map<Integer, ClientThread> clients = new HashMap<>(); // Liste des clients
    private int nextClientId = 1; // ID auto-incrémenté pour les clients

    public Restaurant(String name) {
        this.name = name;
    }

    // Gérer le buffet
    public String getBuffetState() {
        return buffetState;
    }

    public void setBuffetState(String buffetState) {
        this.buffetState = buffetState;
    }

    public List<String> getDishes() {
        return dishes;
    }

    public void addDish(String dish) {
        dishes.add(dish);
    }

    public void removeDish(String dish) {
        dishes.remove(dish);
    }

    // Gérer les clients
    public ClientThread createClient(String clientName) {
        ClientThread newClientThread = new ClientThread(clientName);
        newClientThread.setId(nextClientId++);
        clients.put(newClientThread.getId(), newClientThread);
        return newClientThread;
    }

    public Collection<ClientThread> getClients() {
        return clients.values();
    }

    public ClientThread getClient(int id) {
        return clients.get(id);
    }

}
