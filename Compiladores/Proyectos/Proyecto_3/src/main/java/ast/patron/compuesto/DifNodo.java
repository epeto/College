package ast.patron.compuesto;
import ast.patron.visitante.*;

public class DifNodo extends NodoBinario
{

    public DifNodo(Nodo l, Nodo r){
	super(l,r);
    }

    public void accept(Visitor v){
	v.visit(this);
    }
}
