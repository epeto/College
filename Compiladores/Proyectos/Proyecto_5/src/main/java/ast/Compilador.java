package ast;

import java.io.*;
import ast.patron.compuesto.*;
import ast.patron.visitante.*;

public class Compilador {

    Parser parser;
    Nodo raízAST;
    VisitorPrint v_print;
    VisitorGenerator v_generate;
    VisitorType v_type;

    Compilador(Reader fuente) {
        parser  = new Parser(fuente);
        v_print = new VisitorPrint();
        v_type  = new VisitorType();
        v_generate = new VisitorGenerator(v_type);
    }

    public void ConstruyeAST(boolean debug) {
        parser.yydebug = debug;
        parser.yyparse(); // análisis léxico, sintáctio y constucción del AST
        raízAST = parser.raiz;
    }

    public void imprimeAST() {
        parser.raiz.accept(v_print);
    }

    public void verificaTiposAST() {
        parser.raiz.accept(v_type);
    }

    private void genera() {
        parser.raiz.accept(v_generate);
    }

    public static void main(String[] args) {
            int numTest = 4; //Cambiar para una prueba diferente
            String archivo = "src/main/resources/test"+numTest+".p";
        try{
            Reader a = new FileReader(archivo);
            Compilador c  = new Compilador(a);
            c.ConstruyeAST(true);
            c.imprimeAST();
            
            try{
                c.verificaTiposAST();
            }catch(Exception e){}
            
            c.genera(); //Genera el archivo en ensamblador
            c.v_generate.escribeCodigo();
        }catch(FileNotFoundException e) {
            System.err.println("El archivo " + archivo +" no fue encontrado. ");
        }catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println("Uso: java Compilador [archivo.p]: ");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
