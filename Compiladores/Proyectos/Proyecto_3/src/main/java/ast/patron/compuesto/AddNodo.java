package ast.patron.compuesto;
    import ast.patron.visitante.*;

public class AddNodo extends NodoBinario
{

    public AddNodo(Nodo l, Nodo r){
	super(l,r);
    }

    public void accept(Visitor v){
     	v.visit(this);
    }
}
