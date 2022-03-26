
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class MenorIgualNodo extends NodoBinario{

    public MenorIgualNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
