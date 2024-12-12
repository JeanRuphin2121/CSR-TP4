package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.Restaurant;

public class Employe extends Thread{

    private Compartiment[] compartiments;

    public Employe(Compartiment[] compartiments) {
        this.compartiments = compartiments;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < Restaurant.NB_COMPARTIMENTS; i++) {
                compartiments[i].approvisionner();
            }

            try {
                Thread.sleep(500); // L'employé vérifie périodiquement
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
