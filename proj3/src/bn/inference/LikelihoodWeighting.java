package bn.inference;

/*
 * File: LikelihoodWeighting.java
 * LWC
 * 11/06/2018
 * CSC 242 Artificial Intelligence Project 3
 */

import java.util.List;

import bn.core.*;

//Part II: Approximate Inference
//2. Likelihood weighting
//The likelihood-weighting algorithm for inference in Bayesian networks.
//The WeightedSample function returns an event and a weight

public class LikelihoodWeighting {
	
	 public class WeightedSample{
	        Assignment e;
	        double weight;//vaariable to keep tack of weight

	        public WeightedSample(Assignment e, double weight){
	            this.e = e;
	            this.weight = weight;
	        }
	    }
	 //function Likelihood-Weighting
	    public Distribution likelihoodWeighting(RandomVariable X, Assignment e, BayesianNetwork bn, int num){
	        //X, the query variable
	    	//e, oberved values for variables E
	    	//bn, a Bayesian network specifying joint distribution P(X1,..,Xn)
	    	//N, the total number of samples to be generated
	    	
	    	Distribution weights = new Distribution();//local variables W, a vector of weighted counts for each value of X, initially zero

	        for(int i =0; i <= num; i++){
	            WeightedSample sample = weightedSample(bn, e);
	            if(weights.get(sample.e.get(X)) == null){
	                weights.put(sample.e.get(X), sample.weight);
	            }
	            else{
	                Double counter = weights.get(sample.e.get(X));
	                counter += sample.weight;
	                weights.put(sample.e.get(X), counter);
	            }
	        }

	        weights.normalize();
	        return weights;//return normalized W
	    }
	    	
	   //The WeightedSample function returns an event and a weight
	    public WeightedSample weightedSample(BayesianNetwork bn, Assignment e){
	        double w = 1;
	        Assignment x = e.copy();
	        List<RandomVariable> vars = bn.getVariableListTopologicallySorted();

	        for(RandomVariable var: vars){
	            if(e.containsKey(var)){
	                w = w * bn.getProb(var, x);
	            }
	            else{
	                x.set(var, randomValue(bn, x, var));
	            }
	        }

	        WeightedSample sample = new WeightedSample(x, w);
	        return sample;
	    }
	        public Object randomValue(BayesianNetwork bn, Assignment e, RandomVariable var){
	            double random = Math.random();
	            Assignment temp = e.copy();
	            e.set(var, "true");
	            double prob = bn.getProb(var, e);
	            if( random <= prob){
	                return "true";
	            }
	            else{
	                return "false";
	            }

	    }

}
