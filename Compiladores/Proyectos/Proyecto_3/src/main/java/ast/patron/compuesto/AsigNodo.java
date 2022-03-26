package ast.patron.compuesto;
import ast.patron.visitante.*;

public class AsigNodo extends NodoBinario
{

    public AsigNodo(Nodo l, Nodo r){
        
	super(l,r);
    }

    public void accept(Visitor v){
        //System.out.println(this.hijos.hijos);
        
        v.visit(this);
    }
}
