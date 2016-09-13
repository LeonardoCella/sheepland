package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import java.awt.Color;

public class JPnlGioco {
	private Color[] coloreRegioni = new Color[19];
	private Color[] coloreStrade = new Color[42];

	public JPnlGioco() {
		coloreRegioni[0] = new Color(140, 253, 78);
		coloreRegioni[1] = new Color(252, 151, 50);
		coloreRegioni[2] = new Color(252, 135, 27);
		coloreRegioni[3] = new Color(119, 255, 49);
		coloreRegioni[4] = new Color(253, 168, 78);
		coloreRegioni[5] = new Color(108, 108, 108);
		coloreRegioni[6] = new Color(163, 163, 163);
		coloreRegioni[7] = new Color(232, 254, 27);
		coloreRegioni[8] = new Color(135, 135, 135);
		coloreRegioni[9] = new Color(102, 255, 25);
		coloreRegioni[10] = new Color(43, 254, 56);
		coloreRegioni[11] = new Color(43, 255, 180);
		coloreRegioni[12] = new Color(233, 255, 50);
		coloreRegioni[13] = new Color(86, 254, 105);
		coloreRegioni[14] = new Color(61, 255, 80);
		coloreRegioni[15] = new Color(84, 255, 202);
		coloreRegioni[16] = new Color(60, 254, 192);
		coloreRegioni[17] = new Color(238, 255, 78);
		coloreRegioni[18] = new Color(255, 255, 255);

		coloreStrade[0] = new Color(60, 1, 69);
		coloreStrade[1] = new Color(107, 0, 54);
		coloreStrade[2] = new Color(38, 38, 38);
		coloreStrade[3] = new Color(96, 0, 108);
		coloreStrade[4] = new Color(181, 208, 9);
		coloreStrade[5] = new Color(201, 95, 6);
		coloreStrade[6] = new Color(234, 58, 254);
		coloreStrade[7] = new Color(151, 1, 74);
		coloreStrade[8] = new Color(1, 7, 67);
		coloreStrade[9] = new Color(3, 3, 3);
		coloreStrade[10] = new Color(177, 2, 203);
		coloreStrade[11] = new Color(225, 0, 255);
		coloreStrade[12] = new Color(66, 0, 35);
		coloreStrade[13] = new Color(200, 1, 98);
		coloreStrade[14] = new Color(251, 0, 120);
		coloreStrade[15] = new Color(0, 7, 108);
		coloreStrade[16] = new Color(134, 62, 255);
		coloreStrade[17] = new Color(136, 1, 155);
		coloreStrade[18] = new Color(112, 24, 254);
		coloreStrade[19] = new Color(146, 255, 223);
		coloreStrade[20] = new Color(0, 3, 153);
		coloreStrade[21] = new Color(92, 1, 254);
		coloreStrade[22] = new Color(72, 2, 254);
		coloreStrade[23] = new Color(0, 0, 202);
		coloreStrade[24] = new Color(53, 158, 2);
		coloreStrade[25] = new Color(1, 3, 253);
		coloreStrade[26] = new Color(59, 1, 202);
		coloreStrade[27] = new Color(251, 65, 167);
		coloreStrade[28] = new Color(22, 39, 254);
		coloreStrade[29] = new Color(49, 69, 254);
		coloreStrade[30] = new Color(46, 1, 152);
		coloreStrade[31] = new Color(105, 0, 0);
		coloreStrade[32] = new Color(66, 1, 1);
		coloreStrade[33] = new Color(23, 0, 68);
		coloreStrade[34] = new Color(33, 0, 108);
		coloreStrade[35] = new Color(250, 1, 6);
		coloreStrade[36] = new Color(199, 0, 3);
		coloreStrade[37] = new Color(154, 1, 0);
		coloreStrade[38] = new Color(80, 100, 254);
		coloreStrade[39] = new Color(252, 38, 50);
		coloreStrade[40] = new Color(251, 70, 77);
		coloreStrade[41] = new Color(250, 0, 27);
	}
	
	public int getStrada(Color col){
		int i,ret=-1;
		
		for( i = 0 ; i < 42 ; i++ ){
			if(coloreStrade[i].equals(col)){
				ret = i;
				break;
			}
		}
		
		return ret;
	}
	
	public int getRegione(Color col){
		int i,ret=-1;
		
		for( i = 0 ; i < 19 ; i++ ){
			if(coloreRegioni[i].equals(col)){
				ret = i;
				break;
			}
		}
		
		return ret;
	}
	
}
