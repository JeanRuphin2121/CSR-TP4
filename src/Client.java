import java.util.Random;

public class Client extends Thread {

    private int id;
    Compartiment[] compartiments;
    private final Random random = new Random();
    private EtatClient etat;

    public Client(int id, Compartiment[] compartiments) {
        this.id = id;
        /*
        *Je met l'état du client à l'initialisation à WAITING_TO_ENTER
        * car parmi les différents états donnés cet etat est le plus coherent à utiliser
        */

        this.etat = EtatClient.WAITING_TO_ENTER;
        this.compartiments = compartiments;
    }

    public void run() {
        entrerDansRestaurant();

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
                compartiments[i].prendreNourriture(portion);
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
        sortir();
    }



    /*
     * On met un verrou sur la classe Restaurant elle représente une ressource commune
     * partagée par tous les clients.
     * Cela permet une synchronisation globale entre les threads Client qui manipulent NOMBRE_PLACES.
     * */

    private void entrerDansRestaurant() {
        synchronized (Restaurant.class) {
            while (Restaurant.NOMBRE_PLACES <= 0) {
                System.out.println("Client " + id + " attend pour entrer.");
                etat = EtatClient.WAITING_TO_ENTER;
                try {
                    Restaurant.class.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            Restaurant.NOMBRE_PLACES--;
            System.out.println("Client " + id + " est entré dans le restaurant.");
        }
    }

    private void sortir() {
        synchronized (Restaurant.class) {
            Restaurant.NOMBRE_PLACES++;
            System.out.println("Client " + id + " sort du restaurant.");

            /* Réveille un client en attente à l'entré du Restaurant
             *  En effet sachant qu'il ne peut y avoir qu'un seul client
             *  à la fois qui entrera si une place se libère,faire un notifyAll va réveiller les autres
             *  clients inutilement. Donc un notify suffit
             */

            Restaurant.class.notify();
        }
        etat = EtatClient.OUT;
    }
}
