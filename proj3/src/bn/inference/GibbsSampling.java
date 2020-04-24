package bn.inference;

/*
 * File: GibbsSampling.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */

import java.util.ArrayList;
import java.util.Random;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.BayesianNetwork.Node;
import bn.core.Distribution;
import bn.core.RandomVariable;

//Part II: Approximate Inference
//3. Gibbs Sampling
//The Gibbs sampling algorithm for approximate inference in Bayesian networks;

public class GibbsSampling {
	Random rand = new Random();

    private Object sampleCondDist (BayesianNetwork bn, RandomVariable z, Assignment x) {
    	
    	Distribution xCounts = new Distribution(z);
        xCounts.initialize(z);
        
        double sum = 0;
        Assignment xCopy = x.copy();
        for (Object o : z.getDomain()){
        	xCopy.set(z, o);
        	double product = bn.getProb(z, xCopy);
        	for (Node node : bn.getNodeForVariable(z).children){
        		product *= bn.getProb(node.variable, xCopy);
        	}
        	xCounts.put(o,product);
        }
        xCounts.normalize();
        
        for (Object o : z.getDomain()){
        	xCopy.set(z, o);
        	sum += xCounts.get(o);
        	
        	if (rand.nextDouble() <= sum){
        		return o;
        	}
        }
        return null;
    }

	public Distribution Gibbsask(int N, BayesianNetwork bn, RandomVariable X, Assignment e) {
         
        // Initialize local variables
        // N: Vector of counts for each value of X
        // It's pretty shady using a Distribution like this, but I didn't want to 
        //   import another map.
        Distribution xCounts = new Distribution(X);
        xCounts.initialize(X);

        // Z: nonevidence variables in bn (all variables less query & evidence)
        ArrayList<RandomVariable> nonEVars = 
            new ArrayList<RandomVariable>(bn.getVariableListTopologicallySorted());
        // 99% certain you can't use "Entry" like this
        for (RandomVariable k : e.keySet()) {
            if (nonEVars.contains(k)){
                nonEVars.remove(k);
            }
        }
        //nonEVars.remove(X);

        // x: state of network, copied from e
        Assignment state = e.copy();
        for (RandomVariable z : nonEVars) {
            state.put(z,z.getDomain().get(rand.nextInt(z.getDomain().size())));
        }

		for (int i = 0; i < N; i++) {  
			for (RandomVariable z : nonEVars) {
                // set value of Z_j in state by sampling from P(Z_j | mb(Z_j))
                Object value = sampleCondDist(bn, z, state);
                state.put(z, value); 
                xCounts.put(state.get(X), (xCounts.get(state.get(X))+1.0)); 
            }
	    }	
        // might have to change Q from freqs. to rel. freqs. 
        // Q.normalize() might do just that
		xCounts.normalize();
		return xCounts; 
	}

}
