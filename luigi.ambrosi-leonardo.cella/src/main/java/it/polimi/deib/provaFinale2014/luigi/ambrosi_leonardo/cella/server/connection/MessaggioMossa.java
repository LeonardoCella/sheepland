package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class of the message sent by clients to the server to make
 *         a move.
 * 
 *         mod - tipoMossa =0-->posizionamento iniziale pastore ; 1-->sposta
 *         pastore 2-->sposta pecora ; 3-->compra tessera ; 4-->accoppiamento ;
 *         5-->abbattimento
 */
public class MessaggioMossa extends Messaggio {
	private int muoviNera;
	// muoviNera=1----->voglio muovere la pecora nera

	private static final long serialVersionUID = 1L;

	public MessaggioMossa(int a, int b, int c) {
		
		this.mod = a;

		// corpo - soggetto
		// è il colore del pastore
		this.corpo = b;

		// luogo destinazione, tipo terreno da comprare , regione accopp/abb.
		this.destinatario = c;
	}

	public int getTipoMossa() {
		return this.mod;
	}

	public int getSoggetto() {
		return this.corpo;
	}

	public int getObiettivo() {
		return this.destinatario;
	}

	public int getMuoviNera() {
		return this.muoviNera;
	}

	public void setMuoviNera(int i) {
		this.muoviNera = i;
	}

	@Override
	public String toString() {

		String[] ritorno = new String[4];

		ritorno[0] = "MsgMossa: posizionamento iniziale pastore "
				+ Costanti.getColore(this.corpo) + " in strada "
				+ this.destinatario;

		ritorno[1] = "MsgMossa: spostamento pastore "
				+ Costanti.getColore(this.corpo) + " in regione "
				+ this.destinatario;

		ritorno[2] = "MsgMossa: spostamento pecora da parte di pastore "
				+ Costanti.getColore(this.corpo) + " in regione "
				+ this.destinatario;

		ritorno[3] = "MsgMossa: acquisto tessera di tipo " + this.destinatario
				+ " da parte del pastore " + Costanti.getColore(this.corpo);

		return ritorno[this.mod];

	}
}
