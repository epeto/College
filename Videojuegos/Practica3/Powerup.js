var GameEngine = (function(GameEngine) {
  const PI2 = 2*Math.PI;
  var w,h;
  var imagen;

  class Powerup {
    constructor(width, heigth) {
      this.isAlive = false;
      this.speed = 5;
      w = width;
      h = heigth;
      this.x = w*Math.random();
      this.y = h*Math.random();
      let angle = (360 * Math.random()) * Math.PI/180;
      this.vx = Math.cos(angle) * this.speed;
      this.vy = Math.sin(angle) * this.speed;
      imagen = new Image();
      imagen.src = 'vive100.png';
      this.alto = 20;
      this.ancho = this.alto*396/108;
    }

    update(elapse) {
      this.x += this.vx;
      this.y += this.vy;
      this.verificaBorde();
    }

    render(ctx) {
      ctx.drawImage(imagen, this.x, this.y,this.ancho,this.alto);
    }

    verificaBorde(){
      if(this.x<=0 || this.x+this.ancho>=w){
        this.vx = -this.vx;
      }

      if(this.y<=0 || this.y+this.alto>=h){
        this.vy = -this.vy;
      }
    }

    recalculaAngulo(){
      let angle = (360 * Math.random()) * Math.PI/180;
      this.vx = Math.cos(angle) * this.speed;
      this.vy = Math.sin(angle) * this.speed;
    }
  }

  GameEngine.Powerup = Powerup;
  return GameEngine;
})(GameEngine || {})
