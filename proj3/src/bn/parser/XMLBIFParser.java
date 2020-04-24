/*
 * File: XMLBIFParser.java
 * Creator: George Ferguson
 * Created: Sun Mar 25 15:38:48 2012
 * Time-stamp: <Sat Mar 26 08:52:13 EDT 2016 ferguson>
 */

package bn.parser;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import bn.core.*;

/**
 * DOM Parser (DocumentBuilder)-based parser for
 * <a href="http://www.cs.cmu.edu/~fgcozman/Research/InterchangeFormat/">XMLBIF</a>
 * files.
 * 3/26/2018: http://sites.poli.usp.br/p/fabio.cozman/Research/InterchangeFormat/index.html
 * <p>
 * Note that XMLBIF explicitly states that <q>There is no mandatory
 * order of variable and probability blocks.</q> This means that we
 * have to read the DOM, then create nodes for all the variables using
 * the {@code variable} elements, then hook them up and add the CPTs
 * using the {@code definition} blocks. A good reason to use a DOM
 * parser rather than a SAX parser.
 * <p>
 * Also XMLBIF appears to use uppercase tag names, perhaps thinking they
 * really ought to be case-insensitive.
 * <p>
 * I have implemented minimal sanity checking and error handling.
 * You could do better. Caveat codor.
 */
public class XMLBIFParser {

    public BayesianNetwork readNetworkFromFile(String filename) throws IOException, ParserConfigurationException, SAXException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document doc = db.parse(new File(filename));
	return processDocument(doc);
    }

    protected BayesianNetwork processDocument(Document doc) {
	Element networkElt = doc.getDocumentElement();
	final BayesianNetwork network = new BayesianNetwork();
	// First do the variables
	doForEachElement(doc, "VARIABLE", new ElementTaker() {
		public void element(Element e) {
		    processVariableElement(e, network);
		}
	    });
	// Then do the defintions (a.k.a, links and CPTs)
	doForEachElement(doc, "DEFINITION", new ElementTaker() {
		public void element(Element e) {
		    processDefinitionElement(e, network);
		}
	    });
	return network;
    }

    protected void doForEachElement(Document doc, String tagname, ElementTaker taker) {
	NodeList nodes = doc.getElementsByTagName(tagname);
	if (nodes != null && nodes.getLength() > 0) {
	    for (int i=0; i < nodes.getLength(); i++) {
		    Node node = nodes.item(i);
		    taker.element((Element)node);
	    }
	}
    }

    protected void processVariableElement(Element e, BayesianNetwork network) {
	Element nameElt = getChildWithTagName(e, "NAME");
	String name = getChildText(nameElt);
	//trace("creating variable: " + name);
	RandomVariable var = new RandomVariable(name);
	final Domain domain = var.getDomain();
	doForEachChild(e, "OUTCOME", new ElementTaker() {
		public void element(Element e) {
		    String value = getChildText(e);
		    //trace("adding value: " + value);
		    domain.add(value);
		}
	    });
	network.add(var);
    }

    protected void processDefinitionElement(Element e, final BayesianNetwork network) {
	Element forElt = getChildWithTagName(e, "FOR");
	String forName = getChildText(forElt);
	//trace("creating links to variable: " + forName);
	RandomVariable forVar = network.getVariableByName(forName);
	final List<RandomVariable> givens = new ArrayList<RandomVariable>();
	doForEachChild(e, "GIVEN", new ElementTaker() {
		public void element(Element e) {
		    String value = getChildText(e);
		    //trace("adding parent: " + value);
		    givens.add(network.getVariableByName(value));
		}
	    });
	CPT cpt = new CPT(forVar, givens);
	Element tableElt = getChildWithTagName(e, "TABLE");
	String tableStr = getChildText(tableElt);
	initCPTFromString(cpt, tableStr);
	network.connect(forVar, givens, cpt);
    }

    /**
     * Reads numeric values from the given string, and saves them as the
     * succesive probability values of this CPT. This method is used by
     * the XMLBIFParser, and relies crucially on the ordering of the 
     * elements of the CPT matching the order defined for XMLBIF.
     * @see CPT#valueIterator
     */
    public void initCPTFromString(CPT cpt, String str) throws NumberFormatException, CPTFormatException {
	//trace("initCPTFromString: " + str);
	StringTokenizer tokens = new StringTokenizer(str);
	Iterator<CPT.ProbabilityValue> values = cpt.valueIterator();
	while (tokens.hasMoreTokens()) {
	    String token = tokens.nextToken();
	    //trace("probability: " + token);
	    CPT.ProbabilityValue pv = values.next();
	    pv.value = Double.parseDouble(token);
	}
	if (values.hasNext()) {
	    throw new CPTFormatException();
	}
    }

    protected Element getChildWithTagName(Element elt, String tagname) {
	NodeList children = elt.getChildNodes();
	if (children != null && children.getLength() > 0) {
	    for (int i=0; i < children.getLength(); i++) {
		Node node = children.item(i);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element childElt = (Element)node;
		    if (childElt.getTagName().equals(tagname)) {
			return childElt;
		    }
		}
	    }
	}
	throw new NoSuchElementException(tagname);
    }

    protected void doForEachChild(Element elt, String tagname, ElementTaker taker) {
	NodeList children = elt.getChildNodes();
	if (children != null && children.getLength() > 0) {
	    for (int i=0; i < children.getLength(); i++) {
		Node node = children.item(i);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element childElt = (Element)node;
		    if (childElt.getTagName().equals(tagname)) {
			taker.element(childElt);
		    }
		}
	    }
	}
    }

    /**
     * Returns the concatenated child text of the specified node.
     * This method only looks at the immediate children of type
     * Node.TEXT_NODE or the children of any child node that is of
     * type Node.CDATA_SECTION_NODE for the concatenation.
     */
    public String getChildText(Node node) {
	if (node == null) {
	    return null;
	}
	StringBuilder buf = new StringBuilder();
	Node child = node.getFirstChild();
	while (child != null) {
	    short type = child.getNodeType();
	    if (type == Node.TEXT_NODE) {
		buf.append(child.getNodeValue());
	    }
	    else if (type == Node.CDATA_SECTION_NODE) {
		buf.append(getChildText(child));
	    }
	    child = child.getNextSibling();
	}
	return buf.toString();
    }

    protected void trace(String msg) {
	System.err.println(msg);
    }

    public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException {
	XMLBIFParser parser = new XMLBIFParser();
	BayesianNetwork network = parser.readNetworkFromFile(argv[0]);
	network.print(System.out);
    }

}

interface ElementTaker {
    public void element(Element e);
}
