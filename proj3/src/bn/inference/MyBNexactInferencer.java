package bn.inference;

/*
 * File: MyBNexactInference.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */

import java.io.*;
//import bn.*;
import bn.parser.*;
import bn.core.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

//Part I: Exact Inference


public class MyBNexactInferencer {
	 public static void main(String[] args){

		 Enumeration asker = new Enumeration();
	        RejectionSampling rej = new RejectionSampling();
	        LikelihoodWeighting like = new LikelihoodWeighting();

	        if(args[0].contains(".xml")){
	            XMLBIFParser x = new XMLBIFParser();
	            try {
	                BayesianNetwork bn = x.readNetworkFromFile(args[0]);
	                Assignment e = new Assignment();
	                for(int i = 2; i < args.length; i+=2){
	                    e.put(bn.getVariableByName(args[i]), args[i+1]);
	                }
	                //Distribution dist = asker.ask(bn, bn.getVariableByName(argv[1]), e);
	                //Distribution dist = rej.rejectionSampling(bn.getVariableByName(argv[1]),e, bn,100000 );
	                Distribution dist = like.likelihoodWeighting(bn.getVariableByName(args[1]), e, bn, 100000);
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
	                x = new BIFParser(new FileInputStream(args[0]));

	                BayesianNetwork bn = x.parseNetwork();
	                Assignment e = new Assignment();
	                for(int i = 2; i < args.length; i+=2){
	                    e.put(bn.getVariableByName(args[i]), args[i+1]);
	                }
	                Distribution dist = rej.rejectionSampling(bn.getVariableByName(args[1]),e, bn,100000 );

	                System.out.println(dist);
	            } catch (FileNotFoundException e1) {
	                e1.printStackTrace();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }

   }

}
