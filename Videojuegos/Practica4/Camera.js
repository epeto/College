var GameEngine = (function(GameEngine) {
  let dx, dy;

  class Camera {
    constructor(x, y, cw, ch) {
      this.x = x;
      this.y = y;
      this.stage_w = cw;
      this.stage_h = ch;

      this.left   = 64;
      this.right  = 64;
      this.top    = 64;
      this.bottom = 64;
    }

    applyTransform(ctx) {
      ctx.save();
      ctx.translate(parseInt(this.stage_w/2 -this.x), parseInt(this.stage_h/2 -this.y));
    }
    releaseTransform(ctx) {
      ctx.restore();
    }

    update(player, level) {
      // window left
      if (player.x - player.w_2 < this.x - this.left) {
        this.x = Math.max(this.stage_w/2, parseInt(player.x - player.w_2 + this.left));
      }
      // window right
      else if (this.x + this.right < player.x + player.w_2) {
        this.x = Math.min(level.w -this.stage_w/2, parseInt(player.x + player.w_2 - this.right));
      }

      // window bottom
      if (this.y + this.bottom < player.y + player.h_2) {
        this.y = parseInt(player.y + player.h_2 - this.bottom);
      }
      // window top
      else if (player.y - player.h_2 < this.y - this.top) {
        this.y = parseInt(player.y - player.h_2 + this.top);
      }
    }

    //Recibe la vida del jugador para dibujar la barra.
    render(ctx,vida) {
      var dx = 120; //Desplazamiento en x de la barra
      var dy = 115; //Desplazamiento en y de la barra
      //En esta parte dibujamos la barra de vida.
      //Lo dibujo en esta clase porque debe seguir a la cámara.
      ctx.fillStyle = "rgb(0,0,0)";
      ctx.beginPath();
      ctx.rect(this.x-dx,this.y-dy,15,84);
      ctx.strokeStyle = "#AE0E36";
      ctx.lineWidth = "3";
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = "rgb(255,228,181)"
      ctx.beginPath();

      for(let i=1;i<=vida;i++){
        ctx.rect(this.x-dx,this.y-dy+(84-i*3),15,2);
      }

      ctx.fill();
    }
  }

  GameEngine.Camera = Camera;
  return GameEngine;
})(GameEngine || {})