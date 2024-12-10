public class Restaurant {

    static int NOMBRE_PLACES = 25;
    static final int NBRE_CLIENT = 40;
    static final int NB_COMPARTIMENTS = 4;

    private Compartiment[] compartiments = new Compartiment[NB_COMPARTIMENTS];
    private Client[] clients = new Client[NBRE_CLIENT];
    static StandDeCuisson standCuisson;

    public Restaurant() {

        /* Instanciation des compartiments */
        for (int i = 0; i < NB_COMPARTIMENTS; i++) {
            compartiments[i] = new Compartiment(i);
        }

        /* Instanciation des clients */
        for (int i = 0; i < NBRE_CLIENT; i++) {
            clients[i] = new Client(i, compartiments);
        }

        /* Instanciation de l'employé */
        Employe employe = new Employe(compartiments);

        /* Instanciation du stand de cuisson */
        standCuisson = new StandDeCuisson();

        /* Instanciation et démarrage du cuisinier */
        Cuisinier cuisinier = new Cuisinier(standCuisson);
        cuisinier.setDaemon(true);  // Définir le thread cuisinier comme démon avant de le démarrer
        cuisinier.start();

        /* Démarrage des clients */
        for (int i = 0; i < NBRE_CLIENT; i++) {
            clients[i].start();
        }

        /* Démarrage de l'employé */
        employe.setDaemon(true);  // Définir l'employé comme démon avant de démarrer
        employe.start();
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
