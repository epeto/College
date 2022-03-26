
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class WhileNodo extends NodoBinario{

    public WhileNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
