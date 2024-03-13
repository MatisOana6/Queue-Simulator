package logic;

import model.Server;
import model.Task;

import java.util.List;

public interface Strategy {

    public void addTask(List<Server> servers, Task t);
}
