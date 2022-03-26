var GameEngine = (function(GameEngine) {

    class Enemigo6 {
        constructor(x,y,minX,maxX) {
            this.x = x;
            this.y = y;
            this.imagen = new Image();
            this.imagen.src = "imagenes/enemy06.png";
            this.frame = 0;
            this.h = 21;
            this.w = 51;
            this.frameW = this.w/3;
            this.frameRate = 50; //Cada cuantas iteraciones va a cambiar de frame.
            this.frameCounter = 0;
            this.direc = 1;
            this.vida = 5;
            this.minX = minX; //Máximo en x.
            this.maxX = maxX; //Mínimo en x.
            this.balas = []; //Tendrá balas tipo 2.
        }

        creaBalas(){
            var bx = this.x-18*this.direc;
            var by = this.y+5;
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,0));
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,60*Math.PI/180));
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,120*Math.PI/180));
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,180*Math.PI/180));
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,240*Math.PI/180));
            this.balas.push(new GameEngine.Bala2(bx,by,this.direc,300*Math.PI/180));
        }

        update(player){
            this.frameCounter = (this.frameCounter+1) % (this.frameRate);
            
            if(this.frameCounter>35){
                this.frame = 1;
            }else{
                this.frame = 0;
            }

            if(this.x<=this.minX){
                this.direc = 1;
            }
    
            if(this.x>=this.maxX){
                this.direc = -1;
            }

            if(this.frame == 0){
                this.x+=this.direc*2;
            }

            if(this.frameCounter == 40){
                this.creaBalas();
            }

            for(let i=0;i<this.balas.length;i++){
                this.balas[i].update(player);
            }

            if(this.frameCounter == 30){
                this.balas.splice(0,this.balas.length);
            }
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

            for(let i=0;i<this.balas.length;i++){
                this.balas[i].render(ctx);
            }
        }
    }

  GameEngine.Enemigo6 = Enemigo6;
  return GameEngine;
})(GameEngine || {})