import java.io.IOException;
import java.net.*;

public class UDPclient extends Thread {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private byte[] buf;

    public UDPclient(int port, String address) {
        this.port = port;
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(address);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String sendEcho(String msg) {
        this.buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(this.buf, this.buf.length, address, this.port);
        try {
            this.socket.send(packet);
            packet = new DatagramPacket(this.buf, this.buf.length);
            this.socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(
                packet.getData(), 0, packet.getLength());
        System.out.println(received);
        return received;
    }

    public void close() {
        socket.close();
    }

    public void closeServer(){sendEcho("end");}

    public static void main(String[] args){
        int i = 0;
        boolean running = true;
        UDPclient client = new UDPclient(4445,args[1]);
        while(running) {
            client.sendEcho(args[0]);
            i++;
            if (i == 1000){
                running = false;
            }
        }
    }
}

