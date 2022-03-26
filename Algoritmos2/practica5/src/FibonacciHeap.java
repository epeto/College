// Clase para heaps de Fibonacci.

import java.util.HashMap;
import java.util.LinkedList;

public class FibonacciHeap{
    NodoF minNodo;
    // La tabla relaciona una llave con un nodo para poder ejecutar
    // decrementa llave en tiempo O(1). Por eso mismo no puede haber
    // llaves repetidas (o sí puede pero habrá colisiones).
    HashMap<Integer, NodoF> tabla;
    int tam; //Tamaño del heap.
    static final double PHI = 1.618; //número áureo.

    public FibonacciHeap(NodoF minimo){
        tabla = new HashMap<>();
        minNodo = minimo;
        tam = 0;
        if(minimo != null)
            tabla.put(minNodo.key, minNodo);
    }

    public FibonacciHeap(){
        this(null);
    }

    //Borra al nodo e de una lista ligada doble.
    public void eliminaNodo(NodoF e){
        NodoF izqTemp = e.izq;
        NodoF derTemp = e.der;

        izqTemp.der = derTemp;
        derTemp.izq = izqTemp;
    }

    //Calcula el logaritmo de un número en cualquier base.
    private double logaritmo(double num, double base) {
        return (Math.log10(num) / Math.log10(base));
    }

    //Agrega todos los nodos de cierto nivel en la posición 'nivel' de la lista de listas.
    public void toListNiveles(LinkedList<LinkedList<NodoF>> lista, int nivel, NodoF nodo){
        if(nivel >= lista.size()){
            LinkedList<NodoF> l2 = new LinkedList<>();
            lista.add(l2);
        }

        LinkedList<NodoF> laux = lista.get(nivel);
        laux.add(nodo);
        if(nodo.hijo != null){
            toListNiveles(lista,nivel+1,nodo.hijo);
        }
        NodoF sig = nodo.der;
        while(sig != nodo){
            laux.add(sig);
            if(sig.hijo != null){
                toListNiveles(lista,nivel+1,sig.hijo);
            }
            sig = sig.der;
        }
    }

    // Convierte este heap en una lista de listas. Todos los nodos en el mismo
    // nivel van a estar en la misma lista, sin importar quién sea el padre.
    public LinkedList<LinkedList<NodoF>> toList(){
        LinkedList<LinkedList<NodoF>> lista  = new LinkedList<>();
        if(minNodo != null){
            toListNiveles(lista, 0, minNodo);
        }
        return lista;
    }

    //Comprueba si una lista es vacía.
    public boolean isEmpty(){
        return tam==0;
    }

    //Se imprime el heap por niveles.
    public void imprimeHeap(){
        LinkedList<LinkedList<NodoF>> listilla = toList();
        for(LinkedList<NodoF> l : listilla){
            System.out.println(l);
        }
    }

    /**
     * Inserta un nuevo nodo en el heap.
     * @param newKey llave del nuevo nodo.
     */
    public void inserta(int newKey){
        NodoF x = new NodoF(newKey);
        tabla.put(newKey, x);
        if(minNodo == null){
            minNodo = x;
        }else{
            minNodo.insertaIzquierda(x);
            if(x.key < minNodo.key){
                minNodo = x;
            }
        }
        tam++;
    }

    /**
     * Devuelve la llave del elemento más pequeño. 
     * @return llave mínima.
     */
    public int encuentraMin(){
        if(minNodo != null){
            return minNodo.key;
        }else{
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Fusiona un heap en este (no se comprueba si heap2 es nulo).
     * @param heap2 heap a fusionar con este.
     */
    public void funde(FibonacciHeap heap2){
        if(minNodo == null){
            minNodo = heap2.minNodo;
        }else{
            minNodo.splice(heap2.minNodo);
            if(heap2.minNodo.key < minNodo.key){
                minNodo = heap2.minNodo;
            }
            tabla.putAll(heap2.tabla);
            tam+=heap2.tam;
        }
    }

    /**
     * Liga al nodo y con el x.
     * y se hace hijo de x.
     */
    public void link(NodoF y, NodoF x){
        eliminaNodo(y); //Se quita de su lista ligada.
        if(x.hijo == null){
            x.hijo = y;
            y.izq = y;
            y.der = y;
        }else{
            x.hijo.insertaIzquierda(y);
        }
        y.padre = x;
        x.rango++;
        y.marcado = false;
    }

    /**
     * Consolida un heap ligando nodos del mismo rango en
     * la lista de raíces.
     */
    public void consolida(){
        int longArr = (int) logaritmo(tam, PHI); //Longitud del arreglo.
        longArr = tam;
        NodoF[] arr = new NodoF[longArr];
        for(int i=0;i<arr.length;i++){
            arr[i] = null;
        }

        LinkedList<NodoF> raices = new LinkedList<>();
        raices.add(minNodo);
        NodoF sig = minNodo.der;
        while(sig != minNodo){
            raices.add(sig);
            sig = sig.der;
        }
        for(NodoF w : raices){ //Se itera sobre la lista de raíces.
            NodoF x = w;
            int r = x.rango;
            while(arr[r] != null){
                NodoF y = arr[r];
                if(x.key > y.key){ //Se intercambian.
                    NodoF temp = x;
                    x = y;
                    y = temp;
                }
                link(y,x);
                arr[r] = null;
                r++;
            }
            x.der = x;
            x.izq = x;
            arr[r] = x;
            w = x.der;
        }
        minNodo = null; // Se borra la lista de raíces.
        //Se crea la lista de raíces a partir del arreglo arr.
        for(int i=0; i<arr.length;i++){
            if(arr[i] != null){
                if(minNodo == null){
                    minNodo = arr[i];
                }else{
                    minNodo.insertaIzquierda(arr[i]);
                    if(arr[i].key < minNodo.key){
                        minNodo = arr[i];
                    }
                }
            }
        }
        /**
        for(NodoF r:raices){
            System.out.println(r+"-> p:"+r.padre+" h:"+r.hijo+" i:"+r.izq+" d:"+r.der);
        }
        */
    }

    /**
     * Devuelve la llave del elemento más pequeño y lo borra.
     */
    public int borraMin(){
        NodoF z = minNodo;
        if(z != null){
            tabla.remove(z.key); //Se borra de la tabla.
            if(z.hijo != null){
                LinkedList<NodoF> hijosZ = new LinkedList<>();
                hijosZ.add(z.hijo);
                NodoF sig = z.hijo.der;
                while(sig != z.hijo){
                    hijosZ.add(sig);
                    sig = sig.der;
                }
                //Se insertan todos los hijos de z en la lista de raíces.
                for(NodoF hz:hijosZ){
                    hz.der = hz;
                    hz.izq = hz;
                    hz.padre = null;
                    minNodo.insertaIzquierda(hz);
                }
            }
            eliminaNodo(z); //Se borra z de su lista de adyacencias.
            if(z == z.der){ //Significa que era el único elemento en la lista.
                minNodo = null;
            }else{
                minNodo = z.der;
                consolida();
            }
            tam--;
            return z.key;
        }else{
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Suponiendo que 'x' es hijo de 'y', elimina a 'x' de la lista de hijos de 'y'
     * y coloca a 'x' en la lista de nodos raíz.
     * @param x: algún hijo de y.
     * @param y: padre de x.
     */
    public void corte(NodoF x, NodoF y){
        eliminaNodo(x);
        if(x.der == x){ //Significa que era el único hijo de y.
            y.hijo = null;
        }else{
            y.hijo = x.der;
        }
        y.rango--;
        minNodo.insertaIzquierda(x); //lo agrega a la lista de nodos raíz.
        x.padre = null;
        x.marcado = false;
    }

    /**
     * Realiza un corte en cascada. Esto es, va cortando nodos
     * desde 'y' hasta la raíz o hasta que encuentra un nodo no marcado.
     * @param y nodo a partir del cual se realiza corte en cascada.
     */
    public void corteCascada(NodoF y){
        NodoF z = y.padre;
        if(z != null){
            if(!y.marcado){
                y.marcado = true;
            }else{
                corte(y,z);
                corteCascada(z);
            }
        }
    }

    //Decrementa la llave de un nodo.
    public void decrementaLlave(int vieja, int nueva){
        NodoF x = tabla.get(vieja); //Obtengo el nodo de la tabla hash.
        if(nueva > vieja){
            return;
        }

        //Hay que sustituir el viejo valor del nodo por el nuevo en la tabla.
        tabla.remove(vieja);
        tabla.put(nueva,x);
        x.key = nueva;
        NodoF y = x.padre;

        if(y != null && x.key < y.key){ //Se viola la propiedad de orden del heap.
            corte(x,y);
            corteCascada(y);
        }
        if(x.key < minNodo.key){
            minNodo = x;
        }
    }

    public static void main(String[] args){
        FibonacciHeap cola = new FibonacciHeap();
        cola.inserta(50);
        cola.inserta(60);
        cola.inserta(43);
        cola.inserta(3);
        cola.inserta(19);
        cola.inserta(23);
        while(!cola.isEmpty()){
            cola.imprimeHeap();
            System.out.println();
            cola.borraMin();
        }
    }

}
