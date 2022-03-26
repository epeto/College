

import java.util.HashMap;

public class Grafica {
    HashMap<String, Vertice> vertices; //Lista de vértices.
    boolean dirigida; //Verdadero si y solo si la gráfica es dirigida.
    boolean tieneFlujo;
  
    /**
     * Constructor de la clase gráfica.
     * @param dir: decide si la gráfica es dirigida o no.
     **/
    public Grafica(boolean dir){
        dirigida = dir;
        vertices = new HashMap<String, Vertice>();
    }

    /**
     * Constructor de la clase gráfica. Por defecto será no dirigida.
     **/
    public Grafica(){
        dirigida = false;
        vertices = new HashMap<String, Vertice>();
    }

    /**
    * Función que recibe un id y devuelve el vértice que tiene dicho id. Si no existe devuelve null.
    * @param ident: identificador del vértice.
    * @return vertice con ese id.
    */
    public Vertice getVerticeId(String ident){
        return vertices.get(ident);
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
    public void agregaArista(String i, String j, int peso){
        Vertice vert_i = getVerticeId(i);
        Vertice vert_j = getVerticeId(j);
        agregaArista(vert_i, vert_j, peso);
    }
  
    /**
    * Agrega un vértice a la gráfica
    * @param ident: identificador del vértice nuevo.
    */
    public void agregaVertice(String ident){
        Vertice nuevo = new Vertice(ident);
        vertices.put(ident, nuevo);
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

    //Modifica el peso de una arista. Le suma el 'agregado'.
    public void sumaPeso(Vertice u, Vertice v, int agregado){
        u.pesos.replace(v.id, getPeso(u,v) + agregado);
    }

    /**
     * Construye la gráfica residual de esta gráfica.
     */
    public Grafica residual(){
        Grafica gr = new Grafica(dirigida);
        gr.tieneFlujo = false;
        
        //Se copian los vértices.
        for(Vertice v : vertices.values()){
            gr.agregaVertice(v.id);
        }

        //Se construyen las nuevas aristas (v,u) y (u,v).
        for(Vertice u : vertices.values()){
            for(Vertice v : u.ady){
                gr.agregaArista(u.id, v.id, getPeso(u, v));
                gr.agregaArista(v.id, u.id, 0);
            }
        }

        return gr;
    }

    /**
     * Agrega un valor de flujo al arco (u,v).
     */
    public void agregaFlujo(Vertice u, Vertice v, int flujo){
        u.flujos.put(v.id, flujo);
    }

    /**
     * Obtiene el valor del flujo del arco (u,v).
     */
    public int getFlujo(Vertice u, Vertice v){
        return u.flujos.get(v.id);
    }

    @Override
    public String toString(){
        String salida = "";
        for(Vertice u : vertices.values()){
            salida += u.toString()+"->[";
            for(Vertice v : u.ady){
                salida += "("+v.id+","+u.pesos.get(v.id)+"), ";
            }
            salida += "]\n";
        }
        return salida;
    }
}



