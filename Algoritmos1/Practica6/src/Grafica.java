


import java.util.LinkedList;

public class Grafica {
    LinkedList<Vertice> vertices; //Lista de vértices.
    boolean dirigida; //Verdadero si y solo si la gráfica es dirigida.
  
    /**
     * Constructor de la clase gráfica.
     * @param dir: decide si la gráfica es dirigida o no.
     **/
    public Grafica(boolean dir){
        dirigida = dir;
        vertices = new LinkedList<Vertice>();
    }

    /**
     * Constructor de la clase gráfica. Por defecto será no dirigida.
     **/
    public Grafica(){
        dirigida = false;
        vertices = new LinkedList<Vertice>();
    }

    /**
    * Función que recibe un id y devuelve el vértice que tiene dicho id. Si no existe devuelve null.
    * @param ident: identificador del vértice.
    * @return vertice con ese id.
    */
    public Vertice getVerticeId(int ident){
        Vertice ret = null;
        for(Vertice v:vertices){
            if(ident == v.id){
                ret = v;
            }
        }
        return ret;
    }
 
    /**
    * Función que agrega una arista cuando recibe los objetos tipo Vertice.
    * @param vi: vértice de origen de la arista.
    * @param vj: id del vértice de destino de la arista.
    * @param peso: peso de la arista a agregar.
    */
    public void agregaArista(Vertice vi, Vertice vj, int peso){
        vi.agregaVecino(vj,peso);
    
        if(!dirigida){
            vj.agregaVecino(vi,peso);
        }
    }

    /**
    * Función que agrega una arista, recibiendo los id's de los vértices.
    * @param i: id del vértice de origen de la arista.
    * @param j: id del vértice de destino de la arista.
    * @param peso: peso de la arista a agregar.
    */
    public void agregaArista(int i, int j, int peso){
        Vertice vert_i = getVerticeId(i);
        Vertice vert_j = getVerticeId(j);
        agregaArista(vert_i, vert_j, peso);
    }
  
    /**
    * Agrega un vértice a la gráfica
    * @param ident: identificador del vértice nuevo.
    */
    public void agregaVertice(int ident){
        Vertice nuevo = new Vertice(ident);
        vertices.add(nuevo);
    }

    /**
    * Obtiene el orden(número de vértices) de la gráfica.
    * @return orden de la gráfica.
    */
    public int getOrden(){
        return vertices.size();
    }

    /**
     * Obtiene el peso de una arista.
     * @param u : vértice de origen.
     * @param v : vértice de destino
     * @return peso de la arista (u,v)
     */
    public int getPeso(Vertice u, Vertice v){
        return u.pesos.get(v.id);
    }

    @Override
    public String toString(){
        String salida = "";
        for(Vertice v:vertices){
            salida += v.toString()+"->"+v.ady.toString()+"\n";
        }
        return salida;
    }
}



