package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.EccezioneNonAdiacenza;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Luogo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.util.List;

/**
 * It is the class that translates the messages received by clients into moves.
 * 
 * @author Luigi Ambrosi,Leonardo Cella
 */
public class TraduttoreMossa {

	private Partita partita;

	public TraduttoreMossa(Partita partitaCorrente) {
		this.partita = partitaCorrente;
	}

	/**
	 * @param m
	 *            is the message received by a client.
	 * @return an object Mossa if it can be created by the message, otherwise
	 *         null.
	 */
	public Mossa traduciMessaggioMossa(MessaggioMossa m) {
		int tipoMossa = m.getTipoMossa();
		int soggetto = m.getSoggetto();
		int obiettivo = m.getObiettivo();
		int muoviNera = m.getMuoviNera();

		Pastore pastore;
		Strada strada;
		Soggetto esecutore;
		Luogo luogo;

		Mossa mossa;

		Regione regione;
		switch (tipoMossa) {

		// crea una mossa posizionamento iniziale pastore
		case 0: {
			// Il cast viene fatto in base al tipodinamico confermato dalle
			// funzioni
			pastore = (Pastore) trovaPastore(soggetto);

			strada = (Strada) trovaStrada(obiettivo);
			if ((pastore == null) || (strada == null)) {
				return null;
			} else {
				mossa = new MossaPosizionamento(pastore, strada, partita);
				return mossa;
			}
		}

		// crea una mossa spostamento pastore
		case 1: {
			// non voglio i cast, perché mossa spostamento richiede i tipi padre
			// Soggetto e Luogo
			esecutore = trovaPastore(soggetto);
			luogo = trovaStrada(obiettivo);
			if ((esecutore == null) || (luogo == null)) {
				return null;
			} else {
				mossa = new MossaSpostamento(esecutore, luogo, partita);
				return mossa;
			}
		}

		// crea una mossa spostamento pecora
		case 2: {
			esecutore = trovaPecora(soggetto, obiettivo, muoviNera);
			luogo = trovaRegione(obiettivo);
			if ((esecutore == null) || (luogo == null)) {
				return null;
			} else {
				mossa = new MossaSpostamento(esecutore, luogo, partita);
				return mossa;
			}
		}

		// crea una mossa acquisto terreno
		case 3: {
			pastore = (Pastore) trovaPastore(soggetto);
			if (pastore == null) {
				return null;
			} else {
				mossa = new MossaAcquisto(pastore, obiettivo, partita);
				return mossa;
			}
		}

		// crea una mossa accoppiamento
		case 4: {
			pastore = (Pastore) trovaPastore(soggetto);
			regione = (Regione) trovaRegione(obiettivo);
			if ((pastore == null) || (regione == null)) {
				return null;
			} else {
				mossa = new MossaAccoppiamento(pastore, regione, this.partita);
				return mossa;
			}

		}

		// crea una mossa abbattimento
		case 5: {
			pastore = (Pastore) trovaPastore(soggetto);
			regione = (Regione) trovaRegione(obiettivo);
			if ((pastore == null) || (regione == null)) {
				return null;
			} else {
				mossa = new MossaAbbattimento(pastore, regione, this.partita);
				return mossa;
			}
		}
		default: {
			break;
		}
		}

		return null;
	}

	// trova il pastore del colore ricercato tra tutti i pastori presenti
	/**
	 * @param colore
	 *            it is the colour of the shepherd that I want to find
	 * @return the sheperd of that colour if it exists.Otherwise null.
	 */
	public Soggetto trovaPastore(int colore) {
		Soggetto risultato;
		List<Pastore> pastori;
		List<Giocatore> giocatori = this.partita.getStatoCorrente()
				.getGiocatori();
		int numGiocatori = giocatori.size();

		// ciclo su tutti i giocatori
		for (int i = 0; i < numGiocatori; i++) {
			// prendo i pastori di ogni giocatore
			pastori = giocatori.get(i).getPastori();
			// ciclo su tutti i pastori di quel giocatore
			for (int j = 0; j < pastori.size(); j++) {
				if (pastori.get(j).getColore() == colore) {
					risultato = pastori.get(j);
					// il tipo statico è soggetto, quello
					// dinamico è pastore
					return risultato;
				}
			}
		}
		return null;
	}

	/**
	 * @param codice
	 *            the number of the street that I want to find.
	 * @return the street with the number codice.
	 */
	public Luogo trovaStrada(int codice) {
		Strada[] strade = this.partita.getMappa().getStrade();
		return strade[codice];
	}

	/**
	 * @param codice
	 *            the number of the region that I want to find.
	 * @return the region with that number.
	 */
	private Luogo trovaRegione(int codice) {
		Regione[] regioni = this.partita.getMappa().getRegioni();
		return regioni[codice];
	}

	/**
	 * @param colorePastore
	 *            the colour of the shepherd that is moving a sheep.
	 * @param regDest
	 *            the destination of the movement of the sheep.
	 * @param nera
	 *            if I am looking for the black sheep, nera is 1, otherwise it
	 *            is 0
	 * @return the sheep, only if there is almost one sheep in the region of
	 *         departure
	 */
	private Soggetto trovaPecora(int colorePastore, int regDest, int nera) {
		// nera = 1 se voglio muovere la pecora nera, nera =0
		// altrimentiPecoraNera pn;
		if (regDest == 18) {
			return null;
		}
		PecoraNera pn;
		int numPecore;
		Regione partenza, arrivo;

		// la regione IN cui muoverò la pecora (l'utente mi passa l'indice)
		arrivo = (Regione) trovaRegione(regDest);
		if (arrivo == partita.getMappa().getRegioni()[18]) {
			return null;
		}

		// trovo il pastore che compie l'azione
		Pastore p = (Pastore) trovaPastore(colorePastore);

		// trovo la strada su cui è il pastore(separa la regione di
		// partenza della pecora da quella di arrivo)
		Strada strada = p.getPosizione();

		// prendo tutte le pecore
		List<Ovino> gregge = this.partita.getStatoCorrente().getOvini();

		// trovo il numero di queste pecore
		numPecore = gregge.size();
		try {
			// trovo la regione DA cui muovere la pecora
			partenza = strada.getConfinante(arrivo);
		} catch (EccezioneNonAdiacenza e) {
			// significa che voglio spostare una pecora in una regione che non è
			// adiacente alla strada su cui è il pastore
			MyLogger.gestisci("Non adiacenza", e);
			return null;
		}

		// se l'utente vuole muovere la nera verifico che la nera sia nella
		// regione di partenza
		if (nera == 1) {
			pn = this.partita.getStatoCorrente().getNera();
			if (pn.getPosizione() == partenza) {
				return pn;
			} else {
				return null;
			}
		} else {
			// tra tutte le pecore prendo la prima che si trova nella regione di
			// partenza
			for (int i = 0; i < numPecore; i++) {
				if (gregge.get(i).getPosizione() == partenza) {
					return gregge.get(i);
				}
			}
		}
		// significa che non ci sono pecore da spostare dalla regione di
		// partenza
		return null;
	}
}
