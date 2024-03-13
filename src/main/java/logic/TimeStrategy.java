package logic;

import model.Server;
import model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.MAX_VALUE;

public class TimeStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task task) {
        int minTime = MAX_VALUE;
        Server serverMinWaitingTime = null;
        for (Server server : servers) {
            if (minTime > server.getWaitingPeriod().get()) {
                minTime = server.getWaitingPeriod().get();
                serverMinWaitingTime = server;
            }
        }
        assert serverMinWaitingTime != null;
        // serverMinWaitingTime.addTask(task); // add the task to the server's queue
        AtomicInteger time = new AtomicInteger();
        time.addAndGet(task.getServiceTime() + serverMinWaitingTime.getWaitingPeriod().intValue());
        serverMinWaitingTime.setWaitingPeriod(time);
        // return serverMinWaitingTime.getWaitingPeriod().intValue() - task.getServiceTime();
    }

}
