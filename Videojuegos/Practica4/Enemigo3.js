var GameEngine = (function(GameEngine) {

    class Enemigo3 {
        constructor(x,y,direc) {
            this.x = x;
            this.y = y;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy03.png";
            this.frame = 0;
            this.h = 20;
            this.w = 32;
            this.frameW = this.w/2;
            this.frameRate = 3; //Cada cuantas iteraciones va a cambiar de frame.
            this.frameCounter = 0;
            this.vy = 10;
            this.vx = 3;
            this.direc = direc;
            this.vida = 1;
            this.tvida = 50; //Tiempo de vida.
        }

        update(player){
            if(this.x+this.frameW>player.x-player.w_2 && this.x<player.x+player.w_2){
                if(this.y+this.h>player.y && this.y<player.y){
                    player.vida--;
                }
            }
            this.frameCounter = (this.frameCounter+1) % (2*this.frameRate);
            this.frame = parseInt(this.frameCounter/this.frameRate);
            this.y-=this.vy;
            this.vy-=1;
            this.x+=this.direc*this.vx;
            this.tvida--;
        }

        render(ctx){
            ctx.save();
            ctx.translate(parseInt(this.x), parseInt(this.y));
            ctx.scale(-this.direc, 1);
            ctx.drawImage(this.imagen,
                this.frame*this.frameW,
                0,
                this.frameW,
                this.h,
                0,
                0,
                this.frameW,
                this.h);
            ctx.restore();
        }
    }

  GameEngine.Enemigo3 = Enemigo3;
  return GameEngine;
})(GameEngine || {})