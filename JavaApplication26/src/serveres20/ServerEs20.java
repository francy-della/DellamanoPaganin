/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package serveres20;

import java.io.IOException;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class ServerEs20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerTCP server = new ServerTCP(18);
            Thread t = new Thread(server);
            t.start();
            t.join();
        } catch (IOException ex) {
            System.err.println("Errore!");
        } catch (InterruptedException ex) {
            System.err.println("Fine!");
        }
    }
    
}
