/*
 * File: Printable.java
 * Creator: George Ferguson
 * Created: Thu Mar  8 17:09:36 2012
 * Time-stamp: <Thu Mar  8 17:39:53 EST 2012 ferguson>
 */

package bn.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Interface implemented by classes that can be printed to a PrintStream
 * or a PrintWriter, such as System.out or System.err.
 * <p>
 * This can be way more efficient than consing up a huge string using
 * StringBuffers or StringBuilders. You did know that that's how Java
 * implements the ``+'' operator on Strings, right? Instead we write
 * these methods, and if we actually want the string for toString(),
 * we let it create a StringOutputStream to collect it using these
 * methods.
 * <p>
 * As a historical aside, when you look at the code that has to be
 * duplicated across classes to implement these methods, you come
 * to appreciate why printing is the canonical example of a ``mix-in''
 * class. Which of course Java doesn't have...
 */
public interface Printable {

    /**
     * Print this Symbol to the given PrintWriter.
     */
    public void print(PrintWriter out);

    /**
     * Print this Symbol to the given PrintStream.
     */
    public void print(PrintStream out);

    /**
     * Print this Symbol to System.out.
     */
    public void print();

}

