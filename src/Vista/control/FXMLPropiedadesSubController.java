package Vista.control;

import Vista.RedSocialView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.Mensaje;
import logica.PanelSub;
import logica.Perfil;

/**
 * FXML Controller class - Propiedades panel de subcripcion
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLPropiedadesSubController implements Initializable {
    /**
     * Variable privada: JCombox de filtros
     */
    @FXML
    private JFXComboBox<String> comboxFiltro;
    /**
     * Variable privada: JText nuevo filtro
     */
    @FXML
    private JFXTextField txtPalabraNew;
    /**
     * Variable privada: tabla de colaboradores
     */
    @FXML
    private TableView<Perfil> tablaColaboradores;
    /**
     * Variable privada: tabla de contactos
     */
    @FXML
    private TableView<Perfil> tablaColaboradores1;
    /**
     * Variable privada: columna nombre tabla colaboradores
     */
    @FXML
    private TableColumn<Perfil, String> cNombre1;
    /**
     * Variable privada: JBitton boton eliminar panel
     */
    @FXML
    private JFXButton btnBorrarPanel;
    /**
     * Variable privada: columna nombre tabla contactos
     */
    @FXML
    private TableColumn<Perfil, String> cNombre;
    /**
     * Variable privada: tabla de mensajes
     */
    @FXML
    private TableView<Mensaje> tablaMensajes;
    /**
     * Variable privada: JText mensaje a buscar
     */
    @FXML
    private JFXTextField txtMensajeC;
    /**
     * Variable privada: columna autor tabla mensajes
     */
    @FXML
    private TableColumn<Mensaje, String> cAutor;
    /**
     * Variable privada: columna mensaje tabla mensaje
     */
    @FXML
    private TableColumn<Mensaje, String> cMensaje;
    /**
     * Variable privada: Auxiliar de palabra
     */
    private String palabra;
    /**
     * Variable privada: Auxiliar de perfil
     */
    private Perfil perfilSelected;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboxFiltro.setItems(RedSocialView.convertirList(RedSocialView.getPanelSelected().getFiltro()));

        comboxFiltro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    palabra = newValue;
                });

        cNombre.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        
        cAutor.setCellValueFactory((TableColumn.CellDataFeatures<Mensaje, String> param) -> param.getValue().getAutor().getIdentificadorPorperty());
        cMensaje.setCellValueFactory(new PropertyValueFactory<>("texto"));

        tablaColaboradores.setItems(RedSocialView.convertirList(RedSocialView.getPanelSelected().getAsociados()));
        tablaColaboradores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    perfilSelected = newValue;
                });

        cNombre1.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tablaColaboradores1.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    perfilSelected = newValue;
                });

        habilitarBoton();
        cargarListaContactos();

    }
    /**
     * Método habilitar boton, habilita o ihnabilita el boton dependiendo la cantidad de asociados
     */
    private void habilitarBoton() {
        if (RedSocialView.getPanelSelected().getAsociados().isEmpty()) {
            btnBorrarPanel.setDisable(false);
        } else {
            btnBorrarPanel.setDisable(true);
        }
    }
    /**
     * Método cargar lista de contactos, carga la lista de contactos del usuario los cuales no son asociados
     */
    private void cargarListaContactos() {
        boolean ban = false;
        ObservableList<Perfil> lista = FXCollections.observableArrayList();
        for (Perfil contacto : RedSocialView.getPanelSelected().getPropietario().getContactos()) {
            for (Perfil asociado : RedSocialView.getPanelSelected().getAsociados()) {
                if (asociado.equals(contacto)) {
                    ban = true;
                    break;
                }
            }
            if (!ban) {
                lista.add(contacto);
            }
            ban = false;
        }
        tablaColaboradores1.setItems(lista);
    }
    /**
     * Método eliminar filtro, elimina un filtro seleccionado
     * @param event evento de mouse
     */
    @FXML
    private void eliminarFiltro(ActionEvent event) {
        if (palabra.isEmpty()) {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escogido ningun filtro!");
        } else {
            RedSocialView.getPanelSelected().eliminarFiltro(palabra);
            RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro eliminado con exito!");
        }
    }
    /**
     * Método agregar filtro, agrega un filtro nuevo al panel
     * @param event evento de mouse
     */
    @FXML
    private void agregarFiltro(ActionEvent event) {
        String newFiltro = txtPalabraNew.getText();
        if (newFiltro.isEmpty()) {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escrito ningun filtro!");
        } else {
            if (!RedSocialView.getPanelSelected().getFiltro().isEmpty()) {
                int a = 0;
                for (String b : RedSocialView.getPanelSelected().getFiltro()) {
                    if (b.compareTo(newFiltro) == 0) {
                        a++;
                        RedSocialView.alertaMensaje("WARNING", "Filtro existente", null, "Este filtro ya hace parte del panel");
                        break;
                    }
                }
                if (a == 0) {
                    RedSocialView.getPanelSelected().agregarFiltro(newFiltro);
                    RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro agregado con exito!");
                }
            } else {
                RedSocialView.getPanelSelected().agregarFiltro(newFiltro);
                RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Filtro agregado con exito!");
            }
        }
    }
    /**
     * Método eliminar asosiado, elimina el asosiado seleccionado
     * @param event evento de mouse
     */
    @FXML
    void eliminarColaborador(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Esta seguro en borrar a "
                + perfilSelected.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            perfilSelected.eliminarTemaInteres(RedSocialView.getPanelSelected().getTemaInteres());
            RedSocialView.getPanelSelected().eliminarColaborador(perfilSelected);
            cargarListaContactos();
            habilitarBoton();
        }
    }
    /**
     * Método agregar asociado, agrega al contacto seleccionado al grupo de asociados
     * @param event evento de mouse
     */
    @FXML
    void agregarColaborador(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Esta seguro en agregar a "
                + perfilSelected.getIdentificador() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (!RedSocialView.getPanelSelected().getAsociados().isEmpty()) {
                int a = 0;
                for (Perfil asociado : RedSocialView.getPanelSelected().getAsociados()) {
                    if (perfilSelected.equals(asociado)) {
                        Alert alert2 = new Alert(AlertType.WARNING, "Colaborador ya ingresado ");
                        a++;
                        break;
                    }
                }
                if (a == 0) {
                    RedSocialView.getPanelSelected().agregarColaborador(perfilSelected);
                    perfilSelected.crearTemaInteres(RedSocialView.getPanelSelected().getTemaInteres());
                    cargarListaContactos();
                }
            }
            else{
                RedSocialView.getPanelSelected().agregarColaborador(perfilSelected);
                perfilSelected.crearTemaInteres(RedSocialView.getPanelSelected().getTemaInteres());
                cargarListaContactos();
            }

            
        }
    }
    /**
     * Método eliminar panel de subcripcion, elimina el panel de la red social
     * @param event evento de mouse
     */
    @FXML
    void eliminarPanelSup(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Esta seguro de eliminar el panel "
                + RedSocialView.getPanelSelected().getTemaInteres() + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            RedSocialView.getPanelSelected().getPropietario().eliminarTemaInteres(RedSocialView.getPanelSelected().getTemaInteres());
            RedSocialView.red.getPaneles().remove(RedSocialView.getPanelSelected());
            RedSocialView.setPanelSelected(new PanelSub(null, "Escoge tema de Interes"));
        }
    }
    /**
     * Método buscar mensaje, busca un mensaje en el panel
     * @param event evento de mouse
     */
    @FXML
    void buscarMensaje(ActionEvent event) {
        ObservableList<Mensaje> lista = FXCollections.observableArrayList();
        String txtBuscar = txtMensajeC.getText();
        if (!txtMensajeC.getText().isEmpty()) {
            for (Mensaje mensaje : RedSocialView.getPanelSelected().getMensajes()) {
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
