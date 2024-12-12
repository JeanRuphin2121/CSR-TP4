package org.inria.restlet.mta.backend;

public class Compartiment {


    static final int QUANTITE_INITIAL = 1000; //La quantité est en grammes;
    static final int BORNE_INF = 100; // MInimum avant approvisionner
    private  int current_stock;
    private  String name; //nom du Compartiment


    public Compartiment(String name) {
        this.name = name;
        this.current_stock = QUANTITE_INITIAL;
    }

    public synchronized void prendreNourriture(int portion) {
        while (current_stock < portion) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        current_stock -= portion;
        System.out.println(portion + "g pris dans le compartiment " + name + ".");
    }

    public synchronized void approvisionner() {
        if (current_stock < BORNE_INF) {
            int manque = QUANTITE_INITIAL - current_stock;  // Ce qu'il manque pour atteindre le stock initial
            this.current_stock += manque;
            System.out.println("Le compartiment " + name + " a été approvisionné.");
            notifyAll();
        }
    }

    public String getName() {
        return name;
    }

    public int getCurrent_stock() {
        return current_stock;
    }
}
