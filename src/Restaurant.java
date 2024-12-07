import java.util.Random;

public class Restaurant {

    static final int NOMBRE_PLACES= 25;
    static final int NBRE_CLIENT=40;
    static final int NB_COMPARTIMENS = 4;

    private int placesDispos = NOMBRE_PLACES;
    private Compartiment [] compartiments = new Compartiment [NB_COMPARTIMENS];
    private Client[] clients = new Client[NBRE_CLIENT];
    private Employe employe = null;
    private final StandDeCuisson standCuisson = new StandDeCuisson();

    public Restaurant() {

        /* Instanciation des compartiments */
        for (int i = 0; i < NB_COMPARTIMENS; i++)
            compartiments[i] = new Compartiment(i);

        /* Instanciation des clients */
        Random r = new Random();
        for (int i = 0; i < NBRE_CLIENT; i++) {
            //clients[i] = new Client();
            clients[i] = new Client(i, this);
        }

        /* Instanciation de l'employé */
        employe = new Employe(this);

        /* Démarrage des clients */
        for (int i = 0; i < NBRE_CLIENT; i++) {
            clients[i].start();
        }

        /*Démarrage de l'employé*/
        employe.start();
    }

    public synchronized void prendrePlace() {
        placesDispos--;
    }

    public synchronized void libererPlace(){
        placesDispos++;
    }

    public int getPlacesDispos(){
        return placesDispos;
    }

    public Compartiment[] getCompartiments() {
        return compartiments;
    }

    public StandDeCuisson getStandCuisson() {
        return standCuisson;
    }

    public static void main(String[] args) {

        new Restaurant();
    }
}