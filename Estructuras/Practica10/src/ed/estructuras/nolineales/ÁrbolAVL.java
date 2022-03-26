
package ed.estructuras.nolineales;

public class ÁrbolAVL<C extends Comparable<C>> extends ÁrbolBOLigado<C> {
    private NodoAVL<C> creaNodo(C dato, 
                                      NodoAVL<C> padre, 
                                      NodoAVL<C> hijoI, 
                                      NodoAVL<C> hijoD){
        return new NodoAVL<>(dato, padre, hijoI, hijoD);
    }

    private NodoAVL<C> addNode(C e){
        NodoAVL<C> nuevo = creaNodo(e, null, null, null);
        if(raiz == null){
            raiz = nuevo;
        }else{
            boolean agregado = false;
            NodoAVL<C> actual = (NodoAVL<C>) raiz;
            while(!agregado){
                int rescomp = e.compareTo(actual.getElemento());
                if(rescomp < 0){ // e es menor que la raíz.
                    if(actual.getHijoI() == null){
                        actual.setHijoI(nuevo);
                        nuevo.setPadre(actual);
                        agregado = true;
                    }else{
                        actual = (NodoAVL<C>) actual.getHijoI();
                    }
                }else{ // e es mayor o igual a la raíz.
                    if(actual.getHijoD() == null){
                        actual.setHijoD(nuevo);
                        nuevo.setPadre(actual);
                        agregado = true;
                    }else{
                        actual = (NodoAVL<C>) actual.getHijoD();
                    }
                }
            }
        }
        return nuevo;
    }

    @Override
    public boolean add(C elem){
        NodoAVL<C> temp = addNode(elem);
        NodoAVL<C> padreTemp;
        while(temp != null){
            temp.calculaAltura();
            padreTemp = (NodoAVL<C>) temp.getPadre();
            if(Math.abs(temp.factorBalanceo()) == 2){
                if(padreTemp == null){
                    raiz = temp.rebalancea();
                }else{
                    if(padreTemp.getHijoI() == temp){
                        NodoAVL<C> nuevoHijo = temp.rebalancea();
                        padreTemp.setHijoI(nuevoHijo);
                        nuevoHijo.setPadre(padreTemp);
                    }else{
                        NodoAVL<C> nuevoHijo = temp.rebalancea();
                        padreTemp.setHijoD(nuevoHijo);
                        nuevoHijo.setPadre(padreTemp);
                    }
                }
            }
            temp = padreTemp;
        }
        tam++;
        return true;
    }
    
    private NodoAVL<C> findNode(C elem){
        if(!contains(elem)){
            return null;
        }
        boolean encontrado = false;
        NodoAVL<C> actual = (NodoAVL<C>) raiz;
        while(!encontrado){
            int cmp = elem.compareTo(actual.getElemento());
            if(cmp == 0){
                encontrado = true;
            }else if(cmp < 0){
                actual = (NodoAVL<C>) actual.getHijoI();
            }else{
                actual = (NodoAVL<C>) actual.getHijoD();
            }
        }
        return actual;
    }

    private NodoAVL<C> remueveNodo(C elem){
        NodoAVL<C> aElim = findNode(elem);
        int gradoNodo = aElim.getGrado();
        if(gradoNodo == 0){ // Si es hoja se elimina sin problemas.
            if(aElim == raiz){
                raiz = null;
            }else{
                NodoAVL<C> padreElim = (NodoAVL<C>) aElim.getPadre();
                if(padreElim.getHijoI() == aElim){
                    padreElim.setHijoI(null);
                }else{
                    padreElim.setHijoD(null);
                }
            }
        }else if(gradoNodo == 1){ // Nodo con un solo hijo.
            if(aElim == raiz){
                NodoAVL<C> nuevaRaiz = (NodoAVL<C>) ((raiz.getHijoI() != null)? raiz.getHijoI() : raiz.getHijoD());
                raiz = nuevaRaiz;
                raiz.setPadre(null);
            }else{
                NodoAVL<C> padreElim = (NodoAVL<C>) aElim.getPadre(); //padre del nodo eliminado.
                NodoAVL<C> nuevoHijo = (NodoAVL<C>) ((aElim.getHijoI() != null)? aElim.getHijoI() : aElim.getHijoD());
                if(padreElim.getHijoI() == aElim){
                    padreElim.setHijoI(nuevoHijo);
                }else{
                    padreElim.setHijoD(nuevoHijo);
                }
                nuevoHijo.setPadre(padreElim);
            }
        }else{ // Nodo con dos hijos.
            NodoAVL<C> hijoD = (NodoAVL<C>) aElim.getHijoD();
            NodoAVL<C> minDer = (NodoAVL<C>) hijoD.findMin(); // El nodo más pequeño del subárbol derecho.
            NodoAVL<C> padreMD = (NodoAVL<C>) minDer.getPadre(); // Padre del nodo más pequeño.
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

    private void intercambia(NodoAVL<C> nodo1, NodoAVL<C> nodo2){
        C temp = nodo1.getElemento();
        nodo1.setElemento(nodo2.getElemento());
        nodo2.setElemento(temp);
    }

    @Override
    public boolean remove(C elem){
        if(elem == null){
            throw new NullPointerException();
        }
        if(!contains(elem)){
            return false;
        }
        NodoAVL<C> eliminado = remueveNodo(elem);
        //Actualizar las alturas.
        NodoAVL<C> temp = (NodoAVL<C>) eliminado.getPadre();
        NodoAVL<C> padreTemp;
        while(temp != null){
            temp.calculaAltura();
            padreTemp = (NodoAVL<C>) temp.getPadre();
            if(Math.abs(temp.factorBalanceo()) == 2){
                if(padreTemp == null){
                    raiz = temp.rebalancea();
                }else{
                    if(padreTemp.getHijoI() == temp){
                        NodoAVL<C> nuevoHijo = temp.rebalancea();
                        padreTemp.setHijoI(nuevoHijo);
                        nuevoHijo.setPadre(padreTemp);
                    }else{
                        NodoAVL<C> nuevoHijo = temp.rebalancea();
                        padreTemp.setHijoD(nuevoHijo);
                        nuevoHijo.setPadre(padreTemp);
                    }
                }
            }
            temp = padreTemp;
        }
        tam--;
        return true;
    }
}
