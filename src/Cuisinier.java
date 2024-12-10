public class Cuisinier extends Thread {

    private StandDeCuisson standDeCuisson;

    public Cuisinier(StandDeCuisson standDeCuisson) {
        this.standDeCuisson = standDeCuisson;
    }

    @Override
    public void run() {
        while (true) {
            standDeCuisson.cuirePlat(); // Attend et cuit les plats des clients
        }
    }
}
