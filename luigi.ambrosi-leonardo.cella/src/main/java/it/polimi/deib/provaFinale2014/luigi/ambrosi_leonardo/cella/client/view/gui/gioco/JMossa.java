package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Buffer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JMossa extends JPanel {

	private static final long serialVersionUID = 1L;
	private Dimension d = new Dimension(130, 90);
	private BufferedImage sfondo;
	private boolean errore = false;
	private int indiceMossa;

	public JMossa(String path,int tipoMossa) {

		this.indiceMossa = tipoMossa;
		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);
		this.setMaximumSize(this.d);
		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/" + path + ".png"));
		} catch (IOException e) {
			this.errore = true;
			MyLogger.gestisci("loading image", e);
		}
		repaint();

		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				return;
			}
			
			public void mousePressed(MouseEvent arg0) {
				return;
			}
			
			public void mouseExited(MouseEvent arg0) {
				return;
			}
			
			public void mouseEntered(MouseEvent arg0) {
				return;
			}
			
			public void mouseClicked(MouseEvent arg0) {
				synchronized (Buffer.getBuffer().getTknMossa()) {
					while (Buffer.getBuffer().checkMossa() != -1) {
						try {
							Buffer.getBuffer().getTknMossa().wait();
						} catch (InterruptedException e) {
							MyLogger.gestisci("info", e);
						}
					}
					Buffer.getBuffer().settaMossa(indiceMossa);
					System.out.println("Stai svegliando" + indiceMossa);
					Buffer.getBuffer().getTknMossa().notify();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}
}