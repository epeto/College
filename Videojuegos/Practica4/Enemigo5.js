var GameEngine = (function(GameEngine) {

    class Enemigo5 {
        constructor(x) {
            this.x = x;
            this.y = 71*16;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy05.png";
            this.frame = 0;
            this.h = 19;
            this.w = 28;
            this.frameW = this.w/2;
            this.minX = 54*16; //mínimo en x
            this.maxX = 76*16; //Máximo en x
            this.piso = 72*16; //El piso sobre el que va a saltar.
            this.vy = 10; //Velocidad en y.
            this.direc = 1; //Dirección.
            this.vida = 2;
            this.bloque0 = this.piso;
            this.bloque1 = this.piso-16;
            this.bloque2 = this.bloque1-32;
            this.bloque3 = this.bloque2-32;
            this.bloque4 = this.bloque3-32;
        }

        update(player){
            //Esto es para restarle vida al jugador.
            if(this.x+this.frameW>player.x-player.w_2 && this.x<player.x+player.w_2){
                if(this.y+this.h>player.y-player.h_2 && this.y<player.y+player.h_2){
                    player.vida--;
                }
            }

            if(this.y+this.h<this.piso){
                this.frame = 1; //Frame para el aire.
            }else{
                this.frame = 0; //Frame para el piso.
            }

            this.y -= this.vy;
            this.vy-=1;
            this.x+=this.direc;

            if(this.x+this.frameW>=this.maxX){
                this.direc = -1; //Cambia de dirección.
            }

            if(this.x<=this.minX){
                this.direc = 1; //Cambia de dirección.
            }

            if(this.y>this.piso){
                this.vy = 10; //Se reinicia la velocidad en y.
                this.y = this.piso;
            }

            //En esta parte ajustamos las alturas a las que va a saltar.
            if(this.x<61*16){
                this.piso = this.bloque0;
            }

            if(this.x>=61*16 && this.x<69*16){
                this.piso = this.bloque1;
            }

            if(this.x>=69*16 && this.x<71*16){
                this.piso = this.bloque2;
            }

            if(this.x>=71*16 && this.x<73*16){
                this.piso = this.bloque3;
            }

            if(this.x>73*16){
                this.piso = this.bloque4;
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

  GameEngine.Enemigo5 = Enemigo5;
  return GameEngine;
})(GameEngine || {})