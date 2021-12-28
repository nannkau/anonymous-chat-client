import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static int destPort = 8081;
    public static String hostname = "localhost";
    public static void client() {
        DatagramSocket socket;
        DatagramPacket send, receive;
        InetAddress add;
        Scanner stdIn;
        try {
            add = InetAddress.getByName(hostname);    //UnknownHostException
            socket = new DatagramSocket();            //SocketException
            stdIn = new Scanner(System.in);
            while (true) {
                System.out.print("Client input: ");
                String tmp = stdIn.nextLine();
                byte[] data = tmp.getBytes();
                send = new DatagramPacket(data, data.length, add, destPort);
                System.out.println("Client sent " + tmp + " to " + add.getHostAddress() +
                        " from port " + socket.getLocalPort());
                socket.send(send);                //IOExeption
                if (tmp.equals("bye")) {
                    System.out.println("Client socket closed");
                    stdIn.close();
                    socket.close();
                    break;
                }
                // Get response from server
                receive = new DatagramPacket(new byte[100000], 100000);
                socket.receive(receive);
                tmp = new String(receive.getData(), 0, receive.getLength());
                System.out.println("Client get: " + tmp + " from server");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
