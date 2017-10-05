package Vista;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import logica.PanelSub;
import logica.Perfil;
import logica.RedSocial;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Clase Redsocial
 *
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class RedSocialView {

    /**
     * Variable publica: Red social
     */
    public static RedSocial red;
    /**
     * Variable privada estatica: Auxiliar panel de subcripcion
     */
    private static PanelSub panelSelected;
    /**
     * Variable privada estatica: Auxiliar perfil
     */
    private static Perfil perfilSelected;
    /**
     * Variable privada estatica: Auxiliar perfil
     */
    private static Perfil perfil;

    /**
     * Método get Perfil
     *
     * @return retorna el perfil actual
     */
    public static Perfil getPerfil() {
        return perfil;
    }

    /**
     * Método set Perfil
     *
     * @param perfil Perfil nuevo a iniciar seccion
     */
    public static void setPerfil(Perfil perfil) {
        RedSocialView.perfil = perfil;
    }

    /**
     * Método get Panel
     * <p>
     * esta funcion es propia de algunas clases</p>
     *
     * @return retorna el panel seleccionado
     */
    public static PanelSub getPanelSelected() {
        return panelSelected;
    }

    /**
     * Método set Panel
     * <p>
     * esta funcion es propia de algunas clases</p>
     *
     * @param panelSelected Panel nuevo a seleccionar
     */
    public static void setPanelSelected(PanelSub panelSelected) {
        RedSocialView.panelSelected = panelSelected;
    }

    /**
     * Método get Perfil
     * <p>
     * esta funcion es propia de algunas clases</p>
     *
     * @return retorna el perfil seleccionado
     */
    public static Perfil getPerfilSelected() {
        return perfilSelected;
    }

    /**
     * Método set Perfil
     * <p>
     * esta funcion es propia de algunas clases</p>
     *
     * @param perfilSelected Perfil nuevo a seleccionar
     */
    public static void setPerfilSelected(Perfil perfilSelected) {
        RedSocialView.perfilSelected = perfilSelected;
    }

    /**
     * Método ALERTAS
     *
     * @param tipoAlerta tipo de alerta
     * @param titulo titulo de la alerta
     * @param cabeza Subtitulo de la alerta
     * @param contenido contenido de la laerta
     */
    public static void alertaMensaje(String tipoAlerta, String titulo, String cabeza, String contenido) {
        Alert alert = new Alert(Alert.AlertType.valueOf(tipoAlerta));
        alert.setTitle(titulo);
        alert.setHeaderText(cabeza);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    
    /**
     * Método ALERTAS
     *
     * @param titulo titulo de la notificacion
     * @param mensaje Contenido de la notificacion
     * @param tipo_not Tipo de notificacion
     */
    public static void mensajeExitoso(String titulo,String mensaje, NotificationType tipo_not) {
        TrayNotification tray = new TrayNotification();
        tray.setNotificationType(tipo_not);
        tray.setTitle(titulo);
        tray.setMessage(mensaje);
        tray.setAnimationType(AnimationType.SLIDE);
        tray.showAndDismiss(Duration.millis(3000));
    }
    
    /**
     *  Convertir ArraryList a ObservableList
     * @param lista ArrayList a convertir
     * @return 
     */
    public static ObservableList convertirList(ArrayList<?> lista){
        ObservableList lista_Return = FXCollections.observableArrayList();
        lista.forEach((object) -> {
            lista_Return.add(object);
        });
        return lista_Return;
    }
}
