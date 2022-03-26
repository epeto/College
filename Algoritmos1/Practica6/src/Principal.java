

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Principal{
    /**
    * Método que recibe un objeto tipo Grafica y la dibuja.
    * @param g: gráfica que recibe.
    */
    public void dibujaGrafica(Grafica g){
        String styleSheet = "edge {size: 2px; text-size: 15;}"
                            +"edge.tree {fill-color: red;}"
                            +"node {text-size: 15;}"
                            +"node.origen {fill-color: blue;}"; //Estilo de la gráfica.

        Graph graph = new SingleGraph("Ventana"); //Se crea una gráfica de graphstream
        
        //En esta parte se agregan todos los vértices a "graph".
        for(Vertice v : g.vertices){
            graph.addNode(v.toString());
            graph.getNode(v.toString()).addAttribute("ui.label", v.toString()+"  "+v.d); //Se agrega una etiqueta con el nombre del vértice.
        }

        //En esta parte se agregan las aristas.
        if(g.dirigida){ //Si es dirigida
            for(Vertice v : g.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.toString()+","+u.toString();
                    graph.addEdge(nombreArista, v.toString(), u.toString(), true);
                    graph.getEdge(nombreArista).addAttribute("ui.label", g.getPeso(v,u)); //Se etiqueta la arista con su peso.
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : g.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.toString()+","+u.toString();
                    String nombreArista2 = u.toString()+","+v.toString();
                    if(graph.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista (u,v).
                        graph.addEdge(nombreArista, v.toString(), u.toString()); //Agregar la (v,u).
                        graph.getEdge(nombreArista).addAttribute("ui.label", g.getPeso(v,u)); //Se etiqueta la arista con su peso.
                    }
                }
            }
        }

        //En esta parte se cambia la clase (de estilo) de las aristas que pertenecen al bosque.
        for(Vertice v : g.vertices){
            if(v.p != null){
                String nombreAri = v.p.toString()+","+v.toString();
                Edge ari = graph.getEdge(nombreAri);

                if(ari != null){ //Si la arista existe.
                    ari.setAttribute("ui.class","tree");
                }
            }else{
                if(v.d == 0){
                    //El vértice de origen se pinta de azul.
                    graph.getNode(v.toString()).addAttribute("ui.class","origen");
                }
            }
        }

        graph.addAttribute("ui.stylesheet", styleSheet); //Se agrega la hoja de estilo a la gráfica.
        graph.display(); //Se pone la gráfica en pantalla.
    }

    public static void main(String[] args){
        Lector l = new Lector();
        l.lee(args[0]); //Se lee el archivo.
        Grafica g1;
        if(args.length > 1){
            g1 = l.creaGrafica(args[1].equals("0"));
        }else{
            g1 = l.creaGrafica(); //Se crea una gráfica con el archivo leído.
        }

        Algoritmo.dijkstra(g1, g1.vertices.get(0));
        Principal pr = new Principal();
        pr.dibujaGrafica(g1); //Se dibuja la gráfica en pantalla
    }
}




