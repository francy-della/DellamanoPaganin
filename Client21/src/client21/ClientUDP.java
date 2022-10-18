package client21;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * @author JENNIFERPAGANIN
 */
public class ClientUDP {
    private int UDP_port;
    private DatagramSocket socket;
    private InetAddress serverAddress;

    public ClientUDP(String host, int port) throws SocketException, UnknownHostException {
        this.UDP_port = port;
        this.serverAddress = InetAddress.getByName(host);
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(2000000000); // 20000ms = 20s
    }

    public void close_socket() {
        this.socket.close();
    }

    public void inviaIntero(int scelta) throws IOException {
        if (socket.isClosed()) {
            throw new IOException();
        }
        ByteBuffer output = ByteBuffer.allocate(4);
        output.putInt(scelta);
        DatagramPacket datagram = new DatagramPacket(output.array(), 4, serverAddress, UDP_port);
        socket.send(datagram);
    }

    public void inviaVoti(int[] voti) throws IOException {
        int risposta;
        DatagramPacket datagram;

        if (socket.isClosed()) {
            throw new IOException();
        }
        ByteBuffer output = ByteBuffer.allocate(16);
        for (int voto : voti) {
            output.putInt(voto);
        }
        datagram = new DatagramPacket(output.array(), 16, serverAddress, UDP_port);
        socket.send(datagram);

        datagram = new DatagramPacket(new byte[4], 4);
        socket.receive(datagram);
        if (datagram.getAddress().equals(serverAddress) && datagram.getPort() == UDP_port) {
            ByteBuffer input = ByteBuffer.wrap(datagram.getData());
            risposta = input.getInt();
        } else {
            throw new SocketTimeoutException();
        }
        if (risposta == 1) {
            System.out.println("\nI tuoi voti sono stati registrati.\n");
        } else if (risposta == -1) {
            System.out.println("\nArchivio pieno!\n");
        }
    }

    public double votoMedio(int numero) throws IOException {
        inviaIntero(numero);

        DatagramPacket datagram = new DatagramPacket(new byte[8], 8);
        socket.receive(datagram);
        if (datagram.getAddress().equals(serverAddress) || datagram.getPort() == UDP_port) {
            ByteBuffer input = ByteBuffer.wrap(datagram.getData());
            return input.getDouble();
        } else {
            throw new SocketTimeoutException();
        }
    }
    
    public void sceltaNonValida() throws IOException {
        DatagramPacket datagram = new DatagramPacket(new byte[256], 256);
        socket.receive(datagram);
        if (datagram.getAddress().equals(serverAddress) || datagram.getPort() == UDP_port) {
            System.out.println(new String(datagram.getData(), 0, datagram.getLength()));
        } else {
            throw new SocketTimeoutException();
        }
    }
}
