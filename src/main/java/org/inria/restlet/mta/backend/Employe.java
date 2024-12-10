package org.inria.restlet.mta.backend;

public class Employe extends Thread{

    private final Restaurant restaurant;

    public Employe(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (Compartiment compartiment : restaurant.getCompartiments()) {
                    synchronized (compartiment) {
                        compartiment.approvisionner();
                    }
                }
                Thread.sleep(500); // L'employé vérifie périodiquement
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
