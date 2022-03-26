/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.visualización.graficas;

import ed.estructuras.nolineales.Nodo;
import ed.estructuras.nolineales.NodoBinario;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author blackzafiro
 */
public class IGUNodoÁrbol extends Pane {
	public static final double RADIO = 20;
	private static final Stop[] altos = { new Stop(0, Color.WHITE), new Stop(1, Color.CADETBLUE)};
	private static final Stop[] altosSeleccionado = { new Stop(0, Color.WHITE), new Stop(1, Color.BLUEVIOLET)};
	private static final RadialGradient pintura =
			new RadialGradient(
					45,          // focusAngle,
                    0.5,         // focusDistance,
                    0,           // centerX,
                    0,           // centerY,
                    1.0,         // radius,
                    true,        // proportional,
                    CycleMethod.NO_CYCLE,
                    altos
			);
	private static final RadialGradient pinturaSeleccionado =
			new RadialGradient(
					45,          // focusAngle,
                    0.5,         // focusDistance,
                    0,           // centerX,
                    0,           // centerY,
                    1.0,         // radius,
                    true,        // proportional,
                    CycleMethod.NO_CYCLE,
                    altosSeleccionado
			);
	private static final Color lineColor = Color.FIREBRICK.deriveColor(0, 1, 1, .6);
	
	/**
	 * Identificador único para este nodo.
	 */
	private NodoBinario<?> nodo;
	
	private final Circle círculo;
	private final Text   texto;
	private int    h;
	
	public IGUNodoÁrbol(Nodo dato) {
		círculo = new Circle(RADIO, pintura);
		
		texto = new Text(dato.getElemento().toString() + 
				         "\nh="+ dato.getAltura());
		texto.setTextOrigin(VPos.CENTER);
		texto.setTextAlignment(TextAlignment.CENTER);
		texto.setTranslateX(-RADIO/2);
		
		this.getChildren().addAll(new Group(círculo), texto);
	}
	
	int h() { return h; }
	void h(int h) { this.h = h; }
}
