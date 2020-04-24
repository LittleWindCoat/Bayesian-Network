%{
/* -*- Mode: yacc; -*-
 *
 * File: BIFParser.y
 * Creator: George Ferguson
 * Created: Thu Mar 29 13:33:29 2012
 * Time-stamp: <Fri Mar 30 15:44:32 EDT 2012 ferguson>
 *
 * Grammar for Bayesian Network Interchange format (BIF) written in
 * yacc/bison format with semantic actions in Java.
 *
 * http://www.cs.cmu.edu/~fgcozman/Research/InterchangeFormat/Old/xmlbif02.html
 * 3/26/2018: http://sites.poli.usp.br/p/fabio.cozman/Research/InterchangeFormat/xmlbif02.html
 *
 * Note that this ``spec'' allows "A series of variable declaration blocks and
 * probability definition blocks, possibly inter-mixed." But the code that adds
 * the distributions will surely need the variables defined first. The examples
 * I've seen all do the variable first, so let's just stick with that for now.
 *
 * - Example "alarm.bif" parses fine.
 * - Example "insurance.bif" dies when "0" and "1" are used in probability
 *   tables. The BIF spec (such as it is) is quite clear on this:
 *   "A non-negative real is a sequence of numeric characters, containing a
 *   decimal point or an exponent or both. ... there is no overlap between
 *   non-negative integer and reals." However it seems overly pedantic to
 *   enforce this.
 * - Example "diabetes.bif" dies because some of the values for the
 *   variables start with a digit rather than a letter (e.g., "6_0mmol_kg"
 *   for variable "cho_init". The spec is very clear about this also:
 *   "A word is a contiguous sequence of characters, with the restriction that
 *   the first character be a letter. Characters are letters plus numbers plus
 *   the underline symbol (_) plus the dash symbol (-)." Not sure what I can
 *   do about this one. Doesn't seem right to change it.
 */

import java.io.*;
import java.util.*;
%}

/*
 * Bison declarations
 */
%define parser_class_name "BIFParser"
%define public
%define throws ParserException

/* YACC Declarations */
%token WORD
%token DECIMAL_LITERAL
%token FLOATING_POINT_LITERAL
%token NETWORK		/* "network" */ 
%token VARIABLE		/* "variable" */ 
%token PROBABILITY	/* "probability" */ 
%token PROPERTY		/* "property" */ 
%token VARIABLETYPE	/* "type" */ 
%token DISCRETE		/* "discrete" */ 
%token DEFAULTVALUE	/* "default" */
%token TABLEVALUES	/* "table" */ 

%type <VariableContentItemList> VariableContent
%type <VariableContentItemList> VariableContentItemStar
%type <VariableContentItem> VariableContentItem
%type <VariableDiscrete> VariableDiscrete
%type <StringList> VariableValuesList
%type <String> ProbabilityVariableValue
%type <StringList> ProbabilityVariablesList
%type <StringList> ProbabilityVariableNamePlus
%type <String> ProbabilityVariableName
%type <ProbabilityContentEntryList> ProbabilityContent
%type <ProbabilityContentEntryList> ProbabilityContentEntryStar
%type <ProbabilityContentEntry> ProbabilityContentEntry
%type <ProbabilityEntry> ProbabilityEntry
%type <StringList> ProbabilityValuesList
%type <StringList> ProbabilityVariableValuePlus
%type <ProbabilityDefaultEntry> ProbabilityDefaultEntry
%type <ProbabilityTable> ProbabilityTable
%type <DoubleList> FloatingPointList
%type <Double> FloatingPointToken
%type <String> WORD
%type <Integer> DECIMAL_LITERAL
%type <Double> FLOATING_POINT_LITERAL

/*
 * Parser class code
 */
%code {

    /**
     * Construct and return a new BIFParser whose input is the given
     * InputStream.
     * Invokes automatically-generated constructor that takes a Lexer
     * as parameter.
     */
    public BIFParser(InputStream input) {
	this(new BIFLexer(input));
    }

    /**
     * Attempt to recover from a parsing error. This is unlikely to
     * work in its current state, but what the hey.
     */
    public void recoverFromError() throws IOException {
	((BIFLexer)yylexer).recoverFromError();
    }

    /**
     * Parse and return a BayesianNetwork from this BIFParser's
     * input.
     * This method wraps a call to the basic Bison-generated
     * {@link BIFParser#parse} method.
     */
    public BayesianNetwork parseNetwork() throws IOException {
	network = new BayesianNetwork();
	if (parse()) {
	    return network;
	} else {
	    return null;
	}
    }

    /**
     * The BayesianNetwork being constructed by this BIFParser's
     * {@link BIFParser#parse} method.
     */
    protected BayesianNetwork network;

    /**
     * Test driver for BIFParser: Parses given file or stdin, prints
     * resulting BN to stdout.
     */
    public static void main(String argv[]) throws IOException {
	BIFParser parser;
	if (argv.length == 0) {
	    parser = new BIFParser(System.in);
	} else {
	    parser = new BIFParser(new FileInputStream(argv[0]));
	}
	//System.out.println(parser.parse());
	BayesianNetwork bn = parser.parseNetwork();
	bn.print(System.out);
    }

    // Classes used in semantic actions
    // (also can't use generic syntax in Bison %type declaration)

    protected class DoubleList extends ArrayList<Double> {
	public static final long serialVersionUID = 1L;
    }
    protected class StringList extends ArrayList<String> {
	public static final long serialVersionUID = 1L;
    }

    abstract protected class ProbabilityContentEntry {
    }
    protected class ProbabilityEntry extends ProbabilityContentEntry {
	public StringList values;
	public DoubleList probabilities;
	public ProbabilityEntry(StringList values, DoubleList probabilities) {
	    this.values = values;
	    this.probabilities = probabilities;
	}
    }
    protected class ProbabilityDefaultEntry extends ProbabilityContentEntry {
	public DoubleList values;
	public ProbabilityDefaultEntry(DoubleList values) {
	    this.values = values;
	}
    }
    protected class ProbabilityTable extends ProbabilityContentEntry {
	public DoubleList values;
	public ProbabilityTable(DoubleList values) {
	    this.values = values;
	}
    }

    protected class ProbabilityContentEntryList extends ArrayList<ProbabilityContentEntry> {
	public static final long serialVersionUID = 1L;
    }

    abstract protected class VariableContentItem {
    }
    protected class VariableDiscrete extends VariableContentItem {
	public Integer numValues;
	public StringList values;
	public VariableDiscrete(Integer numValues, StringList values) {
	    this.numValues = numValues;
	    this.values = values;
	}
    }
    protected class VariableContentItemList extends ArrayList<VariableContentItem> {
	public static final long serialVersionUID = 1L;
    }

    // Methods used in semantic actions

    protected void defineVariable(String name, VariableContentItemList items) {
	trace("defineVariable: " + name);
	if (network != null) {
	    Domain domain = new Domain(items.size());
	    for (VariableContentItem item : items) {
		if (item instanceof VariableDiscrete) {
		    VariableDiscrete vditem = (VariableDiscrete)item;
		    for (String value : vditem.values) {
			domain.add(value);
		    }
		}
	    }
	    RandomVariable variable = new RandomVariable(name, domain);
	    trace("defineVariable: adding " + variable + ", domain=" + domain);
	    network.add(variable);
	}
    }

    /**
     * entries is a List of:
     *  ProbabilityEntry
     * or
     *  ProbabilityDefaultEntry
     * or
     *  ProbabilityTable
     *<p>
     * Not sure how to handle errors here... Should check manual...
     */
    protected void defineProbability(StringList variableNames, ProbabilityContentEntryList entries) throws ParserException {
	trace("defineProbability: " + variableNames);
	if (network != null) {
	    int nvars = variableNames.size();
	    // Main variable
	    String varName = variableNames.get(0);
	    RandomVariable var = network.getVariableByName(varName);
	    if (var == null) {
		throw new ParserException("can't find variable: " + varName);
	    }
	    trace("defineProbability: for variable: " + var);
	    // Conditioning variables (if any)
	    List<RandomVariable> parents = new ArrayList<RandomVariable>(nvars-1);
	    if (nvars > 0) {
		for (String name : variableNames.subList(1, nvars)) {
		    RandomVariable v = network.getVariableByName(name);
		    if (v == null) {
			throw new ParserException("can't find variable: " + name);
		    } else {
			parents.add(v);
		    }
		}
	    }
	    trace("defineProbability: parents: " + parents);
	    // Probability distribution
	    CPT cpt = new CPT(var, parents);
	    for (ProbabilityContentEntry entry : entries) {
		if (entry instanceof ProbabilityEntry) {
		    ProbabilityEntry pe = (ProbabilityEntry)entry;
		    // List of values for conditioning variables followed
		    // by list of probabilities for the values of first variable
		    Assignment x = new Assignment();
		    Iterator<String> values = pe.values.iterator();
		    for (RandomVariable pvar : parents) {
			x.set(pvar, values.next());
		    }
		    Iterator<Double> ps = pe.probabilities.iterator();
		    for (Object value : var.getDomain()) {
			x.set(var, value);
			double p = ps.next().doubleValue();
			trace("defineProbability: entry: " + x + " = " + p);
			cpt.set(x, p);
		    }
		} else if (entry instanceof ProbabilityDefaultEntry) {
		    // Sorry...
		    throw new ParserException("probability default not implemented!");
		} else if (entry instanceof ProbabilityTable) {
		    ProbabilityTable pt = (ProbabilityTable)entry;
		    Iterator<Double> ptvalues = pt.values.iterator();
		    // Values "in the counting order of the declared variables"
		    // Note this is different than XMLBIF, which does the
		    // ``given'' variable first, then the ``for'' variable
		    for (Object firstVal : var.getDomain()) {
			Assignment x = new Assignment();
			x.put(var, firstVal);
			defineProbabilityTableEntry(cpt, x, parents, ptvalues);
		    }
		}
	    }
	    network.connect(var, parents, cpt);
	}
    }

    /**
     * Recursively traverses parents in the order they were declared (ie.,
     * their order in the list), iterating through each of their values,
     * assigning probabilities from ptvalues at the leaves.
     */
    protected void defineProbabilityTableEntry(CPT cpt, Assignment x, List<RandomVariable> parents, Iterator<Double> ptvalues) {
	if (parents.isEmpty()) {
	    Double p = ptvalues.next().doubleValue();
	    trace("defineProbabilityTableEntry: " + x + " = " + p);
	    cpt.set(x, p);
	} else {
	    RandomVariable var = parents.get(0);
	    for (Object value : var.getDomain()) {
		x.put(var, value);
		defineProbabilityTableEntry(cpt, x, parents.subList(1, parents.size()), ptvalues);
	    }
	    x.remove(var);
	}
    }
	    

    protected void trace(String msg) {
	//System.err.println(msg);
    }
}

%%
/*
 * Grammar follows
 */

CompilationUnit:
    NetworkDeclaration 
    /* ( VariableDeclaration   |    ProbabilityDeclaration  )* */
    ContentDeclarationStar
    /* EOF */
  ;

NetworkDeclaration:
    NETWORK WORD NetworkContent
  ;

NetworkContent:
    '{' PropertyStar '}'
  ;

PropertyStar:
    /* empty */
  | PropertyStar Property
  ;

ContentDeclarationStar:
    /* empty */
  | ContentDeclarationStar ContentDeclaration
  ;

ContentDeclaration:
    VariableDeclaration
  | ProbabilityDeclaration
  ;

VariableDeclaration :
    VARIABLE ProbabilityVariableName VariableContent	{ defineVariable($2, $3); }
  ;

VariableContent:
    /* '{'  ( Property | VariableDiscrete )*   '}' */
    '{'  VariableContentItemStar   '}'		{ $$ = $2; }
  ;

VariableContentItemStar:
    /* empty */					{ $$ = new VariableContentItemList(); }
  | VariableContentItemStar VariableContentItem	{ if ($2 != null) { $1.add($2); } }
  ;

VariableContentItem:
    Property					{ $$ = null; }
  | VariableDiscrete				{ $$ = $1; }
  ;

VariableDiscrete :
    VARIABLETYPE DISCRETE 
      '[' DECIMAL_LITERAL ']' '{'    VariableValuesList    '}' ';'
						{ $$ = new VariableDiscrete($4, $7); }
  ;

VariableValuesList:
    /* ( ProbabilityVariableValue )* */
    /* empty */					{ $$ = new StringList(); }
  | VariableValuesList ProbabilityVariableValue	{ $1.add($2); $$ = $1; }
  ;

ProbabilityVariableValue: WORD			{ $$ = $1; }
  ;

ProbabilityDeclaration:
    PROBABILITY ProbabilityVariablesList ProbabilityContent { defineProbability($2, $3); }
  ;

ProbabilityVariablesList:
      /* "("  ProbabilityVariableName ( ProbabilityVariableName   )* ")" */
    '(' ProbabilityVariableNamePlus ')'		{ $$ = $2; }
  ;

ProbabilityVariableNamePlus:
    ProbabilityVariableName			{ $$ = new StringList(); ((StringList)$$).add($1); }
  | ProbabilityVariableNamePlus ProbabilityVariableName { $1.add($2); $$ = $1; }
  ;

ProbabilityVariableName: WORD			{ $$ = $1; }
  ;

ProbabilityContent:
    /* "{" ( Property | ProbabilityDefaultEntry   | ProbabilityEntry   |   ProbabilityTable  )* "}" */
    '{' ProbabilityContentEntryStar '}'		{ $$ = $2; }
  ;

ProbabilityContentEntry:
    Property					{ $$ = null; }
  | ProbabilityDefaultEntry			{ $$ = $1; }
  | ProbabilityEntry				{ $$ = $1; }
  | ProbabilityTable				{ $$ = $1; }
  ;

ProbabilityContentEntryStar:
    /* empty */					{ $$ = new ProbabilityContentEntryList(); }
  | ProbabilityContentEntryStar ProbabilityContentEntry { if ($2 != null) { $1.add($2); } }
  ;

ProbabilityEntry :
    ProbabilityValuesList FloatingPointList ';'	{ $$ = new ProbabilityEntry($1, $2); }
  ;

ProbabilityValuesList :
    /* "(" ProbabilityVariableValue ( ProbabilityVariableValue   )* ")" */
    '(' ProbabilityVariableValuePlus ')'	{ $$ = $2; }
  ;

ProbabilityVariableValuePlus:
    ProbabilityVariableValue			{ $$ = new StringList(); ((StringList)$$).add($1); }
  | ProbabilityVariableValuePlus ProbabilityVariableValue { $1.add($2); $$ = $1; }
  ;

ProbabilityDefaultEntry :
    /* Spec grammar doesn't say this starts with `default', but I think it does */
    DEFAULTVALUE FloatingPointList ';'		{ $$ = new ProbabilityDefaultEntry($2); }
  ;

ProbabilityTable :
    /* Spec grammar doesn't say this starts with `table', but I think it does */
    TABLEVALUES FloatingPointList ';'		{ $$ = new ProbabilityTable($2); }
  ;

FloatingPointList :
    /* FloatingPointToken  ( FloatingPointToken  )* */
    FloatingPointToken				{ $$ = new DoubleList(); ((DoubleList)$$).add($1); }
  | FloatingPointList FloatingPointToken	{ $1.add($2); $$ = $1; }
  ;

// Spec doesn't allow integers here, but why not (they're used in examples)
FloatingPointToken:
    FLOATING_POINT_LITERAL			{ $$ = $1; }
  | DECIMAL_LITERAL				{ $$ = new Double($1.intValue()); }
  ;

Property:  PROPERTY;

%%
