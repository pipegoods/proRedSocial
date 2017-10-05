package logica;

import java.util.ArrayList;

/**
 * Clase Usuario (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class ManipulaPerfil {
    /**
     * Constructor de la clase
    */
    public ManipulaPerfil() {}
    
    /**
     * Método void, agregar un contacto
     * @param a Perfil actual
     * @param  b Perfil que se va a agregar
     * @return true - cuando logro ejecutar la accion\nfalse - cuando existe una excepion
     */
     public boolean agregarContacto(Perfil a, Perfil b) {

        try {
            a.agregarContacto(b);
            b.agregarContacto(a);
            b.removerSolicitud(a);
            a.rechazarSolicitud(b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Método void, rechazar la solicitud de un perfil
     * @param a Perfil actual
     * @param  b Perfil que se va a rechazar
     * @return true - cuando logro ejecutar la accion\nfalse - cuando existe una excepion
     */
    public boolean rechazarSolicitud(Perfil a, Perfil b) {

        try {
            b.removerSolicitud(a);
            a.rechazarSolicitud(b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Método void, enviar una solicitud a un perfil
     * @param a Perfil actual
     * @param  b Perfil que se va a enviar solicitud
     * @return true - cuando logro ejecutar la accion\nfalse - cuando existe una excepion
     */
    public boolean enviarSolicitud(Perfil a, Perfil b) {

        try {
            b.agregarSolicitud(a);
            a.enviarSolicitud(b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Método que retorna la lista de contactos de un perfil
     * @param c perfil desde consultar
     * @return retorna la lista de contactos de un perfil
     */
    public ArrayList consultarContacto(Perfil c) {
        return c.getContactos();
    }
    /**
     * Método void, eliminar contacto a un perfil
     * @param a Perfil actual
     * @param  b Perfil que se va a eliminar
     * @return true - cuando logro ejecutar la accion\nfalse - cuando existe una excepion
     */
    public boolean eliminarContacto(Perfil a, Perfil b) {
        
        try{
            a.eliminarContacto(b);
            b.eliminarContacto(a);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    /**
     * Método void, unirse como colaborador de un panel de subcripcion
     * @param a Perfil actual
     * @param  panel PanelSub que se quiere unir
     */
    public void unisePanelSub(Perfil a, PanelSub panel){
        a.crearTemaInteres(panel.getTemaInteres());
        panel.agregarColaborador(a);
    }

}
