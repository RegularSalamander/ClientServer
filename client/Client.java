import java.io.*;
import java.net.*;

public class Client {
    private static int SERVER_PORT = 8193;

    private Socket socket;

    public static void main(String args[]) throws IOException {
        Client c = new Client();

        c.close();
    }

    public Client() throws IOException {
        socket = new Socket("localhost", SERVER_PORT);
        System.out.println("Server socket created on port " + SERVER_PORT);
    }

    public void close() throws IOException {
        socket.close();
    }
}
