package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class for the shepherd.
 */
public class Pastore extends Soggetto {

	private final int colore;
	private Strada posizioneCorrente;
	private Giocatore giocatoreRiferimento;

	public Pastore(int col) {
		this.posizioneCorrente = null;
		this.giocatoreRiferimento = null;
		this.colore = col;
	}

	public Giocatore getGiocatore() {
		return this.giocatoreRiferimento;
	}

	public void setGiocatore(Giocatore g) {
		this.giocatoreRiferimento = g;
	}

	public boolean setPosizione(Strada s) {
		this.posizioneCorrente = s;
		return true;
	}

	public void azzeraPosizione() {
		this.posizioneCorrente = null;
	}

	public Strada getPosizione() {
		return this.posizioneCorrente;
	}

	public int getColore() {
		return this.colore;
	}

	/**
	 * It changes the current position of the shepherd.
	 * 
	 * @param luogo
	 *            the new position of the shepherd.
	 * @return always true.
	 */
	public boolean muovi(Luogo luogo) {
		Strada strada = (Strada) luogo;
		this.posizioneCorrente = strada;
		return true;
	}

	@Override
	public String toString() {
		String ret = new String();

		ret += "ColorePastore" + Costanti.getColore(this.colore);
		return ret;
	}

}
