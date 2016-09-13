package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JBanner extends JPanel {


	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private JLabel testo;
	
	public JBanner(String colore){
		this.setPreferredSize(new Dimension(800,70));
		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/banner"+colore+".png"));
			repaint();
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void setText(String txt){
		this.testo = new JLabel(txt);
		this.add(testo);
		repaint();
	}
}
