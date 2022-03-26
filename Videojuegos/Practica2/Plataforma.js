//Esta clase representa a las plataformas que están a los lados de la ventana.
var GameEngine = (function(GameEngine) {

  class Plataforma {
    constructor(x,y) {
	this.x = x;
	this.y = y;
	this.color = "rgb(0,0,0)";
	this.long = 60; //Longitud de un lado.
    }

    render(ctx) {
      ctx.fillStyle = this.color;
      ctx.beginPath();
      ctx.rect(this.x,this.y,this.long,this.long);
      ctx.fill();
    }

  }

  GameEngine.Plataforma = Plataforma;
  return GameEngine;
})(GameEngine || {})
