package Vista.control;

import Vista.RedSocialView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.Mensaje;

/**
 * FXML Controller class - Propiedades panel basico
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLPropiedadesController implements Initializable {
    /**
     * Variable privada: JCombox filtros
     */
    @FXML
    private JFXComboBox<String> comboxFiltro;
    /**
     * Variable privada: JText Nuevo filtro
     */
    @FXML
    private JFXTextField txtPalabraNew;
    /**
     * Variable privada: tabla de mensajes
     */
    @FXML
    private TableView<Mensaje> tablaMensajes;
    /**
     * Variable privada: JText Buscar mensaje
     */
    @FXML
    private JFXTextField txtMensajeC;
    /**
     * Variable privada: Columna autor del mensaje
     */
    @FXML
    private TableColumn<Mensaje, String> cAutor;
    /**
     * Variable privada: Columna mensaje
     */
    @FXML
    private TableColumn<Mensaje, String> cMensaje;
    /**
     * Variable privada: Auxiliar de palabra
     */
    private String palabra;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboxFiltro.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().getPanelM().getFiltro()));
        comboxFiltro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    palabra = newValue;
                });

        cAutor.setCellValueFactory((TableColumn.CellDataFeatures<Mensaje, String> param) -> param.getValue().getAutor().getIdentificadorPorperty());
        cMensaje.setCellValueFactory(new PropertyValueFactory<>("texto"));
    }
    /**
     * Método eliminar filro, borra el filtro seleccionado del 
     * panel
     * @param event evento de mouse
     */
    @FXML
    private void eliminarFiltro(ActionEvent event) {
        if (palabra.isEmpty()) {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escogido ningun filtro!");
        } else {
            RedSocialView.getPerfil().getPanelM().eliminarFiltro(palabra);
            RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro eliminado con exito!");
        }
    }
    /**
     * Método agregar filtro, agrega un filtro al panel
     * @param event evento de mouse
     */
    @FXML
    private void agregarFiltro(ActionEvent event) {
        String newFiltro = txtPalabraNew.getText();
        if (newFiltro.isEmpty()) {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escrito ninguna palabra!");
        } else {
            if (!RedSocialView.getPerfil().getPanelM().getFiltro().isEmpty()) {
                int a = 0;
                for (String b : RedSocialView.getPerfil().getPanelM().getFiltro()) {
                    if (b.compareTo(newFiltro) == 0) {
                        a++;
                        RedSocialView.alertaMensaje("WARNING", "Filtro existente", null, "Este filtro ya hace parte del panel");
                        break;
                    }
                }
                if (a == 0) {
                    RedSocialView.getPerfil().getPanelM().agregarFiltro(newFiltro);
                    RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro agregado con exito!");
                }

            } else {
                RedSocialView.getPerfil().getPanelM().agregarFiltro(newFiltro);
                RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro agregado con exito!");
            }

        }
    }
    /**
     * Método buscar mensaje, busca un mensaje dentro del panel
     * @param event evento de mouse
     */
    @FXML
    void buscarMensaje(ActionEvent event) {
        ObservableList<Mensaje> lista = FXCollections.observableArrayList();
        String txtBuscar = txtMensajeC.getText();
        if (!txtMensajeC.getText().isEmpty()) {
            for (Mensaje mensaje : RedSocialView.getPerfil().getPanelM().getMensajes()) {
                if (mensaje.getAutor().getIdentificador().compareTo(txtBuscar) == 0) {
                    lista.add(mensaje);
                } else if (mensaje.getTexto().contains(txtBuscar)) {
                    lista.add(mensaje);
                } else {
                    RedSocialView.alertaMensaje("WARNING", "No encontro mensaje", null, "No existen mensajes condicho autor o mensaje");
                }
            }
        } else {
            RedSocialView.alertaMensaje("WARNING", "Campo vacio", null, "Aun no escribes nada");
        }
        tablaMensajes.setItems(lista);
    }

}
