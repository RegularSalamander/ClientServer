import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    private static final int SERVER_PORT = 5193;
    private static final int SERVER_NUM = 84;

    private ServerSocket server;
    private Socket client;

    private PrintWriter out;
    private BufferedReader in;

    private static String lastLogTime;

    public static void main(String args[]) throws IOException {
        Server s = new Server();
        s.awaitClient();

        s.send("Server of Cameron Kelly");
        s.send(String.valueOf(SERVER_NUM));

        s.receive();

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
        log("Server socket created on port " + SERVER_PORT);
    }

    public void awaitClient() throws IOException {
        log("Awaiting client...");
        client = server.accept();
        log("Client socket conected");

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
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
        if(client != null) {
            client.close();
            log("Client socket closed");
        }
        server.close();
        log("Server socket closed\n");
    }

    public static void log(String message) {
        System.out.println(message);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("server/log.txt", true));

            LocalDateTime now = LocalDateTime.now();
            String time = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
            if(time.equals(lastLogTime)) time = "                      ";
            else lastLogTime = time;

            writer.write(time + message + "\n");

            writer.close();
        } catch(IOException e) {}
    }
}
