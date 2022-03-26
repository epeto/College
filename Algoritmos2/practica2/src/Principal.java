

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Principal{
    /**
    * Método que recibe un objeto tipo Grafica y la dibuja.
    * @param g: gráfica que recibe.
    */
    public void dibujaGrafica(Grafica g){
        String styleSheet =  "edge {size: 2px; text-size: 15;}"
                            +"edge.saturado {fill-color: red;}"
                            +"node {text-size: 15;}"
                            +"node.visitado {fill-color: blue;}"; //Estilo de la gráfica.
        Graph graph = new SingleGraph("Ventana"); //Se crea una gráfica de graphstream
        
        //En esta parte se agregan todos los vértices a "graph".
        for(Vertice v : g.vertices.values()){
            graph.addNode(v.toString());
            graph.getNode(v.toString()).addAttribute("ui.label", v.toString()); //Se agrega una etiqueta con el nombre del vértice.
            //Para pintar los vértices etiquetados.
            if(v.visitado){
                graph.getNode(v.toString()).addAttribute("ui.class","visitado");
            }
        }

        //En esta parte se agregan las aristas.
        if(g.dirigida){ //Si es dirigida
            for(Vertice v : g.vertices.values()){
                for(Vertice u : v.ady){
                    String nombreArista = v.toString()+","+u.toString();
                    graph.addEdge(nombreArista, v.toString(), u.toString(), true);
                    Edge ari = graph.getEdge(nombreArista);
                    if(g.tieneFlujo){
                        ari.addAttribute("ui.label", g.getFlujo(v,u)+","+g.getPeso(v,u));
                        if(g.getFlujo(v,u) == g.getPeso(v,u))
                            ari.setAttribute("ui.class","saturado");
                    }else{
                        ari.addAttribute("ui.label", g.getPeso(v,u));
                    }
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : g.vertices.values()){
                for(Vertice u : v.ady){
                    String nombreArista = v.toString()+","+u.toString();
                    String nombreArista2 = u.toString()+","+v.toString();
                    if(graph.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista (u,v).
                        graph.addEdge(nombreArista, v.toString(), u.toString()); //Agregar la (v,u).
                        Edge ari = graph.getEdge(nombreArista);
                        if(g.tieneFlujo){
                            ari.addAttribute("ui.label", g.getFlujo(v,u)+","+g.getPeso(v,u));
                            if(g.getFlujo(v,u) == g.getPeso(v,u))
                                ari.setAttribute("ui.class","saturado");
                        }else{
                            ari.addAttribute("ui.label", g.getPeso(v,u));
                        }
                    }
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
            g1 = l.creaGrafica(false);
        }

        //Aquí se ejecuta el algoritmo.
        Grafica g2 = Algoritmo.etiquetamiento(g1);

        int fMax = 0;
        Vertice s = g1.getVerticeId("s");
        for(Vertice v : s.ady){
            fMax += s.flujos.get(v.id);
        }
        System.out.println("El flujo en la red es: "+fMax);
        System.out.println("\nLa red residual es:\n"+g2.toString());
        Principal pr = new Principal();
        pr.dibujaGrafica(g2); //Se dibuja la gráfica residual en pantalla
        pr.dibujaGrafica(g1); //Se dibuja la gráfica 1 en pantalla
    }
}




