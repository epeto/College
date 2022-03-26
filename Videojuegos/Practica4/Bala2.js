//Esta clase representará a las balas del enemigo 2.

var GameEngine = (function(GameEngine) {
    class Bala2 {
        constructor(x, y,direc,angulo) {
            this.x = x+10*direc;
            this.y = y+5;
            this.angulo = angulo;
            this.speed = 4;
            this.vx = Math.cos(angulo)*this.speed;
            this.vy = Math.sin(angulo)*this.speed;
            this.tvida = 40; //Número de ciclos que va a vivir.
            this.radio = 2;
            this.imagen = new Image();
            this.imagen.src = "imagenes/bala2.png";
        }
  
        update(player){
            if(this.tvida>0 && this.x>player.x-player.w_2 && this.x<player.x+player.w_2){
                if(this.y>player.y-player.h_2 && this.y<player.y+player.h_2){
                    player.vida--;
                    this.tvida = 1;
                }
            }
            this.x+=this.vx;
            this.y+=this.vy;
            this.tvida--;
        }
  
        render(ctx){
            ctx.drawImage(this.imagen,this.x,this.y);
        }
    }
  
    GameEngine.Bala2 = Bala2;
    return GameEngine;
  })(GameEngine || {})