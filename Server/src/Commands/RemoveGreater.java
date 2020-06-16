package Commands;

import Collection.Worker;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Iterator;

/***
 * Класс, реализующий команду remove_greater
 */

public class RemoveGreater extends Comands<Worker> {
    Worker worker;
    public byte[] bb;
    int j = 0;
    SQLreader reader;
    Connection connection;
    DbWork dbWork = new DbWork();

    public RemoveGreater(SQLreader reader, Connection connection){
        name = "remove_greater";
        args = worker;
        this. reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }
    public synchronized void removeG() throws FieldException, IOException, SQLException {
        StringBuilder answer = new StringBuilder();
        Add add = new Add(reader, connection);
        add.worker = args;
        add.add_id();
        add.add_date();


        // Возможно надо удалить!
        reader.idAll.remove(reader.idAll.indexOf(add.worker.getId()));

        Comparator comparator = reader.workers.comparator();


        int k = reader.workers.size();
        Iterator<Worker> i = reader.workers.iterator();
        while (i.hasNext()) {
            Worker worker = i.next();
            if (comparator.compare(worker, add.worker) > 0 && add.worker.getUser().getLogin().equals(worker.getUser().getLogin())) {
                dbWork.delete(worker.getId());
                i.remove();
                int idid = worker.getId();
                reader.idAll.remove(reader.idAll.indexOf(idid));
            }

        }
        j = reader.workers.size();

        if (!(j==0))
            answer.append("удалено " + (k-j) + " " + "worker");
        else{
            answer.append("[таких worker'ов нет]");
        }

        bb = String.valueOf(answer).getBytes();
    }
}
