package Commands;

import Collection.User;
import Collection.Worker;
import DataBase.DbWork;
import DataBase.SQLreader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * Класс, реализующий команду remove_by_id id
 */

public class Remove extends Comands<User> {
    public byte[] bb;
    SQLreader reader;
    Connection connection;
    DbWork dbWork = new DbWork();
    public User user = new User();
    boolean deleted = false;
    List<Worker> workers = new ArrayList<>();
    public Remove(SQLreader reader, Connection connection){
        name = "remove_by_id";
        this.connection = connection;
        this.reader = reader;
        commandsNames.add(name);
    }
    public synchronized void remove(int id) throws SQLException {
        for (Worker worker : reader.workers){
            System.out.println(worker.toString());
        }

        workers.addAll(reader.workers);
        StringBuilder answer  = new StringBuilder();;
        args = user;

        if (!(reader.idAll.contains(id))) {
            System.out.println("[нет worker'а с таким id]");
            answer.append("[нет worker'а с таким id]");
            bb = String.valueOf(answer).getBytes();
        }
        Iterator<Worker> i = reader.workers.iterator();
        while (i.hasNext()) {
            Worker worker = i.next();
            if (worker.getId() == id && worker.getUser().getLogin().equals(user.getLogin())) {

                dbWork.delete(id);
                i.remove();

                // Возможно придется удалить!
                reader.idAll.remove(reader.idAll.indexOf(id));
                deleted = true;
                System.out.println("[worker удалён]");
                answer.append("[worker удалён]");
                bb = String.valueOf(answer).getBytes();
                }
            }
        if (!deleted){
            System.out.println("Извините, но вы не имеете право на редактирование данного элемента");
            answer.append("Извините, но вы не имеете право на редактирование данного элемента");
            bb = String.valueOf(answer).getBytes();
        }
    }
}