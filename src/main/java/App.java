import logic.SimulationManager;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}
