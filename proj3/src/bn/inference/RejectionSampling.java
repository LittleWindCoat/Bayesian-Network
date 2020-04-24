package bn.inference;

/*
 * File: RejectionSampling.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */

import java.util.List;

import bn.core.*;

//Part II: Approximate Inference
//1. Rejection Sampling
//The rejection-sampling algorithm for answering queries given evidence in a Bayesian network.

public class RejectionSampling {
	//function Rejection-Sampling(X,e,bn,N)returns an estimate of P(X|e)
	  public Distribution rejectionSampling(RandomVariable X, Assignment e, BayesianNetwork bn, int num){
	    //inputs: X, the query variable
		  //e, observed values for variables E
		  //bn, a Bayesian network
		  //N, the total number of samples to be generated
		  
		  
		  Distribution count = new Distribution();

	        for(int j = 0; j <= num; j++){
	            Assignment x = PriorSample(bn);
	            if(isConsistent(e,x)){
	                if(count.get(x.get(X)) == null){
	                    count.put(x.get(X), 1);
	                }
	                else{
	                    Double counter = count.get(x.get(X));
	                    counter++;
	                    count.put(x.get(X), counter);
	                }
	            }
	        }
	        count.normalize();
	        return count;

	    }

	    public boolean isConsistent(Assignment e, Assignment x){
	       for(RandomVariable V: e.variableSet()){
	           if(x.containsKey(V)){
	               if(!e.get(V).equals(x.get(V))){//check whether evidence values are the same
	                   return false;
	               }
	           }
	       }
	       return true;
	    }

	    //This is the prior sample algorithm which generates samples from the prior joint distribution
	    public Assignment PriorSample(BayesianNetwork bn)//imputs: bn
	    												//a Bayesian network specifying joint distribution P(X1,...,Xn)
	    {
	        Assignment e = new Assignment();
	        List<RandomVariable> vars = bn.getVariableListTopologicallySorted();//iterate through each random variable
	        	for(int i = 0; i < vars.size(); i++){
	        		RandomVariable X = vars.get(i);
	        		Distribution dist = new Distribution();
	            for(int j = 0; j < X.getDomain().size(); j++){
	                Assignment temp = e.copy();
	                temp.set(X, X.getDomain().get(j));
	                double prob = bn.getProb(X, temp);
	                dist.put(X.getDomain().get(j), prob );
	            }

	            dist.normalize();
	            double value = Math.random();
	            double test = 0.0;

	            for(int k=0; k < X.getDomain().size();k++){
	                test += dist.get(X.getDomain().get(k));
	                if(value <= test){
	                    e.set(X, X.getDomain().get(k));
	                    break;
	                }
	            }


	        }
	        return e;
	    }


	    public  Assignment priorSampleBinary(BayesianNetwork bn){
	        Assignment event = new Assignment();
	        for(RandomVariable V:bn.getVariableListTopologicallySorted()){
	            Assignment e = event.copy();
	            e.set(V, "true");
	            double probability = bn.getProb(V, e);
	            double rand = Math.random();
	            if(rand <= probability){
	                event.set(V, "true");
	            } else {
	                event.set(V, "false");
	            }
	        }
	        return event;
	    }

	
}
