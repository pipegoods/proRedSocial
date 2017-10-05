package Persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase PersistenciaBIN - Archivo binario
 * @author Andres Vizcaino
 * @author Francisco Scholborgh
 * @version 1.0.0
 */
public class PersistenciaBIN {
    public static Object leer() {
        try {
            ObjectInputStream cargar = new ObjectInputStream(new FileInputStream("baseDedatos.VUZ"));
            return cargar.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

    }

    public static boolean guardar(Object user) {
        try {
            ObjectOutputStream guardar1 = new ObjectOutputStream(new FileOutputStream("baseDedatos.VUZ"));

            guardar1.writeObject(user);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
