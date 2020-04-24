package bn.inference;

import bn.core.*;

/**
 * Defines the interface supported by implementations of the
 * inference algorithms for Bayesian Networks.
 */
public interface Inferencer {
	
	/*
	 * Returns the Distribution of the query RandomVariable X
	 * given evidence Assignment e, using the distribution encoded
	 * by the BayesianNetwork bn.
	 * Note that some algorithms may require additional parameters, for example
	 * the number of samples for approximate inferencers. You can have methods
	 * that accept those parameters and use them in your testing. Just implement
	 * this method using some reasonable default value to satisfy the interface.
	 * Or don't implement this interface at all. It's really here to guide you
	 * as to what an inferencer should do (namely, compute a posterior distriubution.
	 */
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e);

}
