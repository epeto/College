
import java.util.Random;
import java.util.LinkedList;
import java.io.*;

public class Principal{
    Grafica grafica;
    Lector lector;
    LinkedList<Vertice> grafica2; //De aquí se irán borrando los vértices.
    LinkedList<Vertice> conjuntoInd; //Conjunto independiente.

    //Constructor
    public Principal(String archivo){
        lector = new Lector(archivo);
        grafica = new Grafica(false);
        //Se agregan todos los vértices.
        for(int i=0;i<lector.vertices.length;i++){
            grafica.agregaVertice(lector.vertices[i]);
        }
        //Se agregan todas las aristas.
        for(String[] a:lector.aristas){
            grafica.agregaArista(a[0],a[1]);
        }
        grafica2 = new LinkedList<Vertice>();
        grafica2.addAll(grafica.vertices);
        conjuntoInd = new LinkedList<Vertice>();
    }

    //Función que borra un vértice y a sus vecinos de "grafica2".
    public void borraVV(Vertice v){
        grafica2.removeAll(v.vecinos);
        grafica2.remove(v);
    }

    //Obtiene un vértice aleatorio de "grafica2".
    public Vertice getVA(){
        Random rn = new Random();
        int indice = rn.nextInt(grafica2.size());
        return grafica2.get(indice);
    }

    //Genera el conjunto independiente de la gráfica.
    public void generaCI(){
        while(!grafica2.isEmpty()){ //Se repite el proceso hasta que "grafica2" sea vacía.
            Vertice vert = getVA(); //Obtiene un vértice aleatorio de la lista "grafica2".
            conjuntoInd.add(vert); //Agrega este vértice al conjunto independiente.
            borraVV(vert); //Borra este vértice y a sus vecinos.
        }
    }

    //Clase principal
    public static void main(String[] args){
        Principal p = new Principal("Input/"+args[0]); //El nombre del archivo se pasa como parámetro al programa.
        p.generaCI();
        String salida = p.conjuntoInd.toString();
        if(salida.length()>2){
            salida = salida.substring(1,salida.length()-1);
        }

        System.out.println(salida);

        try{
            FileWriter fw=new FileWriter("Output/Salida"+args[0]);
            fw.write(salida);
            fw.close(); 
        }catch(IOException e){System.err.println("No se pudo escribir el archivo");}
    }
}



