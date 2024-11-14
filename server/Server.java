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

        s.send("Server of Cameron Kelly");
        s.send(String.valueOf(SERVER_NUM));

        System.out.println(s.receive());

        int num = -1;
        String client_num = s.receive();
        try {
            num = Integer.parseInt(client_num);
        } catch(NumberFormatException e) {
            System.out.println("Client sent an invalid number");
            s.close();
            System.exit(1);
        }

        System.out.println("Client number: " + num);
        System.out.println(SERVER_NUM + " + " + num + " = " + (num+SERVER_NUM));

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
