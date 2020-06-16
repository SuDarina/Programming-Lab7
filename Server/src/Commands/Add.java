package Commands;

import Collection.Worker;
import DataBase.SQLreader;
import Exceptions.FieldException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;


/***
 * Класс, реализующий команду add
 */

public class Add extends Comands<Worker> {
    Connection connection;
    public byte[] bb;
    SQLreader reader;


    public Add(SQLreader reader, Connection connection) {
        name = "add";
        args = worker;
        this.reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }


    StringBuilder answer = new StringBuilder();

    public Worker worker = new Worker();


    public synchronized void add_id() throws FieldException, IOException, SQLException {
        System.out.println("id нового Worker'a:");
        answer.append("id нового Worker'a:\n");

        int newId = reader.idAll.get(reader.idAll.size()-1) + 1;
        reader.idAll.add(newId);
        worker.setId(newId);
        answer.append(worker.getId());
        answer.append("\n");
        for (int d : reader.idAll){
            System.out.println(d);
        }
    }

    public void add_date() throws IOException {
        worker.setStartDate(LocalDateTime.now());
        worker.setCreationDate();
        System.out.println("[даты установлены]");
        answer.append("[даты установлены]\n");
    }


    public synchronized void add() throws IOException, SQLException {
        reader.workers.add(worker);
        System.out.println("[worker добавлен]");
        answer.append("[worker добавлен]");
        bb = String.valueOf(answer).getBytes();

    }
}
