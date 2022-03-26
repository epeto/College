/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.visualización.graficas;

import ed.estructuras.nolineales.NodoBinario;
import ed.estructuras.nolineales.ÁrbolBinario;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 *
 * @author blackzafiro
 */
public class IGUÁrbol extends Group {
	
	protected final double DISTANCIA_X = IGUNodoÁrbol.RADIO * 2.0;
	protected final double DISTANCIA_Y = IGUNodoÁrbol.RADIO * 3;
	
	protected static final Label etiquetaVacío = new Label("Árbol vacío");
	static {
		etiquetaVacío.getStyleClass().add("dark");
		etiquetaVacío.setTranslateX(50);
		etiquetaVacío.setTranslateY(30);
	}
	
	protected final Pane panelBase = new Pane();
	
	protected final ÁrbolBinario<?> árbol;
	
	
	public IGUÁrbol(ÁrbolBinario<?> árbol) {
		this.getChildren().add(panelBase);
		
		this.árbol = árbol;
		if (árbol.isEmpty()) {
			panelBase.getChildren().add(etiquetaVacío);
			return;
		}
		IGUNodoÁrbol raíz = armaÁrbol(panelBase, árbol.getRaíz());
		double traslación = DISTANCIA_X * Math.pow(2, raíz.h()) * 1.5;
		raíz.setTranslateX(traslación);
	}
	
	/**
	 * Devuelve el nodo que acaba de crear.
	 * @param padre
	 * @param nodo
	 * @return 
	 */
	private IGUNodoÁrbol armaÁrbol(Pane padre, NodoBinario<?> nodo) {
		IGUNodoÁrbol iguNodo = new IGUNodoÁrbol(nodo);
		iguNodo.setTranslateY(DISTANCIA_Y);
		padre.getChildren().add(iguNodo);
		if (nodo.getHijoI() == null && nodo.getHijoD() == null) {
			iguNodo.h(0);
		} else {
			int hmax = 0;
			if (nodo.getHijoI() != null) {
				IGUNodoÁrbol izq = armaÁrbol(iguNodo, nodo.getHijoI());
				izq.setTranslateX(-DISTANCIA_X * Math.pow(2, izq.h()) * 1.5);
				Line l = new Line(0, 0,
								  izq.getTranslateX(),
								  izq.getTranslateY());
				l.getStyleClass().add("arista");
				iguNodo.getChildren().add(0, l);
				hmax = izq.h();
			}
			if (nodo.getHijoD() != null) {
				IGUNodoÁrbol der = armaÁrbol(iguNodo, nodo.getHijoD());
				der.setTranslateX(DISTANCIA_X * Math.pow(2, der.h()) * 1.5);
				Line l = new Line(0, 0,
								  der.getTranslateX(),
								  der.getTranslateY());
				l.getStyleClass().add("arista");
				iguNodo.getChildren().add(0, l);
				hmax = (hmax < der.h()) ? der.h() : hmax;
			}
			iguNodo.h(hmax + 1);
		}
		return iguNodo;
	}
}
