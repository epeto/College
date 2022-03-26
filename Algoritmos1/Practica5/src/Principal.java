
import java.util.LinkedList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Principal{
    /**
    * Método que recibe un objeto tipo Grafica y la dibuja.
    * @param g: gráfica que recibe.
    */
    public void dibujaGrafica(Grafica g){
        String styleSheet = "edge.tree {fill-color: red;}"; //Para pintar de rojo las aristas que pertenecen al árbol.
        Graph graph = new SingleGraph("Ventana"); //Se crea una gráfica de graphstream
        
        //En esta parte se agregan todos los vértices a "graph".
        for(Vertice v : g.vertices){
            graph.addNode(v.nombre);
            graph.getNode(v.nombre).addAttribute("ui.label", v.nombre); //Se agrega una etiqueta con el nombre del vértice.
        }

        //En esta parte se agregan las aristas.
        if(g.dirigida){ //Si es dirigida
            for(Vertice v : g.vertices){
                for(Vertice u : v.vecinos){
                    String nombreArista = v.nombre+","+u.nombre;
                    graph.addEdge(nombreArista, v.nombre, u.nombre,true);
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : g.vertices){
                for(Vertice u : v.vecinos){
                    String nombreArista = v.nombre+","+u.nombre;
                    String nombreArista2 = u.nombre+","+v.nombre;
                    if(graph.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista.
                        graph.addEdge(nombreArista, v.nombre, u.nombre);
                    }
                }
            }
        }

        //En esta parte se cambia la clase(de estilo) de las aristas que pertenecen al bosque.
        for(Vertice v : g.vertices){
            if(v.p != null){
                String nombreAri1 = v.nombre+","+v.p.nombre;
                String nombreAri2 = v.p.nombre+","+v.nombre;
                Edge ari1 = graph.getEdge(nombreAri1);
                Edge ari2 = graph.getEdge(nombreAri2);

                if(ari1 != null){ //Si la arista existe.
                    ari1.setAttribute("ui.class","tree");
                }else{
                    if(ari2 != null){
                        ari2.setAttribute("ui.class","tree");
                    }
                }
            }
        }

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.display(); //Se pone la gráfica en pantalla.
    }

    public static void main(String[] args){
        Lector l = new Lector();
        l.lee(args[0]); //Se lee el archivo.
        Grafica g1 = l.creaGrafica(); //Se crea una gráfica con el archivo leído.
        Principal pr = new Principal();
        LinkedList<LinkedList<Vertice>> componentes;

        if(args[1].equals("dfs")){
            componentes = Recorrido.dfs(g1);
        }else{
            componentes = Recorrido.bfs(g1);
        }

        for(LinkedList<Vertice> componente:componentes){
            System.out.println(componente); //Se imprime cada componente.
        }

        pr.dibujaGrafica(g1); //Se dibuja la gráfica en pantalla
    }
}




