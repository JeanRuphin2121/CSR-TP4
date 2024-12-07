public class Compartiment {


    static final int QUANTITE_INITIAL = 1000; //La quantité est en grammes;
    static final int BORNE_INF = 100;
    int current_stock;
    int number; //Numéro de Compartiment


    public Compartiment(int i) {
        this.number = i;
       this.current_stock = QUANTITE_INITIAL;
    }

    public synchronized void prendreNourriture(int portion) throws InterruptedException {
        while (current_stock < portion) {
            wait();
        }
        current_stock -= portion;
        System.out.println(portion + "g pris dans le compartiment " + number + ".");
    }

    public synchronized void approvisionner() {
        if (current_stock < BORNE_INF) {
            int manque = QUANTITE_INITIAL - current_stock;  // Ce qu'il manque pour atteindre le stock initial
            this.current_stock += manque;
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
