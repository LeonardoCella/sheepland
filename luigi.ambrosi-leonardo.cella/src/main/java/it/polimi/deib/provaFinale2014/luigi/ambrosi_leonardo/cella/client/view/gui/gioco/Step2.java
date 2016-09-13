package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;


import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Step2 extends JPanel {
	private static final long serialVersionUID = 1L;
	private Dimension d = new Dimension(800, 750);
	private DivPlanciaGioco plancia;
	private DivListaUtenti listaGiocatori;
	private DivComandi comandi;

	public Step2(boolean prova) {

		this.plancia = new DivPlanciaGioco(prova);
		this.listaGiocatori = new DivListaUtenti();
		this.comandi = new DivComandi();
		
		setMinimumSize(this.d);
		setMaximumSize(this.d);
		setPreferredSize(this.d);
		
		setLayout(new BorderLayout());

		this.add(comandi, BorderLayout.WEST);
		this.add(plancia, BorderLayout.CENTER);
		this.add(listaGiocatori, BorderLayout.EAST);
		
	}

	public void scrivi(String msg) {
		comandi.scrivi(msg);
	}

	public void scriviTessera(String tipoTerreno) {
		comandi.aggiornaDisplay(tipoTerreno);
	}

	public void coloraPastore(int colorePastore1,int tipo) {
		listaGiocatori.coloraPastore(colorePastore1);
		plancia.coloraPastore(colorePastore1,tipo);
	}

	public void aggiornaStato(StatoClient stato, int indiceNellaPartita) {
		plancia.aggiornaStato(stato,indiceNellaPartita);
		comandi.aggiornaStato(stato,indiceNellaPartita);
	}

	public void posiziona(int strada) {
		comandi.scrivi("Posizionato su " + strada);
		plancia.posiziona(strada);
	}

	public int getText() {
		return comandi.getText();
	}

	
}
