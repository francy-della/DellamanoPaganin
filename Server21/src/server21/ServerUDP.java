package server21;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
        
/**
 *
 * @author FRANCESCADELLAMANO
 */

        public class ServerUDP implements Runnable {
    protected int count = 1;
    protected DatagramSocket socket;

    public ServerUDP(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.socket.setSoTimeout(20000); // 20000 ms = 20s
        System.out.println("Server in attesa...");
    }
    
    public int[] riceviVoti() throws IOException {
        ByteBuffer data;
        int[] voti = new int[4];
        byte[] buffer = new byte[16];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);

        data = ByteBuffer.wrap(buffer, 0, 16);
        for (int i = 0; i < voti.length; i++) {
            voti[i] = data.getInt();
        }
        return voti;
    }

    public int saveToFile() throws IOException {
        int[] voti = riceviVoti();
        FileWriter file = new FileWriter("dati.csv", true);
        if (count > 10) {
            return -1;
        }
        for (int i = 0; i < voti.length; i++) {
            file.write(String.valueOf(voti[i]));
            file.write(",");
        }
        file.write("\n");
        file.close();
        count++;
        return 1;
    }

    public double votoMedio(int colonna) throws FileNotFoundException, IOException {
        String line;
        int voti = 0;
        int totale = 0;
        BufferedReader br = new BufferedReader(new FileReader("dati.csv"));

        while ((line = br.readLine()) != null) {
            String[] b = line.split(",");
            voti += Integer.parseInt(b[colonna]);
            totale++;
        }
        return voti / (double) totale;
    }

    @Override
    public void run() {
        int scelta;
        ByteBuffer data;
        DatagramPacket answer;
        byte[] buffer = new byte[4];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        while (!Thread.interrupted()) {
            try {
                socket.receive(request);
                
                InetAddress ipClient = request.getAddress();
                int portaClient = request.getPort();
                
                scelta = ByteBuffer.wrap(buffer, 0, buffer.length).getInt();
                
                switch (scelta) {
                    case 1:
                        int risultato = saveToFile();
                        data = ByteBuffer.allocate(4);
                        data.putInt(risultato);
                        answer = new DatagramPacket(data.array(), 4, ipClient, portaClient);
                        socket.send(answer);
                        break;
                        
                    case 2:
                        socket.receive(request);
                        int idCaffe = ByteBuffer.wrap(buffer, 0, buffer.length).getInt();
                        double risposta = votoMedio(idCaffe - 1);
                        data = ByteBuffer.wrap(new byte[8], 0, 8);
                        data.putDouble(risposta);
                        answer = new DatagramPacket(data.array(), 8, ipClient, portaClient);
                        socket.send(answer);
                        break;
                        
                    case 3:
                        new FileWriter("dati.csv");
                        socket.close();
                        Thread.currentThread().interrupt();
                        break;
                        
                    default:
                        byte[] nonValida = "Per favore, inserire una scelta valida!".getBytes();
                        answer = new DatagramPacket(nonValida, nonValida.length, ipClient, portaClient);
                        socket.send(answer);
                }
            } catch (IOException ex) {
                System.err.println("Errore nella risposta " + ex);
            }
        }
            
    }
}
