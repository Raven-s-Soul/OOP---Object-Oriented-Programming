package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 * e gestisce le condizione di vittoria/sconfitta.
 *
 * @author docente di POO
 * @see Labirinto
 * @see Giocatore
 * @version 1.0
 */
public class Partita {


	private Labirinto Labirinto;
	private Giocatore giocatore;
	private IO io;
	private boolean finita;

	/**
	* Crea Partita, definisce finita, giocatore e Labirinto.
	*/
	public Partita(IO io) {
		this.Labirinto = new Labirinto();
		this.getLabirinto().creaStanze();
		this.finita = false;
		this.giocatore= new Giocatore();
		this.io = io;
	}

	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * 
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.getLabirinto().getStanzaAttuale() == this.getLabirinto().getStanzaFinale();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * 
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || isCfuFiniti();
	}
	
	public boolean isCfuFiniti() {
		return getGiocatore().getCfu() == 0;
	}
	
	//Imposta la partita come finita
	public void setFinita() {
		this.finita = true;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public Labirinto getLabirinto() {
		return Labirinto;
	}


	public IO getIo() {
		return io;
	}
	

}
