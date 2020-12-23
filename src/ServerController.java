import model.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerController {
    private DatagramSocket socket;
    private final int serverPort = 8888;
    private DatagramPacket receivedPacket = null;

    public ServerController(){
        openServer(serverPort);
        while (true){
            listening();
        }
    }

    private void openServer(int serverPort) {
        try{
            socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void listening() {
        User user = receiveData();
        sendData(user.getUsername().equals("admin") && user.getPassword().equals("dmlam"));
    }

    private void sendData(boolean b) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(b);
            oos.flush();

            InetAddress ipAddress = receivedPacket.getAddress();
            int clientPort = receivedPacket.getPort();

            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, clientPort);
            socket.send(sendPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User receiveData(){
        System.out.println("receive Data");
        User user = null;
        try{
            byte[] receivedData = new byte[1024];
            receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            socket.receive(receivedPacket);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            user = (User) ois.readObject();
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
