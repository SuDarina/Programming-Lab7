package Commands;

import Collection.Worker;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;


/***
 * Класс, реализующий команду add_if_min
 */

public class AddIfMin extends Comands<Worker> {

    public Add add1;
    public Worker worker;
    public byte[] bb;
    Connection connection;
    SQLreader reader;
    DbWork dbWork = new DbWork();

    public AddIfMin(SQLreader reader, Connection connection){
        name = "add_if_min";
        args = worker;
        this.reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }

    public void addIfMin() throws FieldException, IOException, SQLException {
        StringBuilder answer = new StringBuilder();

        add1 = new Add(reader, connection);
        add1.worker = args;
        add1.add_id();
        add1.add_date();

        Comparator comparator = reader.workers.comparator();
        long min = reader.workers.stream().filter((o1) -> comparator.compare(o1, add1.worker) < 0).count();
        if (min == 0) {
              dbWork.insertbyId(add1.worker);
              add1.add();
        }
         else {
            reader.idAll.remove ((Integer) add1.worker.getId());
            System.out.println("[worker не добавлен]");
            answer.append("\n[worker не добавлен]");
            add1.bb = String.valueOf(answer).getBytes();
        }
    }

}


