/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o
 * potenciales pues se afectará su realización de los ejercicios.
 */
package laberinto;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Clase que controla la lógica de la aplicación.
 * 
 * Las funciones definidas aquí son mandadas llamar desde la interfaz gráfica.
 * @author blackzafiro
 */
public class Control {
	
	/// Controles
	
	@FXML
	private TextField txtCols;
	@FXML
	private TextField txtRens;
	
	@FXML
	private StackPane panelFondo;
	
	private Laberinto laberinto;
	private Laberinto.Agente agente;
	
	/// Variables de estado
	
	private int cols;
	private int rens;
	
	/**
	 * Lee el número de columnas indicado en la interfaz.
	 */
	public void nuevoNumCols() {
		try {
			cols = Integer.parseInt(txtCols.getText());
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Introduzca un número positivo");
			alert.showAndWait();
		}
	}
	
	/**
	 * Lee el número de renglones indicado en la interfaz.
	 */
	public void nuevoNumRens() {
		try {
			rens = Integer.parseInt(txtRens.getText());
		} catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Introduzca un número positivo");
			alert.showAndWait();
		}
	}
	
	/**
	 * Genera un laberinto según el número de columnas y renglones
	 * indicados.
	 */
	public void generaLaberinto() {
		if (cols <= 0 || rens <= 0) {
			nuevoNumCols();
			nuevoNumRens();
		}
		txtCols.setText("" + cols);
		txtRens.setText("" + rens);
		/*Alert alert = new Alert(AlertType.INFORMATION, "Generando laberinto con " + cols + " cols y " + rens + " rens.");
		alert.showAndWait();*/
		
		panelFondo.getChildren().clear();
		laberinto = new Laberinto(panelFondo, rens, cols);
		panelFondo.getChildren().add(laberinto);
		
		agente = laberinto.agente();
		
		panelFondo.getChildren().add(agente);
	}
	
	/**
	 * Mueve al agente de celda en celda según el algoritmo
	 * de retractación.
	 * @return indica si el agente ya logró llegar a la meta o si terminó de
	 *         visitar todas las direcciones accesibles desde su posición
	 *         actual y no encontró la meta.
	 */
	public boolean resuelveLaberinto() {
		agente.marcaCelda();
		// Caso base: está parado sobre la meta.
		if(agente.estáEnMeta()){
			agente.ejecutaAnimación();
			return true;
		}
		// Caso recursivo: está en algún otro mosaico.
		for(Dirección d : agente.direccionesPasillos ()){
			if(agente.miraSiNoVisitada(d)){
				agente.avanza(d);
				boolean encontrado = resuelveLaberinto();
				if(encontrado){
					return true;
				}
				agente.avanza(d.opuesta());
			}
		}
		agente.desmarcaCelda();
		return false;
	}
	
	/**
	 * Regresa al agente a su posición inicial.
	 */
	public void reiniciaAgente() {
		agente.reinicia();
	}
	
	private double ratonXIni;
	private double ratonXFin;
	private double ratonYIni;
	private double ratonYFin;
	/**
	 * Método auxiliar para probar al agente moviéndolo con el ratón.
	 * @param e Información sobre el movimiento del ratón.
	 */
	public void mueveAgente(MouseEvent e) {
		if (agente != null) {
			if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
				ratonXIni = e.getX();
				ratonYIni = e.getY();
			} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
				Dirección dirección;
				ratonXFin = e.getX();
				ratonYFin = e.getY();
				double difX = ratonXFin - ratonXIni;
				double difY = ratonYFin - ratonYIni;
				if(Math.abs(difX) >= Math.abs(difY)) {
					dirección = (difX >= 0) ? Dirección.ESTE : Dirección.OESTE;
				} else {
					dirección = (difY >= 0) ? Dirección.SUR : Dirección.NORTE;
				}
				agente.avanza(dirección);
			}
		}
	}
}
