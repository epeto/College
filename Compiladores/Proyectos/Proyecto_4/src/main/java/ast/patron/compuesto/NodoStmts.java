package ast.patron.compuesto;
import ast.patron.visitante.*;

public class NodoStmts extends Compuesto
{

    public NodoStmts(Nodo l){
        
        super(l);
    }
    public void accept(Visitor v){
     	v.visit(this);
    }
}
