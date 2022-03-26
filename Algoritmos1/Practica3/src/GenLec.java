
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

//Esta clase tiene el generador de números y el lector.

public class GenLec{

    /**
     * Función que genera una lista de números ordenados y lo guarda en el archivo que recibe.
     * @param archivo: nombre del archivo que va a leer.
     * @param n: tamaño del ejemplar.
    **/
    public void generador(int n, String archivo){
        LinkedList<Integer> lista = new LinkedList<Integer>();
        Random rn = new Random();
        int inicial = -rn.nextInt(50);
        lista.add(inicial);
        
        for(int i=1;i<n;i++){
            int siguiente = lista.getLast();
            siguiente += rn.nextInt(15);
            lista.add(siguiente);
        }

        try{
            FileWriter fw=new FileWriter(archivo);
            for(Integer e:lista){
                fw.write(e+" ");
            }
            fw.close();
        }catch(IOException e){System.err.println("No se pudo escribir el archivo");}
    }

    /**
     * Función que lee un archivo de números y devuelve el arreglo con esos números.
     * @param archivo: nombre del archivo a leer
     * @return arreglo con los números
    **/
    public int[] lector(String archivo){
        int[] retorno;
        String total = ""; //Aquí se guardará todo el archivo de texto
        try{
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while((linea = br.readLine())!=null){
                total += linea+" ";
            }
            br.close();
        }catch(IOException e){System.err.println("No se pudo leer el archivo");}

        total = total.trim(); //Se le quitan los espacios al inicio y al final
        String[] separado = total.split(" "); //Se separan los números por espacio
        retorno = new int[separado.length];
        //Se convierten las cadenas a números.
        for(int i=0;i<separado.length;i++){
            if(separado[i]!=""){
                retorno[i] = Integer.parseInt(separado[i]);
            }
        }
        return retorno;
    }

    public static void main(String[] args){
        GenLec gl = new GenLec();
        int tam = Integer.parseInt(args[0]); //Tamaño  de la secuencia.
        gl.generador(tam,args[1]); //Se generan los números.
    }
}




