/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clientes20;

import java.io.IOException;

/**
 *
 * @author FRANCESCADELLAMANO
 */
public class ClientEs20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP("127.0.0.1", 18);
            client.avviaClient();
        } catch (IOException ex) {
            System.err.println("Errore generico di comunicazione!");
        }
    }
    
}
