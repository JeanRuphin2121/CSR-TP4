package org.inria.restlet.mta.database;

import java.util.*;

import org.inria.restlet.mta.backend.*;

/**
 *
 * In-memory database
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class Restaurant {

    private int idClient=0;
    public static int NOMBRE_PLACES = 25;
    static final int NBRE_CLIENT = 40;
    public static final int NB_COMPARTIMENTS = 4;

    private Compartiment[] compartiments = new Compartiment[NB_COMPARTIMENTS];
    //private Client[] clients = new Client[NBRE_CLIENT];
    public static StandDeCuisson standCuisson;
    //private final List<Client> clients = new ArrayList<>();
    private final Map<Integer,Client> clients = new HashMap<>();

    public Restaurant() {
        String[] nomsCompartiments = {"poisson cru", "viande crue", "légumes crus", "nouilles froides"};

        /* Instanciation des compartiments */
        for (int i = 0; i < NB_COMPARTIMENTS; i++) {
            compartiments[i] = new Compartiment(nomsCompartiments[i]);
        }

        /* Instanciation de l'employé */
        Employe employe = new Employe(compartiments);

        /* Instanciation du stand de cuisson */
        standCuisson = new StandDeCuisson();

        /* Instanciation et démarrage du cuisinier */
        Cuisinier cuisinier = new Cuisinier(standCuisson);
        cuisinier.setDaemon(true);  // Définir le thread cuisinier comme démon avant de le démarrer
        cuisinier.start();

        /* Démarrage de l'employé */
        employe.setDaemon(true);  // Définir l'employé comme démon avant de démarrer
        employe.start();
    }

    // Gérer les clients
    public Client createClient() {
        if(idClient>clients.size()){
            throw new IllegalStateException("Limite de clients atteinte");
        }
        Client newClient = new Client(idClient, compartiments, this);
        clients.put(idClient, newClient);
        newClient.start();
        return newClient;
    }

    public Collection<Client> getClients() {
        return clients.values();
    }

    public Client getClient(int id) {
        return clients.get(id);
    }

    public synchronized void entrerDansRestaurant(int clientId) {
        while (NOMBRE_PLACES <= 0){
            System.out.println("Client"+ clientId + "attent pour entrer.");
            try {
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        NOMBRE_PLACES--;
        System.out.println("Client"+ clientId + "est entré dans le restaurant.");
    }

    public synchronized void sortir(int clientId) {
        NOMBRE_PLACES++;
        System.out.println("Client"+ clientId + "sort.");
        notify();
    }

    public Compartiment[] getCompartiments() {
        return compartiments;
    }
}
