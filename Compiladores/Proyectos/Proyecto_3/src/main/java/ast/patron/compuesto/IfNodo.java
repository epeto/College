
package ast.patron.compuesto;

import ast.patron.visitante.Visitor;

public class IfNodo extends Compuesto
{

    public IfNodo(Nodo t , Nodo l , Nodo r){
        super(t);
        this.getHijos().agregaHijoFinal(l);
        this.getHijos().agregaHijoFinal(r);       
    }
    public IfNodo(Nodo t , Nodo l){
        super(t);
        this.getHijos().agregaHijoFinal(l);
    }
    
    
    public void accept(Visitor v){
     	v.visit(this);
    }
    
}
