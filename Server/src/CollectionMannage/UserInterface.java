package CollectionMannage;

import Collection.User;
import Collection.Worker;
import Commands.*;
import DataBase.Connect;
import DataBase.DbWork;
import DataBase.SQLreader;
import Exceptions.FieldException;
import Exceptions.FileIsEmptyException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/***
 * Класс для запроса ввода у пользователя и вывода результата действия команд
 */

public class UserInterface {

    private static ForkJoinPool writeClient = new ForkJoinPool();
    private static ExecutorService executeComand = Executors.newCachedThreadPool();
    private static ForkJoinPool readClient = new ForkJoinPool();

   Connection connection = Connect.connect();

    public String comand;
    Comands comands;


    SQLreader reader = new SQLreader(connection);

   public ServerSocket server;
    Future<byte[]> bytess;
    Selector selector;
    ServerSocketChannel ssc;
    ObjectInputStream ois = null;
    SelectionKey key;
    Execute execute = new Execute(reader, comands);
    HashMap<SelectionKey, Future<byte[]>> res = new HashMap<>();




    public UserInterface()  {
    }

    public  void setServer() throws IOException, ClassNotFoundException, FileIsEmptyException, java.text.ParseException, FieldException, ExecutionException, InterruptedException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        server = ssc.socket();
        server.bind(new InetSocketAddress(3555));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        serverWork();
    }
    public void serverWork() throws ClassNotFoundException, FileIsEmptyException, java.text.ParseException, FieldException, ExecutionException, InterruptedException {
        try {
            while (true) {

                selector.select();

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    key = keys.next();
                    keys.remove();

                    if(!key.isValid()){
                        continue;
                    }

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(4000));

                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4000);
                        readClient.execute(new ReadClient(channel, buffer));
                        System.out.println("получено: " + new String(buffer.array(), buffer.position(), buffer.remaining()));
                        byte[] bytes = buffer.array();
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        ois = new ObjectInputStream(bais);
                        comands = (Comands) ois.readObject();
                        System.out.println(comands.getName());
                        bais.close();
                        ois.close();


                        System.out.println("имя: " + comands.getName());
                        comand = comands.getName();
                        execute.comands = comands;
                        res.put(key, executeComand.submit(new ExecuteCommand(execute)));
                        key.interestOps(SelectionKey.OP_WRITE);


                    } else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (res.containsKey(key)) {
                            writeClient.execute(new WriteClient(channel, res.get(key).get(), comand));
                            res.remove(key);
                        }

                        key.interestOps(SelectionKey.OP_READ);


                    }
                }

            }
        } catch (IOException e){
            System.out.println("Server work has been interrupted because of some problems with connection");
            System.exit(0);
            e.printStackTrace();
        }
    }


}




