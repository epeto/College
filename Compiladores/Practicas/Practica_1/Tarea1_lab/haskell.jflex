// Clase de analizador léxico Haskell para comentarios, enteros e // identificadores.

%%

%public
%class AL   // Nombre a la clase AL.
%unicode
%standalone // Crea una función.

COMENTARIO_1 = --~\n
COMENTARIO_2 = \{-~-\}
ENTERO = [1-9][0-9]* | 0 | -[1-9][0-9]*
ID = [:letter:]([:jletterdigit:] | _ )*

%% // Aquí se definen las macros.
{COMENTARIO_1} {System.out.println("COMENTARIO_1("+yytext()+")");}
{COMENTARIO_2} {System.out.print("COMENTARIO_2("+yytext()+")");}
{ENTERO}       {System.out.print("ENTERO("+yytext() + ")");}
{ID}           {System.out.print("ID("+yytext() + ")"); }
