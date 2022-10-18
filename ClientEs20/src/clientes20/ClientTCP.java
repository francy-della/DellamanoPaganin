/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientes20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author FRANCESCADELLAMANO
 */
public class ClientTCP {

    private String server;
    private int port;

    public ClientTCP(String server, int port) throws IOException {
        this.server = server;
        this.port = port;
    }

    public void avviaClient() throws IOException {
        Socket client_socket = new Socket();
        InetSocketAddress server_adress = new InetSocketAddress(server, port);
        client_socket.setSoTimeout(10000);
        client_socket.connect(server_adress);
        DataInputStream in = new DataInputStream(client_socket.getInputStream());
        DataOutputStream out = new DataOutputStream(client_socket.getOutputStream());

        int codice;
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("1. Aprire cassonetto\n"
                    + "2. Registrare una nuova tessera\n"
                    + "3. Eliminare una tessera\n"
                    + "4. Uscire\n");
            System.out.print("Effettuare una scelta: ");
            int scelta = input.nextInt();
            out.writeInt(scelta);

            switch (scelta) {
                case 1://apertura cassonetto
                    System.out.print("Inserire codice: ");
                    codice = input.nextInt();
                    out.writeInt(codice);
                    int risposta = in.readInt();
                    if (risposta == 1) {
                        System.out.println("Apertura cassonetto avvenuta con successo!\n");
                        break;
                    }
                    if (risposta == -1) {
                        System.out.println("ERRORE! Tessera non trovata!\n");
                        break;
                    }
                    if (risposta == -2) {
                        System.out.println("Non puoi utilizzare il cassonetto finche' non sono passate 72 ore\n");
                        break;
                    }
                    break;
                case 2://creazione nuova tessera
                    codice = in.readInt();
                    System.out.println("La tua tessera e' stata registrata!\n Il tuo codice e': " + codice + "\n");
                    break;
                case 3://elimazione tessera
                    System.out.print("Inserire il codice da eliminare: ");
                    codice = input.nextInt();
                    out.writeInt(codice);
                    boolean ris = in.readBoolean();
                    if (ris) {
                        System.out.println("Tessera eliminata con successo!\n");
                        break;
                    }
                    System.out.println("ERRORE! Tessera non trovata!\n");
                    break;
                case 4://uscita
                    in.close();
                    out.close();
                    client_socket.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(in.readUTF());
            }
        }
    }

}
