package logica;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase Panel (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class Panel implements java.io.Serializable{
    /**
     * Variable protegida: Perfil propietario del panel 
     */
    protected final Perfil propietario;
    /**
     * Variable protegida: lista de mensajes del panel tipo Mensaje
     */
    protected ArrayList<Mensaje> mensajes;
    /**
     * Variable protegida: lista de filtros del panel tipo String
     */
    protected ArrayList<String> filtro;
    
    /**
     * Constructor de la clase
     * @param propietario  Perfil del dueño del panel
    */
    public Panel(Perfil propietario) {
        this.propietario = propietario;
        this.mensajes = new ArrayList<>();
        this.filtro = new ArrayList<>();
    }
    /**
     * Método void, publica un mensaje en el panel
     * @param texto contenido del mensaje
     * @param  a Perfil autor del mensaje
     */
    public void publicarMensaje(String texto, Perfil a) {
        long index = this.mensajes.size();
        index++;
        this.mensajes.add(new Mensaje(a, texto,index));
    }
    /**
     * Método void, publica un mensaje que responde a otro en el panel
     * @param texto contenido del mensaje
     * @param a Perfil autor del mensaje
     * @param idR id del mensaje a responder
     */
    public void publicarMensajeRes(String texto, Perfil a, int idR){
        long index = this.mensajes.size();
        index++;
        this.mensajes.add(new MensajeRespuesta(a, texto, index, mensajes.get(idR-1)));
    }
    /**
     * Método que retorna la lista de mensajes en el panel
     * @return retorna Una lista de tipo <b>Mensaje</b>
     */
    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }
    /**
     * Método que retorna la lista de filtros del panel
     * @return retorna Una lista de tipo <b>String</b>
     */
    public ArrayList<String> getFiltro() {
        return filtro;
    }
    /**
     * Método void, agrega un filtro al panel
     * @param filtro String a agregar
     */
    public void agregarFiltro(String filtro) {
        this.filtro.add(filtro);
    }
    /**
     * Método void, eliminar un filtro al panel
     * @param filtro String a eliminar
     */
    public void eliminarFiltro(String filtro){
        this.filtro.remove(filtro);
    }
    /**
     * Método que retorna el propietario del panel
     * @return retorna Un objeto de <b>Perfil</b>
     */
    public Perfil getPropietario() {
        return propietario;
    }
    
    /**
     * Método cargar mensajes
     * @return retorna un String con todos los mensajes
     */
    public String cargarMensajesChat() {
        String chat = "";
        for (Mensaje actual : this.mensajes) {
            if (actual instanceof MensajeRespuesta) {
                chat += "#" + actual.getId() + " " + actual.getAutor().getIdentificador() + " dice: \n" + "@"
                        + ((MensajeRespuesta) actual).getmRespuesta().getAutor().getIdentificador() + " #"
                        + ((MensajeRespuesta) actual).getmRespuesta().getId() + "  " + actual.getTexto() + "\n";
            } else {
                chat += "#" + actual.getId() + " - " + actual.getAutor().getIdentificador() + " dice: \n" + actual.getTexto() + "\n";
            }
        }
        return chat;
    }
    /**
     * Método que retorna la informacion basica del panel
     * @return retorna Un objeto de <b>StringProperty</b>
     */
    public StringProperty toStringProperty() {
        return new SimpleStringProperty("Panel Basico " + propietario.getIdentificador());
    }
}
