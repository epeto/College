/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos, pero no está permitido
 * entregarlo tal cual a los estudiantes.
 */
package ed.visualización;

import ed.visualización.demos.Demo;
import ed.visualización.demos.DemoÁrbolesBinariosOrdenados;
import ed.visualización.demos.MétodoDemo;
import ed.visualización.dibujantes.Dibujante;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Lógica para la aplicación que visualiza estructuras de datos.
 * @author blackzafiro
 */
public class FXMLController implements EventHandler<ActionEvent> {
	
	@FXML
	private Accordion acordeón;
	
	@FXML
	private BorderPane lienzo;
	
	@FXML
	private Label etqIzquierda;
	
	@FXML
	private TextArea errores;
	
	@FXML
	private Label etqDerecha;
	
	@FXML
	private SplitPane biPanel;
	
	protected static final Label etiquetaError = new Label("Error");
	static {
		etiquetaError.getStyleClass().add("dark-error");
		etiquetaError.setTranslateX(50);
		etiquetaError.setTranslateY(30);
	}
	
	
	private enum DemoOpciones {
		/*
		Binarios_ordenados,
		AVL,
		Rojinegros;
		*/
		Binarios_ordenados(new DemoÁrbolesBinariosOrdenados());
		
		private final Demo demo;
		
		DemoOpciones(Demo demo) {
			this.demo = demo;
		}
		
		Demo demo() { return demo; }
	}
	
	/**
	 * Pequeño contenedor con un método y su nombre.
	 */
	private class Método {
		private final DemoOpciones opciónDemo;
		private final Method método;
		private final String nombre;
		private final String ejercicio;
		private Dibujante dibujante = null;
		
		Método(DemoOpciones d, Method m) {
			MétodoDemo dm = m.getAnnotation(MétodoDemo.class);
			opciónDemo = d;
			método = m;
			nombre = dm.nombre();
			ejercicio = dm.ejercicio();
		}
		
		String nombre() {
			return nombre;
		}
		
		void invoca() {
			etqIzquierda.setText("Ejercicio: " + ejercicio);
			if (dibujante == null) {
				try {
					dibujante = (Dibujante)método.invoke(opciónDemo.demo());
					errores.setText("Ok");
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
					// ex.getCause().printStackTrace();
					etqDerecha.setText(ex.getCause().toString());
					
					StackTraceElement[] trace = ex.getCause().getStackTrace();
					String strOfErrors = Arrays
						.stream(trace)
						.map((a) -> a.toString())
						.reduce((a, b) -> a.concat("\n").concat(b))
						.get();
					
					errores.setStyle("-fx-text-fill: red;");
					errores.setText(strOfErrors);
					
					lienzo.setCenter(etiquetaError);
					return;
				}
			}
			
			Node graf = dibujante.gráfica();
			lienzo.setCenter(graf);
			errores.setText("Ok");
			errores.setStyle("-fx-text-fill: gainsboro;");
			//System.out.println("Bipanel " + biPanel);
			//biPanel.setDividerPositions(0.9);
		}
		
		@Override
		public String toString() {
			return nombre;
		}
	}
	
    
	/**
	 * Crea los menús con los demos disponibles y los agrega al
	 * acordeón.
	 */
    public void initialize() {
		
		for(DemoOpciones opciónDemo : DemoOpciones.values()) {
			VBox botones = new VBox();
			
			Method[] métodos = opciónDemo.demo().getClass().getDeclaredMethods();
            for(Method método : métodos) {
                if(método.isAnnotationPresent(MétodoDemo.class)) {
					Método m = new Método(opciónDemo, método);
					Button botón = new Button(m.nombre());
					botón.setUserData(m);
					botón.setOnAction(this);
					AnchorPane ancla = new AnchorPane();
					AnchorPane.setLeftAnchor(botón, 10.0);
					AnchorPane.setRightAnchor(botón, 10.0);
					ancla.getChildren().add(botón);
					botones.getChildren().add(ancla);
                }
            }
			
			ScrollPane pane = new ScrollPane();
			pane.setContent(botones);
			//pane.setMinViewportHeight(200);
			
			TitledPane tPane = new TitledPane(opciónDemo.toString(), pane);
			acordeón.getPanes().add(tPane);
		}
		
    }
	
	/**
	 * Invoca al método asociado con el botón.
	 * El método creará un árbol de nodos según el demo asociado y
	 * lo mostrará en el lienzo.
	 * @param t 
	 */
	@Override
	public void handle(ActionEvent t) {
		Button b = (Button)(t.getTarget());
		Método m = (Método)(b.getUserData());
		m.invoca();
	}
	
}
