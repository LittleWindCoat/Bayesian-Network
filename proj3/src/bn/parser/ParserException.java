/*
 * File: ParserException.java
 * Creator: George Ferguson
 * Created: Mon Feb 21 20:25:16 2011
 * Time-stamp: <Fri Mar 30 11:29:54 EDT 2012 ferguson>
 */

package bn.parser;

import java.io.IOException;

public class ParserException extends IOException {

    public static final long serialVersionUID = 1L;

    public ParserException(String msg) {
	super(msg);
    }

}
