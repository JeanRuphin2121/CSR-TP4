public class Compartiment {


    static final int QUANTITE_MAX = 1000; //La quantité est en grammes;
    static final int BORNE_INF = 100;


    public Compartiment(int i) {
    }


    public synchronized void approvisionner() {


        notify();
    }

}
