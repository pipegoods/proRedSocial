package Vista.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import Vista.RedSocialView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * FXML Controller class - Inicio
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLinicioController implements Initializable {
    /**
     * Variable privada: JButton identificador
     */
    @FXML
    private JFXButton btnIdentificador;
    /**
     * Variable privada: JFXDrawer Tablero
     */
    @FXML
    private JFXDrawer drawer1;
    /**
     * Variable privada: AnchorPane Ventana
     */
    @FXML
    private AnchorPane root;
    /**
     * Variable privada: Label Numero de solicitudes
     */
    @FXML
    private Label labelSollicitudes;
    /**
     * Variable privada: Marco imagen de perfil
     */
    @FXML
    private ImageView imagenPerfil;
    /**
     * Variable privada: Label "Cambiar foto"
     */
    @FXML
    private Label txtImagen;
    /**
     * Variable privada: Pane Marco imagen + Label
     */
    @FXML
    private Pane paneImagen;
    /**
     * Variable privada: Label Numero de notificaciones
     */
    @FXML
    private Label labelNotificaciones;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnIdentificador.setText(RedSocialView.getPerfil().getIdentificador());
        imagenPerfil.setPreserveRatio(false);
        imagenPerfil.fitWidthProperty().bind(paneImagen.widthProperty());
        imagenPerfil.fitHeightProperty().bind(paneImagen.heightProperty());
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(RedSocialView.getPerfil().getImg()));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            imagenPerfil.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(FXMLinicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        drawer1.open();
        VBox box;
        try {
            box = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLimg.fxml"));
            drawer1.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(FXMLinicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarNoti();
    }
    /**
     * Método actualizar notificaciones
     */
    private void actualizarNoti() {
        labelSollicitudes.setText(String.valueOf(RedSocialView.getPerfil().getSolicitudesRecibir().size()));
        labelNotificaciones.setText(String.valueOf(RedSocialView.getPerfil().getNotificaciones().size()));
    }
    /**
     * Método abrir ventana, abre la ventana dependiendo del boton que se haya
     *  seleccionado
     * @param event evento de mouse
     * @throws IOException Exepcion que no depende del programador
     */
    @FXML
    private void pressMe(ActionEvent event) throws IOException {
        actualizarNoti();
        JFXButton btn = (JFXButton) event.getSource();
        drawer1.open();
        VBox box;

        switch (btn.getText()) {
            case "Contactos":
                box = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLContacto.fxml"));
                drawer1.setSidePane(box);

                break;
            case "Panel":
                box = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLPanelBasico.fxml"));
                drawer1.setSidePane(box);
                break;
            case "Temas de interes":
                box = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLTemasInteres.fxml"));
                drawer1.setSidePane(box);
                break;
        }

    }
    /**
     * Método cargar ventana ermengete de las notificaciones o solicitudes, dependiendo
     *  del boton seleccionado
     * @param event evento de mouse
     * @throws IOException Exepcion que no depende del programador
     */
    @FXML
    private void cargarDialogo(ActionEvent event) throws IOException {
        actualizarNoti();
        JFXButton btn = (JFXButton) event.getSource();
        JFXDialogLayout contenido = null;

        switch (btn.getId()) {
            case "btnMensajes":
                contenido = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLMensajes.fxml"));
                break;
            case "btnNotificaciones":
                contenido = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLSolicitudes.fxml"));
                break;
        }
        JFXDialog dialog = new JFXDialog(drawer1, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }
    /**
     * Método cambiar foto de perfil, abre el explorador de Windows
     *  y permite al usuario escoger una foto
     *  del boton seleccionado
     * @param event evento de mouse
     */
    @FXML
    private void cambiarFoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File dirImg = fileChooser.showOpenDialog(null);
        RedSocialView.getPerfil().setImg(dirImg.getAbsolutePath());
        try {
            BufferedImage bufferedImage = ImageIO.read(dirImg);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imagenPerfil.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Método cambia el mensaje del label, se ejecuta cuando el mouse
     *  pasa sobre la foto
     * @param event evento de mouse
     */
    @FXML
    private void entraImg(MouseEvent event) {
        actualizarNoti();
        txtImagen.setText("Cambiar foto");
        imagenPerfil.setOpacity(0.3);
    }
    /**
     * Método cambia el mensaje del label, se ejecuta cuando el mouse
     *  pasa sobre la foto
     * @param event evento de mouse
     */
    @FXML
    private void saleImg(MouseEvent event) {
        txtImagen.setText("");
        imagenPerfil.setOpacity(1);
    }
    /**
     * Método salir seccion, cierra la seccion del perfil actual
     * @param event evento de mouse
     * @throws IOException Exepcion que no depende del programador
     */
    @FXML
    void salir(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Esta seguro Cerrar sesión "
                + RedSocialView.getPerfil().getIdentificador() + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            RedSocialView.setPerfil(null);
            Stage mainStage = new Stage();
            Scene scene;
            scene = new Scene(FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLLogin.fxml")));
            mainStage.setScene(scene);
            mainStage.getIcons().add(new Image("/Vista/Img/logoUDC.png"));
            mainStage.setTitle("POO : Taller segundo corte");
            root.getScene().getWindow().hide();
            mainStage.initStyle( StageStyle.UNDECORATED );
            mainStage.show();
        }
    }
    /**
     * Método cerrar ventanas, cierra las ventanas y muestra el inicio (gif)
     *  pasa sobre la foto
     * @param event evento de mouse
     * @throws IOException Exepcion que no depende del programador
     */
    @FXML
    private void cerrarDialogo(ActionEvent event) throws IOException {
        drawer1.open();
        VBox box;
        box = FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLimg.fxml"));
        drawer1.setSidePane(box);
    }

}
