/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serveres20;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class Tessera {

    private int codice;
    private boolean valida;
    private LocalDate ultimaApertura;
    private ArrayList<Integer> numeriGenerati = new ArrayList<>();

    public Tessera() {
        this.codice = generaCodice();
        this.valida = true;
        this.ultimaApertura = LocalDate.now();
    }

    /**
     * Calcola la differenza in giorni tra la data dell'ultima apertura e quella
     * corrente.
     *
     * @return true se valida e' true o la differenza tra le due date e'
     * maggiore o uguale a 3 giorni(72 ore).
     */
    public boolean isValida() {
        int differenza = Period.between(getUltimaApertura(), LocalDate.now()).getDays();

        if (valida) {
            return true;
        }
        if (differenza >= 3) {
            setValida(true);
            return true;
        }
        return false;
    }

    /**
     * Genera un codice casuale da 1 a 50 e viene salvato in un arraylist. Se il
     * codice e' presente nell'arraylist viene generato un altro codice finche'
     * quest'ultimo non e' presente all'interno di numeriGenerati.
     */
    private int generaCodice() {
        int codiceGenerato = new Random().nextInt(49) + 1;

        while (numeriGenerati.contains(codiceGenerato)) {
            codiceGenerato = new Random().nextInt(49) + 1;
        }
        numeriGenerati.add(codiceGenerato);
        return codiceGenerato;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }

    public LocalDate getUltimaApertura() {
        return ultimaApertura;
    }

    public void setUltimaApertura(LocalDate ultimaApertura) {
        this.ultimaApertura = ultimaApertura;
    }

    @Override
    public String toString() {
        return "Tessere --> " + "codice = " + codice + ", ultimaApertura = " + ultimaApertura + ", valida = " + valida;
    }
}
