package server21;

import java.net.SocketException;

/**
 *
 * @author FRANCESCADELLAMANO
 */
        
public class Server21 {
    public static void main(String[] args) {
        try {
            ServerUDP server = new ServerUDP(12345);
            Thread thrServer = new Thread(server);
            thrServer.start();
            thrServer.join();
        } catch (SocketException ex) {
            System.err.println("Errore all'avvio: " + ex);
        } catch (InterruptedException ex) {
            System.err.println("Fine!");
        }
    }
}

