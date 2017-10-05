package Vista.control;

import Vista.RedSocialView;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import logica.Perfil;
import logica.ManipulaPerfil;
import tray.notification.NotificationType;

/**
 * FXML Controller class - Contactos
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLContactoController implements Initializable {
    /**
     * Variable privada: tabla de contactos
     */
    @FXML
    private TableView<Perfil> tablaContactos;
    /**
     * Variable privada: columna Foto
     */
    @FXML
    private TableColumn<Perfil, String> cFoto;
    /**
     * Variable privada: JText buscar contacto
     */
    @FXML
    private JFXTextField txtBuscarContacto;
    /**
     * Variable privada: tabla de perfiles
     */
    @FXML
    private TableView<Perfil> tablaPerfiles;
    /**
     * Variable privada: columna nombre
     */
    @FXML
    private TableColumn<Perfil, String> cNombre;
    /**
     * Variable privada: columna Foto
     */
    @FXML
    private TableColumn<Perfil, String> columFoto;
    /**
     * Variable privada: columna nombre
     */
    @FXML
    private TableColumn<Perfil, String> columNombre;
    /**
     * Variable privada: JText buscar perfil
     */
    @FXML
    private JFXTextField txtBuscar;
    /**
     * Variable privada: StackPane de contactos
     */
    @FXML
    private StackPane pane;
    /**
     * Variable privada: Auxiliar de perfil
     */
    private Perfil aux;
    /**
     * Variable privada: Segundo auxiliar de perfil
     */
    private Perfil aux2;
    /**
     * Variable privada: Control de perfil
     */
    private ManipulaPerfil usu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usu = new ManipulaPerfil();

        columFoto.setCellValueFactory(new PropertyValueFactory<>("img"));
        cFoto.setCellValueFactory(new PropertyValueFactory<>("img"));
        columNombre.setCellValueFactory(new PropertyValueFactory<>("identificador"));

        cNombre.setCellValueFactory(new PropertyValueFactory<>("identificador"));

        tablaContactos.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getContactos()));

        tablaPerfiles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    aux = newValue;
                });

        tablaContactos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    aux2 = newValue;
                    RedSocialView.setPerfilSelected(newValue);
                });
    }
    /**
     * Método que busca desde la lista de perfiles de RedSocial 
     * los perfiles que desea buscar el perfil actual 
     * @param event evento de mouse
     */
    @FXML
    private void buscarPerfil(ActionEvent event) {
        int c = 0;
        if (!txtBuscarContacto.getText().isEmpty()) {
            String txt = txtBuscarContacto.getText().toLowerCase();
            String aux;
            ObservableList<Perfil> perfiles = FXCollections.observableArrayList();
            for (Perfil actual : RedSocialView.red.getPerfiles()) {
                aux = actual.getIdentificador().toLowerCase();
                if (aux.contains(txt)) {
                    if (!(actual.equals(RedSocialView.getPerfil()))) {
                        c = 0;
                        for (Perfil contacto : RedSocialView.getPerfil().getContactos()) {
                            if (actual.getIdentificador().compareTo(contacto.getIdentificador()) == 0) {
                                c += 1;
                                break;
                            }
                        }
                        if (c == 0) {
                            perfiles.add(actual);
                        }
                    }
                }
            }
            tablaPerfiles.setItems(perfiles);
        }

    }
    /**
     * Método agregar perfil, envia la solicitud al perfil que  
     * se desea agregar
     * @param event evento de mouse
     */
    @FXML
    void agregarPerfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Esta seguro en enviar la solicitud a: "
                + aux.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (!aux.getSolicitudesRecibir().isEmpty()) {
                int b = 0;
                for (Perfil perfil : RedSocialView.getPerfil().getSolicitudesEnviadas()) {
                    if (aux.equals(perfil)) {
                        b++;
                        RedSocialView.alertaMensaje("WARNING", "Solicitud ya enviada", null, "La solicitud ya ha sido previamente enviada");
                        break;
                    }
                }

                if (b == 0) {

                    if (usu.enviarSolicitud(RedSocialView.getPerfil(), aux) == true) {
                        RedSocialView.mensajeExitoso("Exito", "Solicitud enviada con exito", NotificationType.SUCCESS);
                    } else {
                        RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "Error al enviar la solicitud");
                    }
                }
            } else {
                if (usu.enviarSolicitud(RedSocialView.getPerfil(), aux) == true) {
                    RedSocialView.mensajeExitoso("Exito", "Solicitud enviada con exito", NotificationType.SUCCESS);
                } else {
                    RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "Error al enviar la solicitud");
                }
            }

        }
    }
    /**
     * Método consultar contacto, busca de la lista de contactos del perfil actual
     * @param event evento de mouse
     */
    @FXML
    private void consultarContacto(ActionEvent event) {
        if (txtBuscar.getText() != null) {
            String txt = txtBuscar.getText().toLowerCase();
            String aux;
            ObservableList<Perfil> perfiles = FXCollections.observableArrayList();
            for (Perfil actual : RedSocialView.getPerfil().getContactos()) {
                aux = actual.getIdentificador().toLowerCase();
                if (aux.contains(txt)) {
                    perfiles.add(actual);
                }
            }
            tablaContactos.setItems(perfiles);
        }
    }
    /**
     * Método eliminar contacto, elimina el contacto seleccionado de la tabla  
     * @param event evento de mouse
     */
    @FXML
    void eliminarContacto(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Desea eliminar a " + aux2.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (usu.eliminarContacto(RedSocialView.getPerfil(), aux2) == true) {
                tablaContactos.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getNotificaciones()));
                RedSocialView.mensajeExitoso("Exito", "Solicitud enviada con exito", NotificationType.SUCCESS);
            } else {
                RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "Error al eliminar el contacto");
            }
        }
    }
    /**
     * Método ver perfil, abre una ventana ermengente con la informacion del contacto seleccionado
     * @throws IOException Exepcion que no depende del programador
     * @param event evento de mouse 
     */
    @FXML
    private void verPerfi(ActionEvent event) throws IOException {
        JFXDialogLayout contenido = null;
        contenido = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLverPerfil.fxml"));
        JFXDialog dialog = new JFXDialog(pane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

}
