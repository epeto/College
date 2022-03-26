
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class DivisionEnteraNodo extends NodoBinario{

    public DivisionEnteraNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
