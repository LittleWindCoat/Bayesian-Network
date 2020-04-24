/*
 * File: Assignment.java
 * Creator: George Ferguson
 * Created: Tue Mar 27 13:03:29 2012
 * Time-stamp: <Wed Apr  3 18:38:46 EDT 2013 ferguson>
 */

package bn.core;

import java.util.*;

/**
 * An Assignment is a list of RandomVariables and their corresponding
 * values (Objects).
 * Currently implementation uses a LinkedHashMap to get a predictable
 * iteration ordering, which is helpful for debugging.
 */
public class Assignment extends LinkedHashMap<RandomVariable,Object> {

    public static final long serialVersionUID = 1L;

    public Assignment() {
	super();
    }

    /**
     * Set the value of the given RandomVariable stored in this Assignment.
     * This method is an alias for {@link LinkedHashMap#put}.
     */
    public void set(RandomVariable var, Object val) {
	put(var, val);
    }

    /**
     * Returns a Set view of the RandomVariables contained in this Assignment.
     * The set is backed by the map.
     * @see HashMap#keySet
     */
    public Set<RandomVariable> variableSet() {
	return keySet();
    }

    /**
     * Returns a shallow copy of this HashMap instance: the keys and
     * values themselves are not cloned.
     * @see LinkedHashMap#clone
     */
    public Assignment copy() {
	return (Assignment)this.clone();
    }

    public String toString() {
	StringBuilder buf = new StringBuilder();
	for (Map.Entry<RandomVariable,Object> entry : entrySet()) {
	    if (buf.length() > 0) {
		buf.append(",");
	    }
	    buf.append(entry.getKey());
	    buf.append("=");
	    buf.append(entry.getValue());
	}
	return buf.toString();
    }
}
