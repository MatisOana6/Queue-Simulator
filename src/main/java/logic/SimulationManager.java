package logic;

import GUI.SimulationController;
import GUI.SimulationFrame;
import model.Server;
import model.Task;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private String str = " ";
    private int maxTaskPerServer = 100;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    public Scheduler scheduler = new Scheduler(getNumberOfServers(), maxTaskPerServer);
    private SimulationFrame frame;
    private SimulationController control;
    private List<Task> generatedTasks;
    private int currentTime = 0;
    private int maxTime = 0;

    public FilesUtil fileUtil = new FilesUtil();

    private int id = 0;

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    private void generateNRandomTasks(int nbOfTasks) {
        //generate N random tasks;
        // - random processing time
        //minProcessingTime < processingTime < maxProcessingTime
        // - random arrivalTime
        //sort list with respect to arrivalTime
        generatedTasks = new ArrayList<Task>();
        for (int i = 0; i < nbOfTasks; i++) {
            int arrivalTime = ThreadLocalRandom.current().nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int processingTime = ThreadLocalRandom.current().nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            id = id + 1;
            Task newTask = new Task(id, arrivalTime, processingTime);
            generatedTasks.add(newTask);
        }
        Collections.sort(generatedTasks);
    }

    public SimulationManager() throws IOException, InterruptedException {
        frame = new SimulationFrame();
        control = new SimulationController(this, frame);
        frame.setVisible(true);
        while (control.getStartSim() == 0) {
            sleep(100);
        }
        // Generate random clients
        this.generateNRandomTasks(getNumberOfClients());
        this.scheduler = new Scheduler(frame.getQueue(), maxTaskPerServer);
        this.scheduler.changeStrategy(selectionPolicy);
        {
            for (Server s : this.scheduler.getServers()) {
                new Thread(s).start();
            }
        }
    }


    public void averageWaitingTime() throws IOException {
        int waitingTime = 0;
        int completedTasks = getNumberOfClients();
        if (completedTasks > 0) {
            for (Server s : this.scheduler.getServers()) {
                waitingTime += s.timeWaitingQueue;
                s.timeWaitingQueue++;
            }
            float averageWaitingT = (float) waitingTime / completedTasks;
            String str = "\nAverage waiting time is: " + averageWaitingT;
            this.fileUtil.writer.append(str);
        } else {
            String str = "\nAverage waiting time is: 0.0 (No completed tasks)";
            this.fileUtil.writer.append(str);
        }
    }

    public void averageServiceTime() throws IOException {
        int servingTime = 0;
        int completedTasks = getNumberOfClients();
        if (completedTasks > 0) {
            for (Server s : this.scheduler.getServers()) {
                servingTime += s.timeServingQueue;
                s.timeServingQueue++;
            }
            double averageServiceTime = (double) servingTime / completedTasks;
            String str = "\nAverage service time is: " + averageServiceTime;
            this.fileUtil.writer.append(str);
        } else {
            String str = "\nAverage service time is: 0.0 (No completed tasks)";
            this.fileUtil.writer.append(str);
        }
    }

    public void peakHour() throws IOException {
        int sum = 0, max = -1; // fix: initialize max to -1
        for (Server s : this.scheduler.getServers()) {
            sum += s.taskPersQueue;
        }
        if (max < sum) {
            max = sum;
            maxTime = currentTime;
        }
        String str = "\nPeak hour is: " + maxTime + " with " + max + " tasks in queue.";
        this.fileUtil.writer.append(str);
    }


    public String updateFrame(int currentTime, List<Task> remainingTasks, List<Server> servers) throws IOException {
        this.currentTime = currentTime;
        StringBuilder sb = new StringBuilder();
        sb.append("Time: ").append(currentTime).append("\n");
        sb.append("Waiting clients: ");
        if (remainingTasks.isEmpty()) {
            sb.append("none");
        } else {
            for (Task task : remainingTasks) {
                sb.append(task).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // remove the last comma and space
        }
        sb.append("\n");
        for (Server s : servers) {
            sb.append("Queue").append(s.getServerNumber(servers)).append(":");
            if (s.isOpen()) {
                sb.append("closed");
            } else {
                sb.append("processing client:");
                if (!s.getTasks().isEmpty()) {
                    for (Task t : s.getTasks()) {
                        sb.append(" ").append(t);
                        sb.append(", ");
                    }
                    sb.deleteCharAt(sb.length() - 1); // remove the last space
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public void run() {
        int currentTime = -1;
        while (currentTime < getTimeLimit() && control.getStartSim() == 1) {
            try {
                fileUtil.writeData(currentTime, generatedTasks, scheduler.getServers());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentTime++;
            List<Task> toRemove = new ArrayList<>();
            List<Integer> toRemoveIndexes = new ArrayList<>();
            int index = 0;
            int clientsBeingServed = 0; // initialize counter variable
            for (Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    toRemove.add(t);
                    toRemoveIndexes.add(index);
                    clientsBeingServed++; // increment counter variable
                }
                index++;
            }
            // remove tasks with arrival time equal to current time
            generatedTasks.removeAll(toRemove);
            String frame = null;
            try {
                frame = updateFrame(currentTime, generatedTasks, scheduler.getServers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SimulationFrame.textArea.append(frame);

            for (Task task : generatedTasks) {
                (new StringBuilder()).append(task).append(", ");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Server server : scheduler.getServers()) {
                Task t = server.getTasks().peek();
                if (t != null) {
                    if (t.getServiceTime() <= 0) {
                        server.getTasks().remove();
                        int serviceTimeQueue = server.getServiceTimeQueue();
                        server.setServiceTimeQueue(serviceTimeQueue - 1);
                        clientsBeingServed -= serviceTimeQueue; // decrement counter variable
                    } else {
                        t.setServiceTime(t.getServiceTime() - 1);
                    }
                }
            }
            generatedTasks.removeAll(toRemove);
            // decrement clients in queue by clientsBeingServed counter
            int clientsInQueue = generatedTasks.size() - clientsBeingServed;
            if (clientsInQueue < 0) {
                clientsInQueue = 0;
            }
            scheduler.setClientsInQueue(clientsInQueue);
        }

        try {
            this.peakHour();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.averageWaitingTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.averageServiceTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileUtil.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopSimulation() {
        scheduler.setRunning(false);
        for (Server s : scheduler.getServers()) {
            s.setRunning(false);
        }
    }
}
