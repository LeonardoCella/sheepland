package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Stato extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField soldi,grano,montagna,sabbia,fiumi,bosco,pianura;
	private Dimension d = new Dimension(150,180);
	private Map<String, JTextField> dizionario ;
	
	public Stato(){
		this.setLayout(new GridLayout(7,2));
		this.setMinimumSize(this.d);
		this.setMaximumSize(this.d);
		this.setPreferredSize(this.d);
		
		dizionario = new HashMap<String, JTextField>(7);
		
		soldi = new JTextField();
		grano = new JTextField();
		montagna = new JTextField();
		sabbia = new JTextField();
		fiumi = new JTextField();
		bosco = new JTextField();
		pianura = new JTextField();
		
		this.add(new JLabel("Soldi: "));
		soldi.setText("0");
		this.add(soldi);
		dizionario.put("soldi", soldi);
		
		this.add(new JLabel("grano: "));
		grano.setText("0");
		this.add(grano);
		dizionario.put("GRANO",grano);
		
		this.add(new JLabel("montagna: "));
		montagna.setText("0");
		this.add(montagna);
		dizionario.put("MONTAGNA",montagna);

		this.add(new JLabel("sabbia: "));
		sabbia.setText("0");
		this.add(sabbia);
		dizionario.put("SABBIA",sabbia);

		this.add(new JLabel("fiume: "));
		fiumi.setText("0");
		this.add(fiumi);
		dizionario.put("FIUMI",fiumi);
		
		this.add(new JLabel("bosco: "));
		bosco.setText("0");
		this.add(bosco);
		dizionario.put("BOSCO",bosco);
		
		this.add(new JLabel("pianure: "));
		pianura.setText("0");
		this.add(pianura);
		dizionario.put("PIANURA",pianura);




		
		repaint();
		
	}


	public void aggiorna(String tipoTerreno) {
		int tmp;
		JTextField fieldCorrispondente = dizionario.get(tipoTerreno);
		tmp = Integer.parseInt( fieldCorrispondente.getText());
		fieldCorrispondente.setText( "" + (tmp + 1));
	}
	

	public void aggiornaStato(StatoClient stato, int indice) {
		grano.setText(stato.getCostoTessera()[0]+"");
		montagna.setText(stato.getCostoTessera()[1]+"");
		sabbia.setText(stato.getCostoTessera()[2]+"");
		fiumi.setText(stato.getCostoTessera()[3]+"");
		bosco.setText(stato.getCostoTessera()[4]+"");
		pianura.setText(stato.getCostoTessera()[5]+"");
		soldi.setText(stato.getSoldiGiocatori()[indice]+"");
	}

}
