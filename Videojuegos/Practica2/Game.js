var GameEngine = (function(GameEngine) {
  let cw;
  let ch;
  let KEY = GameEngine.KEY;

  var comenzar; //Esta variable indica cuándo comenzar el juego.
  var score;
  var bloque;
  var bloquesR = []; //Bloques rojos.
  var tamR; //Tamaño de bloquesR.
  var bloquesA = []; //Bloques amarillos.
  var tamA; //Tamaño de bloquesA.
  var bloquesP = []; //Bloques púrpura.
  var tamP; //Tamaño de bloquesP.
  var platI; //Plataforma izquierda.
  var platD; //Plataforma derecha.
  var balancin;
  var mono1; //El mono que inicialmente estará en el balancín.
  var mono2; //El mono que inicialmente estará en la plataforma.
  var primsal; //Verifica el primer salto del mono de la izquierda.
  var enAire; //Dice cuál mono se encuentra en el aire (1 o 2).
  var puntaje;
  var vidas;
  
  class Game {
    constructor(ctx) {
      score = 0;
      cw = ctx.canvas.width;
      ch = ctx.canvas.height;
      this.ctx = ctx;
      platI = new GameEngine.Plataforma(0,ch-60);
      platD = new GameEngine.Plataforma(cw-60,ch-60);
      balancin = new GameEngine.Balancin(cw/2,ch-30,cw,ch);
      mono1 = new GameEngine.Mono(cw,ch,balancin.getBase());
      mono2 = new GameEngine.Mono(cw,ch,balancin.getBase());
      mono1.recibeDatosB(balancin.x,balancin.y,balancin.getIncder());
      primsal = false;
      enAire = 0; //Inicialmente ningún mono estará en el aire.
      tamA = 20;
      tamP = 20;
      tamR = 20;
      puntaje=0;
      vidas = 3;

      window.addEventListener("keydown", function(evt) {
        KEY.onKeyDown(evt.keyCode);
      });
      window.addEventListener("keyup", function(evt) {
        KEY.onKeyUp(evt.keyCode);
      });
      
      //En esta parte llenamos los arreglos de bloques.
      var inc=cw/20;
      var i;
      for(i=0;i<20;i++){
		  bloquesR.push(new GameEngine.Bloque(i*inc+10,20,"rgb(165, 28, 48)",0));
		  bloquesA.push(new GameEngine.Bloque(i*inc+10,60,"rgb(241, 196, 15)",1));
		  bloquesP.push(new GameEngine.Bloque(i*inc+10,100,"rgb(91, 44, 111)",2));
	  }

	  document.getElementById("pie").innerHTML = "Vidas: "+vidas;
    }

	processInput() {
		if (KEY.isPress(KEY.RIGHT)){
			balancin.derecha();
		}
		
		if (KEY.isPress(KEY.LEFT)){
			balancin.izquierda();
		}

		if (KEY.isPress(KEY.Z)){
			balancin.cambiaInc();
		}
	}

	//Reinicia las configuraciones cuando un mono cae.
	reiniciaMuerte(){
		mono1.muerto = false;
		mono2.muerto = false;
		enAire = 0;
		primsal = false;
		mono2.x = 3;
		mono2.y = ch-60-mono2.altMono;
		balancin.setIncder(true);
		balancin.x = cw/2;
		balancin.y = ch-30;

		mono1.terminoSalto = false;
     	mono1.vx = 0; //Velocidad en x.
     	mono1.colision = false; //Detecta si chocó con un bloque.
     	mono1.setIncderB(true);
     	mono1.setSubiendo(true);

     	mono2.terminoSalto = false;
     	mono2.vx = 0; //Velocidad en x.
     	mono2.colision = false; //Detecta si chocó con un bloque.
     	mono2.setIncderB(true);
     	mono2.setSubiendo(true);
	}

	update(elapsed) {
		var i;

		if(mono1.muerto || mono2.muerto){ //Si alguno de los 2 monos muere, se reinician sus configuraciones.
			vidas--;
			document.getElementById("pie").innerHTML = "Vidas: "+vidas;
			this.reiniciaMuerte();
		}

		if(vidas == 0){
			vidas = 3;
			puntaje = 0;
			this.reiniciaBloques();
			this.reiniciaMuerte();
			document.getElementById("pie").innerHTML = "Vidas: "+vidas;
		}

		//Movimiento de bloques rojos.
		for(i=0;i<tamR;i++){
			bloquesR[i].derecha(); //Los rojos a la derecha.
			this.checkBorder(bloquesR[i]);
		}

		//Movimiento de bloques amarillos.
		for(i=0;i<tamA;i++){
			bloquesA[i].izquierda(); //Los rojos a la derecha.
			this.checkBorder(bloquesA[i]);
		}

		//Movimiento de bloques púrpura.
		for(i=0;i<tamP;i++){
			bloquesP[i].derecha(); //Los rojos a la derecha.
			this.checkBorder(bloquesP[i]);
		}

		mono1.recibeDatosB(balancin.x,balancin.y,balancin.getIncder());
		mono1.calculaCentro();
		mono2.recibeDatosB(balancin.x,balancin.y,balancin.getIncder());
		mono2.calculaCentro();

		if(enAire==1){
			mono1.salta();
			mono1.mueveX();
			mono2.posicionarEnB();
			this.verificaBorde(mono1);
			if(mono1.terminoSalto){
				enAire = 2;
				balancin.cambiaInc();
				mono1.vx = 0;
				mono1.colision = false;
				puntaje+=1;
				document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
			}
		}else{
			if(enAire==2){
				mono1.posicionarEnB();
				mono2.salta();
				mono2.mueveX();
				this.verificaBorde(mono2);
				if(mono2.terminoSalto){
					enAire = 1;
					balancin.cambiaInc();
					mono2.vx = 0;
					mono2.colision = false;
					puntaje+=1;
					document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
				}
			}else{
				mono1.posicionarEnB();
			}
		}

		//Si no se ha dado el primer salto
		if(!primsal){
			if((balancin.x-balancin.getBase()/2)<=60){
				primsal = true;
				enAire = 1;
				balancin.cambiaInc();
				document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
			}
		}

		//En esta parte verificaremos la colisión de un mono con un bloque.
		if(mono1.y<110){
			for(var i=0;i<tamP;i++){
				this.verificaColision(mono1,bloquesP[i]);
			}

			for(var i=0;i<tamA;i++){
				this.verificaColision(mono1,bloquesA[i]);
			}

			for(var i=0;i<tamR;i++){
				this.verificaColision(mono1,bloquesR[i]);
			}
		}

		if(mono2.y<110){
			for(var i=0;i<tamP;i++){
				this.verificaColision(mono2,bloquesP[i]);
			}

			for(var i=0;i<tamA;i++){
				this.verificaColision(mono2,bloquesA[i]);
			}

			for(var i=0;i<tamR;i++){
				this.verificaColision(mono2,bloquesR[i]);
			}
		}

		if(tamR==0 && tamA==0 && tamP==0){
			this.reiniciaBloques();
		}

	}//update

	//Verifica si un bloque tocó el borde.
	checkBorder(block) {
		if(block.x>=cw){
			block.x=1;
		}
		
		if(block.x<=0){
			block.x=cw-1;
		}
    }

    //Verifica si un mono tocó el borde.
    verificaBorde(monkey){
    	if(monkey.x<=0){
    		monkey.x=1;
    		monkey.vx = -monkey.vx;
    	}

    	if(monkey.x+monkey.getAnchoMono()>=cw){
    		monkey.x=cw-1-monkey.getAnchoMono();
    		monkey.vx = -monkey.vx;
    	}
    }

    //Verifica si el mono colisionó con un bloque.
    verificaColision(monkey,block){
    	if(block!=undefined){
    	var x1,y1,x2,y2,dx,dy;
    	x1 = block.x+10;
    	y1 = block.y+10;
    	x2 = monkey.centroX;
    	y2 = monkey.centroY;
    	
    	dx = Math.abs(x1-x2); //Distancia en x
    	dy = Math.abs(y1-y2); //Distancia en y

    	if(dx<(10+monkey.anMono/2) && dy<(10+monkey.altMono/2)){
    		monkey.colision = true;
    		if(block.idColor==1){
    			monkey.vx = -3;
    		}else{
    			monkey.vx = 3;
    		}

    		var indiceP = bloquesP.indexOf(block);
    		if(indiceP>=0){
    			bloquesP.splice(indiceP,1);
    			tamP--;
    			if(tamP==0){
    				puntaje+=20;
    				document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
    			}
    		}

    		var indiceA = bloquesA.indexOf(block);
    		if(indiceA>=0){
    			bloquesA.splice(indiceA,1);
    			tamA--;
    			if(tamA==0){
    				puntaje+=50;
    				document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
    			}
    		}

    		var indiceR = bloquesR.indexOf(block);
    		if(indiceR>=0){
    			bloquesR.splice(indiceR,1);
    			tamR--;
    			if(tamR==0){
    				puntaje+=100;
    				document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;
    			}
    		}

    		if(block.idColor==0){
    			puntaje+=9;
    		}else{
    			if(block.idColor==1){
    				puntaje+=5;
    			}else{
    				puntaje+=2;
    			}
    		}
    		document.getElementById("titulo").innerHTML = "Puntos: "+puntaje;

    	}
    	}
    }//Fin de verificaColision

    //Función para colocar todos los bloques en el canvas.
    reiniciaBloques(){
    	bloquesR.splice(0);
    	bloquesA.splice(0);
    	bloquesP.splice(0);

    	tamR=20;
    	tamA=20;
    	tamP=20;

    	var inc=cw/20;
        var i;
        for(i=0;i<20;i++){
			bloquesR.push(new GameEngine.Bloque(i*inc+10,20,"rgb(165, 28, 48)",0));
			bloquesA.push(new GameEngine.Bloque(i*inc+10,60,"rgb(241, 196, 15)",1));
			bloquesP.push(new GameEngine.Bloque(i*inc+10,100,"rgb(91, 44, 111)",2));
	  	}
    }//Fin de reiniciaBloques

	render() {
		this.ctx.clearRect(0, 0, cw, ch);
		platI.render(this.ctx);
		platD.render(this.ctx);
		balancin.render(this.ctx);
		var i;

		for(i=0;i<tamR;i++){
			bloquesR[i].render(this.ctx);
		}

		for(i=0;i<tamA;i++){
			bloquesA[i].render(this.ctx);
		}

		for(i=0;i<tamP;i++){
			bloquesP[i].render(this.ctx);
		}

		mono1.render(this.ctx);
		mono2.render(this.ctx);
    }
  }

  GameEngine.Game = Game;
  return GameEngine;
})(GameEngine || {})
