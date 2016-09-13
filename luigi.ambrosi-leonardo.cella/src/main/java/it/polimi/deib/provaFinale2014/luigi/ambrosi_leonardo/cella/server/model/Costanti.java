package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         The class with all the constants
 */
public class Costanti {

	private Costanti() {

	}

	/**
	 * --- TIPO TERRENO
	 * */

	public static final int GRANO = 0;
	public static final int MONTAGNA = 1;
	public static final int SABBIA = 2;
	public static final int FIUMI = 3;
	public static final int BOSCO = 4;
	public static final int PIANURA = 5;
	public static final int SHEEPSBURG = 6;

	/**
	 * COLORE
	 * */

	public static final int ROSSO = 1;
	public static final int GIALLO = 2;
	public static final int VERDE = 3;
	public static final int ARANCIONE = 4;
	public static final int NERO = 5;
	public static final int BLU = 6;
	public static final int[] COLORE = { 0, ROSSO, GIALLO, VERDE, ARANCIONE,
			NERO, BLU };

	public static final int POSIZIONAPASTORE = 0;
	public static final int SPOSTAPASTORE = 1;
	public static final int SPOSTAPECORA = 2;
	public static final int COMPRATESSERA = 3;
	public static final int ACCOPPIAMENTO = 4;
	public static final int ABBATTIMENTO = 5;
	/**
	 * --- MESSAGGIO SERVIZIO
	 * */
	// mod=0//---->ping e pong corpo è il progressivo che i due si scambiano

	// mod=1//----->messaggio tipo turno-----corpo=1 dammi la prima mossa, setta
	// turno a true
	// corpo=2 dammi la seconda mossa
	// corpo=3 dammi la terza mossa
	// corpo=4 il tuo turno è finito, setta turno a false

	/**
	 * MESSAGGIO MOSSA
	 * */

	public static final int UNCOLORE = 1;
	// colore------corpo=colore primo e unico pastore

	public static final int DUECOLORI = 3;
	// ---->setta 2 colori------corpo=colore primo pastore,destinatario=colore
	// secondo pastore

	public static final int DUEPASTORI = 4;
	// ---->partita a 2 giocatori

	public static final int UNPASTORE = 5;
	// ---->partita con piu di due giocatori

	public static final int MOSSANONVALIDA = 6;
	// --->la mossa non va bene

	public static final int MOSSAVALIDA = 7;
	// --->la mossa va bene

	/**
	 * 
	 * */

	public static final int SOCKET = 1;
	public static final int RMI = 2;

	public static String getColore(int i) {
		switch (i) {
		case 1: {
			return "ROSSO";

		}
		case 2: {
			return "GIALLO";

		}
		case 3: {
			return "VERDE";

		}
		case 4: {
			return "ARANCIONE";

		}
		case 5: {
			return "NERO";

		}
		case 6: {
			return "BLU";
		}
		default: {
			return null;
		}
		}
	}

	public static String getTipoTerreno(int i) {
		switch (i) {
		case 0: {
			return "GRANO";
		}
		case 1: {
			return "MONTAGNA";
		}
		case 2: {
			return "SABBIA";

		}
		case 3: {
			return "FIUMI";

		}
		case 4: {
			return "BOSCO";
		}
		case 5: {
			return "PIANURA";
		}
		case 6: {
			return "SHEEPSBURG";

		}
		default: {
			return null;
		}
		}
	}

}
