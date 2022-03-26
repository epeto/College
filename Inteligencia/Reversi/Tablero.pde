import java.util.LinkedList;

/**
 * Representación de un tablero del juego Reversi/Othello.
 * La representación del tablero es mediante un arreglo bidimensional de enteros
 * que representan: 0 = casilla vacia, 1 = disco del jugador 1, 2 = disco del jugador 2.
 * @author  Rodrigo Colín
 * @version %I%, %G%
 */
class Tablero {
  int ancho;
  int alto;
  int tamano;
  int[][] mundo;
  color p1Color = color(205, 150, 0);
  color p2Color = color(110, 230, 200);
  color p3Color = color(1 , 1 , 1);
  boolean[] lineas_validas;
  boolean[] jugada;
  LinkedList<Coordenada> casillas_validas = new LinkedList<Coordenada>();
  /**
   * Constructor por default de un Tablero de 8x8 con tamaño de casilla 100px.
   */
  Tablero() {
    this(8, 8, 100);
  }
  
  /**
    Constructor de un tablero con otro tablero 
  */
  
  Tablero(Tablero tablero){
    this(tablero.mundo);
    this.casillas_validas = (LinkedList<Coordenada>)tablero.casillas_validas.clone();
    lineas_validas = new boolean[8];
    jugada = new boolean[8];
    for(int i = 0 ; i < 8 ; i++){
        lineas_validas[i] = false;
    }
    
    
  }

  /**
   * Constructor por default de un Tablero de 8x8.
   * @param tamano tamaño en pixeles de cada casilla cuadrada del tablero
   */
  Tablero(int tamano) {
    this(8, 8, tamano);
  }

  /**
   * Constructor principal de un tablero.
   * @param  ancho  anchura, en casillas, del tablero
   * @param  alto   altura, en casillas, del tablero
   * @param  tamano tamaño en pixeles de cada casilla cuadrada del tablero
   */
  Tablero(int ancho, int alto, int tamano) {
    this.ancho = ancho;
    this.alto = alto;
    this.tamano = tamano;
    mundo = new int[alto][ancho]; 
    for (int i = 0; i < this.alto; i++) {
      for (int j = 0; j < this.ancho; j++) {
        mundo[i][j] = 0;
      }
    }
    lineas_validas = new boolean[8];
    jugada = new boolean[8];
    for(int i = 0 ; i < 8 ; i++){
        lineas_validas[i] = false;
    }
    // Configuración inicial
    mundo[alto/2][ancho/2] = 1;
    mundo[alto/2-1][ancho/2] = 2;
    mundo[alto/2][ancho/2-1] = 2;
    mundo[alto/2-1][ancho/2-1] = 1;
  }

  /**
   * Constructor de copia para un Tablero.
   * @param mundo representa la configutación que será copiada para crear el nuevo Tablero
   */
  Tablero(int[][] mundo) {
    this(mundo, 100);
     lineas_validas = new boolean[8];
     jugada = new boolean[8];
      for(int i = 0 ; i < 8 ; i++){
          lineas_validas[i] = false;
      }
  }

  /**
   * Constructor de copia para un Tablero con tamaño de casilla personalizado.
   * @param mundo  representa la configutación que será copiada para crear el nuevo Tablero
   * @param tamano tamaño en pixeles de cada casilla cuadrada del tablero
   */
  Tablero(int[][] mundo, int tamano) {
    ancho = mundo[0].length;
    alto = mundo.length;
    this.tamano = tamano; 
    this.mundo = new int[mundo.length][mundo[0].length];
    for (int i = 0; i < alto; i++) {
      for (int j = 0; j < ancho; j++) {
        this.mundo[i][j] = mundo[i][j];
      }
    }
  }

  /**
   * Conversor de pixel a número de casilla dentro de un Tablero.
   * Útil al dar clic sobre la pantalla y determinar la posición (x ó y) en el Tablero.
   * @param n valor en pixel con respecto a la ventana
   * @return  entero que representa el número de casilla en el Tablero
   */
  int toTileInt(int n) {
    return floor(n/tamano);
  }

  /**
   * Cuenta la cantidad de discos/fichas de un determinado jugador en el Tablero.
   * @param  player identificador del jugador, es decir: 1 = Jugador 1, 2 = Jugador 2
   * @return        cantidad de discos/fichas en el tablero del jugador dado como parámetro 
   */
  int count(int player) {
    int total = 0;
    for (int i = 0; i < alto; i++) {
      for (int j = 0; j < ancho; j++) {
        if (mundo[i][j] == player) {
          total += 1;
        }
      }
    }
    return total;
  }
  
  /**
   * Establece o cambia un disco/ficha del tablero en la posición indicada.
   * Nota: no se realiza ninguna verificación sobre la posición o cambios en los demás discos
   * @param x      posición X en Coordenada del tablero (no en pixeles) donde se estable el disco
   * @param y      posición Y en Coordenada del tablero (no en pixeles) donde se estable el disco
   * @param player identificador del jugador, es decir: 1 = Jugador 1, 2 = Jugador 2
   */
  void setDisk(int x, int y, int player) {
     
    mundo[y][x] = player;
  }

  /**
   * Determina si la casilla seleccionada es válida para colocar un disco/ficha.
   * TODO: Realizar una validación más exhaustiva, por el momento solo revisa si la casilla es vacia
   * @param x posición X en Coordenada del tablero (no en pixeles) donde se valida la casilla
   * @param y posición Y en Coordenada del tablero (no en pixeles) donde se valida la casilla
   */
  boolean isValid(int x, int y) {
    if (mundo[y][x] == 3){
      casillas_validas.remove(new Coordenada(y , x));
       return true;
      
     
    }else{
       return false;
    }
      
   
  }

  /**
   * Dibuja una representación gráfica del tablero en la ventana de Processing. 
   */
  void display() {
    for (int i = 0; i < alto; i++) {
      for (int j = 0; j < ancho; j++) {
        // Dibujar las casillas del tablero:
        fill(80);
        stroke(255);
        rect(j * tamano, i * tamano, tamano, tamano);
        // Dibujar los discos/fichas del tablero:
        if (mundo[i][j] == 1) {
          fill(p1Color);
          noStroke();
          ellipse((j*tamano)+(tamano/2), (i*tamano)+(tamano/2), tamano/2, tamano/2);
        } else if (mundo[i][j] == 2) {
          fill(p2Color);
          noStroke();
          ellipse((j*tamano)+(tamano/2), (i*tamano)+(tamano/2), tamano/2, tamano/2);
        }else{
          if(mundo[i][j] == 3){
           fill(p3Color);
           noStroke();
           ellipse((j*tamano)+(tamano/2), (i*tamano)+(tamano/2), tamano/2, tamano/2);
          }
        }
      }
    }
  }
  
  /*
    Verifica casilla por casilla si esa casilla es una casilla valida para tiro
    @param playerCode el color del jugador de turno
  */
  void calculaValido(int playerCode){
   for(int i = 0; i < alto ; i++){
     for(int j = 0; j < ancho ; j++){
       if(mundo[i][j] == 0){
         if(jugadaValida(i , j , playerCode)){
          mundo[i][j] = 3;
          casillas_validas.addLast(new Coordenada(i, j));
         }   
       }
        
     }
   }
  }
  
  /*
    Función que limpia el tablero de toda las juadas validas para dejar solo el  mundo con 0 , 1 , 2
  */
  public void Limpia (){
    
    for(Coordenada cor : casillas_validas){
      mundo[cor.getX()][cor.getY()] = 0;  
    }
    casillas_validas.clear();
  }
  
  /*
    Funcion que verifica en una casilla es valida primero verificando adyacencias 
  */
  boolean jugadaValida(int  x , int y , int turno_actual ){
      int turno_contrario = (turno_actual % 2) +1;
      if(x == 0){
        if(y == 0){
            // Caso esquina superior izquierda
            if(mundo[x][y+1] == turno_contrario){
                  lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
            }
            
            if(mundo[x+1][y] == turno_contrario){
               lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);  
            }
            
            if(mundo[x+1][y+1] == turno_contrario){
               lineas_validas[7] = verificaLinea(x , y , 7 , turno_contrario , turno_actual); 
            }
            
            
        }else{
           if(y == ancho-1){
               // Caso esquina superior derecha
              if(mundo[x][y-1] == turno_contrario){
                 lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
              }
              
              if(mundo[x+1][y] == turno_contrario){
                lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);
              }
              
              if(mundo[x+1][y-1] == turno_contrario){
                  lineas_validas[5] = verificaLinea(x , y , 5 , turno_contrario , turno_actual);
              }
           }else{
             // Caso linea superior del tablero sin tomar en cuenta las esquinas
             if(mundo[x][y-1] == turno_contrario){
                 lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
              }
              
              if(mundo[x+1][y-1] == turno_contrario){
                  lineas_validas[5] = verificaLinea(x , y , 5 , turno_contrario , turno_actual);
              }
              
              if(mundo[x+1][y] == turno_contrario){
                lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);
              }
              
              if(mundo[x+1][y+1] == turno_contrario){
                 lineas_validas[7] = verificaLinea(x , y , 7 , turno_contrario , turno_actual); 
              }
              
              if(mundo[x][y+1] == turno_contrario){
                  lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
              }        
           }
        }
      }else{
         if( x  == alto-1){
            if(y == 0){
              // Caso esquina inferior izquierda
               if(mundo[x][y+1] == turno_contrario){
                  lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
              } 
              
               if(mundo[x-1][y] == turno_contrario){
                  lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
              } 
              
               if(mundo[x-1][y+1] == turno_contrario){ 
                  lineas_validas[2] = verificaLinea(x , y , 2 , turno_contrario , turno_actual);  
              } 
            }else{
               if(y == ancho-1){
                 // Caso esquina inferior derecha 
                 if(mundo[x][y-1] == turno_contrario){
                   lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
                 }
                
                  if(mundo[x-1][y] == turno_contrario){
                    lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x-1][y-1] == turno_contrario){
                      lineas_validas[0] = verificaLinea(x , y , 0 , turno_contrario , turno_actual);  
                  } 
                
               }else{
                  // Caso borde inferior sin tomar en cuenta las esquinas
                 if(mundo[x][y-1] == turno_contrario){
                   lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
                 }
                
                  if(mundo[x-1][y] == turno_contrario){
                    lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x-1][y-1] == turno_contrario){
                      lineas_validas[0] = verificaLinea(x , y , 0 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x][y+1] == turno_contrario){
                      lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
                  }         
                 if(mundo[x-1][y+1] == turno_contrario){
                    lineas_validas[2] = verificaLinea(x , y , 2 , turno_contrario , turno_actual);  
                 }
              }
           }
        }else{
            if(y == 0){
                  // Caso borde izquierdo sin tomar en cuenta las esquinas
                  if(mundo[x-1][y] == turno_contrario){
                    lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x][y+1] == turno_contrario){
                      lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
                  }         
                 if(mundo[x-1][y+1] == turno_contrario){ 
                    lineas_validas[2] = verificaLinea(x , y , 2 , turno_contrario , turno_actual);  
                 }
                 
                 if(mundo[x+1][y] == turno_contrario){
                    lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);
                  }
              
                if(mundo[x+1][y+1] == turno_contrario){
                   lineas_validas[7] = verificaLinea(x , y , 7 , turno_contrario , turno_actual); 
                }
            }else{
              if(y == ancho-1){
                  // Caso borde derecho sin tomar en cuenta las esquinas
                 if(mundo[x][y-1] == turno_contrario){
                   lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
                 }
                
                  if(mundo[x-1][y] == turno_contrario){
                    lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x-1][y-1] == turno_contrario){
                      lineas_validas[0] = verificaLinea(x , y , 0 , turno_contrario , turno_actual);  
                  }
                  
                  if(mundo[x+1][y-1] == turno_contrario){
                      lineas_validas[5] = verificaLinea(x , y , 5 , turno_contrario , turno_actual);
                  }
                  
                  if(mundo[x+1][y] == turno_contrario){
                    lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);
                  }
              }else{
                  // Caso resto del tablero (para este punto ya no estamos en ningun borde)
                  if(mundo[x-1][y-1] == turno_contrario){
                      lineas_validas[0] = verificaLinea(x , y , 0 , turno_contrario , turno_actual);  
                  }
                  if(mundo[x-1][y] == turno_contrario){
                    lineas_validas[1] = verificaLinea(x , y , 1 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x-1][y+1] == turno_contrario){
                    lineas_validas[2] = verificaLinea(x , y , 2 , turno_contrario , turno_actual);  
                  }
                 
                 if(mundo[x][y-1] == turno_contrario){
                   lineas_validas[3] = verificaLinea(x , y , 3 , turno_contrario , turno_actual); 
                 }
                 
                  if(mundo[x][y+1] == turno_contrario){
                      lineas_validas[4] = verificaLinea(x , y , 4 , turno_contrario , turno_actual);  
                  } 
                  
                  if(mundo[x+1][y-1] == turno_contrario){
                      lineas_validas[5] = verificaLinea(x , y , 5 , turno_contrario , turno_actual);
                  }
                  
                  if(mundo[x+1][y] == turno_contrario){
                    lineas_validas[6] = verificaLinea(x , y , 6 , turno_contrario , turno_actual);
                  }
                  
                  if(mundo[x+1][y+1] == turno_contrario){
                     lineas_validas[7] = verificaLinea(x , y , 7 , turno_contrario , turno_actual); 
                  }
              }
            }
        }
      }
      boolean resultado = false;
      for(int i  = 0 ; i < 8 ; i++){
          resultado = resultado || lineas_validas[i];
          jugada[i] = lineas_validas[i];
          lineas_validas[i] = false;
          
      }
      return resultado;
    
  }
  
  /*
    Función que imprime el mundo por casillas 
  */
  public void imprimeMundo(){
      for(int i = 0 ; i < alto ; i++){
         for(int j = 0 ; j < ancho ; j++){
            println("la casilla (" + i + " , " + j + ") = " + mundo[i][j]); 
         }
      }
  }
  
  /*
    Cambia de color el una vez que se elije la jugada
  */
  public void cambiaMundo(int j , int i , int turno_actual){
      for(int valida = 0 ; valida < 8 ; valida++){
          jugada[valida] = false;
      }
      jugadaValida(i , j , turno_actual);
      int x , y;
      for(int valida = 0 ; valida < 8 ; valida++){
          if(jugada[valida]){

              switch(valida){
                case 0:
                    x= i-1;
                    y = j-1;
                    while(mundo[x][y] != turno_actual){
                      mundo[x][y] = turno_actual;
                      x--;
                      y--;      
                    }
                    break;
                case 1:
                    x = i-1;
                    while(mundo[x][j] != turno_actual){
                       mundo[x][j] = turno_actual;
                       x--;
                    }
                    break;
                case 2:
                    x= i-1;
                    y = j+1;
                    while(mundo[x][y] != turno_actual){
                      mundo[x][y] = turno_actual;
                      x--;
                      y++;      
                    }
                    break;
                case 3:
                    y = j-1;
                    while(mundo[i][y] != turno_actual){
                       mundo[i][y] = turno_actual;
                       y--;
                    }
                    break;
                case 4:
                    y = j+1;
                    while(mundo[i][y] != turno_actual){
                       mundo[i][y] = turno_actual;
                       y++;
                    }
                    break;
                case 5:
                    x= i+1;
                    y = j-1;
                    while(mundo[x][y] != turno_actual){
                      mundo[x][y] = turno_actual;
                      x++;
                      y--;      
                    }
                    break;
                case 6:
                    x = i+1;
                    while(mundo[x][j] != turno_actual){
                       mundo[x][j] = turno_actual;
                       x++;
                    }
                    break;
                case 7:
                    x= i+1;
                    y = j+1;
                    while(mundo[x][y] != turno_actual){
                      mundo[x][y] = turno_actual;
                      x++;
                      y++;      
                    }
                    break;
              }
          }
      }
  }
  
  /*
    Verifica si en la direccion de la casillas se sigue fichas del color contrario terminando en una del color actual las direcciones se ven
    0 1 2 
    3 * 4
    5 6 7
    el * representa donde esta parado 
    
  */
  boolean verificaLinea(int x , int y , int direccion , int turno_contrario , int turno_actual){
    int i = 0;
    int j = 0;
    
    switch(direccion){
        case 0:
          for(i = 2 ; x-i >= 0 ; i++){
            if(y-i >= 0){
              if(mundo[x-i][y-i] != turno_contrario){
                  if(mundo[x-i][y-i] == turno_actual){
                      return true;
                  }else{
                     return false; 
                  }
              }              
            }
          }
          break;
        case 1:
          for(i = 2 ; x-i >= 0 ; i++){
              if(mundo[x-i][y] != turno_contrario){
                if(mundo[x-i][y] == turno_actual){
                    return true;
                }else{
                   return false; 
                }
             }
          }
          break;
        case 2:
          for(i = 2 ; x-i >= 0 ; i++){
            if(y+i < ancho){
               if(mundo[x-i][y+i] != turno_contrario){
                  if(mundo[x-i][y+i] == turno_actual){
                      return true;
                  }else{
                     return false; 
                  }
               }
            }       
          }
          break;
        case 3:
          for(j = 2 ; y-j >= 0 ; j++){
             if(mundo[x][y-j] != turno_contrario){
                if(mundo[x][y-j] == turno_actual){
                    return true;
                }else{
                   return false; 
                }
             }
          }
          break;
        case 4:

          for (j  = 2 ; j+y < ancho ; j++){
             if(mundo[x][y+j] != turno_contrario){

                 if(mundo[x][y+j] == turno_actual){

                     return true;
                 }else{
                   return false;
                 }
             }
               
          }
          break;
        case 5:
          for(i = 2 ; x+i < alto ; i++){
            if(y-i >= 0){
              if(mundo[x+i][y-i] != turno_contrario){
                  if(mundo[x+i][y-i] == turno_actual){
                    return true;
                  }else{
                     return false; 
                  }
              }
            }
          }
          break; 
        case 6:
          for(i = 2; i+x < alto ; i++){
             if(mundo[x+i][y] != turno_contrario){
                if(mundo[x+i][y] == turno_actual){
                   return true; 
                }else{
                 return false; 
                }
             }
          }
          break;
        case 7:
          for(i = 2 ; i+x < alto; i++){
            if(y+i < ancho){
              if(mundo[x+i][y+i] != turno_contrario){
                   if(mundo[x+i][y+i] == turno_actual){
                       return true;
                   }  
                   else{
                     return false; 
                   }
               }  
            }
          }
          break;
        default:
          return false;
    }
    return false;
  }
  
  /*
    Verifica si quedan tiros posibles para jugar 
  */
  public boolean tieneCasillasLibres(int turno){
    int turno_contrario = (turno % 2) +1;
     for(int i = 0 ; i < alto ; i++){
         for(int j = 0 ; j < ancho ; j++){
            if(mundo[i][j] == 0 || mundo[i][j] == 3){
              if(jugadaValida(i, j , turno_contrario)){
                casillas_validas.clear();
                return true;
              }
              
            }
         }
      }
      return false;
  }
    
}