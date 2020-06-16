package Commands;

import Collection.User;
import Collection.Worker;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * Класс, реализующий команду update_by_id id
 */


public class Update extends Comands<Worker> {
    public byte[] bb;
    SQLreader reader;
    Connection connection;
    DbWork dbWork = new DbWork();
    User user = new User();
    int index;

    public Update(SQLreader reader, Connection connection) {
        name = "update_by_id";
        this.reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }

    public synchronized void update(int id) throws IOException, SQLException, FieldException {
        List<Worker> workers = new ArrayList<>(reader.workers);
        StringBuilder answer = new StringBuilder();
        Remove remove = new Remove(reader, connection);
        int check = 0;
        Add add = new Add(reader, connection);
        add.worker = args;
        System.out.println(args.toString());

        for (Worker worker : workers) {
            if (worker.getId() == id)
                index = workers.indexOf(worker);
        }

        if (reader.idAll.contains(id)) {
            if (add.worker.getUser().getLogin().equals(workers.get(index).getUser().getLogin())) {
                remove.user = add.worker.getUser();
                remove.remove(id);
                add.add_date();
                dbWork.insertbyId(add.worker);
                add.add();
                check++;
                reader.idAll.add(id);


                if (!(check == 0)) {
                    answer.append("[worker обновлен]");
                    bb = String.valueOf(answer).getBytes();
                }
                } else {
                System.out.println("Извините, но вы не имеете право на редактирование данного элемента");
                answer.append("Извините, но вы не имеете право на редактирование данного элемента");
                bb = String.valueOf(answer).getBytes();
            }


        } else {
            System.out.println("[Нет worker'a с таким id]");
            answer.append("[Нет worker'a с таким id]");
            bb = String.valueOf(answer).getBytes();
        }
    }
}
