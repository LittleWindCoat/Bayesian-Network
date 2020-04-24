/*
 * File: CPTFormatException.java
 * Creator: George Ferguson
 * Created: Mon Mar 26 12:07:43 2012
 * Time-stamp: <Mon Mar 26 12:09:39 EDT 2012 ferguson>
 */

package bn.parser;

/**
 * Class of exceptions thrown while parsing CPTs from the {@code table}
 * element of a {@code definition} element in an XMLBIF file.
 */
public class CPTFormatException extends IllegalArgumentException {

    public static final long serialVersionUID = 1L;

    public CPTFormatException() {
	super();
    }

}
