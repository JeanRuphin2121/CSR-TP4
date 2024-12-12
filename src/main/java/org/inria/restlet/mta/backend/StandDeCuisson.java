package org.inria.restlet.mta.backend;

import java.util.Random;

public class StandDeCuisson {
    private boolean libre;

    public StandDeCuisson() {
        this.libre = true;
    }

    /*
     * Le client demande à cuire son plat.
     * Si le cuisinier est libre, il commence la cuisson, sinon le client attend.
     */
    public synchronized void demanderCuisson(int clientId)  {
        while (!libre) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        libre = false;
        System.out.println("Client " + clientId + " utilise le stand de cuisson.");

        /* Réveille le cuisinier pour commencer la cuisson
         * Mais comme il est possible qu'à ce moment là un client soit aussi bloqué
         * il serait judicieux de faire un notifyall pour etre sue de reveiller le
         * cuisinier
         */
        notifyAll();
    }

    /*
     * Le cuisinier cuit le plat du client.
     * Si aucun plat n'est à cuire, il attend qu'un client fasse une demande.
     */
    public synchronized void cuirePlat() {
        while (libre) {
            try {
                wait();  // Attendre qu'un client vienne demander la cuisson.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Le cuisinier commence à cuire un plat.");
        try {
            Thread.sleep(500);  // Simulation de la cuisson
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("La cuisson est terminée !");
        libre = true;

        /*
         * Réveille un client en attente pour indiquer que le stand est libre.On n'a pas besoin de reveiller tous
         * les clients car le cuisinier ne peut cuire qu'un plat à la fois
         */

        notify();
    }
}
