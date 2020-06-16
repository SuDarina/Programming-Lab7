package CollectionMannage;

import Exceptions.FieldException;
import Exceptions.FileIsEmptyException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class Start {
    public static void main(String[] args) throws ParseException, IOException, FileIsEmptyException, FieldException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException {

        UserInterface us = new UserInterface();
        System.out.println("Server started!");
        us.reader.createBase();
        us.reader.loadCollection();
        us.setServer();

    }
}
