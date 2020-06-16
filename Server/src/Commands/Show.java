package Commands;

import Collection.Worker;
import CollectionMannage.WorkerComparator;
import DataBase.SQLreader;
import Exceptions.FileIsEmptyException;

import java.util.PriorityQueue;
import java.util.Queue;


/***
 * Класс, реализующий команду show
 */

public class Show extends Comands {

    public byte[] bb;

    public Show() {
        name = "show";
        commandsNames.add(name);
    }

    public void show(SQLreader reader) {
        WorkerComparator workerComparator = new WorkerComparator();
        Queue<Worker> pq = new PriorityQueue<>(workerComparator);
        String answer;

        try {
            StringBuilder sb = new StringBuilder();
            pq.addAll(reader.workers);
            while (!(pq.isEmpty())) {
                answer = pq.poll().toString();
                sb.append(answer);
                sb.append("\n");


            }
            sb.deleteCharAt(sb.lastIndexOf("\n"));
            bb = (String.valueOf(sb).getBytes());


            if (reader.workers.isEmpty()) throw new FileIsEmptyException();

        } catch (FileIsEmptyException e) {
            System.out.println("[коллекция пустая]");
            bb = ("[коллекция пустая]").getBytes();
        }
    }
}
