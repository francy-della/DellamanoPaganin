/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serveres20;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class Cassonetto {

    public static ArrayList<Tessera> tessere = new ArrayList<>();

    protected int apriCassonetto(int codice) {
        for (Tessera t : tessere) {
            if (t.getCodice() == codice) {
                if (!t.isValida()) {
                    return -2; // apertura non consentita
                }
                t.setUltimaApertura(LocalDate.now());
                t.setValida(false);
                return 1; // apertura consentita
            }
        }
        return -1; // tessera non trovata
    }

    protected int creaTessera() {
        Tessera t = new Tessera();
        tessere.add(t);
        return t.getCodice();
    }

    protected boolean eliminaTessera(int codice) {
        for (Tessera t : tessere) {
            if (t.getCodice() == codice) {
                tessere.remove(t);
                return true;
            }
        }
        return false;
    }
}
