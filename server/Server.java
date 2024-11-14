import java.io.*;
import java.net.*;

public class Server {
    private static int SERVER_PORT = 5193;
    private static int SERVER_NUM = 84;

    private ServerSocket server;
    private Socket client;

    private PrintWriter out;
    private BufferedReader in;

    public static void main(String args[]) throws IOException {
        Server s = new Server();
        s.awaitClient();

        System.out.println(s.receive());
        System.out.println(s.receive());

        s.send("Server of Cameron Kelly");
        s.send("hehe!");

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

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
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
