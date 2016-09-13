package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Buffer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DivComandi extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false;
	private Dimension d = new Dimension(150, 750);
	private JMossa[] btnMossa = new JMossa[5];
	private Stato display;
	private JTextArea schermo;

	public DivComandi() {
		this.setMinimumSize(this.d);
		this.setMaximumSize(this.d);
		this.setPreferredSize(this.d);

		try {
			this.sfondo = ImageIO.read(new File(
					"./media/immagini/bckComando.png"));
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
		}
		display = new Stato();
		schermo = new JTextArea();

		repaint();

		btnMossa[0] = new JMossa("mossa1", 1);
		btnMossa[1] = new JMossa("mossa2", 2);
		btnMossa[2] = new JMossa("mossa3", 3);
		btnMossa[3] = new JMossa("mossa4", 4);
		btnMossa[4] = new JMossa("mossa5", 5);

		this.add(btnMossa[0]);
		this.add(btnMossa[1]);
		this.add(btnMossa[2]);
		this.add(btnMossa[3]);
		this.add(btnMossa[4]);

		this.add(display);

		schermo.setSize(new Dimension(140, 60));
		schermo.setPreferredSize(new Dimension(140, 60));
		schermo.setMinimumSize(new Dimension(140, 60));
		schermo.setLineWrap(true);
		schermo.setText("Da qui parlerò con te");
		this.add(schermo);
		repaint();

		schermo.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
				return;
			}

			public void keyReleased(KeyEvent arg0) {
				return;
			}

			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					synchronized (Buffer.getBuffer().getTknDisplay()) {
						while (! "".equals(Buffer.getBuffer().checkDisplay())) {
							try {
								Buffer.getBuffer().getTknMossa().wait();
							} catch (InterruptedException e) {
								MyLogger.gestisci("info", e);
							}
						}
						Buffer.getBuffer().settaDisplay(schermo.getText());
						Buffer.getBuffer().getTknDisplay().notify();
					}
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

	public void scrivi(String msg) {
		schermo.setText(msg);
		repaint();
		return;
	}

	public void aggiornaDisplay(String tipoTerreno) {
		display.aggiorna(tipoTerreno);
	}

	public void aggiornaStato(StatoClient stato, int indice) {
		display.aggiornaStato(stato, indice);

	}

	public int getText() {
		int risposta;
		try {
			risposta = Integer.parseInt(schermo.getText());
		} catch (Exception e) {
			risposta = -1;
			MyLogger.gestisci("input schermo", e);
		}
		return risposta;
	}

}
