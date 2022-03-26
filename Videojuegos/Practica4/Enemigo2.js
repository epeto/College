var GameEngine = (function(GameEngine) {

    class Enemigo2 {
        constructor(x, y,direc) {
            this.x = x;
            this.y = y;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy02.png";
            this.frame = 0;
            this.h = 16;
            this.w = 80;
            this.frameW = this.w/5;
            this.frameRate = 8; //Cada cuantas iteraciones va a cambiar de frame.
            this.frameCounter = 0;
            this.direc = direc;
            this.balas = [];
            this.angulo = 0; //Angulo en el que disparará.
            this.vida = 2;
        }

        update(player){
            for(let i = 0;i<this.balas.length;i++){
                this.balas[i].update(player);
                if(this.balas[i].tvida<=0){
                    this.balas.splice(i,1);
                }
            }
            this.frameCounter = (this.frameCounter+1) % (4*this.frameRate);
            this.frame = parseInt(this.frameCounter/this.frameRate);

            if(this.frame == 3 && (this.frameCounter%8==0)){
                switch(this.angulo){
                    case 30: this.angulo = 0;
                    break;
                    case 0: this.angulo = -30;
                    break;
                    case -30: this.angulo = 30;
                    break;
                }
                var angParam = (this.angulo+90*(this.direc-1))*Math.PI/180; //El ángulo que se va a pasar como argumento.
                this.balas.push(new GameEngine.Bala2(this.x,this.y,this.direc,angParam));
            }
        }

        render(ctx){
        for(let i = 0;i<this.balas.length;i++){
            this.balas[i].render(ctx);
        }
        ctx.save();
        ctx.translate(parseInt(this.x), parseInt(this.y));
        ctx.scale(this.direc, 1);
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

  GameEngine.Enemigo2 = Enemigo2;
  return GameEngine;
})(GameEngine || {})

