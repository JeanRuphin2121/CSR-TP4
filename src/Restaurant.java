import java.util.Random;

public class Restaurant {

    static final int NBRE_CLIENT=40;
    static final int NB_COMPARTIMENS = 4;


    private Compartiment [] compartiments = new Compartiment [NB_COMPARTIMENS];
    private Client[] clients = new Client[NBRE_CLIENT];
    private Employe employe = null;

    public Restaurant() {

        /* Instanciation des compartiments */
        for (int i = 0; i < NB_COMPARTIMENS; i++)
            compartiments[i] = new Compartiment(i);

        /* Instanciation des clients */
        Random r = new Random();
        for (int i = 0; i < NBRE_CLIENT; i++) {
            clients[i] = new Client();
        }

        /* Instanciation de l'employé */
        employe = new Employe();

        /* Démarrage des clients */
        for (int i = 0; i < NBRE_CLIENT; i++) {
            clients[i].start();
        }

        /*Démarrage de l'employé*/
        employe.start();
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}