var GameEngine = (function(GameEngine) {
  const PI2 = 2*Math.PI;

  var imageObj;

  class Asteroid {
    constructor(x, y, size) {
      this.isAlive = false;
      this.x = x;
      this.y = y;
      this.speed = 100;
      imageObj = new Image();
      imageObj.src = 'asteroide.png';
      let angle = (360 * Math.random()) * Math.PI/180;
      this.vx = Math.cos(angle) * this.speed;
      this.vy = Math.sin(angle) * this.speed;
      this.size = this.radius = size;
      this.hp = 3;
    }

    hit() {
      this.hp--;

      if (this.hp > 0) {
        this.radius = this.size = this.size/2;
      }
      else {
        this.isAlive = false;
      }
    }

    activate(x, y) {
      this.x = x;
      this.y = y;
      this.isAlive = true;
      this.hp = 3;
    }

    update(elapsed) {
      this.x += this.vx *elapsed;
      this.y += this.vy *elapsed;
    }

    render(ctx) {
      ctx.drawImage(imageObj, this.x-this.size, this.y-this.size,this.size*2,this.size*2);
    }
  }

  GameEngine.Asteroid = Asteroid;
  return GameEngine;
})(GameEngine || {})