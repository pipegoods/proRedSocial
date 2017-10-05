package logica;

/**
 * Clase MensajeRespuesta - subclase de Mensaje (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class MensajeRespuesta extends Mensaje {
    /**
     * Variable privada: Mensaje mRespuesta
     */
    private final Mensaje mRespuesta;
    
    /**
     * Constructor de la clase
     * @param autor Perfil del autor del mensaje
     * @param texto String con el contenido del mensaje
     * @param id long del id del mensaje
     * @param m Mensaje a que se hace respuesta
    */
    public MensajeRespuesta(Perfil autor, String texto, long id, Mensaje m) {
        super(autor, texto, id);
        this.mRespuesta = m;
    }
    /**
     * Método que retorna el contenido del mensaje en un StringProperty
     * @return retorna Un objeto <b>Mensaje</b> con el mensaje que se responde
     */
    public Mensaje getmRespuesta() {
        return mRespuesta;
    }
}
