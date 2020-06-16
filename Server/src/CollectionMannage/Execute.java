package CollectionMannage;

import Collection.User;
import Collection.Worker;
import Commands.*;
import DataBase.Connect;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Execute{
    SQLreader reader;
    Connection connection = Connect.connect();
    public String comand;
    Comands comands;
    byte[] bytes;
    DbWork dbWork = new DbWork();


    public Execute(SQLreader reader, Comands comands){
        this.reader = reader;
        this.comands = comands;
    }


    Help help = new Help();
    Show show = new Show();
    Clear clear = new Clear(reader, connection);
    Save save = new Save();
    MaxStatus maxStatus = new MaxStatus();
    ExecuteScript executeScript = new ExecuteScript();
    Info info = new Info();
    Regester regester = new Regester(reader, connection);
    Login login = new Login(reader, connection);


   public byte[] execute() throws FieldException, SQLException, IOException {
       List<byte[]> finalBytes = new ArrayList<>();
       comand = comands.getName();
        switch (comand) {
            case ("regester"):
                regester.user = (User) comands.getArgs();
                regester.regester();
                bytes = regester.bb;
                finalBytes.add(bytes);

                break;
            case ("login"):
                login.user = (User) comands.getArgs();
                try {
                    login.login();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bytes = login.bb;
                finalBytes.add(bytes);

                break;
            case ("help"):
                help.help();
                bytes = help.bb;
                finalBytes.add(bytes);

                break;
            case ("exit"):
                save.save();
                bytes = "[программа завершена]".getBytes();
                finalBytes.add(bytes);
                if (!comands.getArgs().equals(null)) {
                    dbWork.exit((User) comands.getArgs());
                }

                break;
            case ("show"):
                show.show(reader);
                bytes = show.bb;
                finalBytes.add(bytes);

                break;
            case ("add"):
                Add add = new Add(reader, connection);
                add.worker = (Worker) comands.getArgs();
                System.out.println(add.worker.toString());
                add.add_date();
                add.add_id();
                dbWork.insertbyId(add.worker);
                add.add();
                bytes = add.bb;
                finalBytes.add(bytes);

                break;
            case ("remove_by_id"):
                int id;

                    try {
                        Remove remove = new Remove(reader, connection);
                        remove.user = (User) comands.getArgs();
                        id = remove.user.getId();
                        remove.remove(id);
                        bytes = remove.bb;
                        finalBytes.add(bytes);
                    } catch (NumberFormatException e) {
                        System.out.println("Вы ввели не число, введите команду заново");
                        bytes = ("Вы ввели не число, введите команду заново").getBytes();
                        finalBytes.add(bytes);
                    }


                break;


            case ("clear"):
                clear.clear(reader);
                bytes = clear.bb;
                finalBytes.add(bytes);

                break;

            case ("sum_of_salary"):

                SalarySum sSum = new SalarySum(reader);
                sSum.salarySum();
                bytes = sSum.bb;
                finalBytes.add(bytes);
                break;

            case ("max_by_status"):
                maxStatus.maxStatus(reader);
                bytes = maxStatus.bb;
                finalBytes.add(bytes);
                break;

            case ("update_by_id"):
                Update update = new Update(reader, connection);
                update.setArgs((Worker) comands.getArgs());
                int id2 = 0;

                    id2 = update.getArgs().getId();
                    ((Worker) comands.getArgs()).setId(id2);
                    try {
                        update.update(id2);
                        bytes = update.bb;
                        finalBytes.add(bytes);

                    } catch (NumberFormatException e) {
                        System.out.println("Вы ввели не число, введите команду заново");
                        bytes = ("Вы ввели не число, введите команду заново").getBytes();
                        finalBytes.add(bytes);

                        break;
                    }


                break;

            case ("add_if_min"):
                AddIfMin addIfMin = new AddIfMin(reader, connection);
                addIfMin.setArgs((Worker) comands.getArgs());
                System.out.println(comands.getArgs().toString());
                try {
                    addIfMin.addIfMin();
                } catch (FieldException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bytes = addIfMin.add1.bb;
                finalBytes.add(bytes);

                break;

            case ("remove_greater"):
                RemoveGreater removeG = new RemoveGreater(reader, connection);
                removeG.setArgs((Worker) comands.getArgs());
                try {
                    removeG.removeG();
                } catch (FieldException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bytes = removeG.bb;
                finalBytes.add(bytes);

                break;

            case ("remove_lower"):
                RemoveLower removeL = new RemoveLower(reader, connection);
                removeL.setArgs((Worker) comands.getArgs());
                try {
                    removeL.removeL();
                } catch (FieldException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bytes = removeL.bb;
                finalBytes.add(bytes);

                break;

            case ("info"):
                try {
                    info.info(reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = info.bb;
                finalBytes.add(bytes);

                break;

            case ("count_less_than_start_date"):
                CountLessStartDate countDate = new CountLessStartDate(reader);
                countDate.ldt = (LocalDateTime) comands.getArgs();
                countDate.countDateTime();
                bytes = countDate.bb;
                finalBytes.add(bytes);

                break;

            default:
                System.out.println("такой команды нет, повторите ввод");
                bytes = "такой команды нет, повторите ввод".getBytes();
                finalBytes.add(bytes);

                break;
        }
        return bytes;
    }

}

