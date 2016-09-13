package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DivListaUtenti extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false;
	private Dimension d = new Dimension(100, 750);
	private Utente blu, arancione, nero, rosso, verde, giallo;
	private Map<String,Utente> dizionario;

	public DivListaUtenti() {
		setMinimumSize(this.d);
		setMaximumSize(this.d);
		setPreferredSize(this.d);
		dizionario = new HashMap<String,Utente>();

		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/Utenti.jpg"));
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
		}
		repaint();
		
		blu = new Utente("blu.jpg");
		arancione = new Utente("arancione.jpg");
		nero = new Utente("nero.jpg");
		rosso = new Utente("rosso.jpg");
		verde = new Utente("verde.jpg");
		giallo = new Utente("giallo.jpg");
		
		this.add(blu);
		dizionario.put("BLU", blu);
		
		this.add(arancione);
		dizionario.put("ARANCIONE", arancione);
		
		this.add(nero);
		dizionario.put("NERO", nero);
		
		this.add(rosso);
		dizionario.put("ROSSO", rosso);
		
		this.add(verde);
		dizionario.put("VERDE", verde);
		
		this.add(giallo);
		dizionario.put("GIALLO", giallo);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void coloraPastore(int colorePastore1) {
		Utente mioPastore = dizionario.get(Costanti.getColore(colorePastore1));
		mioPastore.repaint();
	}
}
