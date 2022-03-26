
/**
 * Clase para hacer cifrado de Hill.
 * @author Emmanuel Peto Gutiérrez.
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Hill {
    String ALF_ESP = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"; //Incluye la Ñ.
    String ALF_LATIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //No incluye la Ñ.

    //Obtiene el índice en el alfabeto latín.
    public int getIndexLatin(char c){
        return c-65;
    }

    //Obtiene el índice en el alfabeto español.
    public int getIndexEsp(char c){
        return ALF_ESP.indexOf(c);
    }

    /**
     * Recibe un texto en cualquier formato y deja solamente los caracteres alfanuméricos.
     * Además, convierte todo el texto a mayúsculas.
     * @param texto
     * @return
     */
    public String limpiaTexto(String texto){
        texto = texto.toUpperCase();
        String limpio = "";
        for(int i=0; i<texto.length();i++){
            char c = texto.charAt(i);
            if((c > 64 && c < 91) || c == 'Ñ'){
                limpio += c;
            }
        }
        return limpio;
    }

    /**
     * Lee un archivo de texto y devuelve el texto en forma de String.
     * @param archivo Nombre del archivo que contiene texto.
     * @return Contenido del texto.
     */
    public String lee(String archivo){
        String lectura = "";
        try{
            FileReader fr1 = new FileReader(archivo);
            BufferedReader br1 = new BufferedReader(fr1);
            String linea = br1.readLine(); //Lee la primera línea.
            while(linea!=null){ //Mientras no llegue al final, lee líneas.
                lectura += linea;
                linea = br1.readLine();
            }
            br1.close();
        }catch(Exception e){System.err.println("Archivo no encontrado");}
        
        return lectura;
    }

    /**
     * Lee una matriz en un archivo de texto y devuelve la matriz en enteros.
     * @param archivo Nombre del archivo que contiene la matriz.
     * @return matriz guardada en el archivo de texto.
     */
    public static int[][] leeMatriz(String archivo){
        LinkedList<String> lineas = new LinkedList<>();
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

        int[][] matriz = new int[lineas.size()][lineas.size()];
        int i=0;
        for(String l : lineas){
            String[] vectorS = l.split(",");
            for(int j=0;j<vectorS.length;j++){
                matriz[i][j] = Integer.parseInt(vectorS[j]);
            }
            i++;
        }

        return matriz;
    }

    /**
     * Escribe un texto en un archivo.
     * @param texto Cadena de caracteres a escribir en un archivo.
     * @param archivo Nombre del nuevo archivo.
     */
    public void escribe(String texto, String archivo){
        try{
            FileWriter fw1 = new FileWriter(archivo);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            for(int i=0;i<texto.length();i++){
                if((i % 100) == 0){ //Cada 100 letras escribe un salto de línea.
                    bw1.write('\n');
                }
                bw1.write(texto.charAt(i));
            }
            bw1.close();
        }catch(Exception ex){}
    }

    /**
     * Transforma una cadena de caracteres a un bloque de números.
     * @param bloque Trozo de texto a transformar.
     * @return Arreglo de números ligados al bloque, donde A->0, B->1,...Z->26.
     */
    public int[] stringToInt(String bloque){
        int[] ret = new int[bloque.length()];
        for(int i=0;i<bloque.length();i++){
            ret[i] = getIndexEsp(bloque.charAt(i));
        }
        return ret;
    }

    /**
     * Transforma un bloque de números a una cadena de caracteres.
     * @param bloque de números a transformar en una cadena.
     * @return Cadena de caracteres que representa un bloque de números. 0->A, 1->B...
     */
    public String intToString(int[] bloque){
        String bloqueAlfa = "";
        for(int i=0;i<bloque.length;i++){
            bloqueAlfa += ALF_ESP.charAt(bloque[i]);
        }
        return bloqueAlfa;
    }

    /**
     * Cifrado de Hill.
     * @param entrada Texto claro que se va a cifrar.
     * @param llave Matriz que se va a usar para cifrar el texto.
     */
    public String cifrar(String entrada, int[][] llave){
        String tl = limpiaTexto(entrada); //Primero obtengo el texto limpio.
        int tam = llave.length; //Se obtiene el tamaño de los trozos.
        if((tl.length()%tam) != 0){ //Si el texto no es divisible en tam, se agregan X's.
            int numX = tam - (tl.length()%tam);
            String xs = "";
            for(int i=0;i<numX;i++){
                xs += 'X';
            }
            tl = tl+xs;
        }

        //Se crea un arreglo que va a contener bloques de texto transformados a número.
        LinkedList<int[]> textoNum = new LinkedList<>();

        for(int i=0; i<tl.length(); i+=tam){
            int[] bloqueNums = stringToInt(tl.substring(i,i+tam));
            textoNum.add(bloqueNums);
        }

        //Esta lista va a contener los bloques de números cifrados.
        LinkedList<int[]> criptoNum = new LinkedList<>();

        for(int[] bloque : textoNum){
            int[] bloqueCifrado = OperMat.matrizXvector(llave, bloque);
            OperMat.vectorModulo(bloqueCifrado, ALF_ESP.length());
            criptoNum.add(bloqueCifrado);
        }

        //Después, todos esos bloques cifrados se cambian a texto. Así se obtiene el criptograma.
        String criptograma = "";
        for(int[] bc : criptoNum){
            criptograma += intToString(bc);
        }

        return criptograma;
    }

    /**
     * Descifra un criptograma de Hill.
     * @param entrada Criptograma.
     * @param llave La llave que fue usada para cifrar.
     */
    public String descifrar(String entrada, int[][] llave){
        int[][] llaveInversa = OperMat.matrizInversa(llave, ALF_ESP.length());
        return cifrar(entrada, llaveInversa);
    }

    public static void main(String[] args){
        //Nombre del autor: Emmanuel Peto Gutiérrez
        Hill hill = new Hill();
        String textoPred = "No hay nada que un hombre no sea capaz de hacer cuando una mujer le mira";
        int[][] llavePred = {{9,4},
                             {5,7}};

        Scanner sc = new Scanner(System.in);
        System.out.println("Elija una operación:\n1.- Cifrar un archivo de texto."
                          +"\n2.- Descifrar un archivo de texto."
                          +"\n3.- Cifrar el texto por defecto.");

        int opcion = Integer.parseInt(sc.nextLine());

        if(opcion == 1){
            System.out.println("Escriba el nombre del archivo que contiene el texto claro.");
            String archivoEntrada = sc.nextLine();
            System.out.println("Escriba el nombre del archivo que contiene la llave.");
            String archivoLlave = sc.nextLine();
            System.out.println("Escriba el nombre del archivo donde se va a guardar el texto cifrado.");
            String archivoSalida = sc.nextLine();

            int[][] llave = leeMatriz("llaves/"+archivoLlave);
            OperMat.imprimeMatriz(llave);
            if(OperMat.mcd(OperMat.det(llave), hill.ALF_ESP.length()) != 1){
                sc.close();
                throw new RuntimeException("La matriz no es invertible");
            }

            String textoPlano = hill.lee("original/"+archivoEntrada); //Se lee el archivo de texto.
            if(textoPlano.length() == 0){
                sc.close();
                return;
            }
            String criptotexto = hill.cifrar(textoPlano, llave); //Se cifra.
            //Se escribe el criptograma en un archivo de salida.
            hill.escribe(criptotexto, "criptograma/"+archivoSalida);
            System.out.println("El resultado está en la carpeta 'criptograma'");
        }else if(opcion == 2){
            System.out.println("Escriba el nombre del archivo que contiene el texto cifrado.");
            String archivoEntrada = sc.nextLine();
            System.out.println("Escriba el nombre del archivo que contiene la llave que se usó para cifrar.");
            String archivoLlave = sc.nextLine();
            System.out.println("Escriba el nombre del archivo donde se va a guardar el texto descifrado.");
            String archivoSalida = sc.nextLine();

            int[][] llave = leeMatriz("llaves/"+archivoLlave);
            if(OperMat.mcd(OperMat.det(llave), hill.ALF_ESP.length()) != 1){
                sc.close();
                throw new RuntimeException("La matriz no es invertible");
            }

            String textoPlano = hill.lee("criptograma/"+archivoEntrada); //Se lee el archivo de texto.
            if(textoPlano.length() == 0){
                sc.close();
                return;
            }
            String criptotexto = hill.descifrar(textoPlano, llave); //Se descifra.
            //Se escribe el texto original en un archivo de salida.
            hill.escribe(criptotexto, "descifrado/"+archivoSalida);
            System.out.println("El resultado está en la carpeta 'descifrado'");
        }else{
            System.out.println("Texto original:\n"+textoPred);
            System.out.println("Llave usada para cifrar:");
            OperMat.imprimeMatriz(llavePred);
            String tcif = hill.cifrar(textoPred, llavePred);
            System.out.println("Texto cifrado:\n"+tcif);
            System.out.println("Llave inversa:");
            OperMat.imprimeMatriz(OperMat.matrizInversa(llavePred, hill.ALF_ESP.length()));
            System.out.println("Texto descifrado:\n"+hill.descifrar(tcif,llavePred));
        }

        sc.close();
        
    }
}
