package Commands;

import Collection.User;
import DataBase.DbWork;
import DataBase.SQLreader;

import java.sql.Connection;
import java.sql.SQLException;

public class Login extends Comands<User> {
    Connection connection;
    public byte[] bb;
    SQLreader reader;
    public User user = new User();
    DbWork db = new DbWork();
    public String answer;


    public Login(SQLreader reader, Connection connection) {
        name = "login";
        args = user;
        this.reader = reader;
        this.connection = connection;
        commandsNames.add(name);
    }
    public boolean login() throws SQLException {
        if (db.login(user)) {
//            if (!db.online(user)) {
                answer = "доступ разрешён";
                System.out.println("доступ разрешён");
                bb = answer.getBytes();
                return true;

//            }else {
//                answer = "доступ запрещен\nпользователь с данной учетной записью уже в сети";
//                System.out.println("доступ запрещен\nпользователь с данной учетной записью уже в сети");
//                bb = answer.getBytes();
//                return false;
//            }

        }
        else {
            answer = "неверный логин или пароль, попробуйте ещё раз";
            System.out.println("неверный логин или пароль, попробуйте ещё раз");
            bb = answer.getBytes();
            return false;
        }
    }
}
