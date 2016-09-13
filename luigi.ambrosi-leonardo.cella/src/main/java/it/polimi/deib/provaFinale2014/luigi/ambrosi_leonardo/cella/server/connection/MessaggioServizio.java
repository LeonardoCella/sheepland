package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class of the messages that implements the protocol of
 *         communication between client and server.
 */
public class MessaggioServizio extends Messaggio {

	private static final long serialVersionUID = 1L;

	public MessaggioServizio(int tipo, int valore, int x) {
		this.mod = tipo;
		this.corpo = valore;
		this.destinatario = x;
	}

	public int getTipoMessaggio() {
		return this.mod;
	}

	public int getCorpo() {
		return this.corpo;
	}

	public int getValore() {
		return this.destinatario;
	}

	public void setCorpo(int i) {
		this.corpo = i;
	}

	@Override
	public String toString() {
		String messaggio = null;
		switch (this.mod) {
		case 1: {
			switch (this.corpo) {
			case 1: {
				messaggio = "Tocca a te, dammi la mossa numero ";
				break;
			}
			case 2: {
				messaggio = "Dammi la mossa numero ";
				break;
			}
			case 3: {
				messaggio = "Dammi la mossa numero ";
				break;
			}
			case 4: {
				messaggio = "Il tuo turno e' finito ";
				break;
			}
			default: {
				break;
			}
			}
			messaggio += this.corpo;
			return messaggio;
		}
		case 2: {
			return "Ecco il colore del mio pastore";
		}
		case 3: {
			return "Ecco i colori dei miei pastori";
		}
		case 4: {
			return "La partita e' a due giocatori";
		}
		case 5: {
			return "La partita è a piu' di due giocatori";

		}
		case 6: {
			return "La mossa non va bene, riformulala";

		}
		case 7: {
			return "La tua mossa e' stata eseguita";

		}
		case 10: {
			return "Un utente con questo username risulta già presente!";
		}
		default: {
			return null;
		}
		}
	}

}