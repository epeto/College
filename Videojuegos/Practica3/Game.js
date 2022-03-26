var GameEngine = (function(GameEngine) {
  let cw;
  let ch;

  let KEY = GameEngine.KEY;

  let aPool;
  let bPool;
  var juegoEmpezado; //Me permitirá verificar si ya empezó el juego.
  var vidas;
  var puntos;
  var el=0; //Levará una cuenta del tiempo que ha transcurrido.
  var s1=true; //Para asegurarnos de que solo agregue 1 asteroide a la vez.
  var intervalo; //Cada cuántos segundos se va a agregar un asteroide.
  var potencia;

  class Game {
    constructor(ctx) {
      cw = ctx.canvas.width;
      ch = ctx.canvas.height;
      this.ctx = ctx;

      this.ship = new GameEngine.Ship(cw/2, ch/2, 50);

      this.asteroidPool = new GameEngine.AsteroidPool(cw, ch);

      potencia = new GameEngine.Powerup(cw,ch);

      window.addEventListener("keydown", function(evt) {
        KEY.onKeyDown(evt.keyCode);
      });
      window.addEventListener("keyup", function(evt) {
        KEY.onKeyUp(evt.keyCode);
      });

      juegoEmpezado = false;
      vidas = 3;
      puntos = 0;
      intervalo = 3; //3 segundos.
    }

    processInput() {
      if (KEY.isPress(KEY.SPACE)) {
        juegoEmpezado = true;
      }

      this.ship.processInput();
    }

    update(elapsed) {
      if(juegoEmpezado){
      this.vive100();
      this.colisionPowerup(); //Verifica constantemente si colisiona la nave con el powerup.
      if(potencia.isAlive){
        potencia.update(elapsed);
      }
      el+= elapsed;
      this.agrAst();
      aPool = this.asteroidPool.asteroids;
      bPool = this.ship.weapon.bullets;

      this.ship.update(elapsed);
      this.asteroidPool.update(elapsed);

      this.checkBorders(this.ship);

      for (let i=0; i<aPool.length; i++) {
        if (aPool[i].isAlive) {
          this.checkBorders(aPool[i]);
        }
      }

      for (let i=0; i<bPool.length; i++) {
        if (bPool[i].isAlive) {
          this.checkBorders(bPool[i]);
        }
      }


      for (let i=0; i<aPool.length; i++) {
        if(this.checkCircleCollision(this.ship, aPool[i], "nave-asteroide")){
          if(vidas>0){
            if(aPool[i].isAlive && !this.ship.empoderado){
              vidas--;
              this.ship.x = cw/2;
              this.ship.y = ch/2;
              this.limpiarAst();
            }
          }else{ //Reiniciamos el juego.
            juegoEmpezado = false;
            this.ship.x = cw/2;
            this.ship.y = ch/2;
            vidas = 3;
            puntos = 0;
            this.limpiarAst();
            el = 0;
          }
          
        }
      }

      for (let bullet_i=0; bullet_i<bPool.length; bullet_i++) {
        if (bPool[bullet_i].isAlive) {
          for (let asteroid_i=0; asteroid_i<aPool.length; asteroid_i++) {
            if ( (aPool[asteroid_i].isAlive) && (this.checkCircleCollision(bPool[bullet_i], aPool[asteroid_i], "bala-asteroide")) ) {
              bPool[bullet_i].isAlive = false; //Si colisiona muere :'v
              this.asteroidPool.split(asteroid_i,bPool[bullet_i].rot);
              switch(aPool[asteroid_i].hp){
                case 0: puntos+=100;
                        break;
                case 1: puntos+=50;
                        break;
                case 2: puntos+=20;
                        break;
              }
            }
          }
        }
      }
      }
    }

    checkCircleCollision(obj1, obj2, tmpmsg) {
      let dist = Math.sqrt( (obj1.x - obj2.x)*(obj1.x - obj2.x) + (obj1.y - obj2.y)*(obj1.y - obj2.y) );
      if (dist < obj1.radius + obj2.radius) {
        console.log("colision", tmpmsg);
        return true;
      }
      return false;
    }

    checkBorders(gameObject) {
      if (gameObject.x < -gameObject.radius) {
        gameObject.x = cw + gameObject.radius;
        if(gameObject instanceof GameEngine.Bullet){
          gameObject.isAlive = false;
        }
      }
      if (gameObject.x > cw+gameObject.radius) {
        gameObject.x = -gameObject.radius;
        if(gameObject instanceof GameEngine.Bullet){
          gameObject.isAlive = false;
        }
      }
      if (gameObject.y < -gameObject.radius) {
        gameObject.y = ch + gameObject.radius;
        if(gameObject instanceof GameEngine.Bullet){
          gameObject.isAlive = false;
        }
      }
      if (gameObject.y > ch+gameObject.radius) {
        gameObject.y = -gameObject.radius;
        if(gameObject instanceof GameEngine.Bullet){
          gameObject.isAlive = false;
        }
      }
    }

    render() {
      this.ctx.fillStyle = "rgba(0,0,0,1)";
      this.ctx.fillRect(0, 0, cw, ch);
      if(juegoEmpezado){
        this.ship.render(this.ctx);
        this.asteroidPool.render(this.ctx);
        this.ctx.fillStyle = "rgb(255,255,255)";
        this.ctx.font = "20px Comic Sans MS";
        this.ctx.fillText("Vidas♥ "+vidas,10,25);
        this.ctx.fillText("Puntos: "+puntos,cw-160,25);
        if(potencia.isAlive){
          potencia.render(this.ctx);
        }
      }else{
        this.ctx.fillStyle = "rgb(255,255,255)";
        this.ctx.font = "30px Comic Sans MS";
        this.ctx.fillText("Presiona espacio para empezar",70,ch/2);
      }
    }

    //Función para agregar un asteroide
    agrAst(){
      if((Math.floor(el)%intervalo)==0){ //Agrega un asteroide cada 5 segundos
        if(s1){
          this.asteroidPool.addAsteroid();
          s1 = false;
        }
      }else{
        s1 = true;
      }
    }

    //Función que "mata" a todos los asteroides.
    limpiarAst(){
      for (let i=0; i<aPool.length; i++) {
        if (aPool[i].isAlive) {
          aPool[i].isAlive = false;
        }
      }
    }

    //Verifica una colisión entre el powerup y la nave.
    colisionPowerup(){
      if(potencia.isAlive){
        if(this.ship.x<=potencia.x+potencia.ancho+this.ship.radius && this.ship.x>=potencia.x-this.ship.radius){
          if(this.ship.y<=potencia.y+potencia.alto+this.ship.radius && this.ship.y>=potencia.y-this.ship.radius){
            this.ship.empoderado = true;
            potencia.isAlive = false; //Se muere el vive 100
          }
        }
      }
    }

    //Función que saca un vive 100 de forma aleatoria.
    vive100(){
      var rn = Math.ceil(Math.random()*500); //Existe una probabilidad de 1/500 de que salga un vive 100.
      if(rn==250 && !this.ship.empoderado){
        potencia.isAlive = true;
        potencia.recalculaAngulo();
      }
    }
  }

  GameEngine.Game = Game;
  return GameEngine;
})(GameEngine || {})