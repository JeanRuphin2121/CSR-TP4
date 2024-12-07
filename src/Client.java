import java.util.Random;

public class Client extends Thread {

    private int id;
    private Restaurant restaurant;
    private Random random = new Random();
    private EtatClient etat;

    public Client(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
        this.etat = EtatClient.WAITING_TO_ENTER;
    }

    //Implémentation du cycle du client
    public void run() {
        try {
            entrerDansRestaurant();
            allerAuBuffet();
            allerAuStandDeCuisson();
            manger();
            sortir();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void entrerDansRestaurant() throws InterruptedException {
        synchronized (restaurant) {
            while (restaurant.getPlacesDispos() == 0) {
                System.out.println("Client " + id + " attend pour entrer.");
                restaurant.wait(); // Attends qu'une place se libère
            }
            restaurant.prendrePlace();
            etat = EtatClient.AT_THE_BUFFET; // Mise à jour de l'état
        }
        System.out.println("Client " + id + " entre dans le restaurant.")
    }

    private void allerAuBuffet() throws InterruptedException {
        for (Compartiment compartiment : restaurant.getCompartiments()) {
            synchronized (compartiment) {
                int portion = random.nextInt(101);
                while (compartiment.getStock() < portion) {
                    System.out.println("Client " + id + " attend le remplissage du compartiment " + compartiment.getNumber() + ".");
                    compartiment.wait(); // Attente du réapprovisionnement
                }
                compartiment.prendreNourriture(portion);
            }
            Thread.sleep(200 + random.nextInt(100)); //Temps pour se servir
            int portion = random.nextInt(101); // Portion entre 0 et 100g
            compartiment.prendreNourriture(portion);
            Thread.sleep(200 + random.nextInt(100)); // Temps passé à se servir
        }
        System.out.println("Client " + id + " a terminé de se servir au buffet.");
        etat = EtatClient.WAITING_FOR_THE_COOK; // Mise à jour de l'état
    }

    private void allerAuStandDeCuisson() throws InterruptedException {
        System.out.println("Client " + id + " attend pour la cuisson.");
        synchronized (restaurant.getStandCuisson()) {
            restaurant.getStandCuisson().ajouterCommande(this); // Le client ajoute sa commande
            restaurant.getStandCuisson().wait(); // Attente de la cuisson
        }
        System.out.println("Cuisson terminée. Client " + id + " récupère son plat.");

    }


    private void manger() throws InterruptedException {
        etat = EtatClient.EATING;
        System.out.println("Client " + id + " mange.");
        Thread.sleep(1000 + random.nextInt(500));
    }


    private void sortir() {
        synchronized (restaurant) {
            restaurant.libererPlace();
            restaurant.notifyAll();
        }
        etat = EtatClient.OUT;
        System.out.println("Client " + id + " sort du restaurant.");
    }

    public EtatClient getEtat() {
        return etat;
    }
}
