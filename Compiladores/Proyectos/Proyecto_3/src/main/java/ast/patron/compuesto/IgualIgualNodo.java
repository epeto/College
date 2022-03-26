
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class IgualIgualNodo extends NodoBinario{

    public IgualIgualNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
