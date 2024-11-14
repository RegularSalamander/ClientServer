import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
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

        System.out.println(c.receive());

        int num = -1;

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Enter an integer from 1 to 100: ");

            try {
                String input = scanner.nextLine();
                num = Integer.parseInt(input);
            } catch(NumberFormatException e) {}

            if(num >= 1 && num <= 100) break;

            System.out.println("Please enter a valid number.");
        }
        scanner.close();

        c.send(String.valueOf(num));
        
        int server_num = -1;
        String response = c.receive();
        try {
            server_num = Integer.parseInt(response);
        } catch(NumberFormatException e) {
            System.out.println("Server sent an invalid number");
            c.close();
            System.exit(1);
        }

        System.out.println("Server number: " + server_num);
        System.out.println(num + " + " + server_num + " = " + (num+server_num));

        c.close();
    }

    public void connect() throws IOException {
        server_ip = new String(Files.readAllBytes(Paths.get("serverip.txt")), StandardCharsets.UTF_8);

        try {
            socket = new Socket(server_ip, SERVER_PORT);
        } catch (ConnectException e) {
            System.out.println("Could not connect to server");
            System.exit(1);
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
