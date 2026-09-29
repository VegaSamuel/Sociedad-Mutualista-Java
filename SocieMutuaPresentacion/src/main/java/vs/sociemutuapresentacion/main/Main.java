package vs.sociemutuapresentacion.main;

import com.formdev.flatlaf.FlatDarkLaf;
import static java.awt.EventQueue.invokeLater;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuapersistencia.connection.FirebaseConnectionManager;
import vs.sociemutuapersistencia.firebase.IglesiaRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.firebase.MensualidadRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.firebase.SocioRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.persistence.IglesiaRepositoryImpl;
import vs.sociemutuapersistencia.persistence.MensualidadRepositoryImpl;
import vs.sociemutuapersistencia.persistence.SocioRepositoryImpl;
import vs.sociemutuapersistencia.sync.StartSyncVerifier;
import vs.sociemutuapersistencia.sync.SyncMotor;
import vs.sociemutuapresentacion.utils.InitialLoader;

/**
 * Clase principal que inicia el programa, configura todo y abre la ventana principal
 * @author Samuel Vega
 */
public class Main {

    public static void main(String[] args) {
        // Le da estilo al aplicacion
        FlatDarkLaf.setup();
        // Inicia la base de datos de Firebase
        FirebaseConnectionManager.inicializar();
        
        ISocioRepository localSocio = new SocioRepositoryImpl();
        ISocioRepository cloudSocio = new SocioRepositoryFirebaseImpl();
        IIglesiaRepository localIglesia = new IglesiaRepositoryImpl();
        IIglesiaRepository cloudIglesia = new IglesiaRepositoryFirebaseImpl();
        IMensualidadRepository localMensualidad = new MensualidadRepositoryImpl();
        IMensualidadRepository cloudMensualidad = new MensualidadRepositoryFirebaseImpl();
        
        // Verifica que las bases de datos no esten vacias
        StartSyncVerifier verificator = new StartSyncVerifier(localSocio, cloudSocio, localIglesia, cloudIglesia, localMensualidad, cloudMensualidad);
        
        SyncMotor motor = new SyncMotor(localSocio, cloudSocio, localIglesia, cloudIglesia, localMensualidad, cloudMensualidad);
        // Prepara el motor para sincronizar las bases de datos
        motor.iniciarSincronizacionAutomatica();

        invokeLater(() -> {
            // Muestra la ventana de carga, en lo que se realiza la sincronizacion
            new InitialLoader(verificator).setVisible(true);
        });
    }
}
