package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Finestra;

public class MainDiProva {

	public static void main(String[] args){
		Finestra f = new Finestra() ;
		Step2 prova = new Step2(true);
		f.add(prova);
		f.abilita();
	}
}
