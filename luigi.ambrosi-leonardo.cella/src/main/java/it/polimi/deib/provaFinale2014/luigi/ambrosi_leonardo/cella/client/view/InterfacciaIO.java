package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the common interface used for the command line and the gui.
 */
public interface InterfacciaIO {

	void scrivi(String string);

	int leggiSceltaMossa();

	int leggiStrada();

	int leggiNeraSINO();

	int leggiTipoTess();

	int leggiRegione();

	void fuoriOrdine(String string);

	void scriviIndice(int indiceNellaPartita);

	void scriviTessIn(String tipoTerreno);

	void colPast(int colorePastore1, int tipo);

	int leggiPosizionamento();

	void errorePosizionamento(String string);

	// 1-Posizionamento
	void attesa(int tipo);

	String leggiUser();

	String leggiIP();

	int leggiSceltaConn();

	void avviso();

	void riscontro();

	void riconnect();

	int leggiIntero();

	void aggiornaStato(StatoClient stato, int indiceNellaPartita);

	void win(int valore);

	void lose(int corpo, int valore);

	void scriviMessaggioServizio(MessaggioServizio messaggioServ);

	void erroreMossa(MessaggioServizio messaggioServ);

	void fineTurno(String string);

	void selezionaPastore(int colorePastore1, int colorePastore2);

	int leggiPastore();

	void reset();

	void resetStrada();

}
