
package ed.estructuras.nolineales;

public class NodoAVL<C extends Comparable<C>> extends NodoBOLigado<C> {

    public NodoAVL(C el) {
        super(el);
    }
    
    public NodoAVL(C el, NodoAVL<C> pa, NodoAVL<C> iz, NodoAVL<C> de){
        super(el, pa, iz, de);
    }

    /**
     * Realiza rotación derecha sobre este nodo.
     * Se asume que este nodo tiene hijo izquierdo diferente de null.
     * @return nueva raíz.
     */
    public NodoAVL<C> rotaDerecha(){
        NodoAVL<C> r2 = this;
        NodoAVL<C> r1 = (NodoAVL<C>) getHijoI();
        NodoAVL<C> hijoDR1 = (NodoAVL<C>) r1.getHijoD();
        r2.setHijoI(hijoDR1);
        if(hijoDR1 != null){
            hijoDR1.setPadre(r2);
        }
        r1.setHijoD(r2);
        r2.setPadre(r1);
        r2.calculaAltura();
        r1.calculaAltura();
        return r1;
    }

    /**
     * Realiza rotación izquierda sobre este nodo.
     * Se asume que este nodo tiene hijo derecho diferente de null.
     * @return nueva raíz.
     */
    public NodoAVL<C> rotaIzquierda(){
        NodoAVL<C> r1 = this;
        NodoAVL<C> r2 = (NodoAVL<C>) getHijoD();
        NodoAVL<C> hijoIR2 = (NodoAVL<C>) r2.getHijoI();
        r1.setHijoD(hijoIR2);
        if(hijoIR2 != null){
            hijoIR2.setPadre(r1);
        }
        r2.setHijoI(r1);
        r1.setPadre(r2);
        r1.calculaAltura();
        r2.calculaAltura();
        return r2;
    }

    public int factorBalanceo(){
        int altIzq = (getHijoI() == null)? -1 : getHijoI().getAltura();
        int altDer = (getHijoD() == null)? -1 : getHijoD().getAltura();
        return altIzq - altDer;
    }

    public NodoAVL<C> rebalancea(){
        NodoAVL<C> newRoot = null;
        if(factorBalanceo() == 2){
            NodoAVL<C> hijoI = (NodoAVL<C>) getHijoI();
            if(hijoI.factorBalanceo() == 1){ // Una rotación derecha
                newRoot = rotaDerecha();
            }else{ // rotación doble LR
                setHijoI(hijoI.rotaIzquierda());
                newRoot = rotaDerecha();
            }
        }else{ // factor de balanceo es igual a -2
            NodoAVL<C> hijoD = (NodoAVL<C>) getHijoD();
            if(hijoD.factorBalanceo() == -1){ // una rotación izquierda.
                newRoot = rotaIzquierda();
            }else{ // rotación doble RL
                setHijoD(hijoD.rotaDerecha());
                newRoot = rotaIzquierda();
            }
        }
        newRoot.setPadre(null);
        return newRoot;
    }

}
