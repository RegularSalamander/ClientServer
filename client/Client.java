import java.io.*;
import java.net.*;

public class Client {
    private static int SERVER_PORT = 8193;

    private Socket socket;

    public static void main(String args[]) throws IOException {
        Client c = new Client();
        c.connect();

        c.close();
    }

    public void connect() throws IOException {
        try {
            socket = new Socket("localhost", SERVER_PORT);
        } catch (ConnectException e) {
            System.out.println("Could not connect to server");
            return;
        }
        System.out.println("Socket created on port " + SERVER_PORT);
    }

    public void close() throws IOException {
        if(socket != null){
            socket.close();
            System.out.println("Closed socket");
        }
    }
}
