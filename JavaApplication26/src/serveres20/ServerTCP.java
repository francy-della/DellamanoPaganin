/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serveres20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class ServerTCP extends Cassonetto implements Runnable {
    private ServerSocket server;

    public ServerTCP(int port) throws IOException {
        this.server = new ServerSocket(port);
        server.setSoTimeout(10000);
    }

    @Override
    public void run() {
        Socket connection = null;
        DataInputStream in = null;
        DataOutputStream out = null;

        System.out.println("Server in attesa... ");
        try {
            connection = server.accept();
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
 
            while (!Thread.interrupted()) {
                int scelta = in.readInt();

                switch (scelta) {
                    case 1://apri casonetto
                        out.writeInt(apriCassonetto(in.readInt()));
                        break;
                    case 2://crea tessera
                        out.writeInt(creaTessera());
                        break;
                    case 3://elimina tessera
                        out.writeBoolean(eliminaTessera(in.readInt()));
                        break;
                    case 4://esci
                        //interruzione della connesione e del thread
                        in.close();
                        out.close();
                        connection.shutdownInput();
                        connection.shutdownOutput();
                        connection.close();
                        Thread.currentThread().interrupt();
                        break;
                    default:
                        out.writeUTF("Scelta non valida!");
                }
            }
        }catch (IOException ex) {
        } finally {
            if (connection != null) {
                try {
                    connection.shutdownInput();
                    connection.shutdownOutput();
                    connection.close();
                } catch (IOException ex) {
                    System.out.println("Fine!");
                }
            }
        }
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
