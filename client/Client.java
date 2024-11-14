import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private static int SERVER_PORT = 5193;

    private String server_ip;
    private Socket socket;

    public static void main(String args[]) throws IOException {
        Client c = new Client();
        c.connect();

        c.close();
    }

    public void connect() throws IOException {
        server_ip = new String(Files.readAllBytes(Paths.get("serverip.txt")), StandardCharsets.UTF_8);

        try {
            socket = new Socket(server_ip, SERVER_PORT);
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
