var GameEngine = (function(GameEngine) {
  let cw;
  let ch;
  var sw = 210*16; //Ancho del del nivel
  var sh = 135*16; //Altura del nivel
  var late3 = 0; //Latencia para crear enemigos tipo 3.

  let KEY = GameEngine.KEY;
  var juegoEmpezado;
  var imagInic; //Imagen inicial

  class Game {
    constructor(ctx) {
      cw = ctx.canvas.width;
      ch = ctx.canvas.height;
      this.ctx = ctx;

      this.camera = new GameEngine.Camera(cw/2, 1900, cw, ch);

      this.player = new GameEngine.Player(120, 2050, 16, 32);
      this.level = new GameEngine.Level();

      window.addEventListener("keydown", function(evt) {
        KEY.onKeyDown(evt.keyCode);
      });
      window.addEventListener("keyup", function(evt) {
        KEY.onKeyUp(evt.keyCode);
      });

      juegoEmpezado = false;
      imagInic = new Image();
      imagInic.src = "imagenes/intro.png";

      //En esta parte colocamos a todos los enemigos tipo 1.
      this.enemigos1 = [];
      this.enemigos1.push(new GameEngine.Enemigo1(300,1970));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80,2050));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80*2,2000));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80*3,1940));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80*4,2060));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80*5,1960));
      this.enemigos1.push(new GameEngine.Enemigo1(300+80*6,2100));
      this.enemigos1.push(new GameEngine.Enemigo1(125*16,4*16));
      this.enemigos1.push(new GameEngine.Enemigo1(130*16,9*16));
      this.enemigos1.push(new GameEngine.Enemigo1(135*16,5*16));

      //En esta parte agregamos a los enemigos tipo 2.
      this.enemigos2 = [];
      this.enemigos2.push(new GameEngine.Enemigo2(54*16,124*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(62*16,127*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(65*16,114*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(58*16,110*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(60*16,108*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(54*16,101*16,1));
      this.enemigos2.push(new GameEngine.Enemigo2(58*16,97*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(62*16,93*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(57*16,88*16,1));
      this.enemigos2.push(new GameEngine.Enemigo2(62*16,80*16,-1));
      this.enemigos2.push(new GameEngine.Enemigo2(60*16,77*16,-1));

      //Arreglo de enemigos tipo 3
      this.enemigos3 = [];

      //Arreglo de enemigos tipo 4.
      this.enemigos4 = [];
      //Pimer grupo
      this.enemigos4.push(new GameEngine.Enemigo4(86*16,70*16,"vertical",70*16,71*16));
      this.enemigos4.push(new GameEngine.Enemigo4(88*16,66*16,"vertical",66*16,68*16));
      this.enemigos4.push(new GameEngine.Enemigo4(87*16,68*16,"horizontal",87*16,93*16));
      //Segundo grupo
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,53*16,"horizontal",84*16,95*16));
      this.enemigos4.push(new GameEngine.Enemigo4(87*16,53*16,"vertical",53*16,56*16));
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,51*16,"horizontal",84*16,89*16));
      this.enemigos4.push(new GameEngine.Enemigo4(86*16,49*16,"horizontal",86*16,93*16));
      //Tercer grupo
      this.enemigos4.push(new GameEngine.Enemigo4(90*16,41*16,"vertical",41*16,42*16));
      this.enemigos4.push(new GameEngine.Enemigo4(88*16,40*16,"horizontal",88*16,89*16));
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,36*16,"horizontal",84*16,93*16));
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,34*16,"horizontal",84*16,95*16));
      //Cuarto grupo
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,25*16,"horizontal",84*16,85*16));
      this.enemigos4.push(new GameEngine.Enemigo4(85*16,22*16,"vertical",22*16,27*16));
      this.enemigos4.push(new GameEngine.Enemigo4(90*16,23*16,"horizontal",90*16,93*16));
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,19*16,"horizontal",84*16,95*16));
      this.enemigos4.push(new GameEngine.Enemigo4(84*16,17*16,"horizontal",84*16,95*16));

      //Arreglo de enemigos tipo 5.
      this.enemigos5 = [];
      this.enemigos5.push(new GameEngine.Enemigo5(53*16));
      this.enemigos5.push(new GameEngine.Enemigo5(64*16));
      this.enemigos5.push(new GameEngine.Enemigo5(75*16));

      //Arreglo de enemigos tipo 6.
      this.enemigos6 = [];
      this.enemigos6.push(new GameEngine.Enemigo6(117*16,22*16,117*16,129*16));
      this.enemigos6.push(new GameEngine.Enemigo6(117*16,38*16,117*16,130*16));

      //Enemigo tipo 7.
      this.enemigos7 = [];
      this.enemigos7.push(new GameEngine.Enemigo7());
    }

    processInput() {
      this.player.processInput();

      if(KEY.isPress(KEY.ENTER)){
        juegoEmpezado = true; //Cuando presiona enter empieza el juego
      }
    }

    update(elapsed) {
      if(juegoEmpezado){

      if(this.player.x>144*16 && this.player.x<145*16){ //Llegó a la barrera.
        this.level.borraBarrera();
      }
      late3 = (late3+1)%10;
      if(late3==5){
        this.creaEnemigos3(); //Función que crea a los enemigos tipo 3.
      }
      //Actualizamos a los enemigos tipo 1.
      for(let i=0;i<this.enemigos1.length;i++){
        this.enemigos1[i].update(this.player.x,this.player.y,this.player);
        if(this.enemigos1[i].vida<=0){
          this.enemigos1.splice(i,1);
        }
      }

      //Actualizamos a los enemigos tipo 2.
      for(let i=0;i<this.enemigos2.length;i++){
        this.enemigos2[i].update(this.player);
        if(this.enemigos2[i].vida<=0){
          this.enemigos2.splice(i,1);
        }
      }

      //Actualizamos a los enemigos tipo 3.
      for(let i=0;i<this.enemigos3.length;i++){
        this.enemigos3[i].update(this.player);
        if(this.enemigos3[i].tvida<=0 || this.enemigos3[i].vida<=0){
          this.enemigos3.splice(i,1);
        }
      }

      //Actualizamos a los enemigos tipo 4.
      for(let i=0;i<this.enemigos4.length;i++){
        this.enemigos4[i].update(this.player);
        if(this.enemigos4[i].vida<=0){
          this.enemigos4.splice(i,1);
        }
      }

      //Actualizamos a los enemigos tipo 5.
      for(let i=0;i<this.enemigos5.length;i++){
        this.enemigos5[i].update(this.player);
        if(this.enemigos5[i].vida<=0){
          this.enemigos5.splice(i,1);
        }
      }

      //Actualizamos a los enemigos tipo 6.
      for(let i=0;i<this.enemigos6.length;i++){
        this.enemigos6[i].update(this.player);
        if(this.enemigos6[i].vida<=0){
          this.enemigos6.splice(i,1);
        }
      }

      //Actualizamos al enemigo tipo 7.
      if(this.enemigos7.length>0){
          this.enemigos7[0].update(this.player);
        if(this.enemigos7[0].vida<=0){
          this.enemigos7.splice(0,1);
        }
      }

      this.verificaBalas(); //Para ver si las balas chocaron con algo

      if ((this.player.tryGrabLadder) && (!this.player.inLadder)) {
        this.checkCollisionLadders(this.player, this.level);
      }

      this.player.update(elapsed);

      if (this.player.inLadder) {
        this.checkInLadders(this.player, this.level);
      }

      this.checkCollisionPlatforms(this.player, this.level);

      if (this.player.inFloor) {
        this.player.inLadder = false;
      }

      this.player.setState();

      this.camera.update(this.player, this.level);

      this.checkCollisionWalls();

      if(this.player.vida<=0){
        this.player.vida = 28;
        juegoEmpezado = false;
      }
      }
    } //Aquí termina update

    //Función que verifica si las balas chocaron con algo.
    verificaBalas(){
      for(let i=0;i<this.enemigos1.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          if((this.player.balas[j].x>this.enemigos1[i].x-8) && (this.player.balas[j].x<this.enemigos1[i].x+8)){
            if((this.player.balas[j].y>this.enemigos1[i].y-10) && (this.player.balas[j].y<this.enemigos1[i].y+10)){
              this.enemigos1[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos2.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos2[i];
          if(bull.x>enemy.x && bull.x<enemy.x+enemy.frameW){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h && enemy.frame!=0){
              this.enemigos2[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos3.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos3[i];
          if(bull.x>enemy.x && bull.x<enemy.x+enemy.frameW){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h){
              this.enemigos3[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos4.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos4[i];
          if(bull.x>enemy.x && bull.x<enemy.x+enemy.frameW){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h){
              this.enemigos4[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos5.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos5[i];
          if(bull.x>enemy.x && bull.x<enemy.x+enemy.frameW){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h){
              this.enemigos5[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos6.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos6[i];
          if(bull.x>enemy.x && bull.x<enemy.x+enemy.frameW){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h){
              this.enemigos6[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }

      for(let i=0;i<this.enemigos7.length;i++){
        for(let j=0;j<this.player.balas.length;j++){
          var bull = this.player.balas[j];
          var enemy = this.enemigos7[i];
          if(bull.x>enemy.x-enemy.frameW/2 && bull.x<enemy.x+enemy.frameW/2){
            if(bull.y>enemy.y && bull.y<enemy.y+enemy.h){
              this.enemigos7[i].vida--;
              this.player.balas.splice(j,1);
            }
          }
        }
      }
    }

    checkCollisionLadders(player, level) {
      let player_tile_pos = level.getTilePos(player.x, player.y);
      let ladder;

      // top
      ladder = level.getPlatform(player_tile_pos.x, player_tile_pos.y-1);
      if (ladder && (ladder.type === "Ladder") && (player.ladderVy < 0)) {
        this.player.x = ladder.x;
        this.player.inLadder = true;
        return;
      }

      //center
      ladder = level.getPlatform(player_tile_pos.x, player_tile_pos.y);
      if (ladder && (ladder.type === "Ladder")) {
        this.player.x = ladder.x;
        this.player.inLadder = true;
        return;
      }

      // bottom
      ladder = level.getPlatform(player_tile_pos.x, player_tile_pos.y+1);
      if (ladder && (ladder.type === "Ladder") && (player.ladderVy > 0)) {
        this.player.x = ladder.x;
        this.player.inLadder = true;
        return;
      }
    }

    checkInLadders(player, level) {
      let player_tile_pos = level.getTilePos(player.x, player.y + player.h_2);
      let ladder;
      //center
      ladder = level.getPlatform(player_tile_pos.x, player_tile_pos.y);
      if (ladder && (ladder.type === "Ladder")) {
        this.player.inLadder = true;
        this.player.w = 16;
        this.player.w_2 = 8;
        this.player.escalar = 2;
      }
      else {
        this.player.inLadder = false;
        this.player.w = 16;
        this.player.w_2 = 8;
        this.player.escalar = 2;
      }
    }

    checkCollisionWalls() {
      if (this.player.x < this.camera.x -cw/2 +this.player.w_2) {
        this.player.x = this.camera.x -cw/2 +this.player.w_2;
      }
      if (this.player.x > this.camera.x +cw/2 -this.player.w_2) {
        this.player.x = this.camera.x +cw/2 -this.player.w_2;
      }

      if (this.player.y > sh) {
        this.player.y -= 150;
        this.player.vy = 0;
      }
    }

    checkCollisionPlatforms(player, level) {
      let player_tile_pos = level.getTilePos(player.x, player.y);

      //center
      this.reactCollision(player, level.getPlatform(player_tile_pos.x,   player_tile_pos.y));
      // left
      this.reactCollision(player, level.getPlatform(player_tile_pos.x-1, player_tile_pos.y));
      // right
      this.reactCollision(player, level.getPlatform(player_tile_pos.x+1, player_tile_pos.y));

      // top
      this.reactCollision(player, level.getPlatform(player_tile_pos.x,   player_tile_pos.y-1));
      // bottom
      this.reactCollision(player, level.getPlatform(player_tile_pos.x,   player_tile_pos.y+1));

      // left top
      this.reactCollision(player, level.getPlatform(player_tile_pos.x-1, player_tile_pos.y-1));
      // right top
      this.reactCollision(player, level.getPlatform(player_tile_pos.x+1, player_tile_pos.y-1));

      // left bottom
      this.reactCollision(player, level.getPlatform(player_tile_pos.x-1, player_tile_pos.y+1));
      // right bottom
      this.reactCollision(player, level.getPlatform(player_tile_pos.x+1, player_tile_pos.y+1));
    }

    reactCollision(player, platform) {
      if ( platform && 
      	   platform.type == "Platform" &&
           Math.abs(player.x - platform.x) < player.w_2 + platform.w_2 && 
           Math.abs(player.y - platform.y) < player.h_2 + platform.h_2 ) {

        let overlapX = (player.w_2 + platform.w_2) - Math.abs(player.x - platform.x);
        let overlapY = (player.h_2 + platform.h_2) - Math.abs(player.y - platform.y);
        var pcf = platform.currentFrame;
        
        if (overlapX < overlapY) {
          if (player.x - platform.x > 0) {
            player.x += overlapX;
          }
          else {
            player.x -= overlapX;
          }
        }
        else if (overlapX > overlapY) {
          if (player.y - platform.y > 0) {
            player.y += overlapY;
            if (player.vy < 0) {
              player.vy = 0;
            }
          }
          else {
            player.y -= overlapY;
            if (player.vy > 0) {
              player.inFloor = true;
              player.vy = 0;
            }
          }
        } //Aquí acaba el elseif
      }
    }

    creaEnemigos3(){
      var xc1 = 945;
      var yc1 = 1130;
      var distancia1 = Math.sqrt((this.player.x-xc1)*(this.player.x-xc1)+(this.player.y-yc1)*(this.player.y-yc1));
      if(distancia1<90){
        if(this.player.x<xc1){
          this.enemigos3.push(new GameEngine.Enemigo3(xc1,yc1,-1));
        }else{
          this.enemigos3.push(new GameEngine.Enemigo3(xc1,yc1,1));
        }
      }

      var xc2 = 1456;
      var yc2 = 169;
      var distancia2 = Math.sqrt((this.player.x-xc2)*(this.player.x-xc2)+(this.player.y-yc2)*(this.player.y-yc2));

      if(distancia2<90){
        if(this.player.x<xc2){
          this.enemigos3.push(new GameEngine.Enemigo3(xc2,yc2,-1));
        }else{
          this.enemigos3.push(new GameEngine.Enemigo3(xc2,yc2,1));
        }
      }
    }

    render() {
      if(juegoEmpezado){
        this.ctx.fillStyle = "#6b8cff";
        this.ctx.fillRect(0, 0, cw, ch);

        this.camera.applyTransform(this.ctx);

        this.level.render(this.ctx);
        this.player.render(this.ctx);
        //En esta parte dibujamos a los enemigos tipo 1.
        for(let i=0;i<this.enemigos1.length;i++){
          this.enemigos1[i].render(this.ctx);
        }

        //Dibujamos a los enemigos tipo 2.
        for(let i=0;i<this.enemigos2.length;i++){
          this.enemigos2[i].render(this.ctx);
        }

        //Dibujamos a los enemigos tipo 3.
        for(let i=0;i<this.enemigos3.length;i++){
          this.enemigos3[i].render(this.ctx);
        }

        //Dibujamos a los enemigos tipo 4.
        for(let i=0;i<this.enemigos4.length;i++){
          this.enemigos4[i].render(this.ctx);
        }

        //Dibujamos a los enemigos tipo 5.
        for(let i=0;i<this.enemigos5.length;i++){
          this.enemigos5[i].render(this.ctx);
        }

        //Dibujamos a los enemigos tipo 6.
        for(let i=0;i<this.enemigos6.length;i++){
          this.enemigos6[i].render(this.ctx);
        }

        //Dibujamos al enemigo tipo 7.
        if(this.enemigos7.length>0){
          this.enemigos7[0].render(this.ctx);
        }

        this.camera.render(this.ctx,this.player.vida);
        this.camera.releaseTransform(this.ctx);

      }else{
        this.ctx.drawImage(imagInic,0,0);
      }
    }
  }

  GameEngine.Game = Game;
  return GameEngine;
})(GameEngine || {})