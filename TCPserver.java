
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class TCPserver {
    private ServerSocket server;
    private int port;
    private String message = " Friend";
    private boolean running = true;
    public TCPserver(String ip, int port) throws Exception {
        this.port = port;
        if (ip != null && !ip.isEmpty())
            this.server = new ServerSocket(this.port, 50, InetAddress.getByName(ip));
        else
            this.server = new ServerSocket(this.port, 50, InetAddress.getLocalHost());
    }
    private void listen() throws Exception {
        while(running) {
            String data = null;
            Socket client = this.server.accept();
            String clientAddress = client.getInetAddress().getHostAddress();
            System.out.println("\r\nNew connection from " + clientAddress);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            while ((data = in.readLine()) != null) {
                if(data.equals("end")){
                    this.running = false;
                    System.out.println("end of server");
                }
                System.out.println("\r\nMessage from " + clientAddress + ": " + data);
                String new_message = data + this.message;
                out.println(new_message.toUpperCase());
            }
        }
    }
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }
    public static void main(String[] args) throws Exception {
        TCPserver app = new TCPserver(args[0],51517);
        System.out.println("\r\nRunning Server: " +
                "Host=" + app.getSocketAddress().getHostAddress() +
                " Port=" + app.getPort());

        app.listen();
    }
}

