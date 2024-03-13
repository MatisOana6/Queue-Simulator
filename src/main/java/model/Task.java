package model;

public class Task implements Comparable<Task> {
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int waitingTime;
    private int totalWaitTime;
    private int startTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;

    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTotalWaitTime() {
        if (startTime == -1) {
            return 0;
        } else
            return startTime - arrivalTime;
    }

    public void setTotalWaitTime(int totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {

        this.ID = ID;
    }

    public int getArrivalTime() {

        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {

        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {

        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {

        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "(" + ID + "," + arrivalTime + "," + serviceTime + ")";
    }

    public int compareTo(Task task) {

        return Integer.compare(this.arrivalTime, task.arrivalTime);
    }

    public int getProcessingTime() {
        return totalWaitTime - arrivalTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

}
