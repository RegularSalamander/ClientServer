import java.io.*;
import java.net.*;

public class Server {
    private static int SERVER_PORT = 5193;
    private static int SERVER_NUM = 84;

    private ServerSocket server;
    private Socket client;

    public static void main(String args[]) throws IOException {
        Server s = new Server();
        s.awaitClient();
        s.close();
    }

    public Server() throws IOException {
        server = new ServerSocket(SERVER_PORT);
        System.out.println("Server socket crted on port " + SERVER_PORT);
    }

    public void awaitClient() throws IOException {
        System.out.println("Awaiting client...");
        client = server.accept();
        System.out.println("Client socket conected");
    }

    public void close() throws IOException {
        if(client != null) {
            client.close();
            System.out.println("Client socket closed");
        }
        server.close();
        System.out.println("Server socket closed");
    }
}
