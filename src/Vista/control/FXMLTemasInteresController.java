package Vista.control;

import Vista.RedSocialView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import logica.Mensaje;
import logica.Notificacion;
import logica.Panel;
import logica.PanelSub;
import logica.Perfil;
import logica.ManipulaPerfil;

/**
 * FXML Controller class - Temas de interes
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLTemasInteresController implements Initializable {
    /**
     * Variable privada: label Titulo tema de interes
     */
    @FXML
    private Label labelPanel;
    /**
     * Variable privada: JCombox temas de interes del perfil actual
     */
    @FXML
    private JFXComboBox<String> comboxTusTemasInteres;
    /**
     * Variable privada: JCombox temas de interes en la red social
     */
    @FXML
    private JFXComboBox<String> comboxTemasInteres;
    /**
     * Variable privada: JText Chat panel de subcripcion
     */
    @FXML
    private JFXTextArea panelSub;
    /**
     * Variable privada: JText Nuevo tema de interes
     */
    @FXML
    private JFXTextField txtCrearTema;
    /**
     * Variable privada: JText mensaje a enviar
     */
    @FXML
    private JFXTextField txtMensaje;
    /**
     * Variable privada: StackPane ventana
     */
    @FXML
    private StackPane stackPane;
    /**
     * Variable privada: JButton propiedades del panel
     */
    @FXML
    private JFXButton btnPropi;
    /**
     * Variable privada: JText id mensaje a responder
     */
    @FXML
    private JFXTextField txtIdMensajeR;
    /**
     * Variable privada: Auxiliar tema escogido
     */
    private String temaEscogido;
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
        btnPropi.setDisable(true);
        cargarTemas();
        comboxTusTemasInteres.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().gettInteres()));

        comboxTemasInteres.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    temaEscogido = newValue;
                });

        comboxTusTemasInteres.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    RedSocialView.setPanelSelected(buscaPanelSub(newValue));
                    if (!(RedSocialView.getPanelSelected() == null)) {
                        labelPanel.setText(RedSocialView.getPanelSelected().getTemaInteres() + " --- Dueño: " + RedSocialView.getPanelSelected().getPropietario().getIdentificador());
                        if (RedSocialView.getPanelSelected().getPropietario().equals(RedSocialView.getPerfil())) {
                            btnPropi.setDisable(false);
                        } else {
                            btnPropi.setDisable(true);
                        }
                        panelSub.setText(RedSocialView.getPanelSelected().cargarMensajesChat());
                    } else {
                        labelPanel.setText("Escoge el tema de interes!!");
                    }

                });

        txtIdMensajeR.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = txtIdMensajeR.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    txtIdMensajeR.setText(txtIdMensajeR.getText().substring(0, txtIdMensajeR.getText().length() - 1));
                }
            }
        });

    }
        /**
     * Método abrir propiedades del panel, abre una ventana ermengente con las propiedades
     *  del panel
     * @param event evento de mouse
     */
    @FXML
    private void abrirPropiedades(ActionEvent event) throws IOException {
        if (RedSocialView.getPanelSelected() != null) {
            JFXDialogLayout contenido = null;
            contenido = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLPropiedadesSub.fxml"));

            JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
            dialog.show();
        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "ERROR", "¡Aun no escoges el tema de interes!");
        }
    }
        /**
     * Método cargar temas de interes, carga los temas de interes que no
     *  sea asociado el perfil actual
     */
    private void cargarTemas() {
        ObservableList temasInteres = FXCollections.observableArrayList();
        int cont = 0;
        for (Panel ps : RedSocialView.red.getPaneles()) {
            if (ps instanceof PanelSub) {
                if (RedSocialView.getPerfil().gettInteres().size() > 0) {
                    for (String fn : RedSocialView.getPerfil().gettInteres()) {
                        if (((PanelSub) ps).getTemaInteres().compareTo(fn) == 0) {
                            cont++;
                        }
                    }
                }
                if (cont == 0) {
                    temasInteres.add(((PanelSub) ps).getTemaInteres());
                } else {
                    cont = 0;
                }
            }
        }
        comboxTemasInteres.setItems(temasInteres);
    }
    /**
     * Método crear tema de interes, crea un tema de interes y lo incluye a la redsocial 
     * y al perfil actual
     * @param event evento de mouse
     */
    @FXML
    private void crearTemaInteres(ActionEvent event) {
        boolean ban = true;
        String temaNueevo = txtCrearTema.getText();
        if (!txtCrearTema.getText().isEmpty()) {
            for (Panel ps : RedSocialView.red.getPaneles()) {
                if (ps instanceof PanelSub) {
                    if (((PanelSub) ps).getTemaInteres().compareTo(temaNueevo) == 0) {
                        ban = false;
                        break;
                    }
                }
            }
            if (ban) {
                RedSocialView.red.agregarPaneles(new PanelSub(RedSocialView.getPerfil(), temaNueevo));
                RedSocialView.getPerfil().crearTemaInteres(temaNueevo);
                txtCrearTema.setText("");
                cargarTemas();
                comboxTusTemasInteres.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().gettInteres()));
                RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Tema '" + temaNueevo + "' fue creado con exito!");
            } else {
                RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "ERROR", "¡Tema '" + temaNueevo + "' ya existe!");
            }
        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "ERROR", "¡Aun no escribes nada!");
        }

    }
    /**
     * Método unirse a un tema de interes, se agrega como asociado 
     * al tema seleccionado
     * @param event evento de mouse
     */
    @FXML
    private void unirsePanelSub(ActionEvent event) {
        if (buscaPanelSub(temaEscogido) != null) {
            usu.unisePanelSub(RedSocialView.getPerfil(), buscaPanelSub(temaEscogido));
            RedSocialView.alertaMensaje("CONFIRMATION", "Ventana de infromacion", "Bien hecho", "¡Fuistes agregado como asociado de " + temaEscogido);
        }
        comboxTusTemasInteres.setItems(RedSocialView.convertirList(RedSocialView.getPerfil().gettInteres()));
        cargarTemas();
    }
    /**
     * Método consultar contacto, busca de la lista de contactos del perfil actual
     * @param a String del tema de interes
     * @return retorna el panel de subcipcion correspondiente
     *  al tema de interes, si no lo encuentra retorna <b>null</b>
     */
    private PanelSub buscaPanelSub(String a) {
        if (!RedSocialView.red.getPaneles().isEmpty()) {
            for (Panel ps : RedSocialView.red.getPaneles()) {
                if (ps instanceof PanelSub) {
                    if (((PanelSub) ps).getTemaInteres().compareTo(a) == 0) {
                        return (PanelSub) ps;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Método publicar mensaje en el panel, envia un mensaje en e panel seleccionado
     * @param event evento de mouse
     */
    @FXML
    private void publicarMensaje(ActionEvent event) {
        boolean ban = false;
        boolean ban2 = false;
        int mensajeRes = (txtIdMensajeR.getText().isEmpty()) ? 0 : Integer.parseInt(txtIdMensajeR.getText());
        String mensajePanel = txtMensaje.getText();
        if (RedSocialView.getPanelSelected() != null) {
            if (!mensajePanel.isEmpty()) {
                for (Object actual : RedSocialView.convertirList(RedSocialView.getPanelSelected().getFiltro())) {
                    if (mensajePanel.contains((String) actual)) {
                        ban = true;
                        break;
                    }
                }
                if (!ban) {
                    if (txtIdMensajeR.getText().isEmpty()) {
                        RedSocialView.getPanelSelected().publicarMensaje(mensajePanel, RedSocialView.getPerfil());
                    } else {
                        for (Mensaje id : RedSocialView.getPanelSelected().getMensajes()) {
                            if (id.getId() == mensajeRes) {
                                ban2 = true;
                            }
                        }
                        if (ban2) {
                            RedSocialView.getPanelSelected().publicarMensajeRes(mensajePanel, RedSocialView.getPerfil(), mensajeRes);
                        } else {
                            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡El mensaje no existe!");
                        }
                    }
                    Mensaje men = RedSocialView.getPanelSelected().getMensajes().get(RedSocialView.getPanelSelected().getMensajes().size() - 1);
                    for (Perfil asociados : RedSocialView.getPanelSelected().getAsociados()) {
                        if (!asociados.equals(RedSocialView.getPerfil())) {
                            asociados.agregarNotificaion(new Notificacion(men, RedSocialView.getPanelSelected()));
                        }
                    }
                    if (!RedSocialView.getPerfil().equals(RedSocialView.getPanelSelected().getPropietario())) {
                        RedSocialView.getPanelSelected().getPropietario().agregarNotificaion(new Notificacion(men, RedSocialView.getPanelSelected()));
                    }
                    panelSub.setText(RedSocialView.getPanelSelected().cargarMensajesChat());
                    txtMensaje.setText("");
                    txtIdMensajeR.setText("");
                    panelSub.end();
                } else {
                    RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Este mensaje no puede ser publicado debido a reglas del panel!");
                    txtMensaje.setText("");
                }
            } else {
                RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No haz escito ningun mensaje!");
            }

        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escogido panel!");
        }
    }

}
