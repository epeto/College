var GameEngine = (function(GameEngine) {
  GameEngine.Utils = {
    lerp : function(v0, v1, t){
      return v0 + (v1 - v0)*t;
    }
  };

  return GameEngine;
})(GameEngine || {})