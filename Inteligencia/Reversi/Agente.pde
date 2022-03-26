
public class Agente{
  
  public Node<Tablero> arbol_decision;
  //Objeto que representa las heurísticas
  Heuristica heu;
  
  public Agente(Tablero raiz){
    arbol_decision = new Node(new Tablero(raiz.mundo));
    heu = new Heuristica();
  }
  
  /*
  Funcion que calcula el arbol de jugadas posibles a una produnfidad determinanda
  tomando el turno actual del jugado que va a jugar en el tablero y el tablero padre 
  */
  public void calculaArbol(int turno , Node<Tablero> padre , int profundidad){
     
    if(profundidad >0){
      Tablero tablero = padre.getData(); // tamamos el tablero padre
      int turno_contrario = (turno % 2) +1; //turno del jugador contrario
      tablero.calculaValido(turno); // calculamos la sposibles jugadas con el turno actual
      if(!tablero.casillas_validas.isEmpty()){ //verificamos que el jugador en turno pueda tirar
        for(Coordenada z : tablero.casillas_validas){
          Tablero aux = new Tablero(tablero); // Hacemos una copia del tablero padre
          aux.isValid(z.getY(), z.getX()); // quitamos la jugada que vamos a a hacer de la lista de jugadas validas posibles
          aux.mundo[z.getX()][z.getY()] = turno; // tiramos 
          aux.cambiaMundo(z.getY() , z.getX() , turno); // hacemos los cambios de colores
          aux.Limpia(); // limpiamos las jugadas que no ocupamos 
          padre.addChild(aux); // lo metemos al arbol
          tablero.mundo[z.getX()][z.getY()] = 0; // cambiamos el mundo del padre para tener bien guardado los datos
        }
        
        /*
          Para cada uno de los hijos calculamos su arbol 
        */
        for(Node<Tablero> tree : padre.getChildren()){
          calculaArbol (turno_contrario , tree , profundidad-1);  
        }
        
      }else{
        /*
          Si llegamos a este punto es por que no hay jugadas validas entonces verficamos haya alguna casilla libre en donde se pueda tirar con 
          con el turno contrario
        */
        if(tablero.tieneCasillasLibres(turno)){
          padre.addChild(new Tablero(tablero.mundo));
          for(Node<Tablero> tree : padre.getChildren()){
            calculaArbol (turno_contrario , tree , profundidad-1);     
          }       
        }
      }
    }    
  }
  
  /*
    Calcula todo el arbol de jugadas posibles 
  */
  public void calculaArbol(int turno , Node<Tablero> padre){
     Tablero tablero = padre.getData(); // tamamos el tablero padre
      int turno_contrario = (turno % 2) +1; //turno del jugador contrario
      tablero.calculaValido(turno); // calculamos la sposibles jugadas con el turno actual
      if(!tablero.casillas_validas.isEmpty()){ //verificamos que el jugador en turno pueda tirar
        for(Coordenada z : tablero.casillas_validas){
          Tablero aux = new Tablero(tablero); // Hacemos una copia del tablero padre
          aux.isValid(z.getY(), z.getX()); // quitamos la jugada que vamos a a hacer de la lista de jugadas validas posibles
          aux.mundo[z.getX()][z.getY()] = turno; // tiramos 
          aux.cambiaMundo(z.getY() , z.getX() , turno); // hacemos los cambios de colores
          aux.Limpia(); // limpiamos las jugadas que no ocupamos 
          padre.addChild(aux); // lo metemos al arbol
          tablero.mundo[z.getX()][z.getY()] = 0; // cambiamos el mundo del padre para tener bien guardado los datos
        }
        
        /*
          Para cada uno de los hijos calculamos su arbol 
        */
        for(Node<Tablero> tree : padre.getChildren()){
          calculaArbol (turno_contrario , tree);     
        }
        
      }else{
        /*
          Si llegamos a este punto es por que no hay jugadas validas entonces verficamos haya alguna casilla libre en donde se pueda tirar con 
          con el turno contrario
        */
        if(tablero.tieneCasillasLibres(turno)){
          padre.addChild(new Tablero(tablero.mundo));
          for(Node<Tablero> tree : padre.getChildren()){
            calculaArbol (turno_contrario , tree);     
          }       
        }
      }
    
  }//Aquí termina calcula arbol
  
  //Función que devuelve el valor más grande.
  public float max_valor(Node<Tablero> nodo){
    if(nodo.isLeaf()){
      nodo.valor = heu.utilidad(nodo.getData());
      return nodo.getValor();
    }else{
      float val = -50000;
      List<Node<Tablero>> hijos = nodo.getChildren();
      for(Node n : hijos){
        val = Math.max(val,min_valor(n));
      }
      nodo.valor = val;
      return val;
    }
  }
  
  //Función que devuelve el valor más pequeño.
  public float min_valor(Node<Tablero> nodo){
    if(nodo.isLeaf()){
      nodo.valor = heu.utilidad(nodo.getData());
      return nodo.getValor();
    }else{
      float val = 50000;
      List<Node<Tablero>> hijos = nodo.getChildren();
      for(Node n : hijos){
        val = Math.min(val,max_valor(n));
      }
      nodo.valor = val;
      return val;
    }
  }
  
  /**
  Función MiniMax.
  */
  public Coordenada miniMax(Node<Tablero> nodoInicial){
    float vinicial = max_valor(nodoInicial);
    Coordenada co;
    int alt=0;
    int anc=0;
    //Tablero que representará la siguiente acción.
    Tablero ac=null;
    List<Node<Tablero>> hijos = nodoInicial.getChildren();
    for(Node<Tablero> n : hijos){
      if(n.getValor()==vinicial){
        ac = n.getData();
      }
    }
    if(ac!=null){
      int[][] mundo1 = nodoInicial.getData().mundo;
      int[][] mundo2 = ac.mundo;
      for(int i=0;i<nodoInicial.getData().alto;i++){
        for(int j=0;j<nodoInicial.getData().ancho;j++){
          if((mundo1[i][j] == 0 || mundo1[i][j] == 3) && mundo2[i][j]==2){
            alt=i;
            anc=j;
          }
        }
      }
    }
    co = new Coordenada(anc,alt);
    return co;
  }//Aquí termina minimax
}