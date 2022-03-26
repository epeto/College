var GameEngine = (function(GameEngine) {
  var imagen;

  class Enemigo1 {
    constructor(x, y) {
      this.x = x;
      this.y = y;
      this.iniY = y; //Guarda la posición inicial en y.
      this.imagen = new Image();
      this.imagen.src = "imagenes/enemy01.png";
      this.frame = 0;
      this.h = 20;
      this.w = 32;
      this.h_2 = this.h/2;
      this.w_2 = this.w/2;
      this.speed = 2;
      this.estado = "pasivo" //En el primer estado solo estará parado.
      this.siguiendo = true; //Verdadero si en el ataque está siguiendo a megaman.
      this.vida = 1; //La vida del enemigo.
      this.toca = false; //Nos servirá para verificar que tocó al jugador.
      this.vx = 0;
      this.vy = 0;
      this.direc = 1;
    }

    //Función para atacar a megaman.
    ataca(x,y,dist,player){
    	if(this.toca){
    		if(Math.abs(this.y-this.iniY)<=3){
    			this.toca = false;
    		}

    		if(this.toca){
    		this.x+=this.vx;
    		if(this.y<this.iniY){
    			this.y+=this.speed*2;
    		}else{
    			this.y-=this.speed*2;
    		}
    	  }
    	}else{
    		var dx = x - this.x;
    		var dy = y - this.y;
    		var angulo = Math.atan2(dy,dx);
    		this.vx = this.speed*2*Math.cos(angulo);
    		this.vy = this.speed*2*Math.sin(angulo);

    		this.x+=this.vx;
    		this.y+=this.vy;

    		if(dist<8){
    			this.toca = true; //Ya lo tocó
    			player.vida--;
    		}
    	}
    }

    //Va a recibir la posición del jugador
    update(x,y,player){
      //Cambia la dirección
      if(this.vx>0){
        this.direc = -1;
      }else{
        this.direc = 1;
      }

      var dist = Math.sqrt((x-this.x)*(x-this.x)+(y-this.y)*(y-this.y)); //Calcula la distancia entre el jugador y este enemigo.
      //console.log(this.y);
      if(dist>300){ //Lo detecta a los 200px.
      	this.estado = "pasivo";
      }else{
      	if(dist>130){
      		this.estado = "seguir";
      	}else{
      		this.estado = "atacar";
      	}
      }

      switch(this.estado){
      	case "seguir": 	 if(x>this.x){
      				       this.x+=this.speed;
      	                 }else{
      	               	   this.x-=this.speed;
      	                 }
      	                this.toca = false;
      	break;
      	case "atacar": this.ataca(x,y,dist,player);
      	break;
      }

      this.frame = (1 + this.frame)%2; //Frame va a valer 0 o 1
    }

    render(ctx){
      ctx.save();
      ctx.translate(parseInt(this.x), parseInt(this.y));
      ctx.scale(this.direc, 1);
      ctx.drawImage(this.imagen,
                    this.frame*this.w_2,
                    0,
                    this.w_2,
                    this.h,
                    -this.w_2/2,
                    -this.h_2,
                    this.w_2,
                    this.h);
      ctx.restore();
    }
  }

  GameEngine.Enemigo1 = Enemigo1;
  return GameEngine;
})(GameEngine || {})
