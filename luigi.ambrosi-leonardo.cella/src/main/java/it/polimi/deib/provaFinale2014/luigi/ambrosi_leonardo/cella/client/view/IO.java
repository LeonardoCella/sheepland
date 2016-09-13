package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It implements all the method used for the command line view.
 * 
 */
public class IO implements InterfacciaIO {
	private BufferedReader in;

	public IO() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
	}

	public void scrivi(String msg) {
		System.out.println(msg);
		return;
	}

	public String leggiStringa() {
		String str = new String("");
		try {
			str = in.readLine();
		} catch (IOException e) {
			MyLogger.gestisci("lettura", e);
		}
		return str;
	}

	public String leggiUser() {
		return leggiStringa();
	}

	public String leggiIP() {
		return leggiStringa();
	}

	public int leggiIntero() {
		int numero = 0;
		try {
			numero = Integer.parseInt(in.readLine() + "");
		} catch (Exception e) {
			MyLogger.gestisci("IOErr", e);
			System.out
					.println("\n PER FAVORE, ASSICURATI DI INSERIRE UN NUMERO INTERO!");
			return leggiIntero();
		}

		return numero;
	}

	public void selezionaPastore(String string) {
		scrivi(string);
		return;
	}

	public int leggiSceltaMossa() {
		return leggiIntero();
	}

	public int leggiStrada() {
		return leggiIntero();
	}

	public int leggiNeraSINO() {
		return leggiIntero();
	}

	public int leggiRegione() {
		return leggiIntero();
	}

	public int leggiTipoTess() {
		return leggiIntero();
	}

	public void fuoriOrdine(String string) {
		scrivi(string);
		return;
	}

	public void scriviIndice(int indiceNellaPartita) {
		scrivi("SEI IL " + indiceNellaPartita + "° GIOCATORE");
		return;
	}

	public void scriviTessIn(String tipoTerreno) {
		scrivi("LA TUA TESSERA INIZIALE E' DI TIPO: " + tipoTerreno);
		return;
	}

	public void colPast(int colorePastore1, int tipo) {
		if (tipo == 0) {
			scrivi("IL TUO PASTORE E': " + Costanti.getColore(colorePastore1));
		} else {
			if (tipo == 1) {
				scrivi("IL TUO PRIMO PASTORE E': "
						+ Costanti.getColore(colorePastore1));
			} else {
				scrivi("IL TUO SECONDO PASTORE E': "
						+ Costanti.getColore(colorePastore1));
			}
		}
		return;
	}

	public int leggiPosizionamento() {
		return leggiIntero();
	}

	public void errorePosizionamento(String string) {
		scrivi(string);
		return;
	}

	public void attesa(int tipo) {
		if (tipo == 1) {
			scrivi("Attendi che tutti gli altri giocatori posizionino i propri pastori");
		}

	}

	public int leggiSceltaConn() {
		return leggiIntero();
	}

	public void avviso() {
		scrivi("Verifica che il server sia disponibile oppure l'username scelto è già attivo!");
		return;
	}

	public void riscontro() {
		scrivi("Username Valido, attendi gli altri giocatori...");
		return;
	}

	public void riconnect() {
		scrivi("Bentornato, attendi di essere reindirizzato alla tua partita");
		return;
	}

	public void aggiornaStato(StatoClient stato, int indiceNellaPartita) {
		scrivi(stato.toString(indiceNellaPartita));
	}

	public void win(int valore) {
		scrivi("IL TUO PUNTEGGIO E': " + valore);
	}

	public void lose(int corpo, int valore) {
		scrivi("HAI PERSO, IL VINCITORE E' IL GIOCATORE" + corpo
				+ "IL TUO PUNTEGGIO E' DI: " + valore);
	}

	public void scriviMessaggioServizio(MessaggioServizio messaggioServ) {
		scrivi(messaggioServ.toString());
	}

	public void erroreMossa(MessaggioServizio messaggioServ) {
		scrivi("LA MOSSA NON E' VALIDA" + messaggioServ.toString());
	}

	public void fineTurno(String string) {
		scrivi("IL TUO TURNO E' FINITO!");
	}

	public void selezionaPastore(int colorePastore1, int colorePastore2) {
		scrivi("\nSELEZIONA CON QUALE PASTORE EFFETTUARE LE MOSSE:");
		scrivi("1 PER IL TUO PASTORE " + Costanti.getColore(colorePastore1));
		scrivi("2 PER IL TUO PASTORE " + Costanti.getColore(colorePastore2));
	}

	public int leggiPastore() {
		return leggiIntero();
	}

	public void reset() {
		return;
	}

	public void resetStrada() {
		reset();
	}

}
