
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class MayorIgualNodo extends NodoBinario{

    public MayorIgualNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
