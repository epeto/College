

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Principal{
    /**
    * Método que recibe un objeto tipo Grafica y la dibuja.
    * @param g: gráfica que recibe.
    */
    public void dibujaGrafica(Grafica g){
        String styleSheet =  "edge {size: 2px; text-size: 15;}"
                            +"edge.rojo {fill-color: red;}"
                            +"edge.negro {fill-color: black;}"
                            +"node {text-size: 15;}"; //Estilo de la gráfica.

        Graph graph = new SingleGraph("Ventana"); //Se crea una gráfica de graphstream
        
        //En esta parte se agregan todos los vértices a "graph".
        for(Vertice v : g.vertices.values()){
            graph.addNode(v.toString());
            graph.getNode(v.toString()).addAttribute("ui.label", v.toString()); //Se agrega una etiqueta con el nombre del vértice.
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
                        }else{
                            ari.addAttribute("ui.label", g.getPeso(v,u));
                        }
                    }
                }
            }
        }
        graph.addAttribute("ui.stylesheet", styleSheet); //Se agrega la hoja de estilo a la gráfica.
        graph.display(); //Se pone la gráfica en pantalla.

        escalable(g, graph);
    }

    /**
     * Actualiza la gráfica original y la gráfica de graphstream enviando flujo por
     * una ruta de 's' a 't' definida en la gráfica residual.
     * @param original Gráfica original
     * @param residual Gráfica residual
     * @param stream Gráfida de graphstream
     */
    public void enviaFlujo(Grafica original, Grafica residual, Graph stream){
        Vertice na = residual.getVerticeId("t"); //Se obtiene el nodo final.
        while(na.p != null){
            //Se envía flujo de 'na.p' a 'na' pero en la gráfica original.
            int flujo = residual.getPeso(na, na.p);
            Vertice v1 = original.getVerticeId(na.p.toString());
            Vertice v2 = original.getVerticeId(na.toString());
            original.setFlujo(v1, v2, flujo);

            Edge ari = stream.getEdge(v1.toString()+","+v2.toString());
            ari.setAttribute("ui.label", original.getFlujo(v1,v2)+","+original.getPeso(v1,v2));
            na = na.p;
        }
    }

    /**
     * Agrega las aristas de una ruta de 's' a 't' a una clase de ui.class.
     * @param residual Gráfica residual
     * @param stream Gráfica de graphstream.
     * @param clase Clase a la cual va a pertenecer stream.
     */
    public void pinta(Grafica residual, Graph stream, String clase){
        Vertice na = residual.getVerticeId("t");
        while(na.p != null){
            Edge ari = stream.getEdge(na.p.toString()+","+na.toString());
            ari.removeAttribute("ui,class");
            ari.setAttribute("ui.class", clase);
            na = na.p;
        }
    }

    /**
     * Algoritmo de capacidades escalables para calcular el flujo máximo en g.
     * Devuelve la gráfica residual final.
     * @param g Gráfica sobre la cual se aplica el algoritmo.
     * @param stream Gráfica de graphstream que sirve para dibujar g.
     * @return Gráfica residual.
     */
    public Grafica escalable(Grafica g, Graph stream){
        g.tieneFlujo = true;
        Grafica gRes = g.residual();
        Vertice source = gRes.getVerticeId("s");
        Vertice terminal = gRes.getVerticeId("t");
        int delta = Algoritmo.potenciaMaxima(Algoritmo.getPesoMax(gRes)); //Obtiene la primer delta.

        while(delta >= 1){
            System.out.println("Delta: "+delta);
            terminal.visitado = true; //Sólo para pasar el primer while.
            while(terminal.visitado){
                Algoritmo.bfs(gRes, source, delta);

                if(terminal.visitado){
                    int aumento = Algoritmo.minEnRuta(gRes, terminal);
                    Algoritmo.actualizaResidual(gRes, terminal, aumento);
                    pinta(gRes, stream, "rojo");
                    enviaFlujo(g, gRes, stream);
                    try{
                        Thread.sleep(3000); //Se duerme 3 segundos
                    }catch(Exception e){
                        System.err.println("No se pudo dormir");
                    }
                    pinta(gRes, stream, "negro");
                }
            }
            delta /= 2;
        }

        return gRes;
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

        Principal pr = new Principal();
        pr.dibujaGrafica(g1); //Se dibuja la gráfica 1 en pantalla

        int fMax = 0;
        Vertice s = g1.getVerticeId("s");
        for(Vertice v : s.ady){
            fMax += s.flujos.get(v.id);
        }
        System.out.println("El flujo en la red es: "+fMax);
    }
}




