package Commands;

import Collection.Worker;
import DataBase.SQLreader;

/***
 * Класс, реализующий команду max_by_status
 */

public class MaxStatus extends Comands {
    public MaxStatus(){
        name = "max_by_status";
        commandsNames.add(name);
    }
    public byte[] bb;
    public void maxStatus(SQLreader reader){
        String answer;
        int max = 0;
        Worker maxWorker = new Worker();
        for(Worker worker : reader.workers){
            if (worker.getStatus().toString().length() > max) {
                max = worker.getStatus().toString().length();
                maxWorker = worker;
            }
        }
        System.out.println(maxWorker.toString());
        answer = maxWorker.toString();
        bb = answer.getBytes();
    }
}
