var GameEngine = (function(GameEngine) {

    class Enemigo7 {
        constructor() {
            this.x = 0;
            this.y = 800;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy07.png";
            this.frame = 0;
            this.h = 48;
            this.w = 64;
            this.frameW = this.w/2;
            this.frameCounter = 0;
            this.minX = 122*16; //mínimo en x
            this.maxX = 141*16; //Máximo en x
            this.piso = 57*16; //El piso sobre el que va a saltar.
            this.vy = 10; //Velocidad en y.
            this.direc = 1; //Dirección.
            this.vida = 16;
        }

        update(player){
            if(this.x+this.frameW/2>player.x-player.w_2 && this.x-this.frameW/2<player.x+player.w_2){
                if(this.y+this.h>player.y-player.h_2 && this.y<player.y+player.h_2){
                    player.vida--;
                }
            }
            if(this.y+this.h<this.piso){
                this.frame = 1; //Frame para el aire.
            }else{
                this.frame = 0; //Frame para el piso.
            }

            if(this.frame == 0){
                this.frameCounter++;
            }else{
                this.y -= this.vy;
                this.vy--;
            }

            if(this.frameCounter == 10){
                this.vy = 10;
                this.y-=this.vy;
                this.vy--;
                this.frameCounter = 0;
            }

            if(this.x+this.frameW>=this.maxX){
                this.direc = -1; //Cambia de dirección.
            }

            if(this.x<=this.minX){
                this.direc = 1; //Cambia de dirección.
            }

            if(this.frame == 1){
                this.x+=this.direc*2;
            }
        }

        render(ctx){
            ctx.save();
            ctx.translate(parseInt(this.x), parseInt(this.y));
            ctx.scale(-this.direc,1);
            ctx.drawImage(this.imagen,
                this.frame*this.frameW,
                0,
                this.frameW,
                this.h,
                -this.frameW/2,
                0,
                this.frameW,
                this.h);
            ctx.restore();
        }
    }

  GameEngine.Enemigo7 = Enemigo7;
  return GameEngine;
})(GameEngine || {})