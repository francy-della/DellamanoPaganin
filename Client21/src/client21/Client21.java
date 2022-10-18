package client21;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @author JENNIFERPAGANIN
 */
public class Client21 {
    public static void main(String[] args) throws IOException {
        ClientUDP client = new ClientUDP("127.0.0.1", 12345);
        int[] voti = new int[4];
        Scanner input = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("1. Inserire voti\n"
                        + "2. Voto medio di un caffè scelto\n"
                        + "3. Uscire\n");
                System.out.print("Effettuare una scelta: ");
                int scelta = input.nextInt();
                client.inviaIntero(scelta);

                switch (scelta) {
                    case 1:
                        int i = 0;
                        while (i < voti.length) {
                            System.out.print("Voto del " + (i + 1) + "° caffe': ");
                            voti[i] = input.nextInt();
                            if (voti[i] < 1 || voti[i] > 10) {
                                System.out.println("\nInserire un voto tra 1 e 10!\n");
                                continue;
                            }
                            i++;
                        }
                        client.inviaVoti(voti);
                        break;
                        
                    case 2:
                        int idCaffe = 0;
                        System.out.print("Seleziona il caffè di cui vuoi sapere il voto medio: ");
                        for (int j = 0; j < 4; j++) {
                            System.out.print((j + 1) + "° caffe' | ");
                        }
                        do {
                            System.out.print("\nScelta: ");
                            idCaffe = input.nextInt();
                        } while (idCaffe < 1 || idCaffe > 4);
                        System.out.println(client.votoMedio(idCaffe) + "\n");
                        break;
                        
                    case 3:
                        client.close_socket();
                        System.exit(0);
                        break;
                        
                    default:
                        client.sceltaNonValida();
                }
            }
        } catch (SocketException ex) {
            System.err.println("Errore creazione socket!");
        }
    }

}
