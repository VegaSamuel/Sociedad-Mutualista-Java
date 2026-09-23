package vs.sociemutuapresentacion.main;

import com.formdev.flatlaf.FlatDarkLaf;
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
        FlatDarkLaf.setup();
        FirebaseConnectionManager.inicializar();
        
        ISocioRepository localSocio = new SocioRepositoryImpl();
        ISocioRepository cloudSocio = new SocioRepositoryFirebaseImpl();
        IIglesiaRepository localIglesia = new IglesiaRepositoryImpl();
        IIglesiaRepository cloudIglesia = new IglesiaRepositoryFirebaseImpl();
        IMensualidadRepository localMensualidad = new MensualidadRepositoryImpl();
        IMensualidadRepository cloudMensualidad = new MensualidadRepositoryFirebaseImpl();
        
        SyncMotor motor = new SyncMotor(localSocio, cloudSocio, localIglesia, cloudIglesia, localMensualidad, cloudMensualidad);
        motor.iniciarSincronizacionAutomatica();
        
        StartSyncVerifier verificator = new StartSyncVerifier(localSocio, cloudSocio, localIglesia, cloudIglesia, localMensualidad, cloudMensualidad);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               new InitialLoader(verificator).setVisible(true);
            }
        });
    }
}
