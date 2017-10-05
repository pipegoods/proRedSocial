package logica;

import java.util.ArrayList;

/**
 * Clase Panel (TDA)
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class RedSocial implements java.io.Serializable{
    /**
     * Variable protegida: lista de perfiles de la red social
     */
    private ArrayList<Perfil> perfiles;
    /**
     * Variable protegida: lista de todos los paneles en la red social
     */
    private ArrayList<Panel> paneles;
    
    /**
     * Constructor de la clase
    */
    public RedSocial() {
        this.paneles = new ArrayList<>();
        this.perfiles = new ArrayList<>();
    }
    /**
     * Método que retorna la lista de perfiles de la red social
     * @return retorna una lista de tipo <b>Perfil</b> de los perfiles
     */
    public ArrayList<Perfil> getPerfiles() {
        return perfiles;
    }
    /**
     * Método void, crear un perfil en la red soial
     * @param perfil perfil a crear
     * @return True - Perfil creado correctamente
     */
    public boolean crearPerfil(Perfil perfil) {
        this.perfiles.add(perfil);
        return true;
    }
    /**
     * Método que retorna la lista de paneles de la red social
     * @return retorna una lista de tipo <b>Panel</b> de los paneles
     */
    public ArrayList<Panel> getPaneles() {
        return paneles;
    }
    /**
     * Método void, agrega un panel a la lista de red social
     * @param panel panel a agregar
     */
    public void agregarPaneles(Panel panel) {
        this.paneles.add(panel);
    }
}