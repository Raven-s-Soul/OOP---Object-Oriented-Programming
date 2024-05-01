package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {
    @Override
    public String getNome() {
        return "Non Valido";
    }

    @Override
    public String getParametro() {
        return null;
    }

    @Override
    public void esegui(Partita partita) {
    	partita.getIo().mostraMessaggio("Comando non valido.");
    }

    @Override
    public void setParametro(String parametro) {

    }
}