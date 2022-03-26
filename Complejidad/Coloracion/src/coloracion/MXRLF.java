/*
 * Algoritmo MXRLF
 */
package coloracion;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class MXRLF {
    
    /**
     * Función que colorea la gráfica aplicando el algoritmo MXRLF
     * @param g 
     */
    public static void colorea(Grafica g){
        int cc = 0; //Color actual (current color)
        int MST = (int)(0.7*g.getOrden()); //MXRLF_SET_LIMIT
        
        while(!esColoreada(g)){ //Mientras no esté completamente coloreada
            LinkedList<Vertice> clase = new LinkedList(); //La clase de equivalencia de cc.
            LinkedList<Vertice> P = new LinkedList(); //Los no adyacentes a cc
            LinkedList<Vertice> R = new LinkedList(); //Los adyacentes a cc
            
            LinkedList<Vertice> nocol = noPintados(g); //Lista de vértices que no han sido coloreados.
            Vertice vm = maxGrado(nocol); //vm será el vértice con el mayor grado en esta lista.
            vm.color = cc;
            clase.add(vm);
            
            for(Vertice v:nocol){
                if(vm.vecinos.contains(v)){
                    R.add(v); //Si v es adyacente a vm lo agregamos a R.
                }else{
                    P.add(v); //Si v no es adyacente a vm lo agregamos a P.
                }
            }
            
            while(!P.isEmpty() && clase.size()<=MST){ //Mientras P no sea vacía
                Vertice v = maxVecR(P,R);
                P.removeAll(v.vecinos); //Quitamos de P todos los vecinos de v.
                for(Vertice vec:v.vecinos){
                    if(!R.contains(vec)){
                        R.add(vec); //Agregamos a R todos los vecinos de v.
                    }
                }
                v.color=cc;
                clase.add(v);
            }
            cc++; //Incrementa el current color
        }
    }
    
    /**
     * Recibe una lista de vértices y devuelve el que tiene el mayor grado.
     * @param lista : Lista a revisar
     * @return vértice con mayor grado
     */
    public static Vertice maxGrado(LinkedList<Vertice> lista){
        Vertice ret = lista.get(0);
        int indice = 0;
        for(int i=0;i<lista.size();i++){
            if(ret.getGrado()<lista.get(i).getGrado()){ //Si el grado del vértice i es mayor al grado del vértice ret lo cambiamos.
                ret = lista.get(i);
                indice = i;
            }
        }
        
        lista.remove(indice);
        return ret;
    }
    
    /**
     * Recibe 2 listas de vértices y devuelve el vértice de a con mayor número de vecinos en b.
     * @param a Lista cuyos vértices se van a revisar.
     * @param b Lista en la que se buscarán incidencias de vecinos.
     * @return
     */
    public static Vertice maxVecR(LinkedList<Vertice> a, LinkedList<Vertice> b){
        Vertice ret = a.get(0);
        
        for(Vertice v:a){
            if(incidencias(v,b)>incidencias(ret,b)){
                ret = v;
            }
        }
        a.remove(ret);
        return ret;
    }
    
    /**
     * Cuenta el número de vecinos de v que están en a.
     * @param vert
     * @param a
     * @return 
     */
    public static int incidencias(Vertice vert, LinkedList<Vertice> a){
        int cont=0;
        
        for(Vertice v:vert.vecinos){
            if(a.contains(v)){
                cont++; //Si v está en a, incrementa el contador.
            }
        }
        return cont;
    }
    
    /**
     * Función que recibe una gráfica y decide si está completamente coloreada.
     * @param g: gráfica que recibe
     * @return verdadero si está completamente coloreada
     */
    public static boolean esColoreada(Grafica g){
        for(Vertice v:g.verts){
            if(v.color == -1){
                return false; //Si algún elemento tiene -1 entonces no está coloreada
            }
        }
        return true; //Si terminó de revisar los vértices y ninguno tiene -1 entonces está coloreada
    }
    
    /**
     * Recibe una gráfica y devuelve una lista de los vértices que no están coloreados.
     * @param g gráfica que recibe
     * @return lista de vértices no coloreados
     */
    public static LinkedList<Vertice> noPintados(Grafica g){
        LinkedList<Vertice> retVal = new LinkedList();
        
        for(Vertice v:g.verts){
            if(v.color == -1){
                retVal.add(v);
            }
        }
        
        return retVal;
    }
    
    public static void main(String[] args){
        Grafica grafo;
        Random rn = new Random();
        int ord = rn.nextInt(41)+10; //El orden de la gráfica estará entre 10 y 50.
        int k = rn.nextInt(6)+5; //Será una gráfica k-partita con 5<=k<=10.
        grafo = new Grafica(ord,600); //Aquí creamos la gráfica.
        
        //Esta parte es para asegurarnos de que cada partición tendrá al menos un vértice.
        for(int i=0;i<k;i++){
            grafo.verts.get(i).part = i;
        }
        
        //En esta parte distribuimos de manera aleatoria el resto de los vértices en las demás particiones.
        for(int i=k;i<grafo.verts.size();i++){
            int p = rn.nextInt(k); //p es la partición a la que se va a agregar el vértice i.
            grafo.verts.get(i).part = p;
        }
        
        //En esta parte agregamos las aristas con una probabilidad de un 1/6 y solo si los vértices no están en la misma partición.
        for(int i=0;i<grafo.verts.size();i++){
            for(int j=i;j<grafo.verts.size();j++){
                int prob = rn.nextInt(6);
                if(prob==0){
                    if(grafo.verts.get(i).part!=grafo.verts.get(j).part){
                        grafo.addArista(i,j); //Agregamos la arista.
                    }
                }
            }
        }
        
        colorea(grafo);
        
        for(Vertice v:grafo.verts){
            System.out.print(v.color+" ");
        }
    }
}
