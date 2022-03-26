//Esta clase representa a los bloques que se van a romper.
var GameEngine = (function(GameEngine) {

  class Bloque {
	//x,y: representan las coordenadas de este bloque.
	//co: representa el color del bloque.
    constructor(x,y,co,idC) {
	this.x = x;
	this.y = y;
	this.color = co;
	this.idColor = idC;  //0 para rojo, 1 para amarillo, 2 para púrpura.
	this.long = 20;
    }

    render(ctx) {
      ctx.fillStyle = this.color;
      ctx.beginPath();
      ctx.rect(this.x,this.y,this.long,this.long);
      ctx.fill();
    }

	//Función para mover el bloque a la izquierda.
	izquierda(){
		this.x-=2;
	}

	//Función para mover el bloque a la derecha.
	derecha(){
		this.x+=2;
	}
  }

  GameEngine.Bloque = Bloque;
  return GameEngine;
})(GameEngine || {})
