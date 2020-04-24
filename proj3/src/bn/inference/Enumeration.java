package bn.inference;

/*
 * File: Enumeration.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */


import bn.core.*;

import java.util.List;

public class Enumeration {
	//function Enumeration-Ask(X,e,bn)returns a distribution over X
	public Distribution ask (BayesianNetwork bn, RandomVariable X, Assignment e) 
	//input X, the query variable, e, observed values for variables E and the Bayes net
	{
		Distribution Q = new Distribution(X); //A distribution over X, initially empty
		for(int i = 0; i < X.getDomain().size(); i++){  
			Assignment copy = e.copy();
			copy.set(X, X.getDomain().get(i));
			Q.put(X.getDomain().get(i), Enumerate_all(bn, copy, 0));
		}
		Q.normalize();
		return Q; 
		
	}
	
	
	//Function Enumerate-All(vars, e)returns a real number
	public double Enumerate_all(BayesianNetwork bn, Assignment e, int index) {
		List<RandomVariable> vars = bn.getVariableListTopologicallySorted();
		
		if(index >= vars.size()){
			return 1.0;
		}//if Empty?(vars) then return 1.0
		
		RandomVariable Y = vars.get(index);
		
	

		if(e.containsKey(Y)){ 
			e = e.copy();
			//if Y has value y in e
			return bn.getProb(Y, e)*Enumerate_all(bn, e, index+1);
		} else {
			double sum = 0;
			for(int i = 0; i < Y.getDomain().size(); i++){
				e.put(Y, Y.getDomain().get(i));
				Assignment ni = e.copy(); 
				double prob = bn.getProb(Y, ni);
				double enumAll = Enumerate_all(bn, ni, index+1);
				sum += prob*enumAll;
			}
			return sum;
	}

}
}
