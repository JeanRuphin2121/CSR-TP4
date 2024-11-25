public class Restaurant {

    static final int NBRE_CLIENT=40;
    static final int NB_COMPARTIMENS = 4;


    private Compartiment [] compartiments = new Compartiment [NB_COMPARTIMENS];
    private Client[] clients = new Client[NBRE_CLIENT];




    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}