package bn.parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.BayesianNetwork;
import bn.core.CPT;
import bn.core.CPT.ProbabilityValue;
import bn.core.RandomVariable;

/**
 * Prints BayesianNetworks in XMLBIF format.
 * @see http://sites.poli.usp.br/p/fabio.cozman/Research/InterchangeFormat/index.html
 */
public class XMLBIFPrinter {
	
	public XMLBIFPrinter(PrintStream out) {
		this.out = out;
	}
	
	protected PrintStream out;
	
	public void print(BayesianNetwork network) {
		print(network, null);
	}

	public void print(BayesianNetwork network, String name) {
		printXMLHeader();
		printDOCTYPE();
		printBIFHeader();
		printNetwork(network, name);
		printBIFTrailer();
	}
	
	protected void printXMLHeader() {
		out.println("<?xml version=\"1.0\"?>");
	}

	protected void printDOCTYPE() {
		out.println("<!-- DTD for the XMLBIF 0.3 format -->\n" + 
				"<!DOCTYPE BIF [\n" + 
				"	<!ELEMENT BIF ( NETWORK )*>\n" + 
				"	      <!ATTLIST BIF VERSION CDATA #REQUIRED>\n" + 
				"	<!ELEMENT NETWORK ( NAME, ( PROPERTY | VARIABLE | DEFINITION )* )>\n" + 
				"	<!ELEMENT NAME (#PCDATA)>\n" + 
				"	<!ELEMENT VARIABLE ( NAME, ( OUTCOME |  PROPERTY )* ) >\n" + 
				"	      <!ATTLIST VARIABLE TYPE (nature|decision|utility) \"nature\">\n" + 
				"	<!ELEMENT OUTCOME (#PCDATA)>\n" + 
				"	<!ELEMENT DEFINITION ( FOR | GIVEN | TABLE | PROPERTY )* >\n" + 
				"	<!ELEMENT FOR (#PCDATA)>\n" + 
				"	<!ELEMENT GIVEN (#PCDATA)>\n" + 
				"	<!ELEMENT TABLE (#PCDATA)>\n" + 
				"	<!ELEMENT PROPERTY (#PCDATA)>\n" + 
				"]>");
	}

	protected void printBIFHeader() {
		out.println("<BIF VERSION=\"0.3\">");
	}

	protected void printBIFTrailer() {
		out.println("</BIF>");
	}

	protected void printNetwork(BayesianNetwork network, String name) {
		out.println("<NETWORK>");
		if (name != null) {
			out.println("<NAME>" + name + "</NAME>");
		}
		// Variables
		for (RandomVariable var : network.getVariableList()) {
			out.println("<VARIABLE TYPE=\"nature\">");
			out.println("  <NAME>" + var.getName() + "</NAME>");
			for (Object value : var.getDomain()) {
				out.println("  <OUTCOME>" + value + "</OUTCOME>");
			}
			out.println("</VARIABLE>");
		}
		// CPTs
		// This requires access to some of the internals of BayesianNetworks, which
		// I have now made public
		for (RandomVariable var : network.getVariableList()) {
			out.println("<DEFINITION>");
			BayesianNetwork.Node node = network.getNodeForVariable(var);
			out.println("  <FOR>" + var.getName() + "</FOR>");
			for (BayesianNetwork.Node parent : node.parents) {
				out.println("  <GIVEN>" + parent.variable.getName() + "</GIVEN>");
			}
			out.println("  <TABLE>");
			CPT cpt = node.cpt;
			Iterator<ProbabilityValue> values = cpt.valueIterator();
			int i = 0;
			while (values.hasNext()) {
				if (i == 0) {
					out.print("    ");
				}
				ProbabilityValue value = values.next();
				out.printf("%.3f", value.value);
				i += 1;
				if (i == var.getDomain().size()) {
					out.println();
					i = 0;
				} else {
					out.print(" ");
				}
			}
			out.println("  </TABLE>");
			out.println("</DEFINITION>");
		}
		out.println("</NETWORK>");
	}

	public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException {
		if (argv.length < 1 || argv.length > 2) {
			System.err.println("usage: java XMLBIFPrinter filename [network name]");
		}
		String infilename = argv[0];
		String networkName = argv.length > 1 ? argv[1] : null;
		XMLBIFParser xp = new XMLBIFParser();
		XMLBIFPrinter printer = new XMLBIFPrinter(System.out);
		BayesianNetwork network = xp.readNetworkFromFile(infilename);
		printer.print(network, networkName);
    }

}
