
package ed.estructuras.lineales;

class Nodo<E> {
    private E dato;
    private Nodo<E> siguiente;

    public Nodo(E d){
        dato = d;
    }

    public Nodo(E d, Nodo<E> sig){
        dato = d;
        siguiente = sig;
    }

    public E getDato(){
        return dato;
    }

    public Nodo<E> getSiguiente(){
        return siguiente;
    }

    public void setDato(E d){
        dato = d;
    }

    public void setSiguiente(Nodo<E> sig){
        siguiente = sig;
    }
}
