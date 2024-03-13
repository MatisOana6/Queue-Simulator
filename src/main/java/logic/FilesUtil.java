package logic;

import model.Server;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilesUtil {
    public static final String FILE_NAME = "testNew.txt";
    public BufferedWriter writer;

    public FilesUtil() throws IOException {
        writer = new BufferedWriter(new FileWriter(FILE_NAME));
    }

    public void writeData(int timpCurent, List<Task> waitingClienti, List<Server> servers) throws IOException {
        StringBuilder s = new StringBuilder("\nTime: " + (timpCurent + 1) + "\nWaiting clients: ");
        for (Task c : waitingClienti) {
            s.append(c.toString());
        }
        for (int i = 0; i < servers.size(); i++) {
            s.append("\nQueue ").append(i).append(": ");   //s=s+"\nQueue " + i + ": ";
            for (Task t : servers.get(i).getTasks()) {
                s.append(t.toString()); //s= s + c.toString();
            }
        }
        System.out.println(s);
        writer.append(s.toString());
    }

}