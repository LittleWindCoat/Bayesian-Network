/*
 * File: BIFLexer.java
 * Creator: George Ferguson
 * Created: Fri Mar 30 10:18:51 2012
 * Time-stamp: <Fri Mar 30 14:07:41 EDT 2012 ferguson>
 */

package bn.parser;

import java.io.*;

/**
 * Scanner for the Bison-generate BIFParser.
 * <q>The scanner has to implement the Lexer inner interface of the parser
 * class</q> (Bison manual, Section 10.2.5).
 * Token values are accessible as public static final ints in the
 * parser class.
 */
public class BIFLexer implements BIFParser.Lexer {

    /**
     * Construct and return a new BIFLexer reading from the given
     * InputStream. It would be easy to add variations on this,
     * or even make it so you could change the input on the fly.
     */
    public BIFLexer(InputStream input) {
	this.input = new PushbackReader(new InputStreamReader(input));
    }

    /**
     * The input to this BIFLxer, as a PushbackReader.
     */
    protected PushbackReader input;

    /**
     * Lexer method: Used to report an error message.
     * As has been the case since the days of yacc, this is pretty useless
     * since it only ever gets "syntax error" and can't throw an exception
     * anyway because of the signature in the Lexer interface.
     * But has to be defined.
     */
    public void yyerror(String s) {
    }

    /**
     * The semantic value associated with the current lexical token.
     */
    protected Object yylval;

    /**
     * Return the semantic value associated with the current lexical token.
     */
    public Object getLVal() {
	return yylval;
    }

    /**
     * Buffer used while reading tokens.
     */
    protected StringBuilder buf = new StringBuilder();

    /**
     * Lexer method: Return the next token and set yylval appropriately.
     * Returns 0 on EOF, -1 or error, otherwise a token code (>0).
     */
    public int yylex() throws IOException {
	trace("yylex: scanning...");
	int i;
	char ch;
	// Whitespace, comments, and ignorables
	while (true) {
	    i = readChar();
	    if (i == -1) {
		return BIFParser.EOF;
	    }
	    ch = (char)i;
	    if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r' ||
		ch == ',' || ch == '|') {
		continue;
	    } else if (ch == '/') {
		int i2 = readChar();
		if (i != -1) {
		    char c2 = (char)i2;
		    if (c2 == '/') {
			// C++-type comment: skip to end of line
			do {
			    i = readChar();
			    if (i == -1) {
				break;
			    }
			    ch = (char)i;
			} while (ch != '\n');
			continue;
		    } else if (c2 == '*') {
			// C-type comment: skip to "*/"
			boolean star = false;
			while (true) {
			    i = readChar();
			    if (i == -1) {
				break;
			    }
			    ch = (char)i;
			    if (ch == '/' && star) {
				continue;
			    } else if (ch == '*') {
				star = true;
			    } else {
				star = false;
			    }
			}
			continue;
		    }
		    unreadChar(c2);
		    break;
		}
	    } else {
		break;
	    }
	}
	trace("yylex: ch='" + ch + "'");
	if (ch == ';' || ch == '{' || ch == '}' ||
	    ch == '(' || ch == ')' || ch == '[' || ch == ']') {
	    trace("yylex: returning punctuation: " + ch);
	    return ch;
	} else if (isDigit(ch) || ch == '.') {
	    // Number
	    trace("yylex: reading number");
	    int n = 0;		// integer or mantissa of float
	    double f = 0.0;	// decimal fraction of float
	    int e = 0;		// exponent of float if any
	    boolean isFloat = false;
	    if (isDigit(ch)) {
		n = digitValue(ch);
		while (true) {
		    i = readChar();
		    if (i != -1) {
			ch = (char)i;
			if (isDigit(ch)) {
			    n = n * 10 + digitValue(ch);
			} else {
			    break;
			}
		    }
		}
	    }
	    trace("yylex: read initial number: " + n);
	    if (ch == '.') {
		isFloat = true;
		trace("yylex: reading fraction");
		double pow = 10.0;
		while (true) {
		    i = readChar();
		    if (i != -1) {
			ch = (char)i;
			if (isDigit(ch)) {
			    f += digitValue(ch) / pow;
			    pow *= 10;
			} else {
			    break;
			}
		    }
		}
		trace("yylex: read fraction: " + f);
	    }
	    if (n > 0 && (ch == 'e' || ch == 'E')) {
		isFloat = true;
		trace("yylex: reading exponent");
		int sign = 1;
		i = readChar();
		if (i == -1) {
		    error("bad exponent");
		} else {
		    ch = (char)i;
		    if (ch == '+') {
			sign = 1;
		    } else if (ch == '-') {
			sign = -1;
		    } else {
			unreadChar(ch);
		    }
		}
		while (true) {
		    i = readChar();
		    if (i != -1) {
			ch = (char)i;
			if (isDigit(ch)) {
			    e = e * 10 + digitValue(ch);
			} else {
			    break;
			}
		    }
		}
		e *= sign;
		trace("yylex: read exponent: " + e);
	    }
	    unreadChar(ch);
	    if (isFloat) {
		double d = (n + f) * Math.pow(10, e);
		yylval = new Double(d);
		trace("yylex: returning FLOATING_POINT_LITERAL: " + yylval);
		return BIFParser.FLOATING_POINT_LITERAL;
	    } else {
		yylval = new Integer(n);
		trace("yylex: returning DECIMAL_LITERAL: " + yylval);
		return BIFParser.DECIMAL_LITERAL;
	    }
	} else if (!isLetter(ch)) {
	    error("bad character: " + ch);
	}
	// Need to read a WORD (possibly a keyword)
	buf.setLength(0);
	buf.append(ch);
	while (true) {
	    i = readChar();
	    if (i == -1) {
		break;
	    }
	    ch = (char)i;
	    if (!isLetter(ch) && !isDigit(ch)) {
		unreadChar(ch);
		break;
	    }
	    buf.append(ch);
	    trace("yylex: storing character: " + ch);
	}
	String str = buf.toString();
	// Check for keywords
	if (str.equalsIgnoreCase("network")) {
	    trace("yylex: returning NETWORK");
	    return BIFParser.NETWORK;
	} else if (str.equalsIgnoreCase("variable")) {
	    trace("yylex: returning VARIABLE");
	    return BIFParser.VARIABLE;
	} else if (str.equalsIgnoreCase("probability")) {
	    trace("yylex: returning PROBABILITY");
	    return BIFParser.PROBABILITY;
	} else if (str.equalsIgnoreCase("property")) {
	    // Complication: spec says property is text up to semi-colon (yuck)
	    while (true) {
		buf.setLength(0);
		i = readChar();
		if (i == -1) {
		    break;
		}
		ch = (char)i;
		if (ch == ';') {
		    break;
		} else {
		    buf.append(ch);
		}
	    }
	    yylval = buf.toString();
	    trace("yylex: returning PROPERTY");
	    return BIFParser.PROPERTY;
	} else if (str.equalsIgnoreCase("type")) {
	    trace("yylex: returning VARABLETYPE");
	    return BIFParser.VARIABLETYPE;
	} else if (str.equalsIgnoreCase("discrete")) {
	    trace("yylex: returning DISCRETE");
	    return BIFParser.DISCRETE;
	} else if (str.equalsIgnoreCase("default")) {
	    trace("yylex: returning DEFAULTVALUE");
	    return BIFParser.DEFAULTVALUE;
	} else if (str.equalsIgnoreCase("table")) {
	    trace("yylex: returning TABLEVALUES");
	    return BIFParser.TABLEVALUES;
	}
	// Otherwise its a WORD
	yylval = str;
	trace("yylex: returning WORD: \"" + yylval + "\"");
	return BIFParser.WORD;
    }

    /**
     * Report an error by throwing a ParserException.
     * Probably should do something different now that we're in bison.
     */
    protected void error(String msg) throws ParserException {
	msg = Integer.toString(lineNum) + ":" + Integer.toString(charNum) + ": " + msg;
	throw new ParserException(msg);
    }

    protected int lineNum = 1;
    protected int charNum = 0;
    protected int lastLineLen = 0;

    /**
     * Read and return the next character from the input, keeping track
     * of line and character locations for error reporting.
     */
    protected int readChar() throws IOException {
	int i = input.read();
	if (i == -1) {
	    trace("readChar: EOF");
	    return i;
	} else if (i == '\n') {
	    lineNum += 1;
	    lastLineLen = charNum;
	    charNum = 0;
	} else {
	    charNum += 1;
	}
	trace("readChar: '" + (char)i + "' @ " + lineNum + ":" + charNum);
	return i;
    }

    /**
     * Push the given character back onto the input, keeping track
     * of line and character locations for error reporting.
     */
    protected void unreadChar(char ch) throws IOException {
	input.unread(ch);
	if (ch == '\n') {
	    lineNum -=1;
	    charNum = lastLineLen;
	} else {
	    charNum -= 1;
	}
	trace("unreadChar: " + lineNum + ":" + charNum + ": " + ch);
    }

    /**
     * Return true if given character is a <q>letter</q> as defined by
     * the BIF specification: {@code ["a"-"z","A"-"Z","_","-"]}.
     */
    protected boolean isLetter(char ch) {
	return Character.isLetter(ch) || ch == '-' || ch == '_';
    }

    /**
     * Return true if given character is a decimal digit.
     */
    protected boolean isDigit(char ch) {
	return Character.isDigit(ch);
    }

    /**
     * Return value of given character as a decimal digit (i.e., number
     * from 0 to 9).
     */
    protected int digitValue(char ch) {
	return Character.digit(ch, 10);
    }

    /**
     * Attempt to recover from the previous error, whetever it was.
     * The strategy is to read up to and including a semi-colon. It
     * might work (for simple errors).
     */
    public void recoverFromError() throws IOException {
	int i;
	do {
	    i = readChar();
	} while (i != -1 && i != ';');
    }

    /**
     * Tracing for debugging.
     */
    protected void trace(String msg) {
	//System.err.println(msg);
    }

}
