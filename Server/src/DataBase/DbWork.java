package DataBase;

import Collection.User;
import Collection.Worker;

import java.sql.*;

public class DbWork {
    int idStatus;
    int idPosition;
    int idUser;
    Integer finalPosition;
    Integer online;
    Connection connection = Connect.connect();
    SQLreader reader = new SQLreader(connection);

    public synchronized void insert(Worker worker) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO coordinates (id, x, y) VALUES (DEFAULT, (?), (?))")){
            statement.setInt(1, worker.getCoordinates().getX());
            statement.setFloat(2, worker.getCoordinates().getY());

            statement.execute();
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO person (id, height, passportID) VALUES (DEFAULT, (?), (?))")){
            statement.setFloat(1, worker.getPerson().getHeight());
            statement.setString(2, worker.getPerson().getPassportID());

            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM statuses WHERE status = (?)")) {
            statement.setString(1, String.valueOf(worker.getStatus()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idStatus = rs.getInt("id");


            }
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE position = (?)")) {
            statement.setString(1, String.valueOf(worker.getPosition()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idPosition = rs.getInt("id");


            }
        }

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO creationdate (id, date) VALUES (DEFAULT, (?))")){
            statement.setTimestamp(1, Timestamp.valueOf(worker.getStartDate()));
            statement.execute();

        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate) VALUES (DEFAULT, (?), (?), (?), (?), (?), (?), (?))")) {
            System.out.println(worker.getId());
            statement.setString(1, worker.getName());
            statement.setInt(2, reader.idAll.get(reader.idAll.size()-1)+1);
            statement.setInt(3, (int) worker.getSalary());
            statement.setInt(4, idPosition);
            statement.setInt(5, idStatus);
            statement.setInt(6, reader.idAll.get(reader.idAll.size()-1)+1);
            statement.setInt(7, reader.idAll.get(reader.idAll.size()-1)+1);
            statement.execute();

        }
    }

    public void insertbyId(Worker worker) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO coordinates (id, x, y) VALUES ("+worker.getId()+", ?, ?)")){
            statement.setInt(1, worker.getCoordinates().getX());
            statement.setFloat(2, worker.getCoordinates().getY());
            System.out.println(statement.execute());
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO person (id, height, passportID) VALUES ("+worker.getId()+", ?, ?)")){
            statement.setFloat(1, worker.getPerson().getHeight());
            statement.setString(2, worker.getPerson().getPassportID());
            System.out.println(statement.execute());
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = (?)")) {
            System.out.println(worker.getUser().getLogin());
            statement.setString(1, worker.getUser().getLogin());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idUser = rs.getInt("id");
            }
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM statuses WHERE status = (?)")) {
            statement.setString(1, String.valueOf(worker.getStatus()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idStatus = rs.getInt("id");
            }
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE position = (?)")) {
            statement.setString(1, String.valueOf(worker.getPosition()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idPosition = rs.getInt("id");
                if (idPosition == 0)
                    finalPosition = null;
                else finalPosition = idPosition;
            }
        }

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO creationdate (id, date) VALUES ("+ worker.getId() + ", (?))")){
            statement.setTimestamp(1, Timestamp.valueOf(worker.getStartDate()));
            System.out.println(statement.execute());
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate, creater) VALUES ("+ worker.getId() + ", ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, worker.getName());
            statement.setInt(2, worker.getId());
            statement.setInt(3, (int) worker.getSalary());
            statement.setObject(4, finalPosition);
            statement.setInt(5, idStatus);
            statement.setInt(6, worker.getId());
            statement.setInt(7, worker.getId());
            statement.setInt(8, idUser);

            statement.execute();
        }
    }
    public void regester (User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (id, login, password, online) VALUES (DEFAULT , ?, ?, ?)")){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, 0);
            statement.execute();
        }
    }

    public boolean login (User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = (?) AND  password = (?)")){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            ResultSet rs = statement.executeQuery();

            return rs.next();
        }

    }

//    public boolean setOnline(User user) throws SQLException {
//        try(PreparedStatement statement = connection.prepareStatement("UPDATE Users SET online = 1 WHERE login = (?)")){
//            statement.setString(1, user.getLogin());
//            return !statement.execute();
//        }
//    }


    public void delete(Integer id) throws SQLException {
        try(Statement statement = connection.createStatement()){
        statement.execute("DELETE FROM workers WHERE id = " + id);
        statement.execute("DELETE FROM person WHERE id = " + id);
        statement.execute("DELETE FROM creationdate WHERE id = " + id);
        statement.execute("DELETE FROM coordinates WHERE id = " + id);

        }
    }

    public boolean online (User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE login = ?" )) {
            statement.setString(1, user.getLogin());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                online = rs.getInt("online");
            }
            return online == 1;
        }
    }

    public void exit (User user) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("UPDATE Users SET online = 0 WHERE login = (?)")){
            statement.setString(1, user.getLogin());
            statement.execute();
        }
    }

//    public void update(Integer id, Worker worker) throws SQLException {
//        delete(id);
//        insertbyId(worker);
//    }
}
