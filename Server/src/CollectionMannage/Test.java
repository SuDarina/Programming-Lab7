package CollectionMannage;

import Collection.User;
import Commands.Login;
import DataBase.Connect;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws FieldException, SQLException {
        Connection connection = Connect.connect();
        SQLreader reader = new SQLreader(connection);
        Login login = new Login(reader, connection);
        DbWork db = new DbWork();
        User user = new User();
        user.setLogin("Pavel");
        user.setPassword("123");
        System.out.println(db.login(user));

    }
}
