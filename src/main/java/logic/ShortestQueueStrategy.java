package logic;

import model.Server;
import model.Task;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        int minNumberOfTasks = MAX_VALUE;
        Server newServ = null;
        for (Server server : servers) {
            if (server.getTasks().size() < minNumberOfTasks) {
                minNumberOfTasks = server.getTasks().size();
                newServ = server;
            }
        }

        newServ.addTask(task);

    }
}