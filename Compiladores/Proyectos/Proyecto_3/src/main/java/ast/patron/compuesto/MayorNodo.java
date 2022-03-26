package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class MayorNodo extends NodoBinario{

    public MayorNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
