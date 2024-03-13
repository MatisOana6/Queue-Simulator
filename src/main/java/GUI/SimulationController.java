package GUI;

import logic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {
    private final SimulationManager manager;
    private final SimulationFrame frame;
    private int startSim;

    public SimulationController(SimulationManager manager, final SimulationFrame frame) {
        this.manager = manager;
        this.frame = frame;
        this.setStartSim(0);
        this.frame.addSimulationListener(new SimulationListener());
        this.frame.addStopListener(new StopListener());
    }

    public int getStartSim() {
        return startSim;
    }

    public void setStartSim(int startSim) {
        this.startSim = startSim;
    }

    class SimulationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            manager.setTimeLimit(frame.getInterval());
            manager.setNumberOfClients(frame.getClienti());
            manager.setNumberOfServers(frame.getQueue());
            manager.setMinArrivalTime(frame.getMinTime());
            manager.setMaxArrivalTime(frame.getMaxTime());
            manager.setMinProcessingTime(frame.getMinService());
            manager.setMaxProcessingTime(frame.getMaxService());
            manager.scheduler.changeStrategy(manager.selectionPolicy);
            setStartSim(1);
        }
    }

    class StopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getStartSim() == 1) {
                manager.stopSimulation();
                setStartSim(0);
            }
        }
    }
}

