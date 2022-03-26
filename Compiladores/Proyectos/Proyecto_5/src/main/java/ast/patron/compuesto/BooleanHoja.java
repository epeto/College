
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class BooleanHoja extends Hoja{
    public BooleanHoja(boolean i){
	valor = new Variable(i);
	tipo = 3;
    }

    public void accept(Visitor v){
     	v.visit(this);
    }
}