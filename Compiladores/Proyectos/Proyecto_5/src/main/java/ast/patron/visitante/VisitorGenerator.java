/**
 * Se utilizará el registro $v0 para guardar el resultado de cada operación.
 */
package ast.patron.visitante;

import ast.patron.compuesto.*;
import ast.patron.registros.Registros;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VisitorGenerator implements Visitor {
    Registros reg = new Registros();
    String data = ""; //Todo lo relacionado con declaración de variables
    String text = ""; //Todo lo relacionado con código ejecutable
    int numCadena = 1; //Cuenta cuántas cadenas se han leído
    int numIf = 0; //Cuenta el número de ifs
    int numWhile = 0; //Cuenta el número de whiles
    HashMap<String, Integer> tabla_tipos;
    
    //Constructor
    public VisitorGenerator(VisitorType vt){
        tabla_tipos = vt.tabla_de_tipos;
        data+="\nmtrue: .asciiz \"True\"";
        data+="\nmfalse: .asciiz \"False\"";
        data+="\nsalto: .asciiz \"\\n\"";
    }
    
    @Override
    public void visit(DifNodo n) {
        text+="\n#Resta";
        String objetivo = reg.getObjetivoString();
        String opcode = "sub ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(AddNodo n) {
        text+="\n#Suma";
        String objetivo = reg.getObjetivoString();
        String opcode = "add ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(AsigNodo n) {
        text+="\n#Asignación";
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();

        String var = hi.getNombre();
        hd.accept(this);
        text+="\n    sw $v0, "+var;
    }

    @Override
    public void visit(DivNodo n) {
        text+="\n#División";
        String objetivo = reg.getObjetivoString();
        String opcode = "div ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(DivisionEnteraNodo n) {
        text+="\n#División";
        String objetivo = reg.getObjetivoString();
        String opcode = "div ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(ModuloNodo n) {
        text+="\n#Modulo";
        String objetivo = reg.getObjetivoString();
        String opcode = "rem ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(PorNodo n) {
        text+="\n#Multiplicación";
        String objetivo = reg.getObjetivoString();
        String opcode = "mul ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(PowNodo n) {
        text+="\n#Exponente";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal pow";
        }
    }

    @Override
    public void visit(AndNodo n) {
        text+="\n#And";
        String objetivo = reg.getObjetivoString();
        String opcode = "and ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(OrNodo n) {
        text+="\n#Or";
        String objetivo = reg.getObjetivoString();
        String opcode = "or ";
        String[] siguientes = reg.getNSiguientes(2);
        reg.siguiente();
        reg.siguiente();
        reg.siguiente();
        Nodo hi = n.getPrimerHijo();
        Nodo hd = n.getUltimoHijo();
        
        hi.accept(this);
        text+="\n    move "+siguientes[0]+", $v0";
        text+="\n    subi $sp, $sp, 4";
        text+="\n    sw "+siguientes[0]+", ($sp)";
        
        hd.accept(this);
        text+="\n    move "+siguientes[1]+", $v0";
        text+="\n    lw "+siguientes[0]+", ($sp)";
        text+="\n    addi $sp, $sp, 4";
        text+="\n    "+opcode+objetivo+", "+siguientes[0]+", "+siguientes[1];
        text+="\n    move $v0, "+objetivo;
    }

    @Override
    public void visit(DiferenteNodo n) {
        text+="\n#Diferente";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "diferente";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(IgualIgualNodo n) {
        text+="\n#Igualigual";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "igualigual";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(MenorIgualNodo n) {
        text+="\n#Menor o igual";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "menorigual";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(MayorIgualNodo n) {
        text+="\n#Mayor o igual";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "mayorigual";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(MenorNodo n) {
        text+="\n#Menor";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "menor";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(MayorNodo n) {
        text+="\n#Mayor";
        if(n.getPrimerHijo() != null && n.getUltimoHijo() != null) {
            String compa = "mayor";
            Nodo hi = n.getPrimerHijo();
            Nodo hd = n.getUltimoHijo();
            
            hi.accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    subi $sp, $sp, 4";
            text+="\n    sw $a0, ($sp)";
            
            hd.accept(this);
            text+="\n    move $a1, $v0";
            text+="\n    lw $a0, ($sp)";
            text+="\n    addi $sp, $sp, 4";
            text+="\n    jal "+compa;
        }
    }

    @Override
    public void visit(NotNodo n) {
        text+="\n#Exponente";
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
            text+="\n    move $a0, $v0";
            text+="\n    jal negacion";
        }
    }

    @Override
    public void visit(IdentifierHoja n) {
        text+="\n    lw $v0, "+n.getNombre();
    }

    @Override
    public void visit(IntHoja n) {
        text+="\n    li $v0, "+n.getValor().ival;
    }

    @Override
    public void visit(RealHoja n) {
        throw new UnsupportedOperationException("Float no soportado.");
    }

    @Override
    public void visit(CadenaHoja n) {
        data+="\ncadena"+numCadena+": .asciiz "+n.getValor().sval;
        text+="\n    la $v0, "+"cadena"+numCadena;
        numCadena++;
    }

    @Override
    public void visit(BooleanHoja n) {
        boolean valor = n.getValor().bval;
        if(valor){
            text+="\n    li $v0, 1"; //1 es true
        }else{
            text+="\n    li $v0, 0"; //0 es false
        }
    }

    @Override
    public void visit(Nodo n) {
        for (Iterator i = n.getHijos().iterator(); i.hasNext();) {
            Nodo hijo = (Nodo) i.next();
            if( hijo != null) {
                hijo.accept(this);    
            }    
        }
    }

    @Override
    public void visit(NodoStmts n) {
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); )  {
            Nodo hijo =(Nodo) i.next();
            if(hijo != null) {
                hijo.accept(this);    
            }    
        }
    }

    @Override
    public void visit(IfStmts n) {
        for(Iterator i = n.getHijos().iterator(); i.hasNext(); )  {
            Nodo hijo =(Nodo) i.next();
            if(hijo != null) {
                hijo.accept(this);
            }
        }
    }

    @Override
    public void visit(IfNodo n) {
        numIf++;
        text+="\nif"+numIf+":";
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
            text+="\n    bnez $v0, then"+numIf;
            text+="\n    beqz $v0, else"+numIf;
        }        
        text+="\nthen"+numIf+":";
        if(n.getHijos().getAll().get(1) != null) {
            n.getHijos().getAll().get(1).accept(this);
        }         
        text+="\nelse"+numIf+":";
        if(n.getHijos().size() == 3) {
            if(n.getHijos().getAll().get(2) != null) {
                n.getHijos().getAll().get(2).accept(this);
            }
        }
    }

    @Override
    public void visit(NodoPrint n) {
        text+="\n#Imprime";
        Nodo hijo = n.getPrimerHijo();
        String objetivo = reg.getObjetivoString();
        reg.siguiente();
        hijo.accept(this);
        int tipo = hijo.getType(); //Sacamos el tipo de dato del hijo
        text+="\n    move "+objetivo+", $v0";
        
        switch(tipo){
            case 1: text+="\n    li $v0, 1"; //Entero
                    text+="\n    move $a0, "+objetivo;
            break;
            case 2: text+="\n    li $v0, 2"; //Float
                    text+="\n    mov.s $f12, "+objetivo;
            break;
            case 3: text+="\n    li $v0, 4";
                    if(hijo.getValor().bval){ //Booleano
                        text+="\n    la $a0, mtrue";
                    }else{
                        text+="\n    la $a0, mfalse";
                    }
            break;
            case 4: text+="\n    li $v0, 4"; //Cadena
                    text+="\n    move $a0, "+objetivo;
            break;
            default: imprimeVariable(hijo.getNombre());
        }
        
        text+="\n    syscall";
        text+="\n    la $a0, salto";
        text+="\n    li $v0, 4";
        text+="\n    syscall";
    }

    @Override
    public void visit(WhileNodo n) {
        numWhile++;
        text+="\nwhile"+numWhile+":";
        if(n.getPrimerHijo() != null) {
            n.getPrimerHijo().accept(this);
        }
        text+="\n    beqz $v0, fin_while"+numWhile;
        text+="\ndo_while"+numWhile+":";
        if(n.getUltimoHijo() != null) {
            n.getUltimoHijo().accept(this);
        }
        text+="\n    b while"+numWhile;
        text+="\nfin_while"+numWhile+":";
    }
    
    /**
     * Servirá para declarar las variables.
     * @param hm Tabla de símbolos
     * @return 
     */
    public String variables(HashMap<String, Integer> hm){
        Set<String> vars = hm.keySet(); //conjunto de variables
        String ret = "";
        for(String s:vars){
            switch(hm.get(s)){
                case 1: ret+="\n"+s+": .word 0";//Entero
                break;
                case 2: ret+="\n"+s+": .float 0";//Real
                break;
                case 3: ret+="\n"+s+": .word 0";//Booleano
                break;
                case 4: ret+="\n"+s+": .word 0";//Cadena (guardará una dirección de memoria)
            }
        }
        return ret;
    }
    
        /**
     * Imprime y devuelve la subrutina asociada al comparador que recibe.
     * @param comp: comparador
     * == : igualigual
     * != : diferente
     * <  : menor
     * >  : mayor
     * <= : menorigual
     * >= : mayorigual
     * @return subrutina
     */
    public String rutina_comparador(String comp){
        String salto=""; //Se le asocia un salto diferente dependiendo del comparador
        
        switch(comp){
            case "igualigual": salto = "beq";
            break;
            case "diferente": salto = "bne";
            break;
            case "menor": salto = "blt";
            break;
            case "mayor": salto = "bgt";
            break;
            case "menorigual": salto = "ble";
            break;
            case "mayorigual": salto = "bge";
            break;
            default: salto = "b";
        }
        
        String ret="";
        ret+="\n"+ comp +":";
        ret+="\n    "+ salto +" $a0, $a1, "+ comp +"_true";
        ret+="\n    b "+ comp +"_false";
        ret+="\n"+ comp +"_true:";
        ret+="\n    li $v0, 1";
        ret+="\n    b fin_"+ comp +"";
        ret+="\n"+ comp +"_false:";
        ret+="\n    li $v0, 0";
        ret+="\nfin_"+ comp +":";
        ret+="\n    jr $ra";
        return ret;
    }
    
    private String rutina_pow(){
        String ret = "";
        ret+="\npow: #Función exponente $a0 elevado a la $a1";
        ret+="\n    subi $sp, $sp, 4";
        ret+="\n    sw $a1, ($sp)";
        ret+="\n    li $v0, 1";
        ret+="\n    beqz $a1, fin_pow #Si el exponente es 0, termina";
        ret+="\nloop_pow:";
        ret+="\n    mul $v0, $v0, $a0";
        ret+="\n    subi $a1, $a1, 1";
        ret+="\n    bgtz $a1, loop_pow";
        ret+="\nfin_pow:";
        ret+="\n    lw $a1, ($sp)";
        ret+="\n    addi $sp, $sp, 4";
        ret+="\n    jr $ra";
        return ret;
    }
    
    private String rutina_not(){
        String rut_not="";
        rut_not+="\nnegacion:";
        rut_not+="\n    beqz $a0, negacion_cero";
        rut_not+="\n    b negacion_uno";
        rut_not+="\nnegacion_cero:";
        rut_not+="\n    li $v0, 1";
        rut_not+="\n    b fin_negacion";
        rut_not+="\nnegacion_uno:";
        rut_not+="\n    li $v0, 0";
        rut_not+="\nfin_negacion:";
        rut_not+="\n    jr $ra";
        return rut_not;
    }
    
    //Función para manejar identificadores en la función print
    private void imprimeVariable(String var){
        int tipo = tabla_tipos.get(var);
        
        switch(tipo){
            case 1: text+="\n    li $v0, 1"; //Entero
                    text+="\n    lw $a0, "+var;
            break;
            case 3: text+="\n    li $v0, 4"; //Booleano
                    text+="\n    lw $v1, "+var;
                    text+="\n    la $t0, mfalse";
                    text+="\n    la $t1, mtrue";
                    text+="\n    movz $a0, $t0, $v1";
                    text+="\n    movn $a0, $t1, $v1";
            break;
            case 4: text+="\n    li $v0, 4"; //Cadena
                    text+="\n    lw $a0, "+var;
            break;
        }
    }
    
    public void escribeCodigo(){
        data+=variables(tabla_tipos);
        text+="\n    li $v0, 10\n    syscall\n"; //Código para terminar con la ejecución
        text+="\n# Subrutinas ############################################################################";
        try{
            FileWriter fw1 = new FileWriter("salida.asm");
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write(".data");
            bw1.newLine();
            bw1.write(data);
            bw1.newLine();
            bw1.write("\n.text");
            bw1.newLine();
            bw1.write(text);
            bw1.newLine();
            //Escribimos al final del documento las rutinas de los comparadores
            bw1.write(rutina_comparador("igualigual"));
            bw1.newLine();
            bw1.write(rutina_comparador("diferente"));
            bw1.newLine();
            bw1.write(rutina_comparador("menor"));
            bw1.newLine();
            bw1.write(rutina_comparador("mayor"));
            bw1.newLine();
            bw1.write(rutina_comparador("menorigual"));
            bw1.newLine();
            bw1.write(rutina_comparador("mayorigual"));
            bw1.newLine();
            bw1.write(rutina_not());
            bw1.newLine();
            bw1.write(rutina_pow());
            
            bw1.close();
            
        }catch(Exception ex){}
    }
}