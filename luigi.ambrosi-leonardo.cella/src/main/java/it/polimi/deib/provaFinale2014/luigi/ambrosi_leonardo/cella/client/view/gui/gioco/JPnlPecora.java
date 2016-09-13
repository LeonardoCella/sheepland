package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JPnlPecora extends JPanel {
	private static final long serialVersionUID = 1L;
	private Dimension d = new Dimension(30, 30);
	private BufferedImage sfondo;
	private JTextField label = new JTextField("1");

	public JPnlPecora(){
		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);
		this.setMaximumSize(this.d);
		this.add(label);
		this.setOpaque(false);
		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/pecora.png"));
			repaint();
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
		}
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void setTesto(int num){
		label.setText(""+num);
	}
}
