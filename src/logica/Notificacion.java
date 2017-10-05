package logica;

/**
 * Clase Notificacion (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class Notificacion implements java.io.Serializable{
    /**
     * Variable privada: Mensaje que se notifica
     */
    private final Mensaje mensaje;
    /**
     * Variable privada: Panel donde se envió el mensaje
     */
    private final Panel panel;
    
    /**
     * Constructor de la clase
     * @param mensaje Mensaje que se esta notificando
     * @param panel Panel donde se envio el mensaje
    */
    public Notificacion(Mensaje mensaje, Panel panel) {
        this.mensaje = mensaje;
        this.panel = panel;
    }
    /**
     * Método que retorna el mensaje
     * @return mensaje Un objeto Mensaje con el mensaje
     */
    public Mensaje getMensaje() {
        return mensaje;
    }
    /**
     * Método que retorna el panel
     * @return panel Un objeto Panel con el panel donde se envio el mensaje
     */
    public Panel getPanel() {
        return panel;
    }
}
