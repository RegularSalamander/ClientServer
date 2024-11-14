import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private static int SERVER_PORT = 5193;

    private String server_ip;
    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    public static void main(String args[]) throws IOException {
        Client c = new Client();
        c.connect();

        c.send("Client of Cameron Kelly");

        int num;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Enter an integer from 1 to 100: ");

            num = scanner.nextInt();
            if(num >= 1 && num <= 100) break;

            System.out.println("\nPlease enter a new number");
        }
        scanner.close();

        c.send(String.valueOf(num));

        System.out.println(c.receive());
        System.out.println(c.receive());

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

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Socket created on port " + SERVER_PORT);
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        if(socket != null){
            socket.close();
            System.out.println("Closed socket");
        }
    }
}
