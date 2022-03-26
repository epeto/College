package lexico;
import java.io.*;

public class AnalizadorLexico {
    Alexico lexer;
    public static String nom_archivo;

    public AnalizadorLexico(String archivo){
        try {
            nom_archivo = archivo;
            Reader lector = new FileReader("src/main/resources/"+nom_archivo+".py");
            lexer = new Alexico(lector);
        }
        catch(FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " No se encontró el archivo;");
        }
    }

    public void analiza(){
        try{
          lexer.yylex();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
