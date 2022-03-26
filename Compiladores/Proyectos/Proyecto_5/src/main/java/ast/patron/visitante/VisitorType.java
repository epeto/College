/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.patron.visitante;

import ast.patron.compuesto.*;
import ast.patron.sistema_de_tipos.SistemaDeTipos;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 */
public class VisitorType implements Visitor {
    public HashMap<String, Integer> tabla_de_tipos;
    public VisitorType() {
        tabla_de_tipos = new HashMap();
    }
    
    private boolean estaDeclarada(String nombre) {
        if(nombre != null) {
            if(nombre.equals("true") || nombre.equals("false") || this.tabla_de_tipos.containsKey(nombre)) {
                return true;
            } else {
                System.out.println("Variable " + nombre + " no declarada");
            }
        }
        return false;
    }
    
    private void Incompatibilidad(String operador, int tipo_izq, int tipo_der) {
        System.out.println("Incompatibilidad de tipos para el operador " + operador + " [" + SistemaDeTipos.getTipo(tipo_izq) + " , " + SistemaDeTipos.getTipo(tipo_der) + "]" );
    }
    
    @Override
    public void visit(AddNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }        
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.SUMA[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'+'", tipo_izq, tipo_der);
        }
    }
   
    @Override
    public void visit(AsigNodo n) {
        String nombre_izq = "";
        String nombre_der = "";
        int tipo_der = 0;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            nombre_izq = n.getPrimerHijo().getNombre();
        }        
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }        
        if(this.estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        if(this.tabla_de_tipos.containsKey(nombre_izq)) {
            if(tipo_der != tabla_de_tipos.get(nombre_izq)) {
               System.out.println("la variable " + nombre_izq + " tiene 2 asignaciones de tipos primero se definio como : " + SistemaDeTipos.getTipo(this.tabla_de_tipos.get(nombre_der)) + " despues como : " + SistemaDeTipos.getTipo(tipo_der));
            }
        } else {
            this.tabla_de_tipos.put(nombre_izq , tipo_der);
        }
    }

    @Override
    public void visit(DifNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'-'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(DivNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }        
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'/'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(DivisionEnteraNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {      
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }        
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'//'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(ModuloNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'%'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(PorNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }          
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'*'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(PowNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_ARITMETICAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'**'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(AndNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {         
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'and'", tipo_izq, tipo_der);
        }
    }
    
    @Override
    public void visit(OrNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'or'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(DiferenteNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'!='", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(IfStmts n){
        System.out.println("[if]");
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            System.out.print("[");
            if( hijo != null){
                hijo.accept(this);    
            }    
            System.out.println("]");
        }
    }

    @Override
    public void visit(IgualIgualNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'=='", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(MenorIgualNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'<='", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(MayorIgualNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'>='", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(MenorNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'<'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(MayorNodo n) {
        int tipo_izq = 0;
        int tipo_der = 0;
        String nombre_izq = "";
        String nombre_der = "";
        int tipo;
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_izq = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_izq = this.tabla_de_tipos.get(nombre_izq);
        }
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
            tipo_der = n.getUltimoHijo().getType();
            nombre_der = n.getUltimoHijo().getNombre();
        }
        if(estaDeclarada(nombre_der)) {
            tipo_der = this.tabla_de_tipos.get(nombre_der);
        }
        tipo = SistemaDeTipos.OPERACIONES_BOOLEANAS[tipo_der-1][tipo_izq-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            this.Incompatibilidad("'>'", tipo_izq, tipo_der);
        }
    }

    @Override
    public void visit(NotNodo n) {
        int tipo= 0;
        String nombre = "";
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo = n.getPrimerHijo().getType();
            nombre = n.getPrimerHijo().getNombre();
        }             
        
        if(estaDeclarada(nombre)) {
            tipo = this.tabla_de_tipos.get(nombre);
        }
        
        tipo = SistemaDeTipos.NOT[tipo-1];
        if(tipo != 0) {
             n.setTipo(tipo);
        } else {
            System.out.println("Incompatibilidad de tipos para el operador 'not' " + "[" + SistemaDeTipos.getTipo(tipo) + "]" );
        }
    }

    @Override
    public void visit(IdentifierHoja n) {
    }

    @Override
    public void visit(IntHoja n) {
        ;
    }

    @Override
    public void visit(RealHoja n) {
        ;
    }

    @Override
    public void visit(CadenaHoja n) {
        ;
    }

    @Override
    public void visit(BooleanHoja n) {
        ;
    }

    @Override
    public void visit(Nodo n) {
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            if(hijo != null) {
                hijo.accept(this);    
            }    
        }
    }

    @Override
    public void visit(NodoStmts n) {
        String nombre = "";
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            if(hijo != null) {
                hijo.accept(this);    
                nombre = hijo.getNombre();
            }    
            estaDeclarada(nombre);
        }
    }

    @Override
    public void visit(IfNodo n) {
        int tipo_cond = 0;        
        String nombre_izq = "";
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_cond = n.getPrimerHijo().getType();
            nombre_izq = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_izq)) {
            tipo_cond = this.tabla_de_tipos.get(nombre_izq);
        }
        if(tipo_cond != 3) {
            ErrorCondicion("'if'");
        }
        if(n.getHijos().getAll().get(1) != null) {
            n.getHijos().getAll().get(1).accept(this);
        }        
        if(n.getHijos().size() == 3) {
            if(n.getHijos().getAll().get(2) != null) {
                n.getHijos().getAll().get(2).accept(this);
            } 
        }       
    }

    private void ErrorCondicion(String operador) {
        System.out.println("Error la condicion del " +  operador  + " debe de ser booleana");
    }

    @Override
    public void visit(NodoPrint n) {
        String nombre = "";
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
            nombre = n.getPrimerHijo().getNombre();
        }    
        estaDeclarada(nombre);
    }

    @Override
    public void visit(WhileNodo n) {
        int tipo_cond = 0;
        String nombre_cond = "";
        if(n.getPrimerHijo() != null) {           
            n.getPrimerHijo().accept(this);
            tipo_cond = n.getPrimerHijo().getType();
            nombre_cond = n.getPrimerHijo().getNombre();
        }
        if(estaDeclarada(nombre_cond)) {
            tipo_cond = this.tabla_de_tipos.get(nombre_cond);
        }
        if(tipo_cond != 3) {
            ErrorCondicion("'while'");
        }  
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
        }       
    }  
}