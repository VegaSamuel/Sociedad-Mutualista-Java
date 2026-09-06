package vs.sociemutuapersistencia.connection;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Esta clase se encarga de la conexion con la base de datos en Firebase.
 * @author Samuel Vega
 */
public class FirebaseConnectionManager {
    private static boolean inicializado = false;
    
    public static void inicializar() {
        if(!inicializado) {
            try {
                Dotenv env = Dotenv.load();
                FileInputStream serviceAccount = new FileInputStream(env.get("FIREBASE_CREDENTIALS_PATH"));
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(env.get("FIREBASE_PROJECT_ID"))
                    .build();
                
                FirebaseApp.initializeApp(options);
                inicializado = true;
            } catch(IOException e) {
                System.out.println("Error al conectar con Firebase: " + e.getMessage());
            }
        }
    }
    
}
