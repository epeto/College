
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class IfStmts extends Compuesto
{

    public IfStmts(Nodo l){
        super(l);
    }
    
    public void accept(Visitor v){
     	v.visit(this);
    }
}
