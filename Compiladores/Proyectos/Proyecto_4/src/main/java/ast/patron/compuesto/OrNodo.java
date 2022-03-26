
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class OrNodo extends NodoBinario{

    public OrNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
