package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.Restaurant;

import java.util.Random;

public class Client extends Thread {

    private int id;
    private final Compartiment[] compartiments;
    private final Random random = new Random();
    private final Restaurant restaurant;
    private EtatClient etat;

    public Client(int id, Compartiment[] compartiments, Restaurant restaurant) {
        this.id = id;
        /*
         *Je met l'état du client à l'initialisation à WAITING_TO_ENTER
         * car parmi les différents états donnés cet etat est le plus coherent à utiliser
         */

        this.etat = EtatClient.WAITING_TO_ENTER;
        this.compartiments = compartiments;
        this.restaurant = restaurant;
    }

    public void run() {
        restaurant.entrerDansRestaurant(id);

        /* Petite simulation du fait qu'on se rend au buffet */
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Passage aux compartiments(L'étape du buffet)
        etat = EtatClient.AT_THE_BUFFET;
        for (int i = 0; i < compartiments.length; i++) {
            int portion = random.nextInt(101);
            try {
                synchronized (compartiments){
                    compartiments[i].prendreNourriture(portion);
                }
                Thread.sleep(200); // Simulation du temps de service
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        /* Petite simulation du fait qu'on se rend au stand de cuisson */
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Passage au stand de cuisson pour cuire le plat
        etat = EtatClient.WAITING_FOR_THE_COOK;
            Restaurant.standCuisson.demanderCuisson(id);

        // Manger
        etat = EtatClient.EATING;
        try {
            System.out.println("Client " + id + " mange.");
            Thread.sleep(1000 + random.nextInt(1001));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        // Sortir
        restaurant.sortir(id);
    }

    /*public int getClientId(){
        return id;
    }*/

    public EtatClient getEtat() {
        return etat;
    }
}
