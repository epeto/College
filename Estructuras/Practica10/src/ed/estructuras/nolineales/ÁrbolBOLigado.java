
package ed.estructuras.nolineales;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import ed.estructuras.ColeccionAbstracta;

public class ÁrbolBOLigado<C extends Comparable<C>> extends ColeccionAbstracta<C>
implements ÁrbolBinarioOrdenado<C>{

    NodoBOLigado<C> raiz;

    private NodoBOLigado<C> creaNodo(C dato, 
                                      NodoBOLigado<C> padre, 
                                      NodoBOLigado<C> hijoI, 
                                      NodoBOLigado<C> hijoD){
        return new NodoBOLigado<>(dato, padre, hijoI, hijoD);
    }

    public ÁrbolBOLigado(){
        raiz = null;
    }

    @Override
    public NodoBinario<C> getRaíz() {
        return raiz;
    }

    @Override
    public Iterator<C> getIteradorInorden() {
        return new Iterador("inorden");
    }

    @Override
    public Iterator<C> getIteradorPreorden() {
        return new Iterador("preorden");
    }

    @Override
    public Iterator<C> getIteradorPostorden() {
        return new Iterador("postorden");
    }

    @Override
    public List<C> recorridoPostorden() {
        LinkedList<C> lista = new LinkedList<>();
        Iterator<C> it = getIteradorPostorden();
        while(it.hasNext()){
            lista.add(it.next());
        }
        return lista;
    }

    public List<C> recorridoPreorden() {
        LinkedList<C> lista = new LinkedList<>();
        Iterator<C> it = getIteradorPreorden();
        while(it.hasNext()){
            lista.add(it.next());
        }
        return lista;
    }

    public List<C> recorridoInorden() {
        LinkedList<C> lista = new LinkedList<>();
        Iterator<C> it = getIteradorInorden();
        while(it.hasNext()){
            lista.add(it.next());
        }
        return lista;
    }

    @Override
    public int getAltura() {
        if(raiz == null){
            return 0;
        }else{
            return raiz.getAltura();
        }
    }

    private NodoBOLigado<C> addNode(C e){
        NodoBOLigado<C> nuevo = creaNodo(e, null, null, null);
        if(raiz == null){
            raiz = nuevo;
        }else{
            boolean agregado = false;
            NodoBinario<C> actual = raiz;
            while(!agregado){
                int rescomp = e.compareTo(actual.getElemento());
                if(rescomp < 0){ // e es menor que la raíz.
                    if(actual.getHijoI() == null){
                        actual.setHijoI(nuevo);
                        nuevo.setPadre(actual);
                        agregado = true;
                    }else{
                        actual = actual.getHijoI();
                    }
                }else{ // e es mayor o igual a la raíz.
                    if(actual.getHijoD() == null){
                        actual.setHijoD(nuevo);
                        nuevo.setPadre(actual);
                        agregado = true;
                    }else{
                        actual = actual.getHijoD();
                    }
                }
            }
        }
        return nuevo;
    }

    @Override
    public boolean add(C elem) {
        NodoBOLigado<C> temp = addNode(elem);
        int acum = 0;
        while(temp != null){
            int hMayor = Math.max(acum, temp.getAltura());
            temp.setAltura(hMayor);
            temp = (NodoBOLigado<C>) temp.getPadre();
            acum++;
        }
        tam++;
        return true;
    }

    @Override
    public Iterator<C> iterator() {
        return new Iterador("inorden");
    }

    @Override
    public boolean contains(C elem) throws NullPointerException {
        if(elem == null){
            throw new NullPointerException();
        }
        if(raiz == null){
            return false;
        }
        NodoBinario<C> actual = raiz;
        while(actual != null){
            int cmp = elem.compareTo(actual.getElemento());
            if(cmp == 0){
                return true;
            }else if(cmp < 0){
                actual = actual.getHijoI();
            }else{
                actual = actual.getHijoD();
            }
        }
        return false;
    }

    private NodoBOLigado<C> findNode(C elem){
        if(!contains(elem)){
            return null;
        }
        boolean encontrado = false;
        NodoBOLigado<C> actual = raiz;
        while(!encontrado){
            int cmp = elem.compareTo(actual.getElemento());
            if(cmp == 0){
                encontrado = true;
            }else if(cmp < 0){
                actual = (NodoBOLigado<C>) actual.getHijoI();
            }else{
                actual = (NodoBOLigado<C>) actual.getHijoD();
            }
        }
        return actual;
    }

    private void intercambia(NodoBOLigado<C> nodo1, NodoBOLigado<C> nodo2){
        C temp = nodo1.getElemento();
        nodo1.setElemento(nodo2.getElemento());
        nodo2.setElemento(temp);
    }

    private NodoBOLigado<C> remueveNodo(C elem){
        NodoBOLigado<C> aElim = findNode(elem);
        int gradoNodo = aElim.getGrado();
        if(gradoNodo == 0){ // Si es hoja se elimina sin problemas.
            if(aElim == raiz){
                raiz = null;
            }else{
                NodoBOLigado<C> padreElim = (NodoBOLigado<C>) aElim.getPadre();
                if(padreElim.getHijoI() == aElim){
                    padreElim.setHijoI(null);
                }else{
                    padreElim.setHijoD(null);
                }
            }
        }else if(gradoNodo == 1){ // Nodo con un solo hijo.
            if(aElim == raiz){
                NodoBOLigado<C> nuevaRaiz = (NodoBOLigado<C>) ((raiz.getHijoI() != null)? raiz.getHijoI() : raiz.getHijoD());
                raiz = nuevaRaiz;
                raiz.setPadre(null);
            }else{
                NodoBinario<C> padreElim = aElim.getPadre(); //padre del nodo eliminado.
                NodoBinario<C> nuevoHijo = (aElim.getHijoI() != null)? aElim.getHijoI() : aElim.getHijoD();
                if(padreElim.getHijoI() == aElim){
                    padreElim.setHijoI(nuevoHijo);
                }else{
                    padreElim.setHijoD(nuevoHijo);
                }
                nuevoHijo.setPadre(padreElim);
            }
        }else{ // Nodo con dos hijos.
            NodoBOLigado<C> hijoD = (NodoBOLigado<C>) aElim.getHijoD();
            NodoBOLigado<C> minDer = hijoD.findMin(); // El nodo más pequeño del subárbol derecho.
            NodoBOLigado<C> padreMD = (NodoBOLigado<C>) minDer.getPadre(); // Padre del nodo más pequeño.
            intercambia(minDer, aElim);
            if(padreMD.getHijoI() == minDer){
                padreMD.setHijoI(minDer.getHijoD());
            }else{
                padreMD.setHijoD(minDer.getHijoD());
            }
            if(minDer.getHijoD() != null){
                minDer.getHijoD().setPadre(padreMD);
            }
            aElim = minDer;
        }
        return aElim;
    }

    @Override
    public boolean remove(C elem) throws NullPointerException {
        if(elem == null){
            throw new NullPointerException();
        }
        if(!contains(elem)){
            return false;
        }
        NodoBOLigado<C> eliminado = remueveNodo(elem);
        //Actualizar las alturas.
        NodoBOLigado<C> temp = (NodoBOLigado<C>) eliminado.getPadre();
        while(temp != null){
            int altIzq = (temp.getHijoI() == null)? -1 : temp.getHijoI().getAltura();
            int altDer = (temp.getHijoD() == null)? -1 : temp.getHijoD().getAltura();
            temp.setAltura(Math.max(altIzq, altDer)+1);
            temp = (NodoBOLigado<C>) temp.getPadre();
        }
        tam--;
        return true;
    }

    private class Iterador implements Iterator<C>{

        String recorrido;
        NodoBinario<C> siguiente;

        public Iterador(String rec){
            recorrido = rec;
            siguiente = raiz;
            if(rec.equals("inorden") || rec.equals("postorden")){
                if(raiz != null){
                    while(siguiente.getHijoI() != null){
                        siguiente = siguiente.getHijoI();
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public C next() {
            if(!hasNext()){
                throw new IllegalStateException();
            }
            C dato = siguiente.getElemento();
            switch(recorrido){
                case "inorden":
                    if(siguiente.getHijoD() != null){
                        siguiente = siguiente.getHijoD();
                        while(siguiente.getHijoI() != null){
                            siguiente = siguiente.getHijoI();
                        }
                    }else{
                        NodoBinario<C> temp = siguiente;
                        NodoBinario<C> padreTemp;
                        boolean encontrado = false; // es true si ya se encontró siguiente.
                        while(!encontrado && temp != raiz){
                            padreTemp = temp.getPadre();
                            if(padreTemp.getHijoI() == temp){
                                siguiente = padreTemp;
                                encontrado = true;
                            }
                            temp = padreTemp;
                        }
                        if(!encontrado){
                            siguiente = null;
                        }
                    }
                break;
                case "preorden":
                    if(siguiente.getHijoI() != null){
                        siguiente = siguiente.getHijoI();
                    }else{
                        NodoBinario<C> temp = siguiente;
                        NodoBinario<C> padreTemp;
                        boolean encontrado = false; // es true si ya se encontró siguiente.
                        while(!encontrado && temp != raiz){
                            padreTemp = temp.getPadre();
                            if(padreTemp.getHijoI() == temp){
                                if(padreTemp.getHijoD() != null){
                                    siguiente = padreTemp.getHijoD();
                                    encontrado = true;
                                }
                            }
                            temp = padreTemp;
                        }
                        if(!encontrado){
                            siguiente = null;
                        }
                    }
                break;
                case "postorden":
                    NodoBinario<C> temp = siguiente;
                    NodoBinario<C> padreTemp;
                    boolean encontrado = false;
                    while(!encontrado && temp != raiz){
                        padreTemp = temp.getPadre();
                        if(padreTemp.getHijoD() == temp){
                            siguiente = padreTemp;
                            encontrado = true;
                        }else if(padreTemp.getHijoD() != null){
                            siguiente = padreTemp.getHijoD();
                            while(siguiente.getHijoI() != null){
                                siguiente = siguiente.getHijoI();
                            }
                            encontrado = true;
                        }
                        temp = padreTemp;
                    }
                    if(!encontrado){
                        siguiente = null;
                    }
            }
            return dato;
        }
        
        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
        ÁrbolBOLigado<Integer> arbolito = new ÁrbolBOLigado<>();
        arbolito.add(5);
        arbolito.add(3);
        arbolito.add(7);
        arbolito.add(2);
        arbolito.add(6);
        arbolito.add(4);
        arbolito.add(8);
        System.out.println("Inorden:");
        System.out.println(arbolito.recorridoInorden());
        System.out.println("Preorden:");
        System.out.println(arbolito.recorridoPreorden());
        System.out.println("Postorden");
        System.out.println(arbolito.recorridoPostorden());
    }
}
