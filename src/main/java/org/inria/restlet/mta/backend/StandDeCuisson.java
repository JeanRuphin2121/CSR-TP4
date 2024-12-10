package org.inria.restlet.mta.backend;

import java.util.Random;

public class StandDeCuisson {
    private boolean platEnCuisson = false; // Indique si un plat est en cours de cuisson
    private final Object lock = new Object(); // Objet de synchronisation

    public void demanderCuisson(ClientThread clientThread) throws InterruptedException {
        synchronized (lock) {
            while (platEnCuisson) {
                lock.wait(); // Attente si un autre plat est en cours de cuisson
            }
            platEnCuisson = true; // Le cuisinier commence la cuisson
            System.out.println("Le cuisinier commence à cuire le plat du client " + clientThread.getId());
        }
        Thread.sleep(1000 + new Random().nextInt(1000)); //Simulation de la cuisson

        synchronized (lock) {
            platEnCuisson = false; // La cuisson est terminée
            System.out.println("Le cuisinier a terminé de cuire le plat du client " + clientThread.getId());
            lock.notifyAll(); // Notifie les clients en attente
        }

    }
}
