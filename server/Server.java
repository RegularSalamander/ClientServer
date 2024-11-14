import java.io.*;
import java.net.*;

public class Server {
    private static int SERVER_PORT = 8193;
    private static int SERVER_NUM = 84;

    private ServerSocket server;
    private Socket client;

    public static void main(String args[]) throws IOException {
        Server s = new Server();
        
    }

    public Server() throws IOException {
        server = new ServerSocket(SERVER_PORT);
        System.out.println("Server socket created on port " + SERVER_PORT);
    }

    public void awaitClient() throws IOException {
        client = server.accept();
        System.out.println("Client socket conected");
    }

    public void close() throws IOException {
        client.close();
        server.close();
    }
}
