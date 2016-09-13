package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoxSinistro extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false;
	private Dimension d = new Dimension(500, 410);

	public BoxSinistro() {
		setMinimumSize(this.d);
		setPreferredSize(this.d);

		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/Sinistro.jpg"));
		} catch (IOException e) {
			this.errore = true;
			MyLogger.gestisci("loading image", e);
		}
		repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

}
