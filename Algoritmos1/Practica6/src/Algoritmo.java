public class Algoritmo {
    
    public static void dijkstra(Grafica g, Vertice s){
        BinomialQueue<Vertice> q = new BinomialQueue<Vertice>();

        for(Vertice v : g.vertices){
            v.d = Integer.MAX_VALUE;
            v.p = null;
        }
        s.d = 0;
        for(Vertice v : g.vertices)
            q.insert(v);
        
        while(!q.isEmpty()){
            Vertice u = q.deleteMin();
            for(Vertice v : u.ady){
                if(v.d > u.d + g.getPeso(u,v)){
                    v.d = u.d + g.getPeso(u,v);
                    v.p = u;
                    q.decreaseKey(v);
                }
            }
        }
    }
}
