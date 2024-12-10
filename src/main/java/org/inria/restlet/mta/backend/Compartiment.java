package org.inria.restlet.mta.backend;

public class Compartiment {


    static final int QUANTITE_INITIAL = 1000; //La quantité est en grammes;
    static final int BORNE_INF = 100; // MInimum avant approvisionner
    private  int current_stock;
    private  int number; //Numéro de Compartiment


    public Compartiment(int i) {
        this.number = i;
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
        System.out.println(portion + "g pris dans le compartiment " + number + ".");
    }

    public synchronized void approvisionner() {
        if (current_stock < BORNE_INF) {
            int manque = QUANTITE_INITIAL - current_stock;  // Ce qu'il manque pour atteindre le stock initial
            this.current_stock += manque;
            System.out.println("Le compartiment " + number + " a été approvisionné.");
            notifyAll();
        }
    }

    public int getStock() {
        return current_stock;
    }

    public int getNumber() {
        return number;
    }

}