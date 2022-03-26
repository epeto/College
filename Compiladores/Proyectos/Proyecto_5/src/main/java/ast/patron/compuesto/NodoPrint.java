
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class NodoPrint extends Compuesto {
    public NodoPrint(Nodo l){
        super(l);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
