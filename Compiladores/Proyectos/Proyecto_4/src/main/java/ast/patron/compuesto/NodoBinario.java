package ast.patron.compuesto;
import ast.patron.visitante.*;

public class NodoBinario extends Compuesto
{
    public NodoBinario(Nodo l, Nodo r){
        super();
        if(l!=null){
            this.agregaHijoPrincipio(l);
        }
        
        if(r!=null){
            this.agregaHijoFinal(r);
        }
    }

    public NodoBinario(Nodo l){
        super();
        if(l!=null){
            this.agregaHijoPrincipio(l);
        }
    }

    public NodoBinario(){
	super();
    }

    public void accept(Visitor v){
     	v.visit(this);
    }
}
