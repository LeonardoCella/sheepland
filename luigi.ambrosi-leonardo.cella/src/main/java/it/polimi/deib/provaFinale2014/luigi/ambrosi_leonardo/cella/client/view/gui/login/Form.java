package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Form extends JPanel {
	private static final long serialVersionUID = 1L;

	private Dimension minSiz, maxSiz;
	private BoxDestro divDx;
	private PiePagina divBtn;

	public Form(int x, int y, int width, int height) {
		super();

		this.minSiz = new Dimension(width, height);
		this.maxSiz = new Dimension(width, height);

		this.setLayout(new BorderLayout());

		this.setBounds(x, y, width, height);
		this.setMinimumSize(this.minSiz);
		this.setMaximumSize(this.maxSiz);

		this.divDx = new BoxDestro();
		this.divBtn = new PiePagina(divDx);

		this.add(new Intestazione(), BorderLayout.NORTH);
		this.add(new BoxSinistro(), BorderLayout.WEST);
		this.add(this.divDx, BorderLayout.EAST);
		this.add(this.divBtn, BorderLayout.SOUTH);
		this.add(pBuco(), BorderLayout.CENTER);

		this.setVisible(true);
	}

	private JPanel pBuco() {
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(0, 0));
		return p;
	}

	public BoxDestro getDivDx(){
		return this.divDx;
	}
	
	public PiePagina getDivBtn(){
		return this.divBtn;
	}

}
