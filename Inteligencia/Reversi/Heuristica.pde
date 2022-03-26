/**
  La clase Euristica implementará diferentes funciones euristicas para determinar la utilidad de un
  tablero dado un momento del juego, es decir, dado un estado.
  Voy a asumir que el agente será el jugador 2.
*/
public class Heuristica{
  
  /**
  Este método saca una razón de la diferencia entre las casillas del jugador 2 y el jugador 1.
  */
  public float paridad(Tablero tab){
    float val = 0;
    float fichas1 = tab.count(1);
    float fichas2 = tab.count(2);
    if((fichas2+fichas1)>0){
      val = 100 * (fichas2 - fichas1) / (fichas2 + fichas1);
    }
    return val;
  }
  
  /**
  Este método verifica cuántas posibles jugadas tiene cada jugador en este estado del tablero.
  */
  public float movilidad(Tablero tab){
    Tablero tm = new Tablero(tab);
    float val = 0;
    tm.Limpia();
    tm.calculaValido(1);
    float valido1 = tm.casillas_validas.size();
    tm.Limpia();
    tm.calculaValido(2);
    float valido2 = tm.casillas_validas.size();
    if((valido2 + valido1)>0){
      val = 100 * (valido2 - valido1) / (valido2 + valido1);
    }
    return val;
  }
  
  public float esquinas(Tablero tab){
   int fichas2 = 0;
   int fichas1 = 0;
   //Verificamos las cuatro esquinas, y contamos las fichas con las que cuenta
   //cada jugador en esas posiciones.
   
   //Esquina superior izquierda.
   if(tab.mundo[0][0] == 1){
     fichas1++;
   }
   if(tab.mundo[0][0] == 2){
     fichas2++;
   }
   
   //Esquina inferior izquierda.
   if(tab.mundo[tab.alto - 1][0] == 1){
     fichas1++;
   }
   if(tab.mundo[tab.alto - 1][0] == 2){
     fichas2++;
   }
   
   //Esquina superior derecha.
   if(tab.mundo[0][tab.ancho - 1] == 1){
     fichas1++;
   }
   if(tab.mundo[0][tab.ancho - 1] == 2){
     fichas2++;
   }
   
   //Esquina inferior derecha.
   if(tab.mundo[tab.alto - 1][tab.ancho - 1] == 1){
     fichas1++;
   }
   if(tab.mundo[tab.alto - 1][tab.ancho - 1] == 2){
     fichas2++;
   }
   if((fichas2 + fichas1)>0)
     return 100 * (fichas2 - fichas1) / (fichas2 + fichas1);
   else
     return 0;
  }
  
  /**
  Funcion que devuelve el número de fichas estables.
  La estrategia es contar el número de columnas decrecientes que
  hay desde una esquina del tablero hacia otra esquina adyacente.
  */
  public int numEstable(int[][] mundo, int jugador){
    int mun[][];
    int numero=0;
    int max=100;
    //Primero copiar el tablero
    mun = new int[mundo.length][mundo.length];
    int longitud = mundo.length;
    for(int a=0;a<longitud;a++){
      for(int b=0;b<longitud;b++){
        mun[a][b] = mundo[a][b];
      }
    }
    //Esquina
    if(mun[0][0] == jugador || mun[0][0]==4){
      max = 100;
      //i representa el número de columnas donde hay fichas estables.
      int i=0;
      while(i<longitud && (mun[i][0]==jugador || mun[i][0]==4)){
        i++;
      }
      
      for(int j=0;j<i;j++){
        int k=0;
        while(k<max && k<longitud && (mun[j][k]==jugador || mun[j][k]==4)){
          if(mun[j][k]==jugador){
            numero++;
            mun[j][k]=4;
          }
          k++;
        }
        max = k;
      }
   }
   
   //Esquina
   if(mun[longitud - 1][0] == jugador || mun[longitud - 1][0] == 4){
     max=100;
     //i representa el número de columnas donde hay fichas estables.
      int i=0;
      while(i<longitud && (mun[longitud-1-i][0]==jugador || mun[longitud-1-i][0]==4)){
        i++;
      }
      
      for(int j=0;j<i;j++){
        int k=0;
        while(k<max && k<longitud && (mun[longitud-1-j][k]==jugador || mun[longitud-1-j][k]==4)){
          if(mun[longitud-1-j][k]==jugador){
            numero++;
            mun[longitud-1-j][k]=4;
          }
          k++;
        }
        max = k;
      }
   }
   
   //Esquina
   if(mun[0][longitud - 1] == jugador || mun[0][longitud - 1] == 4){
     max = 100;
     //i representa el número de columnas donde hay fichas estables.
      int i=0;
      while(i<longitud && (mun[i][longitud - 1]==jugador || mun[i][longitud - 1]==4)){
        i++;
      }
      
      for(int j=0;j<i;j++){
        int k=0;
        while(k<max && k<longitud && (mun[j][longitud - 1 - k]==jugador || mun[j][longitud - 1 - k]==4)){
          if(mun[j][longitud - 1 - k]==jugador){
            numero++;
            mun[j][longitud - 1 - k]=4;
          }
          k++;
        }
        max = k;
      }
   }
   
   //Esquina
   if(mun[longitud - 1][longitud-1] == jugador || mun[longitud - 1][longitud-1] == 4){
     max=100;
     //i representa el número de columnas donde hay fichas estables.
      int i=0;
      while(i<longitud && (mun[longitud-1-i][longitud-1]==jugador || mun[longitud-1-i][longitud-1]==4)){
        i++;
      }
      
      for(int j=0;j<i;j++){
        int k=0;
        while(k<max && k<longitud && (mun[longitud-1-j][longitud-1-k]==jugador || mun[longitud-1-j][longitud-1-k]==4)){
          if(mun[longitud-1-j][longitud-1-k]==jugador){
            numero++;
            mun[longitud-1-j][longitud-1-k]=4;
          }
          k++;
        }
        max = k;
      }
   }
   
   return numero;
  }
  
  /**
  Función auxiliar que devuelve una lista de las casillas en que difieren 2 mundos.
  */
  public ArrayList<Coordenada> difieren(int[][] mundo1, int[][] mundo2, int jugador){
    ArrayList<Coordenada> lista1 = new ArrayList();
    int longitud = mundo1.length;
    for(int i=0;i<longitud;i++){
      for(int j=0;j<longitud;j++){
        if(mundo1[i][j]==jugador){
          if(mundo1[i][j]!=mundo2[i][j]){
            lista1.add(new Coordenada(i,j));
          }
        }
      }
    }
    return lista1;
  }
  /**
  Función que calcula el número de fichas inestables en un tablero. Las inestables son las que se pueden girar en un movimiento.
  */
  public int numInestable(Tablero tab, int jugador){
    int numero=0;
    int[][] mundoCambiado = new int[tab.alto][tab.ancho];
    //Copiamos el mundo de tab al mundo cambiado
    for(int i=0;i<tab.alto;i++){
      for(int j=0;j<tab.ancho;j++){
        mundoCambiado[i][j] = tab.mundo[i][j];
      }
    }
    
    int oponente = (jugador % 2) + 1;
    Agente a1 = new Agente(tab);
    //Apuntador al mundo del padre
    int mundoP[][];
    //Apuntador al mundo del hijo
    int mundoH[][];
    //Hacemos que tire el oponente
    a1.calculaArbol(oponente , a1.arbol_decision, 1);
    Node<Tablero> padre = a1.arbol_decision;
    List<Node<Tablero>> hijos = padre.getChildren();
    mundoP = padre.getData().mundo;
    //Verificamos si hay siguientes jugadas.
    if(hijos.size()>0){
      for(Node<Tablero> h : hijos){
        mundoH = h.getData().mundo;
        //Esta lista nos da todas las coordenadas que cambiaron.
        List<Coordenada> cambios = difieren(mundoP, mundoH, jugador);
        for(Coordenada cor : cambios){
          mundoCambiado[cor.getX()][cor.getY()] = 4;
        }
      }
    }
    
    for(int i=0;i<tab.alto;i++){
      for(int j=0;j<tab.ancho;j++){
        if(mundoCambiado[i][j] == 4){
          numero++;
        }
      }
    }
    
    return numero;
  }
  
    /**
  Función que devuelve una suma de la estabilidad de las fichas.
  *(1)Estable: no se puede girar en ningún punto posterior del juego.
  *(0)Semi-estable: no se pueden girar en el próximo turno pero es posible girarlas en algún punto en el futuro.
  *(-1)Inestables: aquellas que se pueden girar en el siguiente turno.
  */
  public float estabilidad(Tablero tab){
    float numero = 0;
    Tablero tm = new Tablero(tab);
    //Primero calculamos la inestabilidad
    tm.Limpia();
    tm.calculaValido(1);
    float inj1 = numInestable(tm, 1);
    tm.Limpia();
    tm.calculaValido(2);
    float inj2 = numInestable(tm, 2);
    
    //Luego la estabilidad
    float esj1 = numEstable(tab.mundo, 1);
    float esj2 = numEstable(tab.mundo, 2);
    
    float val1 = esj1 - inj1;
    float val2 = esj2 - inj2;
    
    if((val1 + val2)!=0){
      numero = 100*(val2 - val1)/(val2 + val1);
    }
    
    return numero;
  }
  
  //Finalmente la función de utilidad para el tablero tab. Si es positivo favorece al j2.
  public float utilidad(Tablero tab){
    return paridad(tab) + movilidad(tab) + estabilidad(tab) + esquinas(tab);
  }
}