import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
    private Socket socket;
    private Scanner scanner;
    private boolean running = true;
    private TCPclient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }
    private void start() throws IOException {
        String input;
        while (running) {
            input = scanner.next();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            if(out.toString().equals("end_connection")){
                this.running = false;
                continue;
            }
            out.println(input);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputline;
            if ((inputline = in.readLine())!= null){
                System.out.println(inputline);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        TCPclient client = new TCPclient(
                InetAddress.getByName(args[0]),
                Integer.parseInt(args[1]));

        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}

