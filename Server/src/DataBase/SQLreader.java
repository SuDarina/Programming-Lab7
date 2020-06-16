package DataBase;

import Collection.*;
import CollectionMannage.WorkerComparator;
import Exceptions.FieldException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.*;


public class SQLreader {

    Connection connection;
    private WorkerComparator wc = new WorkerComparator();
    public PriorityQueue<Worker> workers = new PriorityQueue<>(wc);
    public List<Integer> idAll = new ArrayList<>();
    public SQLreader(Connection connection){
        this.connection = connection;
    }


   public void createBase() throws SQLException {
       if (!isCreated()) {
           String command = "CREATE TABLE IF NOT EXISTS users(\n" +
                   "  id SERIAL PRIMARY KEY,\n" +
                   "  login VARCHAR(10) UNIQUE NOT NULL,\n" +
                   "  password VARCHAR UNIQUE NOT NULL,\n" +
                   "  online INTEGER NOT NULL\n" +
                   ");\n" +
                   "INSERT INTO users (id, login, password, online) VALUES (DEFAULT, 'Pavel', '202CB962AC59075B964B07152D234B70', 0);\n" +
                   "INSERT INTO users (id, login, password, online) VALUES (DEFAULT, 'Egor', '81DC9BDB52D04DC20036DBD8313ED055', 0);\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS Coordinates(\n" +
                   "  id SERIAL PRIMARY KEY,\n" +
                   "  x INTEGER NOT NULL,\n" +
                   "  y FLOAT NOT NULL\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO Coordinates (id, x, y) VALUES (DEFAULT, 11, 11.09);\n" +
                   "INSERT INTO Coordinates (id, x, y) VALUES (DEFAULT, 111, 111.08);\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS Person(\n" +
                   "  id SERIAL PRIMARY KEY,\n" +
                   "  height FLOAT NOT NULL,\n" +
                   "  passportID VARCHAR(41) NOT NULL\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO Person (id, height, passportID) VALUES (DEFAULT, 187.09, 'uyyuhiu76567568568');\n" +
                   "INSERT INTO Person (id, height, passportID) VALUES (DEFAULT, 179.056, 'huijiuhugytyguy09809890');\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS Statuses(\n" +
                   "  id   SERIAL PRIMARY KEY,\n" +
                   "  Status VARCHAR(30) NOT NULL\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'HIRED');\n" +
                   "INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'RECOMMENDED_FOR_PROMOTION');\n" +
                   "INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'REGULAR');\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS CreationDate (\n" +
                   "  id SERIAL PRIMARY KEY,\n" +
                   "  date TIMESTAMP NOT NULL\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO CreationDate (id, date) VALUES (DEFAULT, now());\n" +
                   "INSERT INTO CreationDate (id, date) VALUES (DEFAULT, now());\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS Positions(\n" +
                   "  id   SERIAL PRIMARY KEY,\n" +
                   "  Position VARCHAR(30)\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'DIRECTOR');\n" +
                   "INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'MANAGER');\n" +
                   "INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'HUMAN_RESOURCES');\n" +
                   "INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'HEAD_OF_DEPARTMENT');\n" +
                   "INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'LEAD_DEVELOPER');\n" +
                   "\n" +
                   "CREATE TABLE IF NOT EXISTS Workers(\n" +
                   "  id SERIAL PRIMARY KEY,\n" +
                   "  name VARCHAR NOT NULL,\n" +
                   "  Coordinates INTEGER NOT NULL,\n" +
                   "  FOREIGN KEY (Coordinates) REFERENCES Coordinates (id),\n" +
                   "  salary INTEGER NOT NULL,\n" +
                   "  Position INTEGER NULL,\n" +
                   "  FOREIGN KEY (Position) REFERENCES Positions (id),\n" +
                   "  Status INTEGER NOT NULL,\n" +
                   "  FOREIGN KEY (Status) REFERENCES Statuses (id),\n" +
                   "  Person INTEGER NOT NULL,\n" +
                   "  FOREIGN KEY (Person) REFERENCES Person (id),\n" +
                   "  CreationDate INTEGER NOT NULL,\n" +
                   "  FOREIGN KEY (CreationDate) REFERENCES CreationDate (id),\n" +
                   "  creater INTEGER NOT NULL,\n" +
                   "  FOREIGN KEY (creater) REFERENCES users (id)\n" +
                   ");\n" +
                   "\n" +
                   "INSERT INTO Workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate, creater) VALUES (DEFAULT, 'bob', 1, 12345, null, 3, 1, 1, 1);\n" +
                   "INSERT INTO Workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate, creater) VALUES (DEFAULT, 'sally', 2, 88797, 2, 2, 2, 2, 1);\n";
//           int i = 25;
           String[] commands;
           commands = command.split(";");
           int i = commands.length;

           for (int j = 0; j < i; j++) {
               try (Statement statement = connection.createStatement()) {
                   statement.execute(commands[j]);

               }
           }

       }
   }
   public boolean isCreated() throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Statuses")){
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }else
                return false;
        }
   }
   public void loadCollection() throws FieldException, SQLException, FileNotFoundException {
        final String user = "postgres";
        final String password = "9218683095";
        final String url = "jdbc:postgresql://localhost:5432/workers";
        int IdStatus;
        int IdPosition;
        int IdCoordinates;
        int IdPerson;
        int IdLdt;
        int IdUser;
        List<Integer> IdStatuses =  new ArrayList<>();
        List<Worker> workersList = new ArrayList<>();
        List <Integer> Positions = new ArrayList<>();
        List <Integer> coordinates = new ArrayList<>();
        List<Integer> persons = new ArrayList<>();
        List<Integer> ldt = new ArrayList<>();
        List<Integer> IdUsers = new ArrayList<>();



        final Connection connection = DriverManager.getConnection(url, user, password);


        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM workers")) {


            final ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                Worker worker = new Worker();
                String byName = resultSet.getString("name");

                int byIndex = resultSet.getInt(1);
                idAll.add(byIndex);

                IdStatus = resultSet.getInt("Status");
                IdStatuses.add(IdStatus);

                IdPosition = resultSet.getInt("Position");
                Positions.add(IdPosition);

                IdCoordinates =resultSet.getInt("Coordinates");
                coordinates.add(IdCoordinates);

                IdPerson = resultSet.getInt("Person");
                persons.add(IdPerson);

                IdUser = resultSet.getInt("creater");
                IdUsers.add(IdUser);

                IdLdt = resultSet.getInt("CreationDate");
                ldt.add(IdLdt);

                int salary = resultSet.getInt(4);
                worker.setId(byIndex);
                worker.setName(byName);
                worker.setSalary(salary);
                workersList.add(worker);

            }
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM statuses WHERE id = (?)")) {
            int index = 0;
            for (int k : IdStatuses) {
                statement.setInt(1, k);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    workersList.get(index).setStatus(Status.valueOf(rs.getString(2)));
                }
                index ++;
            }
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE id = (?)")) {
            int index = 0;
            for (int k : Positions) {
                statement.setInt(1, k);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    workersList.get(index).setPosition(Position.valueOf(rs.getString(2)));
                }
                index++;
            }

        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM coordinates WHERE id = (?)")) {
            int index = 0;
            for (int k : coordinates) {
                statement.setInt(1, k);

                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Coordinates coord = new Coordinates();
                    coord.setX(rs.getInt("x"));
                    coord.setY(rs.getFloat("y"));
                    workersList.get(index).setCoordinates(coord);
                }
                index++;
            }

        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM creationdate WHERE id = (?)")) {
            int index = 0;
            for (int k : ldt) {
                statement.setInt(1, k);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    workersList.get(index).setStartDate(rs.getTimestamp("date").toLocalDateTime());
                    workersList.get(index).setCreationDate();
                }
                index++;
            }
        }
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id = (?)")) {
            int index = 0;
            for (int k : persons) {
                statement.setInt(1, k);

                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Person person = new Person();
                    person.setHeight(rs.getFloat("height"));
                    person.setPassportID(rs.getString("passportID"));
                    workersList.get(index).setPerson(person);
                }
                index++;
            }

        }

       try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = (?)")) {
           int index = 0;
           for (int k : IdUsers) {
               statement.setInt(1, k);

               ResultSet rs = statement.executeQuery();
               if (rs.next()) {
                   User usr = new User();
                   usr.setLogin(rs.getString("login"));
                   usr.setPassword(rs.getString("password"));
                   usr.setOnline(rs.getInt("online") == 1);
                   workersList.get(index).setUser(usr);
               }
               index++;
           }

       }

        workersList.sort(wc);
        idAll.sort(Comparator.naturalOrder());
        workers.addAll(workersList);
    }
}

