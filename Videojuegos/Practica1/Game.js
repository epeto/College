var GameEngine = (function(GameEngine) {
  let cw;
  let ch;
  var bola;
  var barrai; //Barra izquierda.
  var barrad; //Barra derecha.
  var comenzar; //Esta variable indica cuándo comenzar el juego.
  var scorei; //Indica la puntuación del jugador izquierdo.
  var scored; //Indica la puntuación del jugador derecho.

  let Key = {
    _pressed : {},
    
    LEFT: 37,
    UP: 38,
    RIGHT: 39,
    DOWN: 40,
    W: 87,
    S: 83,
    SPACE: 32,

    isPress: function(keyCode) {
      return this._pressed[keyCode];
    },
    onKeyDown: function(keyCode) {
      this._pressed[keyCode] = true;
    },
    onKeyUp: function(keyCode) {
      delete this._pressed[keyCode];
    }
  }
  
  class Game {
    constructor(ctx) {
      scored = 0;
      scorei = 0;
      comenzar = false;
      cw = ctx.canvas.width;
      ch = ctx.canvas.height;
      this.ctx = ctx;

      let num_balls = 1;
      bola = new GameEngine.Ball(cw/2,ch/2,15);
      barrai = new GameEngine.Barra(0,ch/2-60);
      barrad = new GameEngine.Barra(cw-20,ch/2-60);

      window.addEventListener("keydown", function(evt) {
        Key.onKeyDown(evt.keyCode);
        text_keycode = "keyCode = " + evt.keyCode;
      });
      window.addEventListener("keyup", function(evt) {
        Key.onKeyUp(evt.keyCode);
      });
    }

    processInput() {
      if (Key.isPress(Key.UP) && barrad.y>0) {
        barrad.arriba();
      }

      if (Key.isPress(Key.DOWN) && barrad.y+barrad.long<ch) {
        barrad.abajo();
      }
      
      if(Key.isPress(Key.W) && barrai.y>0){
	barrai.arriba();
      }

      if(Key.isPress(Key.S) && barrai.y+barrai.long<ch){
	barrai.abajo();
      }

      if(Key.isPress(Key.SPACE)){
	comenzar = true;
	document.getElementById("titulo").innerHTML = scorei + " _______________________________________ " + scored;
	document.getElementById("pie").innerHTML = "";
      }
    }

	update(elapsed) {
		if(comenzar){ //Si el juego ya comenzó mueve la bola.
			bola.update(elapsed);
			this.checkBorder(bola);
		}
	}

    checkBorder(ball) {

      if (ball.y < ball.size) {
        ball.vy *= -1;
        ball.y = ball.size;
      }
      if (ball.y > ch-ball.size) {
        ball.vy *= -1;
        ball.y = ch-ball.size;
      }
      if(ball.x<=0){//Llega al extremo izquierdo.
	ball.reinicia();
        comenzar = false;
	scored++;
	document.getElementById("titulo").innerHTML = scorei + " _______________________________________ " + scored;
	
	if(scored==5){
		document.getElementById("pie").innerHTML = "Ganó el jugador derecho";
		window.alert("Ganó el jugador derecho");
		scored = 0;
		scorei = 0;
	}
      }
      if(ball.x>=cw){ //Llega al extremo derecho.
	ball.reinicia();
	comenzar=false;
	scorei++;
	document.getElementById("titulo").innerHTML = scorei + " _______________________________________ " + scored;
	if(scorei==5){
		document.getElementById("pie").innerHTML = "Ganó el jugador izquierdo";
		window.alert("Ganó el jugador izquierdo");
		scored = 0;
		scorei = 0;
	}
      }

	//Verifica si la bola pegó con la barra izquierda.
	if(ball.y >= barrai.y && ball.y <= (barrai.y+barrai.long)){
		if(ball.x <= 20+ball.size){
			ball.acelera();
        		ball.x = ball.size+20;
		}
	}

	//Verifica si la bola pegó con la barra derecha.
	if(ball.y >= barrad.y && ball.y <= (barrad.y+barrad.long)){
		if(ball.x >= (cw - 20 - ball.size)){
			ball.acelera();
        		ball.x = cw-ball.size-20;
		}
	}
    }

	render() {
		this.ctx.clearRect(0, 0, cw, ch);
		barrai.render(this.ctx);
		barrad.render(this.ctx);
		bola.render(this.ctx);
    }
  }

  GameEngine.Game = Game;
  return GameEngine;
})(GameEngine || {})
