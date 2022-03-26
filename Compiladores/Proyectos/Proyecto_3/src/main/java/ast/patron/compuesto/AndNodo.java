
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class AndNodo extends NodoBinario{

    public AndNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
