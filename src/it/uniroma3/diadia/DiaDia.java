package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author docente di POO (da un'idea di Michael Kolling and David J. Barnes)
 * @see Partita
 * @see IOConsole
 * @version 1.0
 */
public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, " + "alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";

	private Partita partita;
	private IO io;

	/**
	 * Crea DiaDia, e definisce partita.
	 */
	public DiaDia(IO io) {
		this.partita = new Partita();
        this.io = io;
	}

	/**
	 * Iteratore di DiaDia, legge input dal giocatore
	 * e li manda a analizzare.
	 */
	public void gioca() {
		String istruzione;

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);

		do
			istruzione = io.leggiRiga();
		while (!processaIstruzione(istruzione));

	}

	/**
	 * Processa una istruzione
	 *
	 * @param istruzione ovvero una stringa contenente il comando da eseguire
	 * @return true se l'istruzione e' eseguita e il gioco continua, false
	 *         altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiFisarmonica();
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita);
		if (this.partita.vinta())
			System.out.println("Hai vinto!");
		if (this.partita.isFinita()) // giocatoreIsVivo() capire...
			System.out.println("Hai esaurito i CFU...");

		return this.partita.isFinita();

	}

	// implementazioni dei comandi dell'utente:

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra e ne stampa il
	 * nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if (direzione == null) {
			io.mostraMessaggio("Dove vuoi andare ?");
		}
		else {
			Stanza prossimaStanza = null;
			prossimaStanza = this.partita.getLabirinto().getStanzaAttuale().getStanzaAdiacente(direzione);
			if (prossimaStanza == null)
				io.mostraMessaggio("Direzione inesistente");
			else {
				this.partita.getLabirinto().setStanzaAttuale(prossimaStanza);
				int cfu = this.partita.getGiocatore().getCfu();
				this.partita.getGiocatore().setCfu(--cfu);
			}
		}
		io.mostraMessaggio(partita.getLabirinto().getStanzaAttuale().getDescrizione());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		io.mostraMessaggio("Grazie di aver giocato!"); // si desidera smettere
	}

	/**
	 * Prende un attrezzo dal ambiente e lo inserisce nella borsa del giocatore.
	 *
	 * @param nomeAttrezzo è la stringa utile a cercare l'attrezzo
	 */
	private void prendi(String nomeAttrezzo) {
		if(this.partita.getLabirinto().getStanzaAttuale().getNumAttrezzi() ==0) {
			io.mostraMessaggio("Non sono presenti attrezzi nella stanza");
			return;
		}

		if(nomeAttrezzo==null){
			io.mostraMessaggio("cosa vuoi prendere?");
		}
		else {
			Attrezzo attrezzoPreso= null;
			attrezzoPreso=this.partita.getLabirinto().getStanzaAttuale().getAttrezzo(nomeAttrezzo);  //prende l'attrezzo dalla stanza
			if(attrezzoPreso==null)
				io.mostraMessaggio("oggetto non presente nella stanza");

			else {
				this.partita.getGiocatore().addAttrezzoToBorsa(attrezzoPreso);	//aggiunge l'attrezzo alla borsa
				this.partita.getLabirinto().getStanzaAttuale().removeAttrezzo(attrezzoPreso);	//rimuove da stanza
				io.mostraMessaggio("Oggetto preso");
			}
		}

		io.mostraMessaggio(partita.getLabirinto().getStanzaAttuale().getDescrizioneAttrezzi());

	}

	/**
	 * Posa un attrezzo dalla borsa del giocatore e lo inserisce nel ambiente.
	 *
	 * @param nomeAttrezzo è la stringa utile a cercare l'attrezzo
	 */
	private void posa (String nomeAttrezzo) {
		if(this.partita.getGiocatore().getBorsa().getNumAttrezzi()==0) {
			io.mostraMessaggio("Non sono presenti oggetti nella borsa");
			return;
		}

		if(nomeAttrezzo==null) {
			io.mostraMessaggio("Cosa vuoi posare?");
		}
		else {
			Attrezzo attrezzoDaPosare= null;
			attrezzoDaPosare= this.partita.getGiocatore().removeAttrezzoDaBorsa(nomeAttrezzo);		//rimuouve attrezzo
			if(attrezzoDaPosare==null) {
				io.mostraMessaggio("Attrezzo non presente nella borsa");
			}
			else {
				this.partita.getLabirinto().getStanzaAttuale().addAttrezzo(attrezzoDaPosare);	//aggiunge l'attrezzo alla stanza
			}
		}
		io.mostraMessaggio(partita.getGiocatore().getBorsa().toString());
	}

	public static void main(String[] argc) {
        IO io = new IOConsole();
        DiaDia gioco = new DiaDia(io);
		gioco.gioca();

	}
}