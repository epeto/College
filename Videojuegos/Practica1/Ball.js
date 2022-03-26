var GameEngine = (function(GameEngine) {
  let PI2 = 2*Math.PI;
  var grado;
  var valido;
  var color;
  var inx; //Posición inicial en x.
  var iny; //Posición inicial en y.

  class Ball {
    constructor(x, y, size) {
      this.x = x;
      inx = x;
      this.y = y;
      iny = y;
      this.size = size;
      color = "rgb(1,123,152)";
      this.speed = 200;
      grado = 360 * Math.random(); //Inicialmente el ángulo en grados será aleatorio.
      valido = !(grado>60 && grado<120) && !(grado>240 && grado<300); //Esto es para comprobar si es un ángulo válido.

      while(!valido){ //Si no es válido, recalculamos el ángulo.
           grado = 360 * Math.random();
           valido = !(grado>60 && grado<120) && !(grado>240 && grado<300);
      }

      this.angle = grado * Math.PI/180;
      this.vx = Math.cos(this.angle) * this.speed;
      this.vy = Math.sin(this.angle) * this.speed;
    }

	//Esta función reinicia los valores de la bola.
	reinicia(){
		this.x = inx;
		this.y = iny;
		this.speed = 200;
		grado = 360 * Math.random(); //Inicialmente el ángulo en grados será aleatorio.
      		valido = !(grado>60 && grado<120) && !(grado>240 && grado<300); //Esto es para comprobar si es un ángulo válido.

		while(!valido){ //Si no es válido, recalculamos el ángulo.
			grado = 360 * Math.random();
			valido = !(grado>60 && grado<120) && !(grado>240 && grado<300);
		}

		this.angle = grado * Math.PI/180;
		this.vx = Math.cos(this.angle) * this.speed;
		this.vy = Math.sin(this.angle) * this.speed;
	}

    update(elapsed) {
      this.x += this.vx * elapsed;
      this.y += this.vy * elapsed;
    }

    render(ctx) {
      ctx.fillStyle = color;
      ctx.beginPath();
      ctx.arc(this.x, this.y, this.size, 0, PI2);
      ctx.fill();  
    }

	//Función para acelerar la bola.
	acelera(){
		if(this.speed<1000){
			this.speed+=10; //Aumentamos en 10 la velocidad.
			if(this.vx<0){
				this.vx = Math.abs(Math.cos(this.angle) * this.speed);
			}else{
				this.vx = -Math.abs(Math.cos(this.angle) * this.speed);
			}

			if(this.vy<0){
      				this.vy = -Math.abs(Math.sin(this.angle) * this.speed);
			}else{
				this.vy = Math.abs(Math.sin(this.angle) * this.speed);
			}
		}else{
			this.vx*=-1;
		}
	}
  }

  GameEngine.Ball = Ball;
  return GameEngine;
})(GameEngine || {})
