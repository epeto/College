var GameEngine = (function(GameEngine) {
  let gravity = 16;

  let KEY = GameEngine.KEY;
  var frec; //Para medir la frecuencia de disparo

  class Player extends GameEngine.Sprite {
    constructor(x, y, w, h) {
      super(x, y, w, h, "imagenes/player.svg", 14, 32, 32);

      this.frameCounter = 0;
      this.framesPerChange = 8;

      this.ladderCounter = 0;
      this.ladderFramesPerChange = 12;

      this.w_2 = w/2;
      this.h_2 = h/2;

      this.jump_heigth = 295;
      this.inFloor = false;

      this.speed = 95;
      this.vx = 0;
      this.vy = 0;
      this.canJump = true;
      this.balas = []; //Arreglo que guardará las balas
      frec = 0;
      this.vida = 28;
    }

    processInput() {
      this.vx = 0;
      this.ladderVy = 0;
      this.tryGrabLadder = false;

      if (KEY.isReleased(KEY.X)) {
        this.canJump = true;
      }
      if ((KEY.isPress(KEY.X)) && (this.canJump) && (this.inFloor)) {
        this.vy = -this.jump_heigth;
        this.canJump = false;
      }

      if(KEY.isPress(KEY.Z)){ //Z es para disparar
      	this.disparando = 1;
      }else{
      	this.disparando = 0;
      }

      if ((KEY.isPress(KEY.X)) && (this.inLadder)) {
        this.inLadder = false;
        this.w = 16;
        this.w_2 = 8;
        this.escalar = 2;
      }

      if (KEY.isPress(KEY.LEFT)) {
        this.vx = -this.speed;
        this.direction = -1;
      }
      if (KEY.isPress(KEY.RIGHT)) {
        this.vx = this.speed;
        this.direction = 1;
        console.log("x: "+this.x);
      }

      if (KEY.isPress(KEY.UP)) {
        this.tryGrabLadder = true;
        this.ladderVy = -this.speed/2;

        if (this.inLadder) {
          if (((this.ladderCounter++)%this.ladderFramesPerChange) === 0) {
            this.direction *= -1;
          }
        }
      }
      if (KEY.isPress(KEY.DOWN)) {
        this.tryGrabLadder = true;
        this.ladderVy = this.speed/2;
        if (this.inLadder) {
          if (((this.ladderCounter++)%this.ladderFramesPerChange) === 0) {
            this.direction *= -1;
          }
        }
      }
    }

    setState() {
      if (!this.inLadder) {
        if (this.vx !== 0) {
          this.state = "walking";
        }
        else if (this.inFloor) {
          this.state = "still";
        }
        if (!this.inFloor) {
          this.state = "jumping";
        }
      }
      else {
        this.state = "ladder";
      }
    }

    update(elapsed) {

      this.inFloor = false;

      if (!this.inLadder) {
        this.vy += gravity;

        super.update(elapsed);
      }
      else {
        this.vx = 0;
        this.vy = this.ladderVy;
        super.update(elapsed);
      }

      //En esta parte agregaremos y actualizaremos this.balas.

      if(this.disparando == 1){
      	frec += elapsed;

      	if(this.balas.length<2){
      		this.balas.push(new GameEngine.Bala(this.x,this.y,this.direction,this.state));
      	}

      	if(frec >= 0.08){ //Dispara cada 0.05 segundos
      		this.balas.push(new GameEngine.Bala(this.x,this.y,this.direction,this.state));
      		frec = 0;
      	}
      }else{
      	frec = 0;
      }

      if(this.balas.length>0){
      	for(let i=0;i<this.balas.length;i++){
      		this.balas[i].update();
      		if(this.balas[i].tvida<=0){ //Si ya se murió, remover la bala
      			this.balas.splice(i,1);
      		}
      	}
      }
    }
    
    render(ctx) {
      if (this.state === "walking") {
        this.frameCounter = (this.frameCounter +1)%(this.framesPerChange*4);
        let theFrame = parseInt(this.frameCounter/this.framesPerChange);
        if (theFrame === 3) {
          theFrame = 1;
        }
        this.currentFrame = 1 + theFrame;
      }
      else if (this.state === "still") {
        this.currentFrame = 0;
      }
      else if (this.state === "jumping") {
        this.currentFrame = 4;
      }
      else if (this.state === "ladder") {
        this.currentFrame = 5;
      }

      for(let i=0;i<this.balas.length;i++){
      	this.balas[i].render(ctx);
      }

      super.render(ctx)
    }
  }

  GameEngine.Player = Player;
  return GameEngine;
})(GameEngine || {})