/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.patron.compuesto;


import ast.patron.visitante.Visitor;

public class RealHoja extends Hoja {
    public RealHoja(double i){
	valor = new Variable(i);
	tipo = 2;
    }

    @Override
    public void accept(Visitor v){
     	v.visit(this);
    }
}
