var GameEngine = (function(GameEngine) {
  class AsteroidPool {
    constructor(w, h) {
      this.game_w = w;
      this.game_h = h;

      this.initialSize = 40;

      this.numAsteroids = 15;
      this.asteroids = [];

      for (let i=0; i<this.numAsteroids; i++) {
        this.asteroids.push(new GameEngine.Asteroid(0, 0, this.initialSize));
      }
    }

    addAsteroid() {
      let x, y;

      switch (parseInt(Math.random() * 2)) {
        case 0:
          x = -this.initialSize;
          y = Math.random() * this.game_h;
          break;
        case 1:
          x = Math.random() * this.game_w;
          y = -this.initialSize;
          break;
      }

      for (let i=0; i<this.numAsteroids; i++) {
        if (!this.asteroids[i].isAlive) {
          this.asteroids[i].activate(x, y);
          this.asteroids[i].size = this.initialSize;
          this.asteroids[i].radius = this.initialSize;
          return this.asteroids[i];
        }
      }
    }

    split(old_asteroid_id,rotation) {
      var old_asteroid = this.asteroids[old_asteroid_id];
      var sp = old_asteroid.speed;
      var angulo = 0.698132;
      let new_asteroid = this.addAsteroid();

      if (new_asteroid) {
        new_asteroid.x = old_asteroid.x;
        new_asteroid.y = old_asteroid.y;
        old_asteroid.vx = sp*Math.cos(rotation+angulo);
        old_asteroid.vy = sp*Math.sin(rotation+angulo);
        new_asteroid.vx = sp*Math.cos(rotation-angulo);
        new_asteroid.vy = sp*Math.sin(rotation-angulo);
        new_asteroid.size = new_asteroid.radius = old_asteroid.size;
        new_asteroid.hp = old_asteroid.hp;
        new_asteroid.hit();
      }

      old_asteroid.hit();
    }

    update(elapsed) {
      for (let i=0; i<this.numAsteroids; i++) {
        if (this.asteroids[i].isAlive) {
          this.asteroids[i].update(elapsed);
        }
      }
    }

    render(ctx) {
      for (let i=0; i<this.numAsteroids; i++) {
        if (this.asteroids[i].isAlive) {
          this.asteroids[i].render(ctx);
        }
      }
    }    
  }

  GameEngine.AsteroidPool = AsteroidPool;
  return GameEngine;
})(GameEngine || {})        