package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Buffer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DivPlanciaGioco extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage sfondo;
	private boolean errore = false, posiz1 = false, posiz2 = false;
	private Dimension d = new Dimension(550, 750);
	private JPnlPecora[] gregge;
	private JPnlMontone[] montoni;
	private JPnlAgnello[] agnelli;
	private JPnlLupo lupo;
	private JPnlNera nera;
	private Point[] posPec = { new Point(75, 123), new Point(151, 130),
			new Point(310, 110), new Point(105, 290), new Point(197, 244),
			new Point(292, 214), new Point(395, 156), new Point(371, 261),
			new Point(445, 211), new Point(197, 377), new Point(287, 411),
			new Point(362, 366), new Point(464, 291), new Point(101, 435),
			new Point(189, 515), new Point(283, 574), new Point(363, 501),
			new Point(437, 443) }, posMon = { new Point(91, 178),
			new Point(207, 149), new Point(340, 90), new Point(132, 272),
			new Point(230, 255), new Point(314, 198), new Point(423, 135),
			new Point(360, 285), new Point(485, 206), new Point(212, 364),
			new Point(281, 454), new Point(370, 412), new Point(454, 323),
			new Point(96, 475), new Point(175, 560), new Point(259, 600),
			new Point(390, 555), new Point(483, 465) }, posLupi = {
			new Point(52, 171), new Point(206, 128), new Point(345, 57),
			new Point(120, 356), new Point(208, 291), new Point(279, 213),
			new Point(450, 114), new Point(412, 265), new Point(483, 196),
			new Point(221, 441), new Point(269, 467), new Point(406, 371),
			new Point(460, 281), new Point(143, 434), new Point(234, 542),
			new Point(203, 658), new Point(368, 601), new Point(449, 418),
			new Point(298, 306) }, posAgn = { new Point(90, 230),
			new Point(264, 149), new Point(403, 78), new Point(119, 340),
			new Point(240, 236), new Point(307, 192), new Point(422, 117),
			new Point(412, 249), new Point(507, 177), new Point(207, 429),
			new Point(277, 400), new Point(404, 356), new Point(513, 321),
			new Point(66, 435), new Point(129, 576), new Point(220, 629),
			new Point(366, 555), new Point(419, 477) }, posNere = {
			new Point(143, 222), new Point(182, 182), new Point(345, 149),
			new Point(86, 372), new Point(195, 285), new Point(316, 260),
			new Point(390, 196), new Point(348, 282), new Point(488, 184),
			new Point(197, 355), new Point(324, 443), new Point(357, 409),
			new Point(517, 290), new Point(148, 487), new Point(169, 526),
			new Point(248, 593), new Point(348, 533), new Point(502, 434),
			new Point(276, 320) }, posStrada = { new Point(285, 120),
			new Point(388, 123), new Point(141, 185), new Point(226, 215),
			new Point(282, 188), new Point(331, 175), new Point(364, 206),
			new Point(461, 180), new Point(93, 265), new Point(175, 235),
			new Point(270, 244), new Point(347, 255), new Point(400, 230),
			new Point(441, 245), new Point(496, 264), new Point(175, 295),
			new Point(248, 304), new Point(292, 275), new Point(332, 308),
			new Point(433, 300), new Point(210, 335), new Point(252, 359),
			new Point(337, 357), new Point(375, 335), new Point(434, 355),
			new Point(174, 363), new Point(299, 385), new Point(481, 399),
			new Point(134, 395), new Point(179, 434), new Point(261, 431),
			new Point(336, 418), new Point(414, 425), new Point(213, 483),
			new Point(256, 500), new Point(337, 495), new Point(369, 468),
			new Point(419, 515), new Point(141, 546), new Point(294, 527),
			new Point(213, 601), new Point(333, 578) };
	private JPnlPed[] jPedine;
	private JPnlRecinto[] jRecinti,jFinali;
	private int col1, col2;

	public DivPlanciaGioco(final boolean prova) {

		this.setMinimumSize(this.d);
		this.setMaximumSize(this.d);
		this.setPreferredSize(this.d);
		this.setLayout(null);

		jPedine = new JPnlPed[6];
		jRecinti = new JPnlRecinto[42];
		jFinali = new JPnlRecinto[42];
		
		for (int k = 0; k < jPedine.length; k++) {
			jPedine[k] = new JPnlPed(Costanti.getColore(k));
			jPedine[k].setLocation(new Point(50, 50));
			jPedine[k].setSize(new Dimension(25, 25));
			jPedine[k].setVisible(false);
			this.add(jPedine[k]);
		}
		
		for(int h = 0; h < jRecinti.length ; h++){
			jRecinti[h] = new JPnlRecinto("non");
			jRecinti[h].setLocation(posStrada[h]);
			jRecinti[h].setSize(new Dimension(25, 25));
			jRecinti[h].setVisible(false);
			jFinali[h] = new JPnlRecinto("");
			jFinali[h].setLocation(posStrada[h]);
			jFinali[h].setSize(new Dimension(25, 25));
			jFinali[h].setVisible(false);
			this.add(jFinali[h]);
			this.add(jRecinti[h]);			
			repaint();
		}

		try {
			this.sfondo = ImageIO.read(new File(
					"./media/immagini/CaptClick.jpg"));
		} catch (IOException e) {
			MyLogger.gestisci("loading image", e);
			this.errore = true;
		}

		this.repaint();

		try {
			this.addMouseListener(new MouseListener() {

				private double propX, propY;
				private BufferedImage imgClick = ImageIO.read(new File(
						"./media/immagini/ColorGameBoard.png"));
				private JPnlGioco jPnlGioco = new JPnlGioco();

				public void mouseClicked(MouseEvent arg0) {
					Color c;

					this.propY = (double) (550) / (double) (527);
					this.propX = (double) (750) / (double) (700);

					c = new Color(this.imgClick.getRGB(
							(int) (arg0.getX() / this.propX),
							(int) (arg0.getY() / this.propY)));

					if (!prova) {
						
						synchronized (Buffer.getBuffer().getTknStrada()) {
							while (Buffer.getBuffer().checkStrada() != -1) {
								try {
									Buffer.getBuffer().getTknStrada().wait();
								} catch (InterruptedException e) {
									MyLogger.gestisci("info", e);
								}
							}

							Buffer.getBuffer().settaStrada(
									jPnlGioco.getStrada(c));
							Buffer.getBuffer().getTknStrada().notify();
						}
						
						synchronized (Buffer.getBuffer().getTknRegione()) {
							while (Buffer.getBuffer().checkRegione() != -1) {
								try {
									Buffer.getBuffer().getTknRegione().wait();
								} catch (InterruptedException e) {
									MyLogger.gestisci("info", e);
								}
							}

							Buffer.getBuffer().settaRegione(
									jPnlGioco.getRegione(c));
							Buffer.getBuffer().getTknRegione().notify();
						}
					}

				}

				public void mouseEntered(MouseEvent arg0) {
					return;
				}

				public void mouseExited(MouseEvent arg0) {
					return;
				}

				public void mousePressed(MouseEvent arg0) {
					return;
				}

				public void mouseReleased(MouseEvent arg0) {
					return;
				}

			});
		} catch (IOException e) {
			MyLogger.gestisci("mouseAncestor", e);
		}

		this.loading();
	}

	private void loading() {
		this.gregge = new JPnlPecora[18];
		this.montoni = new JPnlMontone[18];
		this.lupo = new JPnlLupo();
		this.agnelli = new JPnlAgnello[18];
		this.nera = new JPnlNera();

		int i = 0;

		for (i = 0; i < 18; i++) {
			this.gregge[i] = new JPnlPecora();
			this.gregge[i].setLocation(posPec[i]);
			this.gregge[i].setSize(new Dimension(30, 30));
			this.add(gregge[i]);
		}

		i = 0;

		for (i = 0; i < 18; i++) {
			this.montoni[i] = new JPnlMontone();
			this.montoni[i].setLocation(posMon[i]);
			this.montoni[i].setSize(new Dimension(30, 30));
			this.add(montoni[i]);
		}

		i = 0;

		for (i = 0; i < 18; i++) {
			this.agnelli[i] = new JPnlAgnello();
			this.agnelli[i].setLocation(posAgn[i]);
			this.agnelli[i].setSize(new Dimension(30, 30));
			this.add(agnelli[i]);
		}
		i = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.errore) {
			g.drawImage(this.sfondo, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void aggiornaStato(StatoClient stato, int indiceNellaPartita) {
		// Aggiornamento JPanel Pecora Nera
		int regioneNera = stato.getPosizioneNera();
		Point posNera = posNere[regioneNera];
		this.nera.setLocation(posNera);
		this.nera.setSize(new Dimension(30, 30));
		this.add(nera);
		repaint();

		// Aggiornamento JPanel Lupo
		int regioneLupo = stato.getPosizioneLupo();
		Point posLupo = posLupi[regioneLupo];
		this.lupo.setLocation(posLupo);
		this.lupo.setSize(new Dimension(30, 30));
		this.add(lupo);
		repaint();

		// Aggiornamento JPanel Pecore-Montoni-Agnelli
		String[] pecoreRegioni = new String[18];
		int[] temp = new int[3];

		pecoreRegioni = stato.getPecoreRegioni();
		for (int i = 0; i < 18; i++) {
			temp[0] = Integer.parseInt(pecoreRegioni[i].split(":")[0]);
			temp[1] = Integer.parseInt(pecoreRegioni[i].split(":")[1]);
			temp[2] = Integer.parseInt(pecoreRegioni[i].split(":")[2]);

			this.gregge[i].setTesto(temp[0]);
			this.montoni[i].setTesto(temp[1]);
			this.agnelli[i].setTesto(temp[2]);
		}

		// Aggiornamento JPnlPed Pastori
		int[] regioniPastori = stato.getStradePastori();
		for (int i = 0; i < 42; i++){
			if (regioniPastori[i] != 0) {
				jPedine[regioniPastori[i]].setLocation(posStrada[i]);
				jPedine[regioniPastori[i]].setVisible(true);
				repaint();
			}
		}
		
		// Aggiornamento recinti
		int[] recinti = stato.getStradeRecinti();
		for (int i = 0; i < 42; i++){
			if (recinti[i] == 1) {
				jRecinti[i].setVisible(true);
				repaint();
			}
			if (recinti[i] == 2) {
				jRecinti[i].setVisible(false);
				jFinali[i].setVisible(true);
				repaint();
			}
		}
		return;
	}

	public void posiziona(int strada) {
		if (!posiz1) {
			jPedine[col1].setLocation(posStrada[strada]);
			jPedine[col1].setVisible(true);
			posiz1 = true;
		} else if (!posiz2) {
			jPedine[col2].setLocation(posStrada[strada]);
			jPedine[col2].setVisible(true);
			posiz2 = true;
		}

		return;
	}

	public void coloraPastore(int colorePastore1, int tipo) {
		
		if (tipo == 0) {
			this.col1 = colorePastore1;
		} else {
			if (tipo == 1) {
				this.col1 = colorePastore1;
			} else {
				this.col2 = colorePastore1;
			}
		}
		return;
	}
}
