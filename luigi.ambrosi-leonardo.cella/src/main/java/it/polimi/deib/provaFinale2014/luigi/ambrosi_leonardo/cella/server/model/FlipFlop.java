package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * Struct used in the server
 */
public class FlipFlop {
	private String username;
	private int ultimaPartita;
	private boolean stato;

	public FlipFlop(int codicePartita, String user, boolean attivo) {
		this.username = user;
		this.ultimaPartita = codicePartita;
		this.stato = attivo;
	}

	public int getPartita() {
		return this.ultimaPartita;
	}

	public void setPartita(int num) {
		this.ultimaPartita = num;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean getStato() {
		return this.stato;
	}

	public void setStato(boolean nuovoStato) {
		this.stato = nuovoStato;
	}
}
