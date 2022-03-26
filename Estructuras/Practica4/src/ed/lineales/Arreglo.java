
package ed.lineales;

public class Arreglo implements IArreglo {

    int[] dimensiones; //guarda el tamaño de cada dimensión.
    public int[] arrLoc; //arreglo local.

    public Arreglo(int[] dimens){
        dimensiones = dimens;
        int mult = 1; // la multiplicación de todas las dimensiones.
        for(int i=0; i<dimensiones.length; i++){
            mult *= dimensiones[i];
        }
        arrLoc = new int[mult];
    }

    @Override
    public int obtenerElemento(int[] indices) {
        return arrLoc[obtenerIndice(indices)];
    }

    @Override
    public void almacenarElemento(int[] indices, int elem) {
        arrLoc[obtenerIndice(indices)] = elem;
    }

    @Override
    public int obtenerIndice(int[] indices) {
        //Primero se comprueba que todos los índices caigan dentro del rango correcto.
        for(int i=0; i<indices.length; i++){
            if(indices[i] < 0 || indices[i] >= dimensiones[i]){
                throw new IndexOutOfBoundsException("El índice "+i+" está fuera de rango.");
            }
        }
        int f = 1;
        int poli = 0;
        for(int i=indices.length-1; i>=0; i--){
            poli += f*indices[i];
            f *= dimensiones[i];
        }
        return poli;
    }
    
}
