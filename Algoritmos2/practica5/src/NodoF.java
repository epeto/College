// Clase para nodo fibonacci.
// Por simplicidad las llaves serán números enteros.

public class NodoF{
    int key; // La llave del nodo.
    NodoF padre; // Padre del nodo.
    NodoF hijo; // Algún hijo.
    NodoF izq; // Hermano izquierdo.
    NodoF der; // Hermano derecho.
    int rango = 0; // Número de hijos.
    boolean marcado;

    //Estos atributos son para dibujar el nodo.
    int c; //Columna
    int f; //Fila
    int x; //Posición en x
    int y; //Posición en y

    // Constructores
    NodoF( int llave )
    {
        this( llave, null, null, null, null, 0);
    }

    /**
     * Constructor para la clase NodoF
     * @param llave: la llave inicial de este nodo.
     * @param p: padre de este nodo.
     * @param h: algún hijo de este nodo.
     * @param i: hermano izquierdo de este nodo (si es null apunta a this).
     * @param d: hermano derecho de este nodo (si es null apunta a this).
     * @param r: rango inicial de este nodo.
     */
    NodoF( int llave, NodoF p, NodoF h, NodoF i, NodoF d,int r)
    {
        key = llave;
        padre = p;
        hijo = h;
        if(i == null){
            izq = this;
        }else{
            izq = i;
        }

        if(d == null){
            der = this;
        }else{
            der = d;
        }
        rango = r;
        marcado = false;
    }

    //Inserta un nodo a la izquierda de este.
    public void insertaIzquierda(NodoF x){
        x.izq = x;
        x.der = x;
        NodoF a = izq;
        a.der = x;
        x.izq = a;
        x.der = this;
        izq = x;
    }

    //Empalma una lista ligada en esta.
    public void splice(NodoF x){
        NodoF a = izq;
        NodoF b = x.izq;

        a.der = x;
        x.izq = a;
        b.der = this;
        this.izq = b;
    }

    // Imprime una lista ligada.
    public void imprimeLista(){
        int llaveInicial = key;
        System.out.print("["+llaveInicial+",");
        NodoF sig = der;
        while(sig.key != llaveInicial){
            System.out.print(sig.key+",");
            sig = sig.der;
        }
        System.out.println("]");
    }

    // Imprime arbol.
    public void imprimeArbol(){
        imprimeLista();
        if(hijo != null){
            hijo.imprimeArbol();
        }
        int llaveInicial = key;
        NodoF sig = der;
        while(sig.key != llaveInicial){
            if(sig.hijo != null){
                sig.hijo.imprimeArbol();
            }
            sig = sig.der;
        }
    }

    @Override
    public String toString(){
        return String.valueOf(key);
    }

    public static void main(String[] args){
        //Lista 1
        NodoF lista1 = new NodoF(3);
        lista1.insertaIzquierda(new NodoF(6));
        lista1.insertaIzquierda(new NodoF(8));
        lista1.insertaIzquierda(new NodoF(10));
        lista1.insertaIzquierda(new NodoF(24));
        lista1.insertaIzquierda(new NodoF(56));
        lista1.imprimeLista();

        //Lista 2
        NodoF lista2 = new NodoF(5);
        lista2.insertaIzquierda(new NodoF(10));
        lista2.insertaIzquierda(new NodoF(20));
        lista2.insertaIzquierda(new NodoF(23));
        lista2.insertaIzquierda(new NodoF(45));
        lista2.insertaIzquierda(new NodoF(90));
        lista2.insertaIzquierda(new NodoF(15));
        lista2.imprimeLista();

        lista1.splice(lista2);
        lista1.imprimeLista();
    }
}

