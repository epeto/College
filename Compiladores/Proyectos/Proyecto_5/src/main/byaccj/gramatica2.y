%{
  import ast.Flexer;
  import ast.patron.compuesto.*;
  import java.io.*;
%}

%token SALTO INDENTA DEINDENTA IDENTIFICADOR ENTERO REAL CADENA BOOLEANO AND OR NOT WHILE IF ELSE ELIF PRINT IGUAL MAIGUAL MEIGUAL DIF EXP DIV

%%

input : /*Cadena vacía*/
      | file_input {System.out.println("Reconocimiento exitoso"); raiz = $1;}
      ;

file_input : file_input SALTO {$$ = $1;}
           | file_input stmt {$1.agregaHijoFinal($2); $$ = $1;}
           | SALTO
           | stmt {$$ = new NodoStmts($1);}
           ;

stmt : simple_stmt {$$ = $1;}
     | compound_stmt {$$ = $1;}
     ;

simple_stmt : small_stmt SALTO {linea++; $$ = $1;}
            ;

small_stmt : expr_stmt {$$ = $1;}
           | print_stmt {$$ = $1;}
           ;

expr_stmt : test '=' test {$$ = new AsigNodo($1 , $3);}
          | test {$$ = $1;}
          ;

print_stmt : PRINT test {$$ = new NodoPrint($2);}
           ;

compound_stmt : if_stmt {$$ = $1;}
              | while_stmt {$$ = $1;}
              ;

if_stmt : IF test ':' suite ELSE ':' suite {$$ = new IfNodo($2 , $4 , $7);}
        |  IF test ':' suite {$$ = new IfNodo($2 , $4);}
        ;

while_stmt : WHILE test ':' suite {$$ = new WhileNodo($2 , $4);}
           ;

suite : simple_stmt {$$ = $1;}
      | SALTO INDENTA stmt2 DEINDENTA {$$ = $3;}
      ;

stmt2 : stmt2 stmt {$1.agregaHijoFinal($2); $$ = $1;}
      | stmt {$$ = new NodoStmts($1);}
      ;

test : or_test {$$ = $1;}
     ;

or_test : or_test OR and_test {$$ = new OrNodo($1 , $3);}
        |   and_test {$$ = $1;}
        ;

and_test : and_test AND not_test {$$ = new AndNodo($1 , $3);}
         | not_test {$$ = $1;}
         ;

not_test : NOT not_test {$$ = new NotNodo($2);}
         | comparison {$$ = $1;}
         ;

comparison : expr comp_op comparison {$2.agregaHijoPrincipio($1); $2.agregaHijoFinal($3); $$ = $2;}
           | expr {$$ = $1;}
           ;

comp_op : '<' {$$ = new MenorNodo(null , null);}
        | '>' {$$ = new MayorNodo(null , null);}
        | IGUAL {$$ = new IgualIgualNodo(null , null);}
        | MAIGUAL {$$ = new MayorIgualNodo(null , null);}
        | MEIGUAL {$$ = new MenorIgualNodo(null , null);}
        | DIF {$$ = new DiferenteNodo(null , null);}
        ;

expr : expr '+' term {$$ = new AddNodo($1,$3);}
     | expr '-' term {$$ = new DifNodo($1,$3);}
     | term {$$ = $1;}
     ;

term : term '*' factor {$$ = new PorNodo($1 , $3);}
     | term '/' factor {$$ = new DivNodo($1 , $3);}
     | term '%' factor {$$ = new ModuloNodo($1 , $3);}
     | term DIV factor {$$ = new DivisionEnteraNodo($1 , $3);}
     | factor {$$ = $1;}

factor : '+' factor {$$ = new AddNodo(null,$2);}
       | '-' factor {$$ = new DifNodo(null, $2);}
       | power {$$ = $1;}
       ;

power : atom EXP factor {$$ = new PowNodo($1, $3);}
      | atom {$$ = $1;}
      ;

atom : IDENTIFICADOR {$$ = $1;}
     | ENTERO {$$ = $1;}
     | CADENA {$$ = $1;}
     | REAL {$$ = $1;}
     | BOOLEANO {$$ = $1;}
     | '(' test ')' {$$ = $2;}
     ;

%%

  private Flexer lexer;
  public Nodo raiz;
  public static short linea = 0 ;

  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

    public void yyerror (String error) {
        System.err.println ("[ERROR] " + error);
    }


    public Parser(Reader r) {
        lexer = new Flexer(r, this);
    }

  public static void main(String args[]) throws IOException {
    Parser yyparser;
    yyparser = new Parser(new FileReader("src/main/resources/test.p"));
    yyparser.yydebug = false; //true para que imprima el proceso.
    int condicion = yyparser.yyparse();

    if(condicion != 0){
      linea++;
      System.err.print ("[ERROR] ");
      yyparser.yyerror("La expresión aritmética no esta bien formada. en la línea " + linea);
    }else{
      System.out.println("Expresión bien formada");
    }
  }
