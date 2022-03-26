
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class PorNodo extends NodoBinario{

    public PorNodo(Nodo l, Nodo r){
	super(l,r);
    }
    public void accept(Visitor v){
     	v.visit(this);
    }
}
