package ast.patron.compuesto;
import ast.patron.visitante.*;

public class IdentifierHoja extends Hoja
{

    public IdentifierHoja(String id){
	name = id;
    }

    public void accept(Visitor v){
	v.visit(this);
    }
}
