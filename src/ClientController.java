import model.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientController {
    private int serverPort = 8888;
    private int clientPort = 9999;
    private String serverHost = "localhost";
    private DatagramSocket client;

    void openConnect() {
        try {
            client = new DatagramSocket(clientPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    void closeConnect() {
        try {
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void sendData(User user) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            oos.flush();
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());

            InetAddress inetAddress = InetAddress.getByName(serverHost);
            System.out.println(inetAddress);
            byte[] sentData = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sentData, sentData.length, inetAddress, serverPort);
            client.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean receiveData() {
        try {
            byte[] dataReceived = new byte[1024];
            DatagramPacket packet = new DatagramPacket(dataReceived, dataReceived.length);
            client.receive(packet);

            ByteArrayInputStream bais = new ByteArrayInputStream(dataReceived);
            ObjectInputStream ois = new ObjectInputStream(bais);
            boolean result =  (Boolean) ois.readObject();
            System.out.println("Login " + result);
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
