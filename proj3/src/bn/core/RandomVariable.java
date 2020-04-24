/*
 * File: RandomVariable.java
 * Creator: George Ferguson
 * Created: Sun Mar 25 15:06:21 2012
 * Time-stamp: <Mon Mar 26 10:41:40 EDT 2012 ferguson>
 */

package bn.core;

import java.io.*;

import bn.util.Printable;

/**
 * A RandomVariable in a BayesianNetwork has a name and a Domain of possible
 * values.
 */
public class RandomVariable implements Printable {

    public RandomVariable(String name, Domain domain) {
	this.name = name;
	this.domain = domain;
    }

    public RandomVariable(String name) {
	this(name, new Domain());
    }

    protected String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    protected Domain domain;

    public Domain getDomain() {
	return domain;
    }

    public void setDomain(Domain domain) {
	this.domain = domain;
    }

    // Printable

    /**
     * Print this RandomVariable to the given PrintWriter.
     */
    public void print(PrintWriter out) {
	out.print(name);
    }

    /**
     * Print this RandomVariable to the given PrintStream.
     */
    public void print(PrintStream out) {
	PrintWriter writer = new PrintWriter(out, true);
	print(writer);
	writer.flush();
    }

    /**
     * Print this RandomVariable to System.out.
     */
    public void print() {
	print(System.out);
    }

    /**
     * Return the string representation of this RandomVariable.
     */
    public String toString() {
	StringWriter writer = new StringWriter();
	PrintWriter out = new PrintWriter(writer);
	print(out);
	out.flush();
	return writer.toString();
    }

}
