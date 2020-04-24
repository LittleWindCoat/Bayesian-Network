/* A Bison parser, made by GNU Bison 2.5.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007-2011 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* First part of user declarations.  */

/* Line 32 of lalr1.java  */
/* Line 1 of "BIFParser.y"  */

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

package bn.parser;

import java.io.*;
import java.util.*;
import bn.core.*;

/**
 * A Bison parser, automatically generated from <tt>BIFParser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class BIFParser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.5";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int WORD = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int DECIMAL_LITERAL = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOATING_POINT_LITERAL = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int NETWORK = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int VARIABLE = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int PROBABILITY = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int PROPERTY = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int VARIABLETYPE = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int DISCRETE = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int DEFAULTVALUE = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int TABLEVALUES = 268;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>BIFParser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror (String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public BIFParser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws java.io.IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    ) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      pop (1);
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height + 1, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  // Internal return codes that are not supported for user semantic
  // actions.
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) throws ParserException
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 11:
  if (yyn == 11)
    
/* Line 351 of lalr1.java  */
/* Line 348 of "BIFParser.y"  */
    { defineVariable(((String)(yystack.valueAt (3-(2)))), ((VariableContentItemList)(yystack.valueAt (3-(3))))); };
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 351 of lalr1.java  */
/* Line 353 of "BIFParser.y"  */
    { yyval = ((VariableContentItemList)(yystack.valueAt (3-(2)))); };
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 351 of lalr1.java  */
/* Line 357 of "BIFParser.y"  */
    { yyval = new VariableContentItemList(); };
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 351 of lalr1.java  */
/* Line 358 of "BIFParser.y"  */
    { if (((VariableContentItem)(yystack.valueAt (2-(2)))) != null) { ((VariableContentItemList)(yystack.valueAt (2-(1)))).add(((VariableContentItem)(yystack.valueAt (2-(2))))); } };
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 351 of lalr1.java  */
/* Line 362 of "BIFParser.y"  */
    { yyval = null; };
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 351 of lalr1.java  */
/* Line 363 of "BIFParser.y"  */
    { yyval = ((VariableDiscrete)(yystack.valueAt (1-(1)))); };
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 351 of lalr1.java  */
/* Line 369 of "BIFParser.y"  */
    { yyval = new VariableDiscrete(((Integer)(yystack.valueAt (9-(4)))), ((StringList)(yystack.valueAt (9-(7))))); };
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 351 of lalr1.java  */
/* Line 374 of "BIFParser.y"  */
    { yyval = new StringList(); };
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 351 of lalr1.java  */
/* Line 375 of "BIFParser.y"  */
    { ((StringList)(yystack.valueAt (2-(1)))).add(((String)(yystack.valueAt (2-(2))))); yyval = ((StringList)(yystack.valueAt (2-(1)))); };
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 351 of lalr1.java  */
/* Line 378 of "BIFParser.y"  */
    { yyval = ((String)(yystack.valueAt (1-(1)))); };
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 351 of lalr1.java  */
/* Line 382 of "BIFParser.y"  */
    { defineProbability(((StringList)(yystack.valueAt (3-(2)))), ((ProbabilityContentEntryList)(yystack.valueAt (3-(3))))); };
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 351 of lalr1.java  */
/* Line 387 of "BIFParser.y"  */
    { yyval = ((StringList)(yystack.valueAt (3-(2)))); };
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 351 of lalr1.java  */
/* Line 391 of "BIFParser.y"  */
    { yyval = new StringList(); ((StringList)yyval).add(((String)(yystack.valueAt (1-(1))))); };
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 351 of lalr1.java  */
/* Line 392 of "BIFParser.y"  */
    { ((StringList)(yystack.valueAt (2-(1)))).add(((String)(yystack.valueAt (2-(2))))); yyval = ((StringList)(yystack.valueAt (2-(1)))); };
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 351 of lalr1.java  */
/* Line 395 of "BIFParser.y"  */
    { yyval = ((String)(yystack.valueAt (1-(1)))); };
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 351 of lalr1.java  */
/* Line 400 of "BIFParser.y"  */
    { yyval = ((ProbabilityContentEntryList)(yystack.valueAt (3-(2)))); };
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 351 of lalr1.java  */
/* Line 404 of "BIFParser.y"  */
    { yyval = null; };
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 351 of lalr1.java  */
/* Line 405 of "BIFParser.y"  */
    { yyval = ((ProbabilityDefaultEntry)(yystack.valueAt (1-(1)))); };
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 351 of lalr1.java  */
/* Line 406 of "BIFParser.y"  */
    { yyval = ((ProbabilityEntry)(yystack.valueAt (1-(1)))); };
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 351 of lalr1.java  */
/* Line 407 of "BIFParser.y"  */
    { yyval = ((ProbabilityTable)(yystack.valueAt (1-(1)))); };
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 351 of lalr1.java  */
/* Line 411 of "BIFParser.y"  */
    { yyval = new ProbabilityContentEntryList(); };
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 351 of lalr1.java  */
/* Line 412 of "BIFParser.y"  */
    { if (((ProbabilityContentEntry)(yystack.valueAt (2-(2)))) != null) { ((ProbabilityContentEntryList)(yystack.valueAt (2-(1)))).add(((ProbabilityContentEntry)(yystack.valueAt (2-(2))))); } };
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 351 of lalr1.java  */
/* Line 416 of "BIFParser.y"  */
    { yyval = new ProbabilityEntry(((StringList)(yystack.valueAt (3-(1)))), ((DoubleList)(yystack.valueAt (3-(2))))); };
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 351 of lalr1.java  */
/* Line 421 of "BIFParser.y"  */
    { yyval = ((StringList)(yystack.valueAt (3-(2)))); };
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 351 of lalr1.java  */
/* Line 425 of "BIFParser.y"  */
    { yyval = new StringList(); ((StringList)yyval).add(((String)(yystack.valueAt (1-(1))))); };
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 351 of lalr1.java  */
/* Line 426 of "BIFParser.y"  */
    { ((StringList)(yystack.valueAt (2-(1)))).add(((String)(yystack.valueAt (2-(2))))); yyval = ((StringList)(yystack.valueAt (2-(1)))); };
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 351 of lalr1.java  */
/* Line 431 of "BIFParser.y"  */
    { yyval = new ProbabilityDefaultEntry(((DoubleList)(yystack.valueAt (3-(2))))); };
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 351 of lalr1.java  */
/* Line 436 of "BIFParser.y"  */
    { yyval = new ProbabilityTable(((DoubleList)(yystack.valueAt (3-(2))))); };
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 351 of lalr1.java  */
/* Line 441 of "BIFParser.y"  */
    { yyval = new DoubleList(); ((DoubleList)yyval).add(((Double)(yystack.valueAt (1-(1))))); };
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 351 of lalr1.java  */
/* Line 442 of "BIFParser.y"  */
    { ((DoubleList)(yystack.valueAt (2-(1)))).add(((Double)(yystack.valueAt (2-(2))))); yyval = ((DoubleList)(yystack.valueAt (2-(1)))); };
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 351 of lalr1.java  */
/* Line 447 of "BIFParser.y"  */
    { yyval = ((Double)(yystack.valueAt (1-(1)))); };
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 351 of lalr1.java  */
/* Line 448 of "BIFParser.y"  */
    { yyval = new Double(((Integer)(yystack.valueAt (1-(1)))).intValue()); };
  break;
    



/* Line 351 of lalr1.java  */
/* Line 623 of "BIFParser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws java.io.IOException, ParserException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yy_pact_value_is_default_ (yyn))
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yy_table_value_is_error_ (yyn))
	      label = YYERRLAB;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs_;
            if (yychar == yyempty_)
              yytoken = yyempty_;
            yyerror (yysyntax_error (yystate, yytoken));
          }

        
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (!yy_pact_value_is_default_ (yyn))
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;

	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);

        yystate = yyn;
	yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        /* There are many possibilities here to consider:
           - Assume YYFAIL is not used.  It's too flawed to consider.
             See
             <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
             for details.  YYERROR is fine as it does not invoke this
             function.
           - If this state is a consistent state with a default action,
             then the only way this function was invoked is if the
             default action is an error action.  In that case, don't
             check for expected tokens because there are none.
           - The only way there can be no lookahead present (in tok) is
             if this state is a consistent state with a default action.
             Thus, detecting the absence of a lookahead is sufficient to
             determine that there is no unexpected or expected token to
             report.  In that case, just report a simple "syntax error".
           - Don't assume there isn't a lookahead just because this
             state is a consistent state with a default action.  There
             might have been a previous inconsistent state, consistent
             state with a non-default action, or user semantic action
             that manipulated yychar.  (However, yychar is currently out
             of scope during semantic actions.)
           - Of course, the expected token list depends on states to
             have correct lookahead information, and it depends on the
             parser not to perform extra reductions after fetching a
             lookahead from the scanner and before detecting a syntax
             error.  Thus, state merging (from LALR or IELR) and default
             reductions corrupt the expected token list.  However, the
             list is correct for canonical LR with one exception: it
             will still contain any token that will not be accepted due
             to an error action in a later state.
        */
        if (tok != yyempty_)
          {
            // FIXME: This method of building the message is not compatible
            // with internationalization.
            StringBuffer res =
              new StringBuffer ("syntax error, unexpected ");
            res.append (yytnamerr_ (yytname_[tok]));
            int yyn = yypact_[yystate];
            if (!yy_pact_value_is_default_ (yyn))
              {
                /* Start YYX at -YYN if negative to avoid negative
                   indexes in YYCHECK.  In other words, skip the first
                   -YYN actions for this state because they are default
                   actions.  */
                int yyxbegin = yyn < 0 ? -yyn : 0;
                /* Stay within bounds of both yycheck and yytname.  */
                int yychecklim = yylast_ - yyn + 1;
                int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
                int count = 0;
                for (int x = yyxbegin; x < yyxend; ++x)
                  if (yycheck_[x + yyn] == x && x != yyterror_
                      && !yy_table_value_is_error_ (yytable_[x + yyn]))
                    ++count;
                if (count < 5)
                  {
                    count = 0;
                    for (int x = yyxbegin; x < yyxend; ++x)
                      if (yycheck_[x + yyn] == x && x != yyterror_
                          && !yy_table_value_is_error_ (yytable_[x + yyn]))
                        {
                          res.append (count++ == 0 ? ", expecting " : " or ");
                          res.append (yytnamerr_ (yytname_[x]));
                        }
                  }
              }
            return res.toString ();
          }
      }

    return "syntax error";
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yy_pact_value_is_default_ (int yyvalue)
  {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code> value indicates a syntax error.
   * @param yyvalue   the value to check
   */
  private static boolean yy_table_value_is_error_ (int yyvalue)
  {
    return yyvalue == yytable_ninf_;
  }

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final byte yypact_ninf_ = -53;
  private static final byte yypact_[] =
  {
         6,    17,    24,   -53,    22,   -53,    34,   -53,   -53,    36,
       8,   -53,   -53,   -53,    25,   -53,    32,    36,    33,   -53,
     -53,   -53,   -53,   -53,    -3,   -53,   -53,   -53,    20,   -53,
     -53,    13,    37,   -53,   -53,   -53,   -53,    40,    40,   -53,
      46,   -53,   -53,    40,   -53,   -53,   -53,    35,   -53,   -53,
       1,   -53,     3,   -53,   -53,    -2,     5,    48,   -53,   -53,
     -53,   -53,   -53,   -53,    38,    39,   -53,    -1,    41,   -53,
     -53
  };

  /* YYDEFACT[S] -- default reduction number in state S.  Performed when
     YYTABLE doesn't specify something else to do.  Zero means the
     default is an error.  */
  private static final byte yydefact_[] =
  {
         0,     0,     0,     7,     0,     1,     2,     5,     3,     0,
       0,     8,     9,    10,     0,    25,     0,     0,     0,    43,
       4,     6,    13,    11,     0,    23,    31,    21,     0,    22,
      24,     0,     0,    12,    14,    16,    15,     0,     0,    26,
       0,    32,    29,     0,    28,    30,    27,     0,    42,    41,
       0,    39,     0,    20,    35,     0,     0,     0,    37,    40,
      38,    34,    36,    33,     0,     0,    18,     0,     0,    19,
      17
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
       -53,   -53,   -53,   -53,   -53,   -53,   -53,   -53,   -53,   -53,
     -53,   -53,   -53,   -52,   -53,   -53,   -53,   -13,   -53,   -53,
     -53,   -53,   -53,   -53,   -53,   -53,     0,   -19,   -15
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final byte
  yydefgoto_[] =
  {
        -1,     2,     3,     8,    14,     6,    11,    12,    23,    28,
      34,    35,    67,    54,    13,    18,    24,    16,    27,    41,
      31,    42,    43,    55,    44,    45,    50,    51,    21
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If YYTABLE_NINF_, syntax error.  */
  private static final byte yytable_ninf_ = -1;
  private static final byte
  yytable_[] =
  {
        15,    53,    53,    62,    25,    48,    49,    48,    49,    48,
      49,    30,     1,    36,    68,    69,    46,    29,    61,    58,
       4,    60,    19,    63,     5,    37,    38,    17,    39,    19,
      32,    59,    40,    59,    19,    33,     7,    59,    52,    15,
      20,     9,    10,    56,    48,    49,    22,    26,    47,    53,
       0,    57,    64,    66,     0,    65,     0,     0,     0,    70
  };

  /* YYCHECK.  */
  private static final byte
  yycheck_[] =
  {
         3,     3,     3,    55,    17,     4,     5,     4,     5,     4,
       5,    24,     6,    28,    15,    67,    31,    20,    20,    18,
       3,    18,     9,    18,     0,    12,    13,    19,    15,     9,
      10,    50,    19,    52,     9,    15,    14,    56,    38,     3,
      15,     7,     8,    43,     4,     5,    14,    14,    11,     3,
      -1,    16,     4,    14,    -1,    17,    -1,    -1,    -1,    18
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     6,    22,    23,     3,     0,    26,    14,    24,     7,
       8,    27,    28,    35,    25,     3,    38,    19,    36,     9,
      15,    49,    14,    29,    37,    38,    14,    39,    30,    20,
      38,    41,    10,    15,    31,    32,    49,    12,    13,    15,
      19,    40,    42,    43,    45,    46,    49,    11,     4,     5,
      47,    48,    47,     3,    34,    44,    47,    16,    18,    48,
      18,    20,    34,    18,     4,    17,    14,    33,    15,    34,
      18
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   123,   125,    91,    93,    59,    40,
      41
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    21,    22,    23,    24,    25,    25,    26,    26,    27,
      27,    28,    29,    30,    30,    31,    31,    32,    33,    33,
      34,    35,    36,    37,    37,    38,    39,    40,    40,    40,
      40,    41,    41,    42,    43,    44,    44,    45,    46,    47,
      47,    48,    48,    49
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     2,     3,     3,     0,     2,     0,     2,     1,
       1,     3,     3,     0,     2,     1,     1,     9,     0,     2,
       1,     3,     3,     1,     2,     1,     3,     1,     1,     1,
       1,     0,     2,     3,     3,     1,     2,     3,     3,     1,
       2,     1,     1,     1
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "WORD", "DECIMAL_LITERAL",
  "FLOATING_POINT_LITERAL", "NETWORK", "VARIABLE", "PROBABILITY",
  "PROPERTY", "VARIABLETYPE", "DISCRETE", "DEFAULTVALUE", "TABLEVALUES",
  "'{'", "'}'", "'['", "']'", "';'", "'('", "')'", "$accept",
  "CompilationUnit", "NetworkDeclaration", "NetworkContent",
  "PropertyStar", "ContentDeclarationStar", "ContentDeclaration",
  "VariableDeclaration", "VariableContent", "VariableContentItemStar",
  "VariableContentItem", "VariableDiscrete", "VariableValuesList",
  "ProbabilityVariableValue", "ProbabilityDeclaration",
  "ProbabilityVariablesList", "ProbabilityVariableNamePlus",
  "ProbabilityVariableName", "ProbabilityContent",
  "ProbabilityContentEntry", "ProbabilityContentEntryStar",
  "ProbabilityEntry", "ProbabilityValuesList",
  "ProbabilityVariableValuePlus", "ProbabilityDefaultEntry",
  "ProbabilityTable", "FloatingPointList", "FloatingPointToken",
  "Property", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        22,     0,    -1,    23,    26,    -1,     6,     3,    24,    -1,
      14,    25,    15,    -1,    -1,    25,    49,    -1,    -1,    26,
      27,    -1,    28,    -1,    35,    -1,     7,    38,    29,    -1,
      14,    30,    15,    -1,    -1,    30,    31,    -1,    49,    -1,
      32,    -1,    10,    11,    16,     4,    17,    14,    33,    15,
      18,    -1,    -1,    33,    34,    -1,     3,    -1,     8,    36,
      39,    -1,    19,    37,    20,    -1,    38,    -1,    37,    38,
      -1,     3,    -1,    14,    41,    15,    -1,    49,    -1,    45,
      -1,    42,    -1,    46,    -1,    -1,    41,    40,    -1,    43,
      47,    18,    -1,    19,    44,    20,    -1,    34,    -1,    44,
      34,    -1,    12,    47,    18,    -1,    13,    47,    18,    -1,
      48,    -1,    47,    48,    -1,     5,    -1,     4,    -1,     9,
      -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final byte yyprhs_[] =
  {
         0,     0,     3,     6,    10,    14,    15,    18,    19,    22,
      24,    26,    30,    34,    35,    38,    40,    42,    52,    53,
      56,    58,    62,    66,    68,    71,    73,    77,    79,    81,
      83,    85,    86,    89,    93,    97,    99,   102,   106,   110,
     112,   115,   117,   119
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   318,   318,   325,   329,   332,   334,   337,   339,   343,
     344,   348,   353,   357,   358,   362,   363,   367,   374,   375,
     378,   382,   387,   391,   392,   395,   400,   404,   405,   406,
     407,   411,   412,   416,   421,   425,   426,   431,   436,   441,
     442,   447,   448,   451
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      19,    20,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    18,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    16,     2,    17,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    14,     2,    15,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 59;
  private static final int yynnts_ = 29;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 5;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 21;

  private static final int yyuser_token_number_max_ = 268;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 927 of lalr1.java  */
/* Line 85 of "BIFParser.y"  */


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



/* Line 927 of lalr1.java  */
/* Line 1514 of "BIFParser.java"  */

}


/* Line 931 of lalr1.java  */
/* Line 453 of "BIFParser.y"  */


