package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PiePagina extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false;
	private Dimension d = new Dimension(800, 190);
	private JBanner avviso,conferma;
	private BoxDestro divDestro;
	
	public PiePagina(BoxDestro bd) {
		this.divDestro = bd;
		JButton login;
		this.setSize(800, 190);
		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);

		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/Prato.jpg"));
		} catch (IOException e) {
			this.errore = true;
			MyLogger.gestisci("loading image", e);
		}
		repaint();

		login = new JBtnConnect("login",bd);

		this.avviso = new JBanner("rosso");
		this.conferma = new JBanner("verde");

		this.add(login);
		this.add(avviso);
		this.add(conferma);
		avviso.setVisible(false);
		conferma.setVisible(false);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void setAvviso(String text){
		this.pulisci();
		this.avviso.setText(text);
		this.avviso.setVisible(true);
		repaint();
		return;
	}
	
	public void setRiscontro(String text){
		this.pulisci();
		this.conferma.setText(text);
		this.conferma.setVisible(true);
		return;
	}
	
	public void pulisci(){
		this.conferma.setVisible(false);
		this.avviso.setVisible(false);
	}
	
	public BoxDestro getDivdx(){
		return this.divDestro;
	}

}