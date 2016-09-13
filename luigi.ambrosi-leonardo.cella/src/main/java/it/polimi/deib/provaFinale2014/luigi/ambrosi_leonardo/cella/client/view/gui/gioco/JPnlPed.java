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

public class JPnlPed extends JPanel {
	private static final long serialVersionUID = 1L;
	private Dimension d = new Dimension(100, 40);
	private BufferedImage sfondo;
	private String coloreP;

	public JPnlPed(String colore) {
		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);
		this.setMaximumSize(this.d);
		this.setOpaque(false);

		this.coloreP = colore;
		
		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/p" + colore
					+ ".png"));
			repaint();
		} catch (IOException e) {
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
				int indice = 0;
				synchronized (Buffer.getBuffer().getTknPedina()) {
					while (Buffer.getBuffer().checkPedina() != -1) {
						try {
							Buffer.getBuffer().getTknPedina().wait();
						} catch (InterruptedException e) {
							MyLogger.gestisci("info", e);
						}
					}
					
					if("ROSSO".equals(coloreP)) indice = 1;
					if("GIALLO".equals(coloreP)) indice = 2;
					if("VERDE".equals(coloreP)) indice = 3;
					if("ARANCIONE".equals(coloreP)) indice = 4 ;
					if("NERO".equals(coloreP)) indice =5 ;
					if("BLU".equals(coloreP)) indice = 6;
					
					Buffer.getBuffer().setPedina(indice);
					Buffer.getBuffer().getTknPedina().notify();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
	}

}
