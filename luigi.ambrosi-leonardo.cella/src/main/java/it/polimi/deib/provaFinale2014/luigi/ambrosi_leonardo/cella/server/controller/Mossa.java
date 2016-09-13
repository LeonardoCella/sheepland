package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;

/**
 * It is an abstract class that is extend by every kind of other moves. It is
 * used to execute every moves with the metod eseguiMossa().
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public abstract class Mossa {

	// classe padre dei vari tipi di mossa
	public abstract boolean eseguiMossa();

	public abstract Soggetto getSoggetto();

}
