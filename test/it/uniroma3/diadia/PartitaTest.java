package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartitaTest {

	Partita partita;
	IO io;
	
	@BeforeEach
    void before(){
		@SuppressWarnings("unused")
		IO io = new IOConsole();
    }
	
	@BeforeEach
    void setUp(){
		partita= new Partita(io);
    }

	@Test
	void testSetVincente() {
		partita.getLabirinto().setStanzaAttuale(partita.getLabirinto().getStanzaFinale());
		assertEquals(true, partita.vinta());
	}
	
	@Test
	void testCFUZero() {
		partita.getGiocatore().setCfu(0);
		assertEquals(true, partita.isFinita());
	}
	
	@Test
	void testSetNullCorrente() {
		partita.getLabirinto().setStanzaAttuale(null);
		assertEquals(false, partita.vinta());
	}
	
	@Test
	void testSetFinita() {
		partita.setFinita();
		assertEquals(true, partita.isFinita());
	}
	
	@Test //controllo vittoria allo start.
    void testGetStanzaVincente() {
        assertEquals(false, partita.vinta());
    }
	

}
