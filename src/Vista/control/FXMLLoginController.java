package Vista.control;

import Persistencia.PersistenciaBIN;
import Vista.RedSocialView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import logica.Perfil;
import tray.notification.NotificationType;

/**
 * FXML Controller class - Login
 *
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class FXMLLoginController implements Initializable {

    /**
     * Variable privada: JText contraseña
     */
    @FXML
    private JFXPasswordField jContrasena;
    /**
     * Variable privada: Icon de la imagen de perfil
     */
    @FXML
    private ImageView img;
    /**
     * Variable privada: JText registro contraseña
     */
    @FXML
    private JFXPasswordField jContrasenaR;
    /**
     * Variable privada: JLabel Coincidencia de contraseñas
     */
    @FXML
    private Label labelConfirm;
    /**
     * Variable privada: JText indentificador
     */
    @FXML
    private JFXTextField jIdentificador;
    /**
     * Variable privada: JText nombre de la cuenta
     */
    @FXML
    private JFXTextField jNombreCuenta;
    /**
     * Variable privada: JText repetir contraseña registro
     */
    @FXML
    private JFXPasswordField jContrasenaR2;
    /**
     * Variable privada: JText Usuario
     */
    @FXML
    private JFXTextField jUsuario;
    /**
     * Variable privada: SplitPane ventana del login
     */
    @FXML
    private SplitPane ventanaLogin;
    /**
     * Variable privada: bandera booleana
     */
    private boolean ban;
    /**
     * Variable privada: Ruta File
     */
    private File dirImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Método borrar Celdas, borrar las celdas del apartado de registro
     */
    private void borrarCeldas() {
        jNombreCuenta.setText("");
        jContrasenaR.setText("");
        jContrasenaR2.setText("");
        jIdentificador.setText("");
        img.setImage(new Image("Vista/Img/mrx.png"));
    }

    /**
     * Método validar contraseña, compara las contraseñas. manda mensaje a
     * travez del label
     *
     * @param event evento del teclado
     */
    @FXML
    private void validarCon(KeyEvent event) {

        if (jContrasenaR.getText().compareTo(jContrasenaR2.getText()) != 0) {
            labelConfirm.setText("Las contraseñas no coinciden!");
            labelConfirm.setTextFill(Color.rgb(210, 39, 30));
        } else {
            labelConfirm.setText("Las contraseñas coinciden!");
            labelConfirm.setTextFill(Color.rgb(21, 117, 84));
        }

    }

    /**
     * Método Iniciar Seccion, toma los campos de login y busca en la lista de
     * perfiles si existe el usuaio
     *
     * @param event evento del mouse
     * @throws IOException exepcion que no depende del programador
     */
    @FXML
    private void checkin(ActionEvent event) throws IOException {
        this.ban = false;
        String usuario = jUsuario.getText();
        String pass = jContrasena.getText();
        Perfil perfil = null;
        for (Perfil actual : RedSocialView.red.getPerfiles()) {
            if (actual.getUsuario().compareTo(usuario) == 0 && actual.getContrasena().compareTo(pass) == 0) {
                this.ban = true;
                perfil = actual;
                break;
            }
        }

        if (!ban) {
            RedSocialView.mensajeExitoso("¡Contraseña o usuario incorrectas!", "Intenta nuevamente, si el problema perciste comunicate con un asesor", NotificationType.ERROR);
        } else {
            RedSocialView.mensajeExitoso("¡Bienvenido!", "Gracias por ingresar a tu red social favorita", NotificationType.SUCCESS);
            RedSocialView.setPerfil(perfil);

            Stage mainStage = new Stage();
            Scene scene;
            scene = new Scene(FXMLLoader.load(RedSocialView.class.getResource("fxml/FXMLinicio.fxml")));
            mainStage.setScene(scene);
            mainStage.getIcons().add(new Image("/Vista/Img/logoUDC.png"));
            mainStage.setTitle("POO : Taller segundo corte");
            mainStage.resizableProperty().setValue(Boolean.FALSE);
            ventanaLogin.getScene().getWindow().hide();
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.show();
        }
    }

    /**
     * Método Buscar foto, abre el explorador de windows y permite al usuario
     * elegir la foto que tomara de perfil
     *
     * @param event evento del mouse
     */
    @FXML
    private void buscarFoto(ActionEvent event) {
        this.ban = true;
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        dirImg = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(dirImg);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            img.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo Registrarse, permite al usuario crear una cuenta
     *
     * @param event evento del mouse
     */
    @FXML
    private void register(ActionEvent event) {
        String cuenta = jNombreCuenta.getText();
        String pass = jContrasenaR.getText();
        String pass2 = jContrasenaR2.getText();
        String identificador = jIdentificador.getText();
        String dImg;

        if (cuenta.isEmpty() || pass.isEmpty() || pass2.isEmpty() || identificador.isEmpty()) {
            RedSocialView.mensajeExitoso("Campo vacio", "Debe llenar todos los campos para registrarse", NotificationType.WARNING);
        } else {
            if (ban) {
                dImg = this.dirImg.getAbsolutePath();
            } else {
                dImg = System.getProperty("user.dir") + "\\src\\Vista\\Img\\mrx.png";
            }

            if (pass.compareTo(pass2) != 0) {
                RedSocialView.mensajeExitoso("¡Contraseñas no coinciden!", "Vuelve a ingresar la contraseña", NotificationType.ERROR);
            } else {
                int x = 0;
                for (Perfil perfil : RedSocialView.red.getPerfiles()) {
                    if (perfil.getUsuario().compareTo(cuenta) == 0) {
                        x++;
                        RedSocialView.mensajeExitoso("Ingrese otro nombre para su cuenta", "Cuenta ya existente", NotificationType.ERROR);
                    } else if (perfil.getIdentificador().compareTo(identificador) == 0) {
                        x++;
                        RedSocialView.mensajeExitoso("Ingrese otro identificador", "Identificador ya existente", NotificationType.ERROR);
                    }
                }
                if (x == 0) {
                    RedSocialView.red.crearPerfil(new Perfil(identificador, dImg, cuenta, pass));
                    RedSocialView.mensajeExitoso("¡Cuenta creada Corecctamente!", "Ahora ingresa seccion", NotificationType.SUCCESS);
                    borrarCeldas();
                }
            }

        }
    }

    /**
     * Método que genera una alerta con informacion del proyecto
     *
     * @param event evento del mouse
     */
    @FXML
    private void informacionProyecto(ActionEvent event) {
        RedSocialView.alertaMensaje("INFORMATION", "Proyecto POO 2017", "Taller Propgramacion Orientada a Objetos",
                "\nProyecto creado por: \n\n\n\t\tAndres Vizcaino\n\t\tFrancisco Scholborgh\n\n\tTercer Semestre\n\n\t\t@2017");
    }

    /**
     * Método cerrar programa
     *
     * @param event evento del mouse
     */
    @FXML
    private void cerrarPrograma(ActionEvent event) {
        System.out.println(PersistenciaBIN.guardar(RedSocialView.red));
        System.exit(0);
    }
}
