/*
 * File: Distribution.java
 * Creator: George Ferguson
 * Created: Mon Mar 26 13:15:54 2012
 * Time-stamp: <Wed Mar 28 15:06:37 EDT 2012 ferguson>
 */

package bn.core;

import java.util.*;

/**
 * Class representing a probability distribution for a single
 * RandomVariable.
 * AIMA uses arrays indexed by the values of the variable. We use
 * a Map whose keys are the values. One could instead map the values
 * to integer indexes (by imposing an ordering on them), and then use
 * an array of doubles to represent the distribution. That might be
 * nice since one can't put primitive doubles in a Map...
 */
public class Distribution extends LinkedHashMap<Object,Double> {

    public static final long serialVersionUID = 1l;

    /**
     * Construct and return a new empty Distribution.
     */
    public Distribution() {
	super();
    }

    /**
     * Construct and return a new empty Distribution with the given initial size.
     */
    public Distribution(int initialSize) {
	super(initialSize);
    }

    /**
     * Construct and return a new empty Distribution for the given RandomVariable.
     * Note that we don't actually enforce that the values in the Distribution
     * are only those for the RandomVariable. But anyway, this is handy.
     */
    public Distribution(RandomVariable X) {
	this(X.getDomain().size());
    }

    /**
     * Store the given double as the value of the given RandomVariable in this
     * distribution.
     */
    public void put(Object x, double d) {
	put(x, new Double(d));
    }

    /**
     * Store the given int as the value of the given RandomVariable in this
     * distribution.
     */
    public void put(Object x, int i) {
	put(x, new Double(i));
    }

    /**
     * Normalize this distribution so that the probabilities add up to 1.
     */
    public void normalize() {
	double sum = 0.0;
	for (Double value : values()) {
	    sum += value.doubleValue();
	}
	for (Object key : keySet()) {
	    put(key, get(key).doubleValue()/sum);
	}
    }
    
    public void initialize(RandomVariable X) {

        for (Object o : X.getDomain()) {
            this.put(o, 0);
        }
    }

}
