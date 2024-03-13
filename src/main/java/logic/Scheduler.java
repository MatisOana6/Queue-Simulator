package logic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private List<Thread> threads;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    private int clientsInQueue;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        //for maxNoServers
        // create server object
        //create thread with the object

        strategy = new TimeStrategy();
        this.servers = new ArrayList<>();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(maxTasksPerServer);
            Thread thread = new Thread(server);
            servers.add(server);
            thread.start();
        }
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public void changeStrategy(SelectionPolicy policy) {
        //apply strategy pattern to instantiate the strategy with the concrete
        //strategy corresponding to policy
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Task t) {
        strategy.addTask(getServers(), t);
    }


    public List<Server> getServers() {

        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void setRunning(boolean b) {
        for (Server server : servers) {
            server.setRunning(b);
        }
    }

    public void setClientsInQueue(int clientsInQueue) {
        this.clientsInQueue = clientsInQueue;
    }

}
