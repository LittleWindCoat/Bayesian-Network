/*
 * File: Domain.java
 * Creator: George Ferguson
 * Created: Sun Mar 25 15:07:31 2012
 * Time-stamp: <Wed Apr  3 19:34:23 EDT 2013 ferguson>
 */

package bn.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Domain represents an ordered set of possible Values for a Variable.
 * <p>
 * We use a ArrayList here since iteration is the main use for Domains
 * in Bayes nets algorithms.
 */
public class Domain extends ArrayList<Object> {

    public static final long serialVersionUID = 1L;

    public Domain() {
	super();
    }

    public Domain(int size) {
	super(size);
    }

    public Domain(Object... elements) {
	this();
	for (Object o : elements) {
	    add(o);
	}
    }

    public Domain(Collection<Object> collection) {
	this();
	for (Object o : collection) {
	    add(o);
	}
    }
}
