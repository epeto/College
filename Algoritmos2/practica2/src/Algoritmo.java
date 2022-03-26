
import java.util.LinkedList;

public class Algoritmo {

    /**
     * Encuentra el mínimo peso en una ruta de 's' a 't'.
     * @param g gráfica para la cual se encuentra el mínimo.
     * @return peso mínimo de cualquier arco entre s y t.
     */
    private static int minEnRuta(Grafica g, Vertice t){
        int minimo = Integer.MAX_VALUE;
        Vertice na = t; //Se supone que el último tiene la etiqueta t.

        //Nótese que si na es nulo se enviará un NullPointerException.
        while(na.p != null){
            int pesoDeArco = g.getPeso(na.p, na);
            if(pesoDeArco < 0)
                break;
    
            if(pesoDeArco < minimo){
                minimo = pesoDeArco;
            }
            na = na.p;
        }

        return minimo;
    }

    /**
     * Actualiza los pesos en la gráfica residual.
     * No se comprueba si la gráfica es residual, así que si no lo es enviará error.
     * @param g : gráfica residual a actualizar.
     * @param residuo : valor a restar (sumar) en la ruta s-t (t-s).
     */
    private static void actualizaResidual(Grafica g, Vertice t, int residuo){
        Vertice na = t;
        String ruta = t.toString();
        //Nótese que si na es nulo se enviará un NullPointerException.
        while(na.p != null){
            g.sumaPeso(na.p, na, -residuo);
            g.sumaPeso(na, na.p, residuo);
            na = na.p;
            ruta = na.toString() + "," + ruta;
        }
        System.out.println("Ruta: "+ruta+" Delta: "+residuo);
    }

    /**
     * Algoritmo de etiquetamiento para calcular el flujo máximo en g.
     * Devuelve la gráfica residual final.
     */
    public static Grafica etiquetamiento(Grafica g){
        g.tieneFlujo = true;
        Grafica gRes = g.residual();
        Vertice source = gRes.getVerticeId("s");
        Vertice terminal = gRes.getVerticeId("t");

        terminal.visitado = true; //Sólo para pasar el primer while.
        while(terminal.visitado){
            
            for(Vertice v : gRes.vertices.values()){
                v.visitado = false;
                v.p = null;
            }
    
            LinkedList<Vertice> lista = new LinkedList<Vertice>();
            source.visitado = true;
            lista.addLast(source);
    
            while(!lista.isEmpty() && !terminal.visitado){
                Vertice u = lista.removeFirst();
                for(Vertice v:u.ady){
                    if(!v.visitado && g.getPeso(u,v) > 0){
                        v.visitado = true;
                        v.p = u;
                        lista.addLast(v);
                    }
                }
            }

            if(terminal.visitado){
                int delta = minEnRuta(gRes, terminal);
                actualizaResidual(gRes, terminal, delta);
            }
        }

        //Se actualiza el flujo en g por cada arco (u,v).
        for(Vertice u : g.vertices.values()){
            for(Vertice v : u.ady){
                Vertice v1 = gRes.getVerticeId(v.id);
                Vertice v2 = gRes.getVerticeId(u.id);
                int flujo = gRes.getPeso(v1, v2);
                g.agregaFlujo(u, v, flujo);
            }
        }

        return gRes;
    }

    public static void main(String[] args){
        Lector l = new Lector();
        l.lee("ejemplares/red1.csv"); //Se lee el archivo.
        Grafica g1 = l.creaGrafica(true);

        etiquetamiento(g1);
    }
}
