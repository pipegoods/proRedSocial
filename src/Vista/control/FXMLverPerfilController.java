package Vista.control;

import Vista.RedSocialView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import logica.Mensaje;
import logica.Notificacion;

/**
 * FXML Controller class - Ver perfil
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLverPerfilController implements Initializable {
    /**
     * Variable privada: Marco imagen del perfil
     */
    @FXML
    private ImageView img;
    /**
     * Variable privada: label nombre del perfil
     */
    @FXML
    private Label nPerfil;
    /**
     * Variable privada: JText chat panel Basico
     */
    @FXML
    private JFXTextArea boxMensaje;
    /**
     * Variable privada: JText Mensaje a enviar
     */
    @FXML
    private JFXTextField mensaje;
    /**
     * Variable privada: JText id mensaje a responder
     */
    @FXML
    private JFXTextField num;
    /**
     * Variable privada: JCombox temas de interes del perfil
     */
    @FXML
    private JFXComboBox<String> temasInteres;
    /**
     * Variable privada: Marco imagen
     */
    @FXML
    private Pane paneIImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nPerfil.setText(RedSocialView.getPerfilSelected().getIdentificador());

        img.setPreserveRatio(false);
        img.fitWidthProperty().bind(paneIImg.widthProperty());
        img.fitHeightProperty().bind(paneIImg.heightProperty());
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(RedSocialView.getPerfilSelected().getImg()));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            img.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(FXMLinicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        temasInteres.setItems(RedSocialView.convertirList(RedSocialView.getPerfilSelected().gettInteres()));

        boxMensaje.setText(RedSocialView.getPerfilSelected().getPanelM().cargarMensajesChat());
        boxMensaje.end();

        num.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = num.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    num.setText(num.getText().substring(0, num.getText().length() - 1));
                }
            }
        });
    }
     /**
     * Método enaviar mensaje al panel, envia un mensaje al pnael
     *  basico del perfil
     * @param event evento de mouse
     */
    @FXML
    private void enviarMensaje(ActionEvent event) {
        boolean ban2 = false;
        boolean ban = false;
        int mensajeRes = (num.getText().isEmpty()) ? 0 : Integer.parseInt(num.getText());
        String mensajePanel = mensaje.getText();
        if (!mensajePanel.isEmpty()) {
            if (!num.getText().isEmpty()) {
                for (Mensaje id : RedSocialView.getPerfilSelected().getPanelM().getMensajes()) {
                    if (id.getId() == mensajeRes) {
                        ban2 = true;
                    }
                }
                if (ban2) {
                    for (Object actual : RedSocialView.convertirList(RedSocialView.getPerfilSelected().getPanelM().getFiltro())) {
                        if (mensajePanel.contains((String) actual)) {
                            ban = true;
                            break;
                        }
                    }
                    if (!ban) {
                        RedSocialView.getPerfilSelected().getPanelM().publicarMensajeRes(mensajePanel, RedSocialView.getPerfil(), mensajeRes);
                        Mensaje men = RedSocialView.getPerfilSelected().getPanelM().getMensajes().get(RedSocialView.getPerfilSelected().getPanelM().getMensajes().size() - 1);
                        RedSocialView.getPerfilSelected().agregarNotificaion(new Notificacion(men, RedSocialView.getPerfilSelected().getPanelM()));
                        boxMensaje.setText(RedSocialView.getPerfilSelected().getPanelM().cargarMensajesChat());
                        mensaje.setText("");
                        boxMensaje.end();
                    } else {
                        RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Este mensaje no puede ser publicado debido a reglas del panel!");
                        mensaje.setText("");
                    }
                } else {
                    RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡El mensaje no existe!");
                }
            } else {
                RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡Para publicar mensajes debes indicar\na que mensajes haces respuesta!");
            }

        } else {
            RedSocialView.alertaMensaje("ERROR", "Ventana de infromacion", "Error", "¡No has escrito nada aun!");
        }
    }
}
