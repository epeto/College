package ast.patron.visitante;
import ast.patron.compuesto.*;

public interface Visitor {

    //Aritmeticas
    public void visit(AddNodo n);
    public void visit(AsigNodo n);
    public void visit(DifNodo n);
    public void visit(DivNodo n);    
    public void visit(DivisionEnteraNodo n);
    public void visit(ModuloNodo n);
    public void visit(PorNodo n);
    public void visit(PowNodo n);
    
    //Lógicas
    public void visit(AndNodo n);
    public void visit(OrNodo n);
    public void visit(DiferenteNodo n);
    public void visit(IgualIgualNodo n);
    public void visit(MenorIgualNodo n);
    public void visit(MayorIgualNodo n);
    public void visit(MenorNodo n);
    public void visit(MayorNodo n);
    public void visit(NotNodo n);
    
    //Hojas
    public void visit(IdentifierHoja n);
    public void visit(IntHoja n);
    public void visit(RealHoja n);
    public void visit(CadenaHoja n);
    public void visit(BooleanHoja n);
    
    public void visit(Nodo n);
    //public void visit(NodoBinario n);
    public void visit(NodoStmts n);
    public void visit(IfStmts n);
    public void visit(IfNodo n);
    public void visit(NodoPrint n);
    public void visit(WhileNodo n);
}
