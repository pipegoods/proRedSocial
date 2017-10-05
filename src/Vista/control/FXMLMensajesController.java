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
import logica.Notificacion;


/**
 * FXML Controller class - Mensajes
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLMensajesController implements Initializable {
    /**
     * Variable privada: tabla de mensajes
     */
    @FXML
    private TableView<Notificacion> tablaMensajes;
    /**
     * Variable privada: columna mensaje
     */
    @FXML
    private TableColumn<Notificacion, String> mensaje;
    /**
     * Variable privada: columna panel
     */
    @FXML
    private TableColumn<Notificacion, String> panel;
    /**
     * Variable privada: columna autor
     */
    @FXML
    private TableColumn<Notificacion, String> autor;
    /**
     * Variable privada: Auxiliar notificacion
     */
    private Notificacion not;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mensaje.setCellValueFactory((TableColumn.CellDataFeatures<Notificacion, String> param) -> {
            return param.getValue().getMensaje().getTextoProperty();
        });
        
        panel.setCellValueFactory((TableColumn.CellDataFeatures<Notificacion, String> param) -> {
            return param.getValue().getPanel().toStringProperty();
        });
        
        autor.setCellValueFactory((TableColumn.CellDataFeatures<Notificacion, String> param) -> {
            return param.getValue().getMensaje().getAutor().getIdentificadorPorperty();
        });
        
        tablaMensajes.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getNotificaciones()));
        
        tablaMensajes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    not = newValue;
                });
        
        System.out.println("Mensajes pendientes de " + RedSocialView.getPerfil().getIdentificador());
        for (Notificacion not : RedSocialView.getPerfil().getNotificaciones()) {
            System.out.println("\t"+not.getPanel().toStringProperty().get() + ": " + not.getMensaje().getTexto());
        }
        
    }  
    /**
     * Metodo mar car como leido, borra de la tabla de mensajes el mensaje seleccionado
     * @param event Evento del mouse
     */
    @FXML
    private void marcarLeido(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Esta seguro en borrar este mensaje" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            RedSocialView.getPerfil().eliminarNotificacion(not);
            tablaMensajes.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getNotificaciones()));
        }
    }
}
