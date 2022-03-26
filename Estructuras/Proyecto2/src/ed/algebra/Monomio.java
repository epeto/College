
package ed.algebra;

public class Monomio{
    private double coeficiente;
    private int potencia;

    // Constructor
    public Monomio(double coef, int pot){
        coeficiente = coef;
        potencia = pot;
    }

    /**
     * Método que suma a este polinomio con otro.
     * @param otro monomio.
     * @return this + otro
     */
    public Monomio más(Monomio otro){
        if(otro.p() != potencia){
            throw new IllegalArgumentException("Los monomios deben tener el mismo exponente");
        }
        return new Monomio(coeficiente + otro.c(), potencia);
    }

    /**
     * Multiplica este monomio por otro.
     * @param otro monomio
     * @return this * otro
     */
    public Monomio por(Monomio otro){
        return new Monomio(coeficiente * otro.c(), potencia + otro.p());
    }

    /**
     * Devuelve al coeficiente de este monomio.
     * @return coeficiente
     */
    public double c(){
        return coeficiente;
    }

    /**
     * Devuelve la potencia de este monomio.
     * @return potencia
     */
    public int p(){
        return potencia;
    }

    @Override
    public String toString(){
        String cadena = "";
        if(potencia == 0){
            cadena = String.valueOf(coeficiente);
        }else if(potencia == 1){
            cadena = coeficiente + "x";
        }else{
            cadena = coeficiente + "x^" + potencia;
        }
        return cadena;
    }

    @Override
    protected Monomio clone(){
        return new Monomio(coeficiente, potencia);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Monomio){
            Monomio m = (Monomio) o;
            if(m.p() == p() && m.c() == c()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
