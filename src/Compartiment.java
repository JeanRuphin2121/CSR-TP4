public class Compartiment {


    static final int QUANTITE_INITIAL = 1000; //La quantité est en grammes;
    static final int BORNE_INF = 100;
    int current_stock;
    int number;


    public Compartiment(int i) {
        this.number = i;
       this.current_stock = QUANTITE_INITIAL;
    }


    public synchronized void approvisionner() {
        if (current_stock < BORNE_INF) {
            int manque = QUANTITE_INITIAL - current_stock;  // Ce qu'il manque pour atteindre le stock initial
            this.current_stock += manque;
        }
        notify();
    }

}
