/********************************************************************************
**  @author Diana Montes                                                       **
**  @about Proyecto 1: Analizador léxico para p, subconjunto de Python.        **
*********************************************************************************/
package lexico;
import java.util.Stack;
import java.io.FileWriter;
import java.io.BufferedWriter;

%%
%{
public String resultado = ""; //Resultado del análisis léxico.
public int num_espacios = 0; //Cuenta el número de espacios.
public Stack<Integer> pila = new Stack();
public int num_linea = 1;
public boolean dperr = false;

public void agrRes(String s){
    if(!dperr){
        resultado+=s;
    }
}

public void reset(){ //Se va a ejecutar cada vez que se lea un caracter diferente al espacio en una nueva línea.
    if(yystate()==I && num_espacios>0){ //Si estaba en estado I (indentación).
        if(pila.isEmpty()){
            pila.push(num_espacios);
            agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
        }else{
            if(pila.peek()<num_espacios){
                pila.push(num_espacios);
                agrRes("INDENTA("+num_espacios+")"); //Se imprime el número de indentación.
            }else{
                while(!pila.isEmpty() && pila.peek() > num_espacios){
                    agrRes("DEINDENTA("+pila.pop()+")\n");
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
            }
        }
    }

    yybegin(YYINITIAL);
    num_espacios = 0;
}

public void escribeArchivo(){
    try{
        FileWriter fw1 = new FileWriter("out/"+AnalizadorLexico.nom_archivo+".plx");
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.write(resultado);
        bw1.close();
    }catch(Exception ex){}
}

%}

%eof{
    while(!pila.isEmpty()){
        agrRes("DEINDENTA("+pila.pop()+")\n");
    }

    System.out.println("\n"+resultado);

    escribeArchivo();
%eof}

%public
%class Alexico
%unicode
%standalone
%xstates I

BOOLEANO = "True" | "False"
PALABRA_RESERVADA = "and" | "or" | "not" | "while" | "if" | "else" | "elif" | "print"
IDENTIFICADOR = [a-zA-Z]([a-zA-Z]|_|[0-9])*
ENTERO = [1-9][0-9]* | 0
REAL =  ([1-9][0-9]* | 0)\.[0-9]*
CADENA = \" ~\"
ERROR_CADENA = \" 
OPERADOR = "+"|"-"|"*"|"/"|"%"|"<"|">"|">="|"<="|"="|"!"|"=="|"="
LINEA_VACIA = ([ ] | \t)+\n | "\n"
SALTO = \r|\n|\r\n
COMENTARIO = #~\n

%%
{COMENTARIO}         {yybegin(I); num_linea++;}
{BOOLEANO}           {agrRes("BOOLEANO("+yytext()+")");}
{PALABRA_RESERVADA}  {agrRes("PALABRA_RESERVADA("+yytext()+")");}
{IDENTIFICADOR}      {agrRes("IDENTIFICADOR("+yytext()+")");}
{REAL}               {agrRes("REAL("+yytext()+")");}
{ENTERO}             {agrRes("ENTERO("+yytext()+")");}
{CADENA}             {agrRes("CADENA("+yytext()+")");}
{ERROR_CADENA}       {agrRes("\nError de cadena en linea "+num_linea); dperr = true;}
{OPERADOR}           {agrRes("OPERADOR("+yytext()+")");}
\( | \)              {}
":"                  {agrRes("SEPARADOR(:)");}
<I> {LINEA_VACIA} {num_linea++;}
<I> [ ] {num_espacios++;}
<I> \t {num_espacios+=4;}
<I> . {reset(); yypushback(1);}
{SALTO}              {agrRes("SALTO\n"); yybegin(I); num_linea++;}
<YYINITIAL> [ ] {}
<YYINITIAL> \t {}
.                    {agrRes("\nError léxico en línea "+num_linea); dperr = true;}
