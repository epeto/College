
package ed.estructuras.nolineales;

import java.util.List;
import java.util.LinkedList;

public class NodoBOLigado<C extends Comparable<C>> implements NodoBinario<C>{

    private int altura;
    private C elem;
    private NodoBOLigado<C> padre;
    private NodoBOLigado<C> izquierdo;
    private NodoBOLigado<C> derecho;

    public NodoBOLigado(C el, NodoBOLigado<C> pa, NodoBOLigado<C> iz, NodoBOLigado<C> de){
        elem = el;
        padre = pa;
        izquierdo = iz;
        derecho = de;
        altura = 0;
    }

    public NodoBOLigado(C el){
        this(el, null, null, null);
    }

    @Override
    public C getElemento() {
        return elem;
    }

    @Override
    public void setElemento(C dato) {
        elem = dato;
    }

    @Override
    public void setPadre(Nodo<C> padre) {
        this.padre = (NodoBOLigado<C>) padre;
    }

    @Override
    public boolean esHoja() {
        return (izquierdo == null && derecho == null);
    }

    @Override
    public int getAltura() {
        return altura;
    }

    public void setAltura(int h){
        altura = h;
    }

    @Override
    public Nodo<C> getHijo(int índice) throws IndexOutOfBoundsException {
        if(índice == 0){
            return izquierdo;
        }else if(índice == 1){
            return derecho;
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getGrado() {
        int degree = 0;
        degree += (izquierdo == null)? 0 : 1;
        degree += (derecho == null)? 0 : 1;
        return degree;
    }

    @Override
    public List<Nodo<C>> getListaHijos() {
        LinkedList<Nodo<C>> lista = new LinkedList<>();
        if(izquierdo != null){
            lista.add(izquierdo);
        }
        if(derecho != null){
            lista.add(derecho);
        }
        return lista;
    }

    @Override
    public NodoBinario<C> getPadre() {
        return padre;
    }

    @Override
    public NodoBinario<C> getHijoI() {
        return izquierdo;
    }

    @Override
    public NodoBinario<C> getHijoD() {
        return derecho;
    }

    @Override
    public void setHijoI(NodoBinario<C> izquierdo) {
        this.izquierdo = (NodoBOLigado<C>) izquierdo;
    }

    @Override
    public void setHijoD(NodoBinario<C> derecho) {
        this.derecho = (NodoBOLigado<C>) derecho;
    }

    /**
     * Encuentra al nodo con el elemento más pequeño del árbol con
     * raíz en este nodo.
     * @return nodo con el elemento más pequeño.
     */
    public NodoBOLigado<C> findMin(){
        if(izquierdo == null){
            return this;
        }else{
            return izquierdo.findMin();
        }
    }

    /**
     * Encuentra al nodo con el elemento más grande del árbol con
     * raíz en este nodo.
     * @return nodo con el elemento más pequeño.
     */
    public NodoBOLigado<C> findMax(){
        if(derecho == null){
            return this;
        }else{
            return derecho.findMax();
        }
    }

}