package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * It is the abstract class that is extend by JPnlPastore and Ovino. It is used
 * to create the move MossaSpostamento.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public abstract class Soggetto {

	public abstract boolean muovi(Luogo destinazione);
}
