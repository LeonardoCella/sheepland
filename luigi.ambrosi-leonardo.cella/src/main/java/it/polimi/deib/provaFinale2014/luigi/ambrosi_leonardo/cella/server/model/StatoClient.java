package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import java.io.Serializable;

/**
 * It is the image of the consistent state that is sent every times to the
 * clients.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class StatoClient implements Serializable {
	// è lo stato che passerò al client dopo ogni mossa per vedere le modifiche

	private static final long serialVersionUID = 1L;

	private String[] pecoreRegioni;
	// in posizione n-esima c'è il numero di pecore nella regione n-esima

	private int posizioneNera;
	// contiene il codice della regione in cui c'è la pecora nera

	private int[] stradeRecinti;
	// in posizione n-esima c'è 0 se la strada è libera, 1 se c'è un recinto, 2
	// se c'è un
	// recinto finale

	private int[] stradePastori;
	// in posizione n-esima c'è 0 se la strada è libera da pastori, oppure
	// l'intero che
	// corrisponde al colore del pastore che la occupa

	private int[] costoTessera;
	// in posizione n-esima contiene il costo attuale della

	// tessera di tipo n-esimo disponibile

	private int[] soldiGiocatori;

	private int posizioneLupo;

	public StatoClient() {
		this.pecoreRegioni = new String[18];
		this.stradeRecinti = new int[42];
		this.stradePastori = new int[42];
		this.costoTessera = new int[6];
		this.posizioneNera = 0;
		this.posizioneLupo = 0;
		this.soldiGiocatori = new int[6];
		for (int i = 0; i < 6; i++) {
			this.costoTessera[i] = 0;
			// all'inizio tutte le prime tessere costano 0
		}
	}

	public String[] getOviniRegioni() {
		return this.pecoreRegioni;
	}

	/**
	 * It is used by clients to print the photo of the state.
	 * 
	 * @param indiceGiocatore
	 *            it is the number of the player that will print the photo.
	 */
	public String toString(int indiceGiocatore) {
		String msg = null, ritorno = new String("");
		int[] temp = new int[3];
		ritorno += "\n\nEcco lo stato del gioco:";
		ritorno += "\n\nI tuoi soldi restanti sono: "
				+ this.soldiGiocatori[indiceGiocatore];
		ritorno += "\n\nSITUAZIONE DELLE PECORE:";
		for (int i = 0; i < 18; i++) {
			// stampo le pecore
			temp[0] = Integer.parseInt(this.pecoreRegioni[i].split(":")[0]);
			temp[1] = Integer.parseInt(this.pecoreRegioni[i].split(":")[1]);
			temp[2] = Integer.parseInt(this.pecoreRegioni[i].split(":")[2]);

			ritorno += "\nNumero pecore,montoni,agnelli in regione " + i + "="
					+ temp[0] + "," + temp[1] + "," + temp[2];
		}

		ritorno += "\n\nLa pecora nera è nella regione " + this.posizioneNera;
		// stampo la nera
		ritorno += "\n\nIl lupo è nella regione " + this.posizioneLupo;
		// stampo il lupo
		ritorno += "\n\nSITUAZIONE DEI RECINTI:";

		for (int i = 0; i < 42; i++) {
			// stampo le strade
			switch (this.stradeRecinti[i]) {
			case 0: {
				msg = "libera da recinti";
				break;
			}
			case 1: {
				msg = "occupata da un recinto";
				break;
			}
			case 2: {
				msg = "occupata da un recinto finale";
				break;
			}
			default: {
				break;
			}
			}
			ritorno += "\nLa strada numero " + i + " e " + msg;
		}

		ritorno += "\n\nPOSIZIONE DEI PASTORI:";
		for (int i = 0; i < 42; i++) {
			// stampo le posizioni dei pastori
			if (this.stradePastori[i] != 0) {
				ritorno += "\nIl pastore "
						+ Costanti.getColore(this.stradePastori[i])
						+ " si trova sulla strada " + i;
			}
		}

		ritorno += "\n\nCOSTO DELLE TESSERE";
		for (int i = 0; i < 6; i++) {
			ritorno += "\nIl costo della prossima tessera di tipo "
					+ Costanti.getTipoTerreno(i) + " e " + this.costoTessera[i];
		}

		return ritorno;
	}

	public String[] getPecoreRegioni() {
		return this.pecoreRegioni;
	}

	public int getPosizioneNera() {
		return this.posizioneNera;
	}

	public int[] getStradeRecinti() {
		return this.stradeRecinti;
	}

	public int[] getStradePastori() {
		return this.stradePastori;
	}

	public int[] getCostoTessera() {
		return this.costoTessera;
	}

	public int[] getSoldiGiocatori() {
		return this.soldiGiocatori;
	}

	public int getPosizioneLupo() {
		return this.posizioneLupo;
	}

	public void setPosizioneNera(int posizioneNera) {
		this.posizioneNera = posizioneNera;
	}

	public void setPosizioneLupo(int posizioneLupo) {
		this.posizioneLupo = posizioneLupo;
	}
}
