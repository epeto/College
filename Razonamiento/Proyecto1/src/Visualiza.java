
//Esta clase se utilizará para ver un dibujo de la gráfica.

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;


public class Visualiza{

    /**
     * Lee un archivo de gráfica y lo separa en líneas.
     * @param archivo
     * @return
     */
    public static LinkedList<String> leeGrafica(String archivo){
        LinkedList<String> lineas = new LinkedList<String>();
        try{
            FileReader fr1 = new FileReader(archivo);
            BufferedReader br1 = new BufferedReader(fr1);
    
            String linea = br1.readLine(); //Lee la primera línea.
            while(linea!=null){ //Mientras no llegue al final, lee líneas.
                lineas.add(linea);
                linea = br1.readLine();
            }
            br1.close();
        }catch(Exception e){System.err.println("Archivo no encontrado");}

        return lineas;
    }

    /**
     * Obtiene la partición de aristas a partir de un modelo de Z3.
     * @param archivo: Nombre del archivo resultante de Z3.
     */
    public static LinkedList<String> obtenParticion(String archivo){
        LinkedList<String> lineas = new LinkedList<String>();
        try{
            FileReader fr1 = new FileReader(archivo);
            BufferedReader br1 = new BufferedReader(fr1);
    
            String linea = br1.readLine(); //Lee la primera línea.
            while(linea!=null){ //Mientras no llegue al final, lee líneas.
                lineas.add(linea);
                linea = br1.readLine();
            }
            br1.close();
        }catch(Exception e){System.err.println("Archivo no encontrado");}

        LinkedList<String> aristas = new LinkedList<String>();

        if(lineas.get(0).equals("sat")){ //Si es satisfacible se llena la lista "aristas".
            lineas.removeFirst(); //elimina la línea "sat"
            lineas.removeFirst(); //elimina la línea "(model"
            lineas.removeLast(); //elimina la línea ")"

            for(int i=0; i<lineas.size(); i++){
                if(i%2 == 0){
                    lineas.set(i, lineas.get(i).substring(14,17));
                }else{
                    lineas.set(i, lineas.get(i).substring(4,8));
                }
            }
        
            for(int i=1; i<lineas.size(); i+=2){
                //Para cada arista uvn (con n=1 o n=2) si el modelo le dio "true" 
                //significa que uv va en la partición n.
                if(lineas.get(i).equals("true")){
                    aristas.add(lineas.get(i-1));
                }
            }
        } //Si es insatisfacible la lista de "aristas" es vacía.

        return aristas;
    }

    /**
     * Dibuja una gráfica de graphstream.
     * @param grafica: gráfica en formato de texto.
     * @param particion: define qué arista pertenece a cada partición.
     */
    public static void dibujaGrafica(List<String> grafica, List<String> particion){
        //Se definen los colores de las aristas.
        String styleSheet = "edge {size: 2px;}"
                            +"edge.part1 {fill-color: red;}"
                            +"edge.part2 {fill-color: blue;}";
        Graph graph = new SingleGraph("Ventana"); //Se crea una gráfica de graphstream

        //En esta parte se agregan todos los vértices a "graph".
        String vertices = grafica.get(0); //Los vértices están en la primera línea.
        for(int i=0; i<vertices.length(); i++){
            String nombreV = vertices.charAt(i)+"";
            graph.addNode(nombreV);
            graph.getNode(nombreV).addAttribute("ui.label", nombreV); //Se agrega una etiqueta con el nombre del vértice.
        }

        //En esta parte se agregan las aristas.
        for(int i=1; i<grafica.size(); i++){
            String u = grafica.get(i).charAt(0)+"";
            String v = grafica.get(i).charAt(1)+"";
            graph.addEdge(grafica.get(i), u, v, false);
        }

        //Se parten las aristas por clase. Una clase es part1 (partición 1) y la otra part2.
        for(String ari:particion){
            String nombreArista = ari.substring(0,2);
            Edge ariStream = graph.getEdge(nombreArista);

            if(ariStream != null){
                if(ari.charAt(2) == '1'){ //Pertenece a la partición 1
                    ariStream.setAttribute("ui.class","part1");
                }else{
                    ariStream.setAttribute("ui.class","part2");
                }
            }
        }

        graph.addAttribute("ui.stylesheet", styleSheet); //Se agrega la hoja de estilo a la gráfica.
        graph.display(); //Despliega la gráfica en pantalla.
    }

    /**
     * Para compilar: javac -cp gs-core-1.3.jar:. Visualiza.java
     * Para ejecutar: java -cp gs-core-1.3.jar:. Visualiza <archivo_de_grafica> <archivo_de_modelo>
     */
    public static void main(String[] args){
        LinkedList<String> grafica = leeGrafica(args[0]); //Archivo donde se encuentra la gráfica.
        LinkedList<String> particion = obtenParticion(args[1]); //Archivo donde se encuentra el modelo dado por z3.
        dibujaGrafica(grafica, particion);
    }
}
