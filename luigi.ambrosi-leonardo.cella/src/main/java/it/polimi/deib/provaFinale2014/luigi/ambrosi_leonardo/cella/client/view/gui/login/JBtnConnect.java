package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Buffer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class JBtnConnect extends JButton {
	private static final long serialVersionUID = 1L;
	private Dimension d = new Dimension(100, 40);
	private BufferedImage sfondo;
	private String tipoConn;
	private BoxDestro formCollegato;

	public JBtnConnect(String tipoConnessione, BoxDestro formIniziale) {
		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);
		this.setMaximumSize(this.d);
		this.setOpaque(false);
		this.tipoConn = tipoConnessione;
		this.formCollegato = formIniziale;

		try {
			this.sfondo = ImageIO.read(new File("./media/immagini/login.png"));
			repaint();
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
		}

		repaint();

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				synchronized (Buffer.getBuffer().getTknIP()) {
					while (Buffer.getBuffer().checkIP() != null) {
						try {
							Buffer.getBuffer().getTknIP().wait();
						} catch (InterruptedException e) {
							MyLogger.gestisci("info", e);
						}
					}
					
					Buffer.getBuffer().disIP( formCollegato.getIP() );
					Buffer.getBuffer().getTknIP().notify();
				}

				synchronized (Buffer.getBuffer().getTknMod()) {
					while (Buffer.getBuffer().checkMod() != 0) {
						try {
							Buffer.getBuffer().getTknMod().wait();
						} catch (InterruptedException e) {
							MyLogger.gestisci("info", e);
						}
					}
					
					Buffer.getBuffer().disMod( formCollegato.getMod() );
					Buffer.getBuffer().getTknMod().notify();
				}
				
				synchronized (Buffer.getBuffer().getTknUser()) {
					while (Buffer.getBuffer().checkU() != null) {
						try {
							Buffer.getBuffer().getTknUser().wait();
						} catch (InterruptedException e) {
							MyLogger.gestisci("info", e);
						}
					}
					
					Buffer.getBuffer().disUser( formCollegato.getNome() );
					Buffer.getBuffer().getTknUser().notify();
				}
				
				return;
			}

		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
	}

	public String getTipoConn() {
		return this.tipoConn;
	}
}
