var GameEngine = (function(GameEngine) {
  var imagen;
  class Bala {
    constructor(x, y, direc,estado) {
      this.y = y;
      this.vel = 10*direc; //Velocidad en x
      this.tvida = 20; //Tiempo de vida

      if(direc<0){
        this.x = x-13;
      }else{
        this.x = x+4;
      }

      if(estado=="jumping"){
        this.y = y-10;
      }else{
        if(estado=="ladder"){
          this.y = y-6;
        }else{
          this.y = y;
        }
      }

      imagen = new Image();
      imagen.src = "imagenes/bullet.png";
    }

    update(){
      this.x+=this.vel;
      this.tvida--;
    }

    render(ctx){
      if(this.tvida>0){
        ctx.drawImage(imagen,this.x,this.y);
      }
    }
  }

  GameEngine.Bala = Bala;
  return GameEngine;
})(GameEngine || {})