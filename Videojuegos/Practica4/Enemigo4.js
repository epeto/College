var GameEngine = (function(GameEngine) {

    class Enemigo4 {
        constructor(x,y,orientacion,min,max) {
            this.x = x;
            this.y = y;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy04.png";
            this.frame = 0;
            this.h = 16;
            this.w = 48;
            this.frameW = this.w/3;
            this.frameRate = 8; //Cada cuantas iteraciones va a cambiar de frame.
            this.frameCounter = 0;
            this.orientacion=orientacion; //Vertical u horizontal.
            this.abriendo = true; //Si está abriendo el ojo.
            this.adelante = true; //Si está avanzando hacia su posición máxima.
            this.min = min;
            this.max = max;
            this.vida = 8;
        }

        update(player){
            if(this.x+this.frameW>player.x-player.w_2 && this.x<player.x+player.w_2){
                if(this.y+this.h>player.y-player.h_2 && this.y<player.y+player.h_2){
                    player.vida--;
                }
            }
            this.frameCounter++;
            if(this.abriendo){
                if(this.frame<2){
                    if(this.frameCounter == this.frameRate){
                        this.frame++;
                        this.frameCounter = 0;
                    }
                }else{
                    this.abriendo = false;
                }
            }else{
                if(this.frame>0){
                    if(this.frameCounter == this.frameRate){
                        this.frame--;
                        this.frameCounter = 0;
                    }
                }else{
                    this.abriendo = true;
                }
            }

            if(this.orientacion == "horizontal"){
                if(this.adelante){
                    if(this.x<this.max){
                        this.x++;
                    }else{
                        this.adelante = false;
                    }
                }else{
                    if(this.x>this.min){
                        this.x--;
                    }else{
                        this.adelante = true;
                    }
                }
            }else{
                if(this.adelante){
                    if(this.y<this.max){
                        this.y++;
                    }else{
                        this.adelante = false;
                    }
                }else{
                    if(this.y>this.min){
                        this.y--;
                    }else{
                        this.adelante = true;
                    }
                }
            }
        }

        render(ctx){
            ctx.save();
            ctx.translate(parseInt(this.x), parseInt(this.y));
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

  GameEngine.Enemigo4 = Enemigo4;
  return GameEngine;
})(GameEngine || {})