package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Finestra extends JFrame {
	private static final long serialVersionUID = 1L;
	private int x, y;
	private int width, height;
	private Dimension minSiz, maxSiz, screenSize;

	public Finestra() {
		super("Sheepland Ambrosi_Cella");
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.x = (this.screenSize.width / 2) - (800 / 2);
		this.y = (this.screenSize.height / 2) - (750 / 2);
		this.width = 800;
		this.height = 750;
		this.minSiz = new Dimension(this.width, this.height);
		this.maxSiz = new Dimension(this.width, this.height);
		this.setResizable(false);

		JFrame.setDefaultLookAndFeelDecorated(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	public int getWdt() {
		return this.width;
	}

	public int getHgt() {
		return this.height;
	}

	public void abilita() {
		this.setBounds(this.x, this.y, this.width, this.height);
		this.setMinimumSize(this.minSiz);
		this.setPreferredSize(this.minSiz);
		this.setMaximumSize(this.maxSiz);
		this.setName("Finestra");
		this.setVisible(true);
	}

}
