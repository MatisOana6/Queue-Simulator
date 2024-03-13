package model;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int timeQueue = 0;
    private int serviceTimeQueue = 0;
    public int timeWaitingQueue = 0;
    public int timeServingQueue = 0;
    public int taskPersQueue = 0;
    private AtomicInteger totalWaitingTime;

    private Task currentTask;
    private boolean ok = true;

    public int getTimeQueue() {
        return timeQueue;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTimeQueue(int timeQueue) {
        this.timeQueue = timeQueue;
    }

    public int getServiceTimeQueue() {
        return serviceTimeQueue;
    }

    public void setServiceTimeQueue(int serviceTimeQueue) {
        this.serviceTimeQueue = serviceTimeQueue;
    }

    public AtomicInteger getTotalWaitingTime() {
        return totalWaitingTime;
    }


    public Server() {
        //initialize queue and waitingPeriod
        this.tasks = new LinkedBlockingQueue<Task>();
        this.waitingPeriod = new AtomicInteger();
        waitingPeriod.set(0);

    }

    public Server(BlockingQueue<Task> tasks, AtomicInteger waitingPeriod) {
        this.tasks = tasks;
        this.waitingPeriod = new AtomicInteger();
        waitingPeriod.set(0);
    }

    public Server(int maxTaskPerServer) {
        this.tasks = new LinkedBlockingQueue<Task>(maxTaskPerServer);
        this.waitingPeriod = new AtomicInteger();
        waitingPeriod.set(0);
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void addTask(Task newTask) {
        //add task to queue
        //increment the waitingPeriod
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = tasks.take();
                int serviceTime = task.getServiceTime();
                for (int i = 0; i < serviceTime; i++) {
                    Thread.sleep(1000);
                    waitingPeriod.decrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error processing task: " + e.getMessage());
            }
        }
    }

    public Task[] getTaskArray() {
        Object[] taskArray = this.tasks.toArray();
        Task[] resultedArray = new Task[taskArray.length];
        for (int i = 0; i < taskArray.length; i++)
            resultedArray[i] = (Task) taskArray[i];
        return resultedArray;
    }

    public int getServerNumber(List<Server> servers) {
        int serverNumber = -1;
        for (int i = 0; i < servers.size(); i++) {
            if (this == servers.get(i)) {
                serverNumber = i + 1;
                break;
            }
        }
        return serverNumber;
    }

    public boolean isOpen() {
        //  return waitingPeriod.get() == 0 && tasks.isEmpty();
        return tasks.isEmpty();
    }


    public String getTasksInQueue() {
        if (tasks.isEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (Task t : tasks) {
            sb.append(t).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public void setRunning(boolean b) {
    }

    public int getWaitingTime() {
        int result = 0;
        for (Task task : tasks) {
            result += task.getServiceTime();
        }
        if (currentTask != null) {
            return result + currentTask.getServiceTime();
        } else return result;
    }

}
