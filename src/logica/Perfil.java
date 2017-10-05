package logica;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase Perfil (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class Perfil implements java.io.Serializable{
    /**
     * Variable privada: Usuario del perfil
     */
    private final String usuario;
    /**
     * Variable privada: Contraseña del perfil
     */
    private String contrasena;
    /**
     * Variable privada: Identificador del perfil
     */
    private final String identificador;
    /**
     * Variable privada: Panel basico del perfil
     */
    private final Panel panelM;
    /**
     * Variable privada: Lista de contactos tipo <b>Perfil</b>
     */
    private ArrayList<Perfil> contactos;
    /**
     * Variable privada: Lista de solicitudes enviadas tipo <b>Perfil</b>
     */
    private ArrayList<Perfil> solicitudesEnviadas;
    /** 
     * Variable privada: Lista de solicitudes recibidas tipo <b>Perfil</b>
     */
    private ArrayList<Perfil> solicitudesRecibir;
    /**
     * Variable privada: Lista de temas de interes tipo <b>String</b>
     */
    private ArrayList<String> tInteres;
    /**
     * Variable privada: Lista de notificaciones tipo <b>Notificacion</b>
     */
    private ArrayList<Notificacion> notificaciones;
    /**
     * Variable privada: Ruta de la imagen de perfil
     */
    private String img;

    /**
     * Constructor de la clase
     * @param identificador identificador del eprfil
     * @param img ruta de la imagen de perfil
     * @param cuenta nombre de la cuenta del perfil
     * @param pass contraseña del perfil
    */
    public Perfil(String identificador, String img, String cuenta, String pass) {
        this.identificador = identificador;
        this.img = img;
        this.contactos = new ArrayList<>();
        this.solicitudesEnviadas = new ArrayList<>();
        this.solicitudesRecibir = new ArrayList<>();
        this.tInteres = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
        this.usuario = cuenta;
        this.contrasena = pass;
        this.panelM = new Panel(this);
    }
    /**
     * Método que retorna el nombre de cuenta
     * @return retorna el nombre de la cuenta
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Método que retorna el identificador
     * @return retorna un objeto tipo <b>StringProperty</b> del identificador
     */
    public StringProperty getIdentificadorPorperty(){
        return new SimpleStringProperty(identificador);
    }
    /**
     * Método que retorna la contraseña
     * @return retorna la contraseña de la cuenta
     */        
    public String getContrasena() {
        return contrasena;
    }
    /**
     * Método void, cambiar la contraseña
     * @param contrasena Nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**
     * Método que retorna la lista de solicitudes enviadas
     * @return retorna la lista de las solicitudes tipo <b>Perfil</b>
     */
    public ArrayList<Perfil> getSolicitudesEnviadas() {
        return solicitudesEnviadas;
    }
    /**
     * Método void, set solicitudes enviadas
     * @param solicitudesEnviadas lista de solicitudes tipo Perfil
     */
    public void setSolicitudesEnviadas(ArrayList<Perfil> solicitudesEnviadas) {
        this.solicitudesEnviadas = solicitudesEnviadas;
    }
    /**
     * Método que retorna la lista de solicitudes recibidas
     * @return retorna la lista de las recibidas tipo <b>Perfil</b>
     */
    public ArrayList<Perfil> getSolicitudesRecibir() {
        return solicitudesRecibir;
    }
    /**
     * Método void, set solicitudes enviadas
     * @param solicitudesRecibir  lista de solicitudes tipo <b>Perfil</b>
     */
    public void setSolicitudesRecibir(ArrayList solicitudesRecibir) {
        this.solicitudesRecibir = solicitudesRecibir;
    }
    /**
     * Método void, agrega un tema de interes al Perfil
     * @param c  String del tema de interes a agregar
     */
    public void crearTemaInteres(String c) {
        this.tInteres.add(c);
    }
    /**
     * Método void, eliminar un tema de interes del Perfil
     * @param a  String del tema de interes a eliminar
     */
    public void eliminarTemaInteres(String a) {
        this.tInteres.remove(a);
    }
    /**
     * Método void, elimina un contacto del Perfil
     * @param b  Perfil del contacto a eliminar
     */
    public void eliminarContacto(Perfil b){
        this.contactos.remove(b);
    }
    /**
     * Método void, agrega un contacto al Perfil
     * @param perfil Perfil del contacto a agregar
     */
    public void agregarContacto(Perfil perfil) {
        this.contactos.add(perfil);
    }
    /**
     * Método que retorna el panel basico del perfil
     * @return retorna un objeto tipo <b>Panel</b>
     */
    public Panel getPanelM() {
        return panelM;
    }
    /**
     * Método que retorna la lista de contactos del perfil
     * @return retorna una lista de tipo <b>Perfil</b> con los contactos
     */
    public ArrayList<Perfil> getContactos() {
        return contactos;
    }
    /**
     * Método que retorna la lista de temas de interes del perfil
     * @return retorna un objeto tipo <b>Panel</b>
     */
    public ArrayList<String> gettInteres() {
        return tInteres;
    }
    /**
     * Método void, set Temas interes
     * @param tInteres lista de tipo <b>String</b> de temas de interes
     */
    public void settInteres(ArrayList tInteres) {
        this.tInteres = tInteres;
    }
    /**
     * Método que retorna la lista de notificaciones del perfil
     * @return retorna una lista tipo <b>Notificacion</b>
     */
    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }
    /**
     * Método void, set notificaciones
     * @param notificaciones  lista de tipo <b>Notificacion</b> de notificaciones
     */
    public void setNotificaciones(ArrayList notificaciones) {
        this.notificaciones = notificaciones;
    }
    /**
     * Método que retorna la ruta de la imagen de perfil
     * @return retorna la ruta de la imagen
     */
    public String getImg() {
        return img;
    }
    /**
     * Método void, cambiar imagen del Perfil
     * @param img String de ruta de la nueva imagen
     */
    public void setImg(String img) {
        this.img = img;
    }
    /**
     * Método que retorna el identificador del perfil
     * @return retorna el identificador
     */
    public String getIdentificador() {
        return identificador;
    }
    /**
     * Método void, agregar una notificacion al perfil
     * @param a <b>Notificacion</b> que va a agregar
     */
    public void agregarNotificaion(Notificacion a){
        this.notificaciones.add(a);
    }
    /**
     * Método void, eliminar una notificacion al perfil
     * @param a <b>Notificacion</b> que va a eliminar
     */
    public void eliminarNotificacion(Notificacion a){
        this.notificaciones.remove(a);
    }
    /**
     * Método void, agregar una solicitud al perfil
     * @param perfil  <b>Perfil</b> que va a agregar
     */
    public void agregarSolicitud(Perfil perfil) {
        this.solicitudesRecibir.add(perfil);
    }
    /**
     * Método void, rechazar una solicitud recibida perfil
     * @param perfil  <b>Perfil</b> que va a rechazar
     */
    public void rechazarSolicitud(Perfil perfil){
        this.solicitudesRecibir.remove(perfil);
    }
    /**
     * Método void, remover una solicitud enviada perfil
     * @param perfil  <b>Perfil</b> que va a remover
     */
    public void removerSolicitud(Perfil perfil){
        this.solicitudesEnviadas.remove(perfil);
    }
    /**
     * Método void, agregar una solicitud al perfil
     * @param perfil  <b>Perfil</b> que va a agregar
     */
    public void enviarSolicitud(Perfil perfil) {
        this.solicitudesEnviadas.add(perfil);
    }

}
