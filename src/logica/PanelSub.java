package logica;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase PanelSub - subclase de Panel (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class PanelSub extends Panel {
    /**
     * Variable privada: lista de asociados tipo Perfil 
     */
    private ArrayList<Perfil> asociados;
    /**
     * Variable privada: Tema de interes
     */
    private String temaInteres;
    
    /**
     * Constructor de la clase
     * @param l Perfil del propietario del panel de subcripcion
     * @param tema tema de interes
    */
    public PanelSub(Perfil l, String tema) {
        super(l);
        this.asociados = new ArrayList<>();
        this.temaInteres = tema;
    }
    /**
     * Método void, agrega un colaborador al panel de subcripcion
     * @param  p Perfil asociado a agregar
     */    
    public void agregarColaborador(Perfil p) {
        this.asociados.add(p);
    }
    /**
     * Método void, eliminar un colaborador al panel de subcripcion
     * @param  a Perfil asociado a eliminar
     */   
    public void eliminarColaborador(Perfil a) {
        this.asociados.remove(a);
    }
    /**
     * Método que retorna la lista de asociados en el panel de subcripcion
     * @return asociados Una lista de tipo Perfil
     */
    public ArrayList<Perfil> getAsociados() {
        return asociados;
    }
    /**
     * Método que retorna el tema de interes
     * @return temaInteres Un Srting del tema de interes
     */
    public String getTemaInteres() {
        return temaInteres;
    }
    /**
     * Método que retorna la informacion basica del panel
     * @return Un objeto de StringProperty
     */
    @Override
    public StringProperty toStringProperty() {
        return new SimpleStringProperty("PanelSus: " + temaInteres);
    }
}
