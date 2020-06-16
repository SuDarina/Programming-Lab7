import Exceptions.FieldException;

import java.io.IOException;

public class StartClient {
    public static void main(String[] args) throws IOException, FieldException, InterruptedException {
        Client client = new Client();
        client.setClient();
    }
}
