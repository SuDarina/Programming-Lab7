import Collection.User;
import Commands.Comands;
import Commands.ExecuteScript;
import Exceptions.FieldException;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    String comand1 = "";
    SocketChannel channel;
    int i = 0;
    boolean online = false;

    Scanner scr = new Scanner(System.in);
    Scanner scanner;
    Scanner oldScanner;
    String es = "execute_script";
    String firstScript;
    String scriptFile;

    String comand = "";
    ByteBuffer bb = ByteBuffer.allocate(4000);
    FormUser fu = new FormUser();
    User user = new User();
    ExecuteScript executeScript = new ExecuteScript();
    Comands comands = new Comands();
    String[] ss = new String[2];

    public void setClient() throws FieldException, InterruptedException {
        try {
            i++;
            channel = SocketChannel.open(new InetSocketAddress("localhost", 3555));
            i = 0;
            System.out.println("Connected to server");
            System.out.println("Войдите или зарегистрируйтесь:");

            clientWork();

        } catch (ConnectException e) {
            if (i == 1)
                System.out.println("[сервер временно недоступен]");
            Thread.sleep(2000);
            setClient();

        } catch (IOException e) {
            setClient();
        } catch (StackOverflowError e) {
            System.out.println("Время на отладку вышло, попробуйте перезапустить приложение позже");
        }
    }

    public void clientWork() throws FieldException, IOException {
        boolean isWorking = true;


        while (isWorking) {
            try {
                comand = scr.nextLine();
                if (comand.contains(es)) {
                    scriptWork();
                } else
                    workWithServer();
            }catch (NoSuchElementException e){
                comand = "exit";
                workWithServer();
            }
        }
    }


    public void scriptWork() throws FieldException, IOException {

        ss = comand.split(" ");
        scriptFile = ss[1];
        System.out.println(ss[1]);
        if (comand1.equals(es)){
            try {
                if (firstScript.equals(scriptFile)) throw new StackOverflowError();
            }catch (StackOverflowError e){
                System.out.println("Невозможно вызвать команду execute_script при выполнении команды execute_script с одним файлом");

            }
        } else {
            firstScript = scriptFile;
            File file = new File(scriptFile);
            try {
                scanner = new Scanner(file);
                if (!(comand1.equals(es))) {
                    oldScanner = scanner;
                }

            } catch (FileNotFoundException e) {
                if (file.exists()) {
                    Rights rights = new Rights();
                    rights.setRights(file);
                    scanner = new Scanner(file);
                    if (!(comand1.equals(es))) {
                        oldScanner = scanner;
                    }
                } else {
                    System.out.println("файла не существует");

                }
            }
            executeScript.executeScript(scriptFile);
        }

        while (!(executeScript.comands.isEmpty())) {
            comand1 = es;
            try {
                    comand = scanner.nextLine();
                    if (comand.contains(es)) {
                        scriptWork();
                    } else
                        workWithServer();
                    executeScript.comands.remove(0);


            } catch (NoSuchElementException e) {
                if (oldScanner.hasNext()) {
                    comand = oldScanner.nextLine();
                    if (comand.contains(es)) {
                        scriptWork();
                    } else
                        workWithServer();

                    executeScript.comands.remove(0);
                }

            }
            if (!scanner.hasNext() && !oldScanner.hasNext()){
                break;
            }

        }
        comand1 = "";


    }

    public void formComandName() {
        if (comand.contains(" ") && !comand.contains(es)) {
            ss = comand.split(" ");
            comands.setName(ss[0]);
            comands.setArgs(ss[1]);
        } else if (!comand.contains(es)) {
            comands.setName(comand);
            System.out.println("имя: " + comand);
        }

    }

    public void workWithServer() throws FieldException, IOException {

        formComandName();
        if (!comand.contains(es)) {


            if (!online && !comands.getName().equals("regester") && !comands.getName().equals("login") && !comands.getName().equals("exit")) {
                System.out.println("Войдите или зареестрируйтесь\nВход (команда login)\nРегистрация (команда: regester)");
                clientWork();
            }


            if (comands.getName().equals("add") | comands.getName().equals("add_if_min") | comands.getName().equals("remove_greater") | comands.getName().equals("remove_lower") | comands.getName().equals("update_by_id")) {
                FormWorker fw = new FormWorker();
                fw.worker.setUser(user);
                fw.add_name();
                fw.add_coordinates();
                fw.add_x();
                fw.add_y();
                fw.add_salary();
                fw.add_position();
                fw.add_status();
                fw.add_height();
                fw.add_passportID();
                fw.add_person();
                System.out.println(user.toString());
                if (comands.getName().equals("update_by_id")) {
                    fw.worker.setId(Integer.parseInt((String) comands.getArgs()));
                }
                comands.setArgs(fw.worker);
            }
            if (comands.getName().equals("count_less_than_start_date")) {
                FormDate fd = new FormDate();
                fd.countDate();
                fd.countTime();
                fd.formDateTime();
                comands.setArgs(fd.ldt);
            }
            if (comands.getName().equals("login") || comands.getName().equals("regester")) {
                if (!online) {
                    fu.formUser();
                    comands.setArgs(fu.user);
                    user = fu.user;
                } else {
                    System.out.println("Вы уже вошли в систему");
                    clientWork();
                }
            }

            if (comands.getName().equals("exit")) {
                comands.setArgs(fu.user);
            }

            if (comands.getName().equals("remove_by_id")) {
                fu.user.setId(Integer.parseInt((String) comands.getArgs()));
                comands.setArgs(fu.user);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);


            oos.writeObject(comands);
            bb.flip();
            bb.clear();

            bb.put(baos.toByteArray());
            bb.flip();
            channel.write(bb);
            if (bb.hasRemaining()) {
                bb.compact();
            } else {
                bb.clear();
            }


            channel.read(bb);
            bb.flip();
            String serverAnswer = new String(bb.array(), bb.position(), bb.remaining());
            System.out.println(serverAnswer);
            bb.clear();

            if (serverAnswer.equals("[программа завершена]"))
                System.exit(0);


            if (serverAnswer.equals("[введите worker'а]")) {
                FormWorker fww = new FormWorker();
                fww.worker.setUser(user);
                fww.add_name();
                fww.add_coordinates();
                fww.add_x();
                fww.add_y();
                fww.add_salary();
                fww.add_position();
                fww.add_status();
                fww.add_height();
                fww.add_passportID();
                fww.add_person();
                comands.setArgs(fww.worker);
                oos.writeObject(comands);
                bb.flip();
                bb.clear();

                bb.put(baos.toByteArray());
                bb.flip();
                channel.write(bb);
                if (bb.hasRemaining()) {
                    bb.compact();
                } else {
                    bb.clear();
                }
            }

            if (serverAnswer.equals("[введите дату и время]")) {

                FormDate fdd = new FormDate();
                fdd.countDate();
                fdd.countTime();
                fdd.formDateTime();
                comands.setArgs(fdd.ldt);
                oos.writeObject(comands);
                bb.flip();
                bb.clear();

                bb.put(baos.toByteArray());
                bb.flip();
                channel.write(bb);
                if (bb.hasRemaining()) {
                    bb.compact();
                } else {
                    bb.clear();
                }
            }


            if (serverAnswer.equals("доступ разрешён")) {
                online = true;
                fu.user.setOnline(true);
            }

            baos.close();
            oos.close();

        }

    }
}
