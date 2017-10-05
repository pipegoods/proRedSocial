package Vista;

import static Vista.RedSocialView.red;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logica.RedSocial;
import Persistencia.PersistenciaBIN;
import javafx.stage.StageStyle;

/**
 * Clase View
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class View extends Application{
    /**
     * Variable privada: Stage
     */
    private Stage windonw;
    /**
     * Metodo init, se ejecuta al iniciar la aplicacion
     */
    @Override
    public void init(){
        System.out.println("Aplicacion cargando...");
        red = new RedSocial();
        Object base = PersistenciaBIN.leer();
        if (base != null) {
            red = (RedSocial) base;
        }
    }
    /**
     * Metodo start
     * @param stage stage
     * @throws java.lang.Exception Exepcion al iniciar aplicacion
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        
        windonw = stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/Vista/Img/logoUDC.png"));
        stage.setTitle("POO : Taller segundo corte");
        stage.resizableProperty().setValue(Boolean.FALSE);
        windonw.setScene(scene);
        stage.initStyle( StageStyle.UNDECORATED );
        windonw.show();
    }
    
    /**
     * Metodo main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
