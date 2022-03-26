
package ed.algebra;

import java.util.LinkedList;
import java.util.ListIterator;

public class Polinomio {

    public LinkedList<Monomio> lista;

    public Polinomio(){
        lista = new LinkedList<>();
    }

    /**
     * Constructor que construye un polinomio a partir de dos arreglos.
     * @param coefs arreglo de coeficientes.
     * @param exps arreglo de exponentes.
     */
    public Polinomio(double[] coefs, int[] exps){
        lista = new LinkedList<>();
        for(int i=0; i<coefs.length && i<exps.length; i++){
            inserta(new Monomio(coefs[i], exps[i]));
        }
    }

    /**
     * Reduce el polinomio, agrupando términos con el mismo exponente.
     * Se supone que los monomios están ordenados por su exponente (de menor a mayor).
     */
    public void simplifica(){
        LinkedList<Monomio> nueva = new LinkedList<>();
        ListIterator<Monomio> iterador = lista.listIterator();
        if(iterador.hasNext()){
            nueva.addLast(iterador.next());
        }

        while(iterador.hasNext()){
            Monomio siguiente = iterador.next(); //Monomio del iterador.
            Monomio ultimo = nueva.getLast(); //Último monomio de la lista nueva.
            if(ultimo.p() == siguiente.p()){ //Si la potencia es igual, se suman.
                nueva.set(nueva.size()-1, ultimo.más(siguiente));
            }else{ //si la potencia es diferente, simplemente se agrega el siguiente.
                if(ultimo.c() == 0){
                    nueva.removeLast();
                }
                nueva.addLast(siguiente);
            }
        }

        lista = nueva;
    }

    /**
     * Inserta un monomio en la posición adecuada de la lista.
     */
    public void inserta(Monomio nuevo){
        if(lista.isEmpty()){
            lista.add(nuevo);
            return;
        }
        ListIterator<Monomio> lit = lista.listIterator();
        while(lit.hasNext()){
            Monomio sig = lit.next();
            if(sig.p() > nuevo.p()){
                lit.previous();
                lit.add(nuevo);
                return;
            }
        }
        lit.add(nuevo);
    }

    /**
     * Convierte el polinomio en String.
     * @return cadena que representa al polinomio.
     */
    public String toString(){
        String salida = "";
        ListIterator<Monomio> lit = lista.listIterator();
        if(lit.hasNext()){
            salida += lit.next().toString();
        }
        while(lit.hasNext()){
            Monomio sig = lit.next();
            if(sig.c() > 0){
                salida = salida + " + " + sig.toString();
            }else{
                salida += " "+sig.toString();
            }
        }
        return salida;
    }

    /**
     * Suma a este polinomio con p. Usa la estrategia de merge.
     * @param p otro polinomio a sumar.
     * @return this + p
     */
    public Polinomio más(Polinomio p){
        Polinomio pNuevo = new Polinomio();
        ListIterator<Monomio> litThis = lista.listIterator();
        ListIterator<Monomio> litP = p.lista.listIterator();
        while(litP.hasNext() && litThis.hasNext()){
            Monomio nextP = litP.next();
            Monomio nextThis = litThis.next();
            if(nextP.p() < nextThis.p()){
                pNuevo.lista.addLast(nextP.clone());
                litThis.previous();
            }else{
                pNuevo.lista.addLast(nextThis.clone());
                litP.previous();
            }
        }
        while(litP.hasNext()){
            pNuevo.lista.addLast(litP.next().clone());
        }
        while(litThis.hasNext()){
            pNuevo.lista.addLast(litThis.next().clone());
        }
        pNuevo.simplifica();
        return pNuevo;
    }

    /**
     * Multiplica este polinomio por un monomio.
     * @param mono monomio por el cual se va a multiplicar.
     * @return this * mono
     */
    private Polinomio porMonomio(Monomio mono){
        Polinomio nuevo = new Polinomio();
        for(Monomio elem : lista){
            nuevo.lista.addLast(elem.por(mono));
        }
        return nuevo;
    }

    /**
     * Multiplica este polinomio por otro polinomio.
     * @param otro polinomio
     * @return this * otro
     */
    public Polinomio por(Polinomio otro){
        Polinomio nuevo = null;
        for(Monomio mono : otro.lista){
            if(nuevo == null){
                nuevo = this.porMonomio(mono);
            }else{
                nuevo = nuevo.más(this.porMonomio(mono));
            }
        }
        nuevo.simplifica();
        return nuevo;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Polinomio){
            Polinomio p = (Polinomio) o;
            return lista.equals(p.lista);
        }else{
            return false;
        }
    }
}
