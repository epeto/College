/* The following code was generated by JFlex 1.4.3 on 3/12/18 04:29 AM */

package ast;
import java.util.Stack;
import java.io.FileWriter;
import java.io.BufferedWriter;
import ast.patron.compuesto.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 3/12/18 04:29 AM from the specification file
 * <tt>/home/emmanuel/Documentos/Compiladores/Compiladores-2019-1/Proyectos/Proyecto_3/src/main/jflex/Atomos.jflex</tt>
 */
public class Flexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int I = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\36\1\37\2\0\1\40\22\0\1\35\1\33\1\27\1\41"+
    "\1\0\1\30\2\0\1\42\1\42\1\31\1\30\1\0\1\30\1\26"+
    "\1\32\1\24\11\25\1\42\1\0\1\33\1\34\1\33\2\0\5\22"+
    "\1\5\15\22\1\1\6\22\4\0\1\23\1\0\1\6\2\22\1\12"+
    "\1\4\1\20\1\22\1\16\1\17\2\22\1\7\1\22\1\11\1\13"+
    "\1\21\1\22\1\2\1\10\1\14\1\3\1\22\1\15\3\22\uff85\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\12\2\2\3\1\1\4\4\1\5\2\6"+
    "\1\1\1\7\1\10\1\11\1\12\1\13\5\2\1\14"+
    "\2\2\1\15\1\0\1\16\1\0\1\17\1\0\5\2"+
    "\1\20";

  private static int [] zzUnpackAction() {
    int [] result = new int[49];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\43\0\106\0\151\0\214\0\257\0\322\0\365"+
    "\0\u0118\0\u013b\0\u015e\0\u0181\0\u01a4\0\u01c7\0\u01ea\0\u020d"+
    "\0\106\0\u0230\0\u0253\0\u0276\0\106\0\106\0\u0299\0\u02bc"+
    "\0\106\0\106\0\u02df\0\u02df\0\106\0\u0302\0\u0325\0\u0348"+
    "\0\u036b\0\u038e\0\214\0\u03b1\0\u03d4\0\u03f7\0\u020d\0\106"+
    "\0\u02bc\0\106\0\u02df\0\u041a\0\u043d\0\u0460\0\u0483\0\u04a6"+
    "\0\214";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[49];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\2\5\1\11"+
    "\1\5\1\12\1\5\1\13\1\5\1\14\1\5\1\15"+
    "\1\5\1\3\1\16\1\17\1\3\1\20\1\21\1\22"+
    "\1\23\2\24\2\25\1\26\1\27\1\30\1\31\35\32"+
    "\1\33\1\34\1\35\3\32\44\0\1\5\1\36\23\5"+
    "\16\0\25\5\16\0\6\5\1\37\16\5\16\0\5\5"+
    "\1\40\17\5\16\0\10\5\1\41\14\5\16\0\12\5"+
    "\1\42\12\5\16\0\1\5\1\43\23\5\16\0\15\5"+
    "\1\44\7\5\16\0\17\5\1\43\5\5\16\0\1\5"+
    "\1\45\23\5\43\0\1\46\40\0\2\17\1\46\14\0"+
    "\27\47\1\50\13\47\31\0\1\21\43\0\1\21\44\0"+
    "\1\21\45\0\1\26\3\0\37\51\1\52\3\51\35\0"+
    "\2\53\1\35\4\0\2\5\1\54\22\5\16\0\7\5"+
    "\1\55\6\5\1\14\6\5\16\0\6\5\1\56\16\5"+
    "\16\0\11\5\1\43\13\5\16\0\13\5\1\43\11\5"+
    "\16\0\16\5\1\57\6\5\16\0\16\5\1\60\6\5"+
    "\41\0\2\46\16\0\3\5\1\61\21\5\16\0\3\5"+
    "\1\43\21\5\16\0\7\5\1\54\15\5\16\0\6\5"+
    "\1\55\16\5\16\0\10\5\1\42\14\5\15\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1225];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\15\1\1\11\3\1\2\11\2\1\2\11"+
    "\2\1\1\11\11\1\1\0\1\11\1\0\1\11\1\0"+
    "\6\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[49];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
//Aquí empieza el código de Java
public String resultado = ""; //Resultado del análisis léxico.
public int num_espacios = 0; //Cuenta el número de espacios.
public Stack<Integer> pila = new Stack();
public int num_linea = 1;
public boolean dperr = false;
private Parser yyparser;

public Flexer(java.io.Reader r, Parser yyparser) {
  this(r);
  this.yyparser = yyparser;
}

public void agrRes(String s){
    if(!dperr){
        resultado+=s;
    }
}

public short reset(){ //Se va a ejecutar cada vez que se lea un caracter diferente al espacio en una nueva línea.
short retorno=0; //Valor de retorno
    if(yystate()==I && num_espacios>0){ //Si estaba en estado I (indentación).
        if(pila.isEmpty()){
            pila.push(num_espacios);
            agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
            retorno = Parser.INDENTA;
        }else{
            if(pila.peek()<num_espacios){
                pila.push(num_espacios);
                agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
                retorno = Parser.INDENTA;
            }else{
                while(!pila.isEmpty() && pila.peek() > num_espacios){
                    agrRes("DEINDENTA("+pila.pop()+")\n");
                    retorno = Parser.DEINDENTA;
                }

                if(pila.isEmpty() || pila.peek() < num_espacios ){ //En este punto el tope de la pila debería ser igual a num_espacios.
                    agrRes("Error de indentación en la línea "+num_linea);
                    dperr = true;
                }
            }
        }
    }else{
        if(yystate()==I){ //Si el número de espacios era 0
            while(!pila.isEmpty()){
                agrRes("DEINDENTA("+pila.pop()+")\n");
                retorno = Parser.DEINDENTA;
            }
        }
    }

    yybegin(YYINITIAL);
    num_espacios = 0;
    return retorno;
}

//Función que cambia el operador por un token para el parser
int operador(String op){
    switch(op){
        case "+":
        case "-":
        case "*":
        case "/":
        case "%":
        case "=":
        case "<":
        case "!":
        case ">": return (int) yycharat(0);
        case "**": return Parser.EXP;
        case "//": return Parser.DIV;
        case ">=": return Parser.MAIGUAL;
        case "<=": return Parser.MEIGUAL;
        case "==": return Parser.IGUAL;
        case "!=": return Parser.DIF;
    }
    return -1;
}

int reservada(String re){
    switch(re){
        case "and": return Parser.AND;
        case "or": return Parser.OR;
        case "not": return Parser.NOT;
        case "while": return Parser.WHILE;
        case "if": return Parser.IF;
        case "else": return Parser.ELSE;
        case "elif": return Parser.ELIF;
        case "print": return Parser.PRINT;
    }
    return -1;
}

//Aquí termina el código de Java


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Flexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Flexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 120) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { dperr = true;
          }
        case 17: break;
        case 14: 
          { yyparser.yylval = new StringHoja(yytext());
                      return Parser.CADENA;
          }
        case 18: break;
        case 12: 
          { return reservada(yytext());
          }
        case 19: break;
        case 3: 
          { yyparser.yylval = new IntHoja(Integer.parseInt(yytext()));
                      return Parser.ENTERO;
          }
        case 20: break;
        case 8: 
          { yypushback(1); short val = reset(); if(val!=0){return val;}
          }
        case 21: break;
        case 7: 
          { return (int) yycharat(0);
          }
        case 22: break;
        case 2: 
          { yyparser.yylval = new IdentifierHoja(yytext());
                      return Parser.IDENTIFICADOR;
          }
        case 23: break;
        case 6: 
          { yybegin(I); num_linea++; return Parser.SALTO;
          }
        case 24: break;
        case 13: 
          { yyparser.yylval = new FloatHoja(Double.parseDouble(yytext()));
                      return Parser.REAL;
          }
        case 25: break;
        case 16: 
          { yyparser.yylval = new BooleanHoja(yytext().equals("True"));
                      return Parser.BOOLEANO;
          }
        case 26: break;
        case 11: 
          { num_linea++;
          }
        case 27: break;
        case 15: 
          { yybegin(I); num_linea++;
          }
        case 28: break;
        case 10: 
          { num_espacios+=4;
          }
        case 29: break;
        case 4: 
          { return operador(yytext());
          }
        case 30: break;
        case 9: 
          { num_espacios++;
          }
        case 31: break;
        case 5: 
          { 
          }
        case 32: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {
                if(!pila.isEmpty()){
            pila.pop();
            return Parser.DEINDENTA;
        }else{
            return 0;
        }
              }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    if (argv.length == 0) {
      System.out.println("Usage : java Flexer <inputfile>");
    }
    else {
      for (int i = 0; i < argv.length; i++) {
        Flexer scanner = null;
        try {
          scanner = new Flexer( new java.io.FileReader(argv[i]) );
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
