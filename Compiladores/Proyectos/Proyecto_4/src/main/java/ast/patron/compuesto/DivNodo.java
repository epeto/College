
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class DivNodo extends NodoBinario{

    public DivNodo(Nodo l, Nodo r){
	super(l,r);
    }
    public void accept(Visitor v){
     	v.visit(this);
    }
}
