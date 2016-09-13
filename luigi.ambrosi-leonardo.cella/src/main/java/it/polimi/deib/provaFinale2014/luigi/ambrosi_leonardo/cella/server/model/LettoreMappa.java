package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         The class to read the map from the xml file.
 */
public class LettoreMappa {

	public static String[][] getMappa() throws EccClient {
		String tmp = new String();
		String[][] mappa = new String[2][42];
		try {
			tmp = (new LettoreMappa()).leggiFile();
			mappa[0] = tmp.split(" SEP ")[0].split("-");
			mappa[1] = tmp.split(" SEP ")[1].split("-");

		} catch (EccParserXML e) {
			MyLogger.gestisci("Ecc", e);
			throw new EccClient(e);
		}

		return mappa;
	}

	/**
	 * parser of the map to put all the data contained in the file into the data
	 * structures (graph).
	 * 
	 * @return a formatted string
	 * @throws EccParserXML
	 */
	private String leggiFile() throws EccParserXML {
		String mappa = new String();
		String tipoTer = new String();
		String dado = new String();
		try {
			int listaRegioni, listaStrade;

			// Pre loading of the XML file, to keep it in a Document

			File xmlFile = new File("./media/mappa.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			// I get the file
			doc.getDocumentElement().normalize();

			// Inizio scansione prima parte
			
			// Lista Regioni
			NodeList nList = doc.getElementsByTagName("reg"); 
			listaRegioni = nList.getLength();
			String confini;
			Element regioneXML, stradaXML;

			// for of all the lands
			for (int temp = 0; temp < listaRegioni; temp++) {
				// Number of the streets near the current land
				Node singolaRegione = nList.item(temp);

				if (singolaRegione.getNodeType() == Node.ELEMENT_NODE) {
					regioneXML = (Element) singolaRegione;
					tipoTer = regioneXML.getAttribute("ter");
					confini = regioneXML.getElementsByTagName("conf").item(0)
							.getTextContent();

					mappa += tipoTer + "%" + confini + "-";
				}
			}

			mappa += " SEP ";

			nList = null;
			// lands
			nList = doc.getElementsByTagName("str");

			// number of the lands
			listaStrade = nList.getLength();

			for (int temp = 0; temp < listaStrade; temp++) {

				Node oggStrada = nList.item(temp);

				if (oggStrada.getNodeType() == Node.ELEMENT_NODE) {
					stradaXML = (Element) oggStrada;
					dado = stradaXML.getAttribute("dado");
					mappa += stradaXML.getElementsByTagName("rSep").item(0)
							.getTextContent();

					mappa += "%" + dado + "-";
				}
			}
		} catch (Exception e) {
			MyLogger.gestisci("Ecc Int", e);
			throw new EccParserXML(e.getMessage());
		}
		return mappa;
	}

}