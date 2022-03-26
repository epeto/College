package ast.patron.visitante;
import ast.patron.compuesto.*;
import java.util.LinkedList;
import java.util.Iterator;

public class VisitorPrint implements Visitor
{
    public void visit(AddNodo n){
        System.out.println("[+]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");
    }
    public void visit(AsigNodo n){
        System.out.println("[=]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");
 

    }
    
    public void visit(AndNodo n){
        System.out.println("[and]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]"); 
    }
    public void visit(Compuesto n){
        for (Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            System.out.print("[");
            if ( hijo != null){
                hijo.accept(this);    
            }    
            System.out.println("]");
        }
    }
    public void visit(DifNodo n){
        System.out.println("[-]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]"); 
    }
    public void visit(DiferenteNodo n){
        System.out.println("[!=]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(DivNodo n){
        System.out.println("[/]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");   
    }
    public void visit(DivisionEnteraNodo n){
        System.out.println("[//]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(IfStmts n){
        System.out.println("[if]");
        for (Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            System.out.print("[");
            if ( hijo != null){
                hijo.accept(this);    
            }    
            System.out.println("]");
        }
    }
    public void visit(IgualIgualNodo n){
        System.out.println("[==]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(MayorIgualNodo n){
        System.out.println("[>=]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(MenorIgualNodo n){
        System.out.println("[<=]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");
    }
    public void visit(MayorNodo n){
        System.out.println("[>]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(MenorNodo n){
        System.out.println("[<]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(ModuloNodo n){
        System.out.println("[%]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");   
    }
    public void visit(OrNodo n){
        System.out.println("[or]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");   
    }
    public void visit(PorNodo n){
        System.out.println("[*]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]"); 
    }
    public void visit(PowNodo n){
        System.out.println("[**]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");  
    }
    public void visit(WhileNodo n){
        System.out.println("[while]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");   
    }
    public void visit(NodoPrint n){
        System.out.println("[print]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");  
    }
    public void visit(NodoStmts n){
        for (Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
            Nodo hijo = (Nodo) i.next();
            System.out.print("[");
            if ( hijo != null){
                hijo.accept(this);    
            }    
            System.out.println("]");
        }
    }
    
    public void visit(NotNodo n){
        System.out.println("[not]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]"); 
    }
    public void visit(IdentifierHoja n){
	System.out.print("[Hoja Identificador] id: "+ n.getNombre());
    }
    public void visit(IntHoja n){
	System.out.print("[Hoja Entera] valor: " + n.getValor().ival);
    }
    public void visit(FloatHoja n){
        System.out.print("[Hoja Real] valor: " + n.getValor().dval);
    }
    public void visit(BooleanHoja n){
        System.out.print("[Hoja Booleano] valor: " + n.getValor().bval);
    }
    public void visit(StringHoja n){
        System.out.print("[Hoja Cadena] valor: " + n.getValor().sval);
    }
    public void visit(Nodo n){
        if(n.getHijos()!=null){
           for (Iterator i = n.getHijos().iterator(); i.hasNext(); ) {
                Nodo hijo = (Nodo) i.next();
                System.out.print("[");
                if ( hijo != null){
                    hijo.accept(this);    
                }    
                System.out.println("]");
            }
        }
    }
    public void visit(NodoBinario n){
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getUltimoHijo() != null){
            n.getUltimoHijo().accept(this);
        }          
        System.out.println("]");     
    }
    
    public void visit(IfNodo n){
        System.out.print("[if]");
        System.out.print("[");
        if(n.getPrimerHijo() != null){
            n.getPrimerHijo().accept(this);
        }        
        System.out.print("]");
        System.out.print("[");
        if( n.getHijos().getAll().get(1) != null){
            n.getHijos().getAll().get(1).accept(this);
        }         
        System.out.println("]"); 
        if(n.getHijos().size() == 3){
            System.out.print("[else]");
            System.out.print("[");
            if( n.getHijos().getAll().get(2) != null){
                n.getHijos().getAll().get(2).accept(this);
            }          
            System.out.println("]"); 
        }
    }
}
