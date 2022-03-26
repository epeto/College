
import processing.core.PApplet;
import javax.swing.JOptionPane;

public class Principal extends PApplet{
    int ancho = 800;
    int alto = 400;
    ColaBinomial<Integer> queue;
    boolean cambios = false; //Para notar si hubo cambios en la cola.
    boolean button1Over = false; //Para verificar si el mouse está sobre el botón 1.
    boolean button2Over = false; //Para verificar si está sobre el botón 2.
    int b1x, b1y; //Posición del botón 1
    int b2x, b2y; //Posición del botón 2
    int bAncho, bAlto; //Alto y ancho de los botones

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

        queue = new ColaBinomial<>();
        queue.inserta(8);
        queue.inserta(4);
        queue.inserta(1);
        queue.inserta(3);
        queue.inserta(5);
        queue.inserta(7);
        queue.inserta(6);
        queue.inserta(2);

        //Los métodos para dibujar la cola
        calculaCoordenadas();
        dibujaLineas();
        dibujaCirculo();
        pintaTexto();

        //Se dibujan los botones
        textSize(15);
        b1x = 20;
        b1y = 20;
        b2x = 200;
        b2y = 20;
        bAncho = 70;
        bAlto = 30;
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
            dibujaLineas();
            dibujaCirculo();
            pintaTexto();
            cambios = false;
        }
    }

    public void dibujaBotones(){
        fill(230,230,230); //Gris
        rect(b1x,b1y,bAncho,bAlto);
        rect(b2x,b2y,bAncho,bAlto);

        fill(0,0,0);
        text("Inserta",b1x+8,b1y+20);
        text("Funde",b2x+10,b2y+20);
    }

    //Calcula las coordenadas de los nodos en la cola
    public void calculaCoordenadas(){
        int separacion = 40;
        queue.toMatrices();
        int cotaIzq = 20;
        int cotaDer = 20;
        int cotaSup = 100;

        for(int i=0; i<queue.arboles.length; i++){
            if(queue.arboles[i] != null){
                cotaIzq = cotaDer;
                cotaDer = cotaIzq + (queue.arboles[i].columna+1)*separacion;
                coordenadaNodo(queue.arboles[i], cotaIzq, separacion, cotaSup);
            }
        }
    }

    //Calcula las coordenadas de un árbol binomial.
    public void coordenadaNodo(ColaBinomial.NodoBin<Integer> nodo, int ci, int r, int cs){
        nodo.x = nodo.columna*r + ci;
        nodo.y = nodo.fila*r + cs;
        if(nodo.hermanoDer != null){
            coordenadaNodo(nodo.hermanoDer, ci, r, cs);
        }

        if(nodo.primerHijo != null){
            coordenadaNodo(nodo.primerHijo, ci, r, cs);
        }
    }

    //Dibuja el contenido de una cola binomial en las coordenadas adecuadas.
    public void pintaTexto(){
        textSize(15);
        for(int i=0; i<queue.arboles.length;i++){
            if(queue.arboles[i] != null){
                pintaTextoNodo(queue.arboles[i]);
            }
        }
    }

    //Dibuja el contenido de un árbol binomial.
    public void pintaTextoNodo(ColaBinomial.NodoBin<Integer> nodo){
        fill(0, 0, 0);
        text(nodo.llave, nodo.x-13, nodo.y+5);
        if(nodo.hermanoDer != null){
            pintaTextoNodo(nodo.hermanoDer);
        }

        if(nodo.primerHijo != null){
            pintaTextoNodo(nodo.primerHijo);
        }
    }

    //Dibuja las líneas en la cola binomial
    public void dibujaLineas(){
        stroke(0,0,0); //Líneas negras
        for(int i=0; i<queue.arboles.length;i++){
            if(queue.arboles[i] != null){
                dibujaLineasNodo(queue.arboles[i]);
            }
        }
    }

    //Dibuja las líneas en el árbol binomial.
    public void dibujaLineasNodo(ColaBinomial.NodoBin<Integer> nodo){
        if(nodo.padre != null){
            line(nodo.x, nodo.y, nodo.padre.x, nodo.padre.y);
        }

        if(nodo.primerHijo != null){
            dibujaLineasNodo(nodo.primerHijo);
        }

        if(nodo.hermanoDer != null){
            dibujaLineasNodo(nodo.hermanoDer);
        }
    }

    //Dibuja un círculo en cada nodo de la cola binomial.
    public void dibujaCirculo(){
        fill(81,209,246);
        stroke(0,0,0);
        for(int i=0; i<queue.arboles.length; i++){
            if(queue.arboles[i] != null){
                dibujaCirculoNodo(queue.arboles[i]);
            }
        }
    }

    //Dibuja un círculo en cada nodo del árbol binomial.
    public void dibujaCirculoNodo(ColaBinomial.NodoBin<Integer> nodo){
        ellipse(nodo.x, nodo.y, 30, 30);

        if(nodo.hermanoDer != null){
            dibujaCirculoNodo(nodo.hermanoDer);
        }

        if(nodo.primerHijo != null){
            dibujaCirculoNodo(nodo.primerHijo);
        }
    }

    //Método para insertar un número en la cola
    public void insertaInput(){
        String mensaje = "Escribe un número entero para insertar.";
        String entrada = JOptionPane.showInputDialog(mensaje);
        int nuevo = Integer.parseInt(entrada);
        queue.inserta(nuevo);
        cambios = true;
    }

    //Método para insertar un conjunto de elementos usando fusión de colas.
    public void mergeInput(){
        String mensaje = "Escribe una lista de números enteros separados por comas.";
        String entrada = JOptionPane.showInputDialog(mensaje);
        String[] numeroString = entrada.split(",");

        ColaBinomial<Integer> colaTemp = new ColaBinomial<>();
        for(int i=0;i<numeroString.length;i++){
            colaTemp.inserta(Integer.parseInt(numeroString[i]));
        }
        System.out.println("Cola a fusionar:");
        colaTemp.imprimeCola();
        queue.fundir(colaTemp);
        cambios = true;
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
        if(overButton(b1x, b1y, bAncho, bAlto)){ //Comprueba si está sobre el botón 1
            button1Over = true;
            button2Over = false;
        }else if(overButton(b2x, b2y, bAncho, bAlto)){ //Comprueba si está sobre el botón 2
            button1Over = false;
            button2Over = true;
        }else{
            button1Over = button2Over = false;
        }
    }

    @Override
    public void mousePressed(){
        if(button1Over){
            insertaInput();
        }

        if(button2Over){
            mergeInput();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Principal");
    }
}
