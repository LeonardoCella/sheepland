package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is a singleton and it is implemented using an xml file.
 */
public class Mappa {
	private static Regione[] regioni;
	private static Strada[] strade;
	private static Mappa istanza = null;
	private static String[][] mappa = null;

	private Mappa() throws EccClient {
		// sheepsburg = regione[18]
		regioni = new Regione[19];
		strade = new Strada[42];

		mappa = LettoreMappa.getMappa();
		for (int i = 0; i < 19; i++) {
			String s = mappa[0][i];
			String[] s1;

			s1 = s.split("%");
			regioni[i] = new Regione(i, Integer.parseInt(s1[0]));
		}

		for (int j = 0; j < 42; j++) {
			String s = mappa[1][j];
			String[] s1;
			s1 = s.split("%");
			int r1, r2, d;
			r1 = Integer.parseInt(s1[0].split(":")[0]);
			r2 = Integer.parseInt(s1[0].split(":")[1]);
			d = Integer.parseInt(s1[1]);

			strade[j] = new Strada(regioni[r1], regioni[r2], j, d);

		}

		// SETTO I CONFINI DELLE SINGOLE REGIONI
		for (int i = 0; i < 19; i++) {
			String s = mappa[0][i];
			String[] s1, stringaConf;
			Strada[] str;

			s1 = s.split("%");

			stringaConf = s1[1].split(":");
			str = new Strada[stringaConf.length];

			for (int k = 0; k < stringaConf.length; k++) {
				str[k] = strade[Integer.parseInt(stringaConf[k])];
			}

			regioni[i].setConfini(str);

		}

	}

	public static Mappa getMappa() throws EccClient {
		if (istanza == null) {
			istanza = new Mappa();
		}

		return istanza;
	}

	public Regione[] getRegioni() {
		return regioni;
	}

	public Strada[] getStrade() {
		return strade;
	}

	@Override
	public String toString() {
		String ret = new String();
		for (Strada s : strade) {
			ret += s + "\n";
		}
		for (Regione r : regioni) {
			ret += "\n" + r;
		}

		return ret;
	}
}
