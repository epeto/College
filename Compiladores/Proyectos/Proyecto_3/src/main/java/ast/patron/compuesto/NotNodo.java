
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class NotNodo extends Compuesto{

    public NotNodo(Nodo l){
	super(l);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
