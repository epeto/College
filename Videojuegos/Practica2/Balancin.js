//Esta clase representa al balancín.
var GameEngine = (function(GameEngine) {

	var ht;
	var long;
	var wi; //Longitud de la ventana.
	var he; //Altura de la ventana.
	var base; //La base del triángulo
	var incder; //Dice si el balancín está inclinado a la derecha.
	var pz; //Indica cuando se presiona z.
	var reloj;
	
  class Balancin {
	//x,y: representan las coordenadas.
    constructor(x,y,w,h) {
	this.x = x;
	this.y = y;
	he = h;
	wi = w;
	this.color = "rgb(40, 55, 71)";
	ht = 30; //La altura del triángulo.
	long = 200; //La longitud del balancín (debe ser estrictamente mayor a 2*ht).
	incder = true;
	pz = true;
	reloj = 0;
	base = 2*Math.sqrt((long**2)/4-(ht**2));
    }

    render(ctx) {
    	if(!pz){ //Si pz es falso se deben esperar 10 ciclos para volver a presionar z.
    		reloj++;
    	}

    	if(reloj>=10){ //Cuando el reloj alcanza los 10 ciclos ya se puede presionar z.
    		pz = true;
    		reloj = 0;
    	}

		ctx.fillStyle = this.color;
		//En esta parte dibujaremos un triángulo.
		ctx.beginPath();
		ctx.lineWidth = 1;
		ctx.moveTo(this.x,this.y);
		ctx.lineTo(this.x-ht*Math.sqrt(4/3)/2,this.y+ht);
		ctx.stroke();
		ctx.lineTo(this.x+ht*Math.sqrt(4/3)/2,this.y+ht);
		ctx.moveTo(this.x,this.y);
		ctx.fill();
		ctx.closePath();
		
		//En esta parte dibujaremos una barra.
		var x1,y1,x2,y2;
		x1 = this.x-base/2;
		x2 = this.x+base/2;

		if(incder){ //Si está inclinado a la derecha.
			y1 = he-Math.sqrt((long**2)-(base**2));
			y2 = he;
		}else{ //Si está inclinado a la izquierda.
			y2 = he-Math.sqrt((long**2)-(base**2));
			y1 = he;
		}

		ctx.beginPath();
		ctx.lineWidth = 7;
		ctx.moveTo(x1,y1);
		ctx.lineTo(x2,y2);
		ctx.stroke();
		ctx.closePath();
    }

	//Función para mover el bloque a la izquierda.
	izquierda(){
		if((this.x-base/2)>=60){
			this.x-=5;
		}
	}

	//Función para mover el bloque a la derecha.
	derecha(){
		if((this.x+base/2)<wi-60){
			this.x+=5;
		}
	}

	//Función para cambiar la inclinación de la barra.
	cambiaInc(){
		if(pz){
			incder = !incder;
			pz = false;
		}
	}

	getBase(){
		return base;
	}

	getIncder(){
		return incder;
	}

	setIncder(b){
		incder = b;
	}
  }

  GameEngine.Balancin = Balancin;
  return GameEngine;
})(GameEngine || {})
