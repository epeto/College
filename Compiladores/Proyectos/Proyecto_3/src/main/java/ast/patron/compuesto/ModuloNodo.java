
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class ModuloNodo extends NodoBinario{

    public ModuloNodo(Nodo l, Nodo r){
	super(l,r);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
