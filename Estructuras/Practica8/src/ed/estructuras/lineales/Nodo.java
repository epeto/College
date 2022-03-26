
package ed.estructuras.lineales;

class Nodo<E> {
    private E dato;
    private Nodo<E> anterior;
    private Nodo<E> siguiente;

    public Nodo(E d, Nodo<E> ant, Nodo<E> sig){
        dato = d;
        anterior = ant;
        siguiente = sig;
    }
    
    public Nodo(E d){
        this(d, null, null);
    }

    public E getDato(){
        return dato;
    }

    public Nodo<E> getAnterior(){
        return anterior;
    }
    
    public Nodo<E> getSiguiente(){
        return siguiente;
    }

    public void setDato(E d){
        dato = d;
    }

    public void setAnterior(Nodo<E> ant){
        anterior = ant;
    }
    
    public void setSiguiente(Nodo<E> sig){
        siguiente = sig;
    }

    @Override
    public String toString(){
        String retVal = "";
        if(dato == null){
            retVal = "⟨null⟩";
        }else{
            retVal = "⟨"+dato.toString()+"⟩";
        }
        return retVal;
    }
}

