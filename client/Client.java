import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private static final int SERVER_PORT = 5193;

    private String server_ip;
    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    private static String lastLogTime;

    public static void main(String args[]) throws IOException {
        Client c = new Client();
        c.connect();

        c.send("Client of Cameron Kelly");

        c.receive();

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
            log("Server sent an invalid number");
            c.close();
            System.exit(1);
        }

        System.out.println("Server number: " + server_num);
        log(num + " + " + server_num + " = " + (num+server_num));

        c.close();
    }

    public void connect() throws IOException {
        log("Obtaining server ip...");
        server_ip = new String(Files.readAllBytes(Paths.get("serverip.txt")), StandardCharsets.UTF_8);
        log("Attempting to connect to server ip " + server_ip);

        try {
            socket = new Socket(server_ip, SERVER_PORT);
        } catch (ConnectException e) {
            log("Could not connect to server\n");
            System.exit(1);
        }

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        log("Socket created on port " + SERVER_PORT);
    }

    public void send(String message) {
        out.println(message);
        log("Sent message: '" + message + "'");
    }

    public String receive() throws IOException {
        String message = in.readLine();
        log("Received message: '" + message + "'");
        return message;
    }

    public void close() throws IOException {
        if(socket != null){
            socket.close();
            log("Closed socket\n");
        }
    }

    public static void log(String message) {
        System.out.println(message);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("client/log.txt", true));

            LocalDateTime now = LocalDateTime.now();
            String time = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
            if(time.equals(lastLogTime)) time = "                      ";
            else lastLogTime = time;

            writer.write(time + message + "\n");

            writer.close();
        } catch(IOException e) {}
    }
}
