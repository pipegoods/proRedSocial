package Vista.control;

import Vista.RedSocialView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logica.Perfil;
import logica.ManipulaPerfil;
import tray.notification.NotificationType;

/**
 * FXML Controller class - Solicitudes
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLSolicitudesController implements Initializable {
    /**
     * Variable privada: tabla de soicitudes
     */
    @FXML
    private TableView<Perfil> tablaSolicitudes;
    /**
     * Variable privada: columna nombre
     */
    @FXML
    private TableColumn<Perfil, String> cNomnre;
    /**
     * Variable privada: Auxiliar de perfil
     */
    private Perfil aux;
    /**
     * Variable privada: control de usuario
     */
    private ManipulaPerfil usu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usu = new ManipulaPerfil();
        aux = null;
        cNomnre.setCellValueFactory((TableColumn.CellDataFeatures<Perfil, String> param) -> param.getValue().getIdentificadorPorperty());
        tablaSolicitudes.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getSolicitudesRecibir()));
        tablaSolicitudes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    aux = newValue;
                });
    }
    /**
     * Método agregar a contactos, agrega a tus contactos el perfil seleccionado
     * @param event evento de mouse
     */
    @FXML
     private void agregar(ActionEvent event) {
        if (aux != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Desea agregar a: "
                    + aux.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (usu.agregarContacto(RedSocialView.getPerfil(), aux) == true) {
                    tablaSolicitudes.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getSolicitudesRecibir()));
                    RedSocialView.mensajeExitoso("Exito", "Contacto gregado con exito", NotificationType.SUCCESS);                    
                } else {
                    RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "No se pudo agregar el contacto");
                }

            }
        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Debes seleccionar una solicitud!");
        }

    }
    /**
     * Método borrar solicitud, borra de la lista de solicitudes el perfil seleccionado
     * @param event evento de mouse
     */
    @FXML
    private void rechazar(ActionEvent event) {
        if (aux != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Desea rechazar a: "
                    + aux.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (usu.rechazarSolicitud(RedSocialView.getPerfil(), aux) == true) {
                    if (!RedSocialView.getPerfil().getSolicitudesRecibir().isEmpty()) {
                        tablaSolicitudes.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getSolicitudesRecibir()));
                        RedSocialView.mensajeExitoso("Exito", "Solicitud rechazada", NotificationType.SUCCESS);
                    } else {
                        RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "Error al rechazar la solicitud");
                    }
                }
            }
        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Debes seleccionar una solicitud!");
        }
    }
}
