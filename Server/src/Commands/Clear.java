package Commands;

import DataBase.DbWork;
import DataBase.SQLreader;

import java.sql.Connection;
import java.sql.SQLException;

/***
 * Класс, реализующий команду clear
 */

public class Clear extends Comands {
    Connection connection;
    SQLreader reader;
    DbWork dbWork = new DbWork();
    public byte [] bb;
    StringBuilder answer = new StringBuilder();
    public Clear(SQLreader reader, Connection connection){
        this.reader = reader;
        this.connection = connection;
        name = "clear";
        commandsNames.add(name);
    }
    public void clear(SQLreader reader) throws SQLException {
        while(!(reader.workers.isEmpty())) {
            dbWork.delete(reader.workers.element().getId());
            reader.workers.remove();
        }
        System.out.println("[коллекция удалена]");
        answer.append("[коллекция удалена]");
        bb = String.valueOf(answer).getBytes();
    }
}
