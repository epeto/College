


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Lector{

    LinkedList<String> lineas;

    //Constructor del lector.
    public Lector(){
        lineas = new LinkedList<String>();
    }

    /**
    * Método que lee línea a línea el archivo que recibe.
    * @param archivo: nombre del archivo a leer.
    */
    public void lee(String archivo){
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
    }

    /**
    * Método que crea una gráfica con el archivo de entrada.
    * @return objeto tipo gráfica.
    */
    public Grafica creaGrafica(boolean directed){
        Grafica grafica = new Grafica(directed);
        String[] verts = lineas.get(0).split(","); //Se separan los vértices por comas
        String[][] matriz = new String[lineas.size()][lineas.size()];
        
        //Se agregan los vértices a la gráfica.
        for(int i=1;i<verts.length;i++){
            grafica.agregaVertice(verts[i]);
        }

        //Se construye la matriz.
        for(int i=0; i<lineas.size(); i++){
            matriz[i] = lineas.get(i).split(",");
        }

        //Se agregan las aristas.
        for(int i=1; i<matriz.length; i++){
            for(int j=1; j<matriz.length; j++){
                String id1 = matriz[i][0];
                String id2 = matriz[0][j];
                int peso = Integer.parseInt(matriz[i][j]);
                if(peso > 0){
                    grafica.agregaArista(id1, id2, peso);
                }
            }
        }

        return grafica;
    }

    public static void main(String[] args){
        Lector lector1 = new Lector();
        lector1.lee("ejemplares/red1.csv");
        System.out.println(lector1.creaGrafica(true));
    }
}




