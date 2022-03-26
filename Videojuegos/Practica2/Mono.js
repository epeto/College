//Esta clase representa a las plataformas que están a los lados de la ventana.
var GameEngine = (function(GameEngine) {

  var imageObj;
  var w;
  var h;
  var razon;
  var xb=0; //La posición en x del balancín.
  var yb=0; //La posición en y del balancín.
  var baseB; //Longitud de base del balancín.
  var incderB;
  var subiendo = true;

  class Mono {
    constructor(width,heigth,base) {
     w = width;
     h = heigth;
     imageObj = new Image();
     imageObj.src = 'mono.png';
     razon = 719/597;
     baseB = base;
     incderB = true;
     this.altMono = 70; //Altura del mono.
     this.anMono = this.altMono*razon; //Anchura del mono.
     this.terminoSalto = false;
     this.vx = 0; //Velocidad en x.
     this.colision = false; //Detecta si chocó con un bloque.
     this.muerto = false;
     this.x = 3;
     this.y = h-60-this.altMono;
     this.centroX = this.x+this.anMono/2;
     this.centroY = this.y+this.altMono/2;
    }

    render(ctx) {
      ctx.drawImage(imageObj, this.x, this.y,this.anMono,this.altMono);
    }

    //Función que devuelve el ancho del mono.
    getAnchoMono(){
      return this.anMono;
    }

    setSubiendo(b){
      subiendo = b;
    }

    //Función que recibe datos del balancín.
    recibeDatosB(x,y,incder){
      xb = x;
      yb = y;
      incderB = incder;
    }

    calculaCentro(){
      this.centroX = this.x+this.anMono/2;
      this.centroY = this.y+this.altMono/2;
    }

    //Función que posiciona al mono en el balancín.
    posicionarEnB(){
      if(incderB){
        this.x = xb+baseB*0.3-this.anMono/2;
        this.y = yb-this.altMono+10;
      }else{
        this.x = xb-baseB*0.3-this.anMono/2;
        this.y = yb-this.altMono+10;
      }
    }

    //Función para mover en x
    mueveX(){
      this.x+=this.vx;
    }

    setIncderB(b){
      incderB = b;
    }

    //Función para saltar.
    salta(){
      if(subiendo){ //Está subiendo.
        if(this.y>0 && !this.colision){
          this.terminoSalto = false;
          this.y-=6;
        }else{ //Ya llegó a su límite de altura.
          subiendo = false;
        }
      }else{ //Está bajando.
        if(this.y<=h-50-this.altMono){
          this.y+=6;
        }else{ //llega a su límite de bajada y termina su salto.
          if(incderB){ //Si está inclinado a la derecha debe caer del lado izquierdo.
            if(this.centroX>xb-baseB/2 && this.centroX < xb){ //Verifica si cayó en el rango adecuado.
              this.terminoSalto = true;
              subiendo = true;
            }else{
              this.y+=6;
              if(this.y>=h-this.altMono){
                this.muerto = true;
              }
            }
          }else{ //Si está inclinado a la izquierda debe caer del lado derecho.
            if(this.centroX>xb && this.centroX < xb+baseB/2){ //Verifica si cayó en el rango adecuado.
              this.terminoSalto = true;
              subiendo = true;
            }else{
              this.y+=6;
              if(this.y>=h-this.altMono){
                this.muerto = true;
              }
            }
          }
        }
      }
    }

  }

  GameEngine.Mono = Mono;
  return GameEngine;
})(GameEngine || {})
