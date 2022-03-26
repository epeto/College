package lexico;

public class Test {

    public static void main (String[] args){
        AnalizadorLexico al1 = new AnalizadorLexico("fizzbuzz");
        al1.analiza();
        AnalizadorLexico al2 = new AnalizadorLexico("error_cadena");
        al2.analiza();
        AnalizadorLexico al3 = new AnalizadorLexico("error_identacion");
        al3.analiza();
        AnalizadorLexico al4 = new AnalizadorLexico("error_lexema");
        al4.analiza();
    }
}
