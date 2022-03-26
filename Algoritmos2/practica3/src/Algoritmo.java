

import java.util.Scanner;

public class Algoritmo {

    /**
     * Encuentra el número potencia de 2 más grande tal que sea menor o igual a un valor.
     * @param valor: valor que va a ser cota superior de la potencia.
     * @return número potencia de 2 menor o igual al valor.
     */
    public static int potenciaMaxima(int valor){
        int potencia = 1;
        int pAnterior = 1;

        while(potencia <= valor){
            pAnterior = potencia;
            potencia = potencia*2;
        }
        return pAnterior;
    }

    /**
     * Obtiene el peso máximo de las aristas de una gráfica.
     */
    public static int getPesoMax(Grafica g){
        int pesoMax = -1;
        int pesoAct;
        for(Vertice u : g.vertices.values()){
            for(Vertice v : u.ady){
                pesoAct = g.getPeso(u,v);
                if(pesoAct > pesoMax){
                    pesoMax = pesoAct;
                }
            }
        }
        return pesoMax;
    }

    /**
     * Encuentra el mínimo peso en una ruta de 's' a 't'.
     * @param g gráfica para la cual se encuentra el mínimo.
     * @return peso mínimo de cualquier arco entre s y t.
     */
    public static int minEnRuta(Grafica g, Vertice t){
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
    public static void actualizaResidual(Grafica g, Vertice t, int residuo){
        Vertice na = t;
        String ruta = t.toString();
        //Nótese que si na es nulo se enviará un NullPointerException.
        while(na.p != null){
            g.sumaPeso(na.p, na, -residuo);
            g.sumaPeso(na, na.p, residuo);
            na = na.p;
            ruta = na.toString() + "," + ruta;
        }
        System.out.println("Ruta: "+ruta+" Aumento: "+residuo);
    }

    /**
     * Realiza una búsqueda en amplitud en g, generando un árbol BFS.
     * @param g Gráfica a la cual se le aplica BFS.
     * @param s Vértice de origen.
     * @param cota Cota inferior para las aristas del árbol BFS.
     */
    public static void bfs(Grafica g, Vertice s, int cota){
        for(Vertice v : g.vertices.values()){
            v.visitado = false;
            v.p = null;
        }

        Cola<Vertice> q = new Cola<Vertice>();
        s.visitado = true;
        q.enqueue(s);

        while(!q.isEmpty()){
            Vertice u = q.dequeue();
            for(Vertice v:u.ady){
                if(!v.visitado && g.getPeso(u,v) >= cota){
                    v.visitado = true;
                    v.p = u;
                    q.enqueue(v);
                }
            }
        }
    }

    public static void main(String[] args){
        int entrada = 1;
        Scanner sc = new Scanner(System.in);
        while(entrada > 0){
            System.out.println("Ingresa un número mayor a 0 para calcular la potencia de 2");
            entrada = sc.nextInt();
            System.out.println(potenciaMaxima(entrada));
        }
        sc.close();
    }
}
