package ast.patron.compuesto;
import ast.patron.visitante.*;
/* Tipos:
 * 1 - Integer
 * 2 - Float
 * 3 - Boolean
 * 4 - String
*/
public class Hoja extends Nodo
{
    public void accept(Visitor v){
     	v.visit(this);
    }

}
