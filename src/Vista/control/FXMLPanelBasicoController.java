package Vista.control;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Vista.RedSocialView;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import logica.Mensaje;
import logica.Notificacion;
import logica.Panel;

/**
 * FXML Controller class - Panel basico
 *
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLPanelBasicoController implements Initializable {

    /**
     * Variable privada: JText chat
     */
    @FXML
    private JFXTextArea chatPanel;
    /**
     * Variable privada: JText Escribe aqui
     */
    @FXML
    private JFXTextField txtPanel;
    /**
     * Variable privada: JText mensaje a responder
     */
    @FXML
    private JFXTextField txtNum;
    /**
     * Variable privada: StackPane chat
     */
    @FXML
    private StackPane panePanel;
    /**
     * Variable privada: Panel Ventana
     */
    private Panel pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane = RedSocialView.getPerfil().getPanelM();
        chatPanel.setText(pane.cargarMensajesChat());
        chatPanel.end();

        txtNum.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = txtNum.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    txtNum.setText(txtNum.getText().substring(0, txtNum.getText().length() - 1));
                }
            }
        });
    }

    /**
     * Metodo abrir propiedades, abre una ventana emergente con las propiedades
     * del panel
     *
     * @param event Evento del mouse
     * @throws IOException Exepcion que no depende del programador
     */
    @FXML
    private void abrirPropiedades(ActionEvent event) throws IOException {
        JFXDialogLayout contenido = null;
        contenido = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLPropiedades.fxml"));
        JFXDialog dialog = new JFXDialog(panePanel, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    /**
     * Metodo mensajes al panel
     *
     * @param event Evento del mouse
     */
    @FXML
    private void enviarPanel(ActionEvent event) {
        boolean ban = false;
        String mensajePanel = txtPanel.getText();
        int mensajeRes = (txtNum.getText().isEmpty()) ? 0 : Integer.parseInt(txtNum.getText());
        if (!mensajePanel.isEmpty()) {
            for (String actual : pane.getFiltro()) {
                if (mensajePanel.contains(actual)) {
                    ban = true;
                    break;
                }
            }
            if (!ban) {
                if (txtNum.getText().isEmpty()) {
                    pane.publicarMensaje(mensajePanel, RedSocialView.getPerfil());
                    chatPanel.setText(pane.cargarMensajesChat());
                    txtPanel.setText("");
                    chatPanel.end();
                } else {
                    for (Mensaje id : pane.getMensajes()) {
                        if (id.getId() == mensajeRes) {
                            ban = true;
                        }
                    }
                    if (ban) {
                        pane.publicarMensajeRes(mensajePanel, RedSocialView.getPerfil(), mensajeRes);
                        if (!(RedSocialView.getPerfil().equals(pane.getMensajes().get(mensajeRes - 1).getAutor()))) {
                            Mensaje men = pane.getMensajes().get(pane.getMensajes().size() - 1);
                            pane.getMensajes().get(mensajeRes - 1).getAutor().agregarNotificaion(new Notificacion(men, pane));
                        }
                        chatPanel.setText(pane.cargarMensajesChat());
                        txtPanel.setText("");
                        chatPanel.end();

                    } else {
                        RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡El mensaje no existe!");
                    }
                }
            } else {
                RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Este mensaje no puede ser publicado debido a reglas del panel!");
                txtPanel.setText("");
            }

        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No haz escito ningun mensaje!");
        }
    }
}
