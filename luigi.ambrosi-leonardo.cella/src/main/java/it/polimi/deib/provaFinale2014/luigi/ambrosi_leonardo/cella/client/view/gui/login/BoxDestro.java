package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoxDestro extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false;
	private Dimension d = new Dimension(300, 410);
	private JTextField txt, mod, ip;

	private Color colore = new Color(0xFFCC99);
	private JLabel modC,nome,ipS;
	
	public BoxDestro() {

		this.setMinimumSize(this.d);
		this.setPreferredSize(this.d);
		this.setLayout(new BorderLayout());

		this.setBackground(this.colore);

		
		nome = new JLabel();
		nome.setText("    Username :");

		txt = new JTextField();
		txt.setText("Inserisci qui il tuo username");
		
		modC = new JLabel();
		modC.setText("    Modalità di comunicazione :");

		mod = new JTextField();
		mod.setText("Inserisci 1 per Socket , 2 per RMI");
		
		ipS = new JLabel();
		ipS.setText("    IP Server :");

		ip = new JTextField();
		ip.setText("Inserisci l'indirizzo del server");
		
		setLayout(new GridLayout(6, 1));
		
		this.add(nome);
		this.add(txt);
		
		this.add(modC);
		this.add(mod);
		
		this.add(ipS);
		this.add(ip);
		this.repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public String getNome() {
		return txt.getText();
	}

	public int getMod() {
		int i;
		try {
			i = Integer.parseInt(mod.getText());
		} catch (Exception e) {
			MyLogger.gestisci("parseInt", e);
			i = 1;
		}
		return i;
	}

	public String getIP() {
		return ip.getText();
	}

}