import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPserver extends Thread {
    private int port;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public UDPserver(int port) {
        this.port = port;
        try {
            this.socket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void flash_buffer() {
        this.buf = new byte[256];
    }

    public void run() {
        this.running = true;
        try {

            while (running) {
                DatagramPacket packet
                        = new DatagramPacket(this.buf, this.buf.length);

                this.socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(this.buf, this.buf.length, address, port);
                String received
                        = new String(packet.getData(), 0, packet.getLength());
                System.out.println(packet.getAddress() + " :" + packet.getPort() + " :" +received);
                flash_buffer();
                if (received.equals("end")) {
                    this.running = false;
                    continue;
                }
                socket.send(packet);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        UDPserver server = new UDPserver(4445);
        server.start();
    }
}
