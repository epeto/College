
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

/**
 Esta clase contiene los algoritmos para recorrer una gráfica, es decir, BFS y DFS.
**/

public class Recorrido{

    /**
     * Algoritmo BFS que calcula la distancia entre el vértice s y el resto de vértices
     * alcanzables por s.
     * @param s Vértice de origen.
     * @return componente: Lista de vértices alcanzables por s, incluyendo s.
     */
    public static LinkedList<Vertice> bfs_visit(Vertice s){
        Cola<Vertice> q = new Cola<Vertice>(); //Cola usada en el algoritmo.
        LinkedList<Vertice> componente = new LinkedList<Vertice>();
        s.d = 0; //La distancia del vértice de origen es 0.
        s.visitado = true;
        componente.add(s);
        q.enqueue(s);

        while(!q.isEmpty()){
            Vertice u = q.dequeue();
            for(Vertice v:u.vecinos){
                if(!v.visitado){
                    v.visitado = true;
                    componente.add(v);
                    v.d = u.d+1;
                    v.p = u;
                    q.enqueue(v);
                }
            }
        }

        return componente;
    }

    /**
     * Aplica el algoritmo BFS_visit a cada vértice de la gráfica g.
     * @param g Gráfica a la que se le aplica el algoritmo.
     * @return comps: Lista de componentes conexas de la gráfica g.
     */
    public static LinkedList<LinkedList<Vertice>> bfs(Grafica g){
        LinkedList<LinkedList<Vertice>> comps = new LinkedList<LinkedList<Vertice>>();
        for(Vertice v:g.vertices){
            if(!v.visitado){
                comps.add(bfs_visit(v));
            }
        }

        return comps;
    }

    public static LinkedList<Vertice> dfs_visit(Vertice s){
        int t = 1; //Variable que marca el momento en que fue descubierto un vértice.
        LinkedList<Vertice> componente = new LinkedList<Vertice>(); //Lista de vértices alcanzables por s
        Stack<Vertice> pila = new Stack<Vertice>();
        s.visitado = true;
        s.dfi = t;
        t++;
        pila.push(s);

        while(!pila.isEmpty()){
            Vertice v = pila.peek();
            if(v.iterador.hasNext()){ //Mientras tenga vecinos.
                Vertice w = v.iterador.next(); //Siguiente vecino de v
                if(!w.visitado){  //Si w no ha sido visitado
                    w.visitado = true; //Se marca como visitado.
                    w.p = v; //El antecesor de w es v.
                    w.dfi = t; //Se le coloca el índice dfi.
                    t++; //Se incrementa el contador de tiempo.
                    pila.push(w); //Se agrega w a la pila.
                    componente.add(w); //Se agrega w a la componente.
                }
            }else{
                pila.pop();
            }
        }

        return componente;
    }

    public static LinkedList<LinkedList<Vertice>> dfs(Grafica g){
        LinkedList<LinkedList<Vertice>> comps = new LinkedList<LinkedList<Vertice>>();
        for(Vertice v:g.vertices){
            v.iterador = v.vecinos.listIterator();
        }

        for(Vertice v:g.vertices){
            if(!v.visitado){
                comps.add(dfs_visit(v));
            }
        }

        return comps;
    }
}
