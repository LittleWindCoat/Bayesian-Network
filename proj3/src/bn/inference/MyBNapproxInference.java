package bn.inference;

/*
 * File: MyBNapproxInference.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.*;
import bn.parser.*;

public class MyBNapproxInference {
	public static void main(String[] argv){
		RejectionSampling wat = new RejectionSampling();
		GibbsSampling wat2 = new GibbsSampling();
		if(argv[1].contains(".xml")){
			XMLBIFParser x = new XMLBIFParser();
			try {
				int samples = Integer.parseInt(argv[0]);
				BayesianNetwork bn = x.readNetworkFromFile(argv[1]);
				Assignment e = new Assignment();
				for(int i = 3; i < argv.length; i+=2){
					e.put(bn.getVariableByName(argv[i]), argv[i+1]);
				}
				Distribution dist = wat2.Gibbsask(samples,bn, bn.getVariableByName(argv[2]), e);
				System.out.println(dist);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		} else {
			BIFParser x;
			try {
				x = new BIFParser(new FileInputStream(argv[1]));
				int samples = Integer.parseInt(argv[0]);
				//System.out.println(x.parseNetwork());
				BayesianNetwork bn = x.parseNetwork();
				Assignment e = new Assignment();
				for(int i = 3; i < argv.length; i+=2){
					e.put(bn.getVariableByName(argv[i]), argv[i+1]);
				} 
				Distribution dist = wat2.Gibbsask(samples, bn, bn.getVariableByName(argv[2]), e);
				
				System.out.println(dist);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	

}
