var GameEngine = (function(GameEngine) {

  var color;

  class Barra {
    constructor(x,y) {
	this.x = x;
	this.y = y;
	color = "rgb(245,64,33)";
	this.long = 120;
    }

    render(ctx) {
      ctx.fillStyle = color;
      ctx.beginPath();
      ctx.rect(this.x,this.y,20,this.long);
      ctx.fill();
    }
    
	//Función para mover la barra hacia arriba.
	arriba(){
		this.y-=5;
	}

	//Función para mover la barra hacia abajo.
	abajo(){
		this.y+=5;
	}
  }

  GameEngine.Barra = Barra;
  return GameEngine;
})(GameEngine || {})
