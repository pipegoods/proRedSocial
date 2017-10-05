package logica;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase Mensaje (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class Mensaje implements java.io.Serializable{
    /**
     * Variable protegida: Perfil autor
     */
    protected final Perfil autor;
    /**
     * Variable protegida: Texto del mensaje
     */
    protected final String texto;
    /**
     * Variable protegida: id del mensaje
     */
    protected final long id;

    /**
     * Constructor de la clase
     * @param autor Perfil del autor del mensaje
     * @param texto String con el contenido del mensaje
     * @param id long del id del mensaje
    */
    public Mensaje(Perfil autor, String texto, long id) {
        this.autor = autor;
        this.texto = texto;
        this.id = id;
    }
    /**
     * Método que retorna el contenido del mensaje en un StringProperty
     * @return texto Un StringProperty con el contenido del mensaje
     */
    public StringProperty getTextoProperty(){
        return new SimpleStringProperty(texto);
    }
    /**
     * Método que retorna el autor del mensaje
     * @return autor Un objeto Perfil con el autor del mensaje
     */
    public Perfil getAutor() {
        return autor;
    }
    /**
     * Método que retorna el contenido del mensaje en un String
     * @return texto Un objeto String con el contenido del mensaje
     */
    public String getTexto() {
        return texto;
    }
    /**
     * Método que retorna el id del mensaje
     * @return id Un long con el id del mensaje
     */
    public long getId() {
        return id;
    }
}
