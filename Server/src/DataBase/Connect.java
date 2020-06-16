package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection connection;

    public static Connection connect() {
        final String user = "postgres";
        final String password = "9218683095";
        final String url = "jdbc:postgresql://localhost:5432/workers";
        try{
            connection = DriverManager.getConnection(url, user, password);
            return connection;

        } catch (SQLException e) {
            System.out.println("Не удалось получить доступ к базе данных");
            System.exit(0);
            return null;
        }
    }
}
