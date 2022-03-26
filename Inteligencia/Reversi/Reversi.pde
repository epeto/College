/**
 * Juego de Reversi/Othello usando Processing
 */
int ancho = 8, 
  alto = 8, 
  tamano = 64;
boolean p1Turn = true;
int playerCode;
Tablero tablero; 
boolean bandera = true;
//Variable para detener el juego
int detener=0;
Heuristica heuristica;
//Variable que representa la profundidad máxima del árbol.
int depth;
//Esto representa dónde va a tirar el agente
Coordenada corj2;

// Inicial para establecer tamaño de ventana con variables
void settings() {
  size((ancho * tamano)+1, (alto * tamano)+1);
}

// Inicialización
void setup() {
  println("Va el jugador 1");
  tablero = new Tablero(ancho, alto, tamano);
  playerCode = 1;
  tablero.calculaValido(playerCode);
  heuristica = new Heuristica();
  //La profundidad del árbol será 5.
  depth = 5;
}

// Update. Continuamente ejecuta y dibuja el código contenido en él
void draw() {
  
  if(bandera){
    tablero.calculaValido(playerCode);
    bandera = false;
  }
  
  if(tablero.casillas_validas.isEmpty()){
        bandera = true;
        println("el jugador " + playerCode + " no pudo va a tirar tira el siguiente jugador");
        p1Turn = !p1Turn;
        if (p1Turn) {
           playerCode = 1;
        } else {
           playerCode = 2;
           tablero.calculaValido(playerCode);
           if(!tablero.casillas_validas.isEmpty()){
             corj2 = tablero.casillas_validas.get(0);
             tablero.Limpia();
           }
       }
       detener++;
       if(detener==2){
         println("El juego se detuvo");
         if(tablero.count(1)>tablero.count(2)){
           println("Ganó el jugador 1");
         }else{
           println("Ganó el jugador 2");
         }
         stop();
       }
   }
  tablero.display();
}

// Callback. Evento que ocurre después de presionar el botón del mouse
void mouseClicked() {

  int x=0;
  int y=0;

  if(playerCode==1){
    x = tablero.toTileInt(mouseX);
    y = tablero.toTileInt(mouseY);
  }else{
    x = corj2.getX();
    y = corj2.getY();
  }

  // Colocar un disco/ficha en el tablero:
  if(tablero.isValid(x , y )){
       tablero.setDisk(x, y, playerCode);
       tablero.cambiaMundo(x , y , playerCode);
      
       tablero.Limpia();
       p1Turn = !p1Turn;
       if (p1Turn) {
          playerCode = 1;
       } else {
          playerCode = 2;
       }        
       bandera = true;
       
       //La variable para detener se reinicia.
       detener=0;
       
       if(playerCode == 2){
          corj2 = tira();
       }
       
       println("Va el jugador "+playerCode);
  }
}//Aquí termina mouseClicked

//Función para que tire el agente
public Coordenada tira(){
   Agente ag1 = new Agente(tablero);
   ag1.calculaArbol(playerCode, ag1.arbol_decision , depth);
   Coordenada cor = ag1.miniMax(ag1.arbol_decision);
   println(cor);
   return cor;
}