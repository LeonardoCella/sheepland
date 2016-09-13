package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * It is an abstract class extended by Regione and Strada. It is used to
 * implement the metod muovi(Luogo destination) for JPnlPastore and Ovino.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public abstract class Luogo {
	// get adiacenze ritorna le strade se invocato su una regione, le regioni se
	// invocato su una strada

	public abstract Luogo[] getAdiacenze();

	public abstract int getCodice();
}