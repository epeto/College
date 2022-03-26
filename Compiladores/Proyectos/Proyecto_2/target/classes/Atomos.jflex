package com.mycompany.proyecto_2;

import java.util.Stack;
import java.io.FileWriter;
import java.io.BufferedWriter;

%%

%{
//Aquí empieza el código de Java
public String resultado = ""; //Resultado del análisis léxico.
public int num_espacios = 0; //Cuenta el número de espacios.
public Stack<Integer> pila = new Stack();
public int num_linea = 1;
public boolean dperr = false;
private Parser yyparser;

public Atomos(java.io.Reader r, Parser yyparser) {
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
%}

%public
%class Atomos
%unicode
%standalone
%xstates I
%byaccj

BOOLEANO = "True" | "False"
PALABRA_RESERVADA = "and" | "or" | "not" | "while" | "if" | "else" | "elif" | "print"
IDENTIFICADOR = [a-zA-Z]([a-zA-Z]|_|[0-9])*
ENTERO = [1-9][0-9]* | 0
REAL =  ([1-9][0-9]* | 0)\.[0-9]*
CADENA = \" ~\"
ERROR_CADENA = \" 
OPERADOR = "+"|"-"|"**"|"*"|"//"|"/"|"%"|"<"|">"|">="|"<="|"="|"!="|"!"|"=="
LINEA_VACIA = ([ ] | \t)+\n | "\n"
SALTO = \r|\n|\r\n
COMENTARIO = #~\n

%%
{COMENTARIO}         {yybegin(I); num_linea++;}
{BOOLEANO}           {return Parser.BOOLEANO;}
{PALABRA_RESERVADA}  {return reservada(yytext());}
{IDENTIFICADOR}      {return Parser.IDENTIFICADOR;}
{REAL}               {return Parser.REAL;}
{ENTERO}             {return Parser.ENTERO;}
{CADENA}             {return Parser.CADENA;}
{ERROR_CADENA}       {dperr = true;}
{OPERADOR}           {return operador(yytext());}
\( | \)              {return (int) yycharat(0);}
":"                  {return (int) yycharat(0);}
<I> {LINEA_VACIA} {num_linea++;}
<I> [ ] {num_espacios++;}
<I> \t {num_espacios+=4;}
<I> . {yypushback(1); short val = reset(); if(val!=0){return val;}}
{SALTO}              {yybegin(I); num_linea++; return Parser.SALTO;}
<YYINITIAL> [ ] {}
<YYINITIAL> \t {}
.                    {dperr = true;}
<<EOF>>{if(!pila.isEmpty()){
            pila.pop();
            return Parser.DEINDENTA;
        }else{
            return 0;
        }
       }