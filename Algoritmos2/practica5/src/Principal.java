
import processing.core.PApplet;
import javax.swing.JOptionPane;
import java.util.LinkedList;

public class Principal extends PApplet{
    int ancho = 800;
    int alto = 400;
    FibonacciHeap queue;
    LinkedList<LinkedList<NodoF>> ultimaLista;
    boolean cambios = false; //Para notar si hubo cambios en la cola.
    //Variables para botones.
    //Las primeras son para verificar si el cursor está sobre algún botón.
    boolean bInsertaOver = false;
    boolean bFundeOver = false;
    boolean bEncuentraOver = false;
    boolean bBorraOver = false;
    boolean bDecrementaOver = false;
    int bx; //Supongamos que la posición de todos es la misma
    int bAlto; //La altura es la misma para todos los botones
    int bAncho; //El ancho es el mismo para todos los botones
    //Posición en el eje 'y' de cada botón.
    int bInsertaY, bFundeY, bEncuentraY, bBorraY, bDecrementaY;

    //Calcula las coordenadas de cada nodo en el heap.
    public void calculaCoordenadas(){
        int hspace = 40;
        int vspace = 60;
        int cotaIzq = 120;
        int cotaSup = 50;

        ultimaLista = queue.toList();

        for(int i=0;i<ultimaLista.size();i++){
            for(int j=0;j<ultimaLista.get(i).size();j++){
                NodoF nodo = ultimaLista.get(i).get(j);
                nodo.c = j;
                nodo.f = i;
                nodo.x = cotaIzq + nodo.c*hspace;
                nodo.y = cotaSup + nodo.f*vspace;
                //System.out.println("("+nodo+","+nodo.c+","+nodo.f+")");
            }
        }
    }

    //Dibuja un círculo por cada nodo del heap.
    public void dibujaCirculo(){
        stroke(0,0,0);
        for(LinkedList<NodoF> listilla : ultimaLista){
            for(NodoF nodo : listilla){
                if(nodo.marcado){
                    fill(85, 107, 47); //Verde olivo.
                }else{
                    fill(81,209,246); //Aguamarina.
                }
                ellipse(nodo.x, nodo.y, 30, 30);
            }
        }
    }

    //Dibuja una línea de un nodo a su padre.
    public void dibujaLinea(){
        stroke(0,0,0);
        for(LinkedList<NodoF> listilla : ultimaLista){
            for(NodoF nodo : listilla){
                if(nodo.padre != null){
                    line(nodo.x, nodo.y, nodo.padre.x, nodo.padre.y);
                }
            }
        }
    }

    //Dibuja el texto de cada nodo.
    public void dibujaTexto(){
        for(LinkedList<NodoF> listilla : ultimaLista){
            for(NodoF nodo : listilla){
                fill(0, 0, 0);
                text(nodo.key, nodo.x-13, nodo.y+5);
            }
        }
    }

    public void dibujaBotones(){
        bx = 10;
        bAlto = 20;
        bAncho = 80;
        bInsertaY = 10;
        bFundeY = 40;
        bEncuentraY = 70;
        bBorraY = 100;
        bDecrementaY = 130;

        fill(230,230,230); //Gris
        rect(bx,bInsertaY,bAncho,bAlto);
        rect(bx,bFundeY,bAncho,bAlto);
        rect(bx,bEncuentraY,bAncho,bAlto);
        rect(bx,bBorraY,bAncho,bAlto);
        rect(bx,bDecrementaY,bAncho,bAlto);

        fill(0,0,0);
        text("Inserta",bx+5,bInsertaY+15);
        text("Funde",bx+5,bFundeY+15);
        text("Encuentra",bx+5,bEncuentraY+15);
        text("Borra",bx+5,bBorraY+15);
        text("Decrementa",bx+5,bDecrementaY+15);
    }

    //Comprueba si el mouse está sobre algún botón
    boolean overButton(int x, int y, int width, int height)  {
        if (mouseX >= x && mouseX <= x+width && 
            mouseY >= y && mouseY <= y+height) {
            return true;
        } else {
            return false;
        }
    }

    //Actualiza las variables de acuerdo a la posición del mouse
    void update(int x,int y){
        if(overButton(bx, bInsertaY, bAncho, bAlto)){ //Comprueba si está sobre el botón inserta.
            bInsertaOver = true;
            bFundeOver = false;
            bEncuentraOver = false;
            bBorraOver = false;
            bDecrementaOver = false;
        }else if(overButton(bx, bFundeY, bAncho, bAlto)){ //Comprueba si está sobre el botón funde.
            bInsertaOver = false;
            bFundeOver = true;
            bEncuentraOver = false;
            bBorraOver = false;
            bDecrementaOver = false;
        }else if(overButton(bx, bEncuentraY, bAncho, bAlto)){ //Comprueba si está sobre el botón encuentra.
            bInsertaOver = false;
            bFundeOver = false;
            bEncuentraOver = true;
            bBorraOver = false;
            bDecrementaOver = false;
        }else if(overButton(bx, bBorraY, bAncho, bAlto)){ //Comprueba si está sobre el botón borra.
            bInsertaOver = false;
            bFundeOver = false;
            bEncuentraOver = false;
            bBorraOver = true;
            bDecrementaOver = false;
        }else if(overButton(bx, bDecrementaY, bAncho, bAlto)){ //Comprueba si está sobre el botón decrementa.
            bInsertaOver = false;
            bFundeOver = false;
            bEncuentraOver = false;
            bBorraOver = false;
            bDecrementaOver = true;
        }else{
            bInsertaOver = false;
            bFundeOver = false;
            bEncuentraOver = false;
            bBorraOver = false;
            bDecrementaOver = false;
        }
    }

    //Función para configuración inicial de la ventana.
    @Override
    public void settings(){
        size(ancho,alto);
    }

    //Función para configuraciones iniciales del programa.
    @Override
    public void setup(){
        background (255, 255, 255); //Define el color de fondo
        frameRate(20); //Cuadros por segundo.

        queue = new FibonacciHeap();
        queue.inserta(8);
        queue.inserta(4);
        queue.inserta(1);
        queue.inserta(5);
        queue.inserta(50);
        queue.inserta(60);
        queue.inserta(43);
        queue.inserta(3);
        queue.inserta(19);
        queue.inserta(23);

        //Los métodos para dibujar la cola
        calculaCoordenadas();
        dibujaLinea();
        dibujaCirculo();
        dibujaTexto();
        dibujaBotones();
    }

    //Dibuja de forma cíclica.
    @Override
    public void draw(){
        update(mouseX, mouseY);
        if(cambios){
            fill(255, 255, 255); //Dibuja un rectángulo blanco para ocultar lo que ya está dibujado.
            rect(0,0,ancho,alto);
            dibujaBotones();
            calculaCoordenadas();
            dibujaLinea();
            dibujaCirculo();
            dibujaTexto();
            cambios = false;
        }
    }

    //Inserta un elemento en este heap.
    public void insert(){
        String mensaje = "Escribe un número entero para insertar.";
        String entrada = JOptionPane.showInputDialog(mensaje);
        int nuevo = Integer.parseInt(entrada);
        queue.inserta(nuevo);
        cambios = true;
    }

    //Funde un heap de fibonacci en este.
    public void merge(){
        String mensaje = "Escribe una lista de números enteros separados por comas.";
        String entrada = JOptionPane.showInputDialog(mensaje);
        String[] numeroString = entrada.split(",");

        FibonacciHeap colaTemp = new FibonacciHeap();
        for(int i=0;i<numeroString.length;i++){
            colaTemp.inserta(Integer.parseInt(numeroString[i]));
        }
        System.out.println("Cola a fusionar:");
        colaTemp.imprimeHeap();
        queue.funde(colaTemp);
        cambios = true;
    }

    //Muestra el elemento más pequeño sin eliminarlo.
    public void min(){
        JOptionPane.showMessageDialog(null, queue.encuentraMin(), "Mínimo", JOptionPane.INFORMATION_MESSAGE);
    }

    //Elimina y devuelve el elemento más pequeño del heap.
    public void deleteMin(){
        JOptionPane.showMessageDialog(null, queue.borraMin(), "Mínimo eliminado", JOptionPane.INFORMATION_MESSAGE);
        cambios = true;
    }

    //Decrementa la llave de algún nodo.
    public void decreaseKey(){
        String mensaje = "Escribe dos números separados por una coma.\nEl primero es la llave actual y el segundo la nueva.";
        String entrada = JOptionPane.showInputDialog(mensaje);
        String[] numeroString = entrada.split(",");

        int old = Integer.parseInt(numeroString[0]);
        int niu = Integer.parseInt(numeroString[1]);
        queue.decrementaLlave(old,niu);
        cambios = true;
    }

    @Override
    public void mousePressed(){
        if(bInsertaOver){
            insert();
        }
        if(bFundeOver){
            merge();
        }
        if(bEncuentraOver){
            min();
        }
        if(bBorraOver){
            deleteMin();
        }
        if(bDecrementaOver){
            decreaseKey();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Principal");
    }
}

