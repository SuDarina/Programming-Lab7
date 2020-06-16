package Commands;

import Collection.User;
import DataBase.DbWork;
import DataBase.SQLreader;

import java.sql.Connection;
import java.sql.SQLException;

public class Regester extends Comands<User>{
    Connection connection;
    public byte[] bb;
    SQLreader reader;
    DbWork db = new DbWork();
    public User user = new User();


    public Regester(SQLreader reader, Connection connection) {
        name = "regester";
        args = user;
        this.reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }

    public void regester() {
        try {
            db.regester(user);
            String answer = "вы успешно зарегестрировались";
            bb = answer.getBytes();
        }catch (SQLException e){
            String answer = "пользователь с такой учетной записью уже существует";
            bb = answer.getBytes();
        }
    }
}
