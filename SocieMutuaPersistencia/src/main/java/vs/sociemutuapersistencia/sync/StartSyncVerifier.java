package vs.sociemutuapersistencia.sync;

import java.util.List;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuadominio.models.Socio;

/**
 * Esta clase verifica la sincronizacion entre las bases de datos al iniciar.
 * @author Samuel Vega
 */
public class StartSyncVerifier {
    private final ISocioRepository localSocioRepo;
    private final ISocioRepository cloudSocioRepo;
    private final IIglesiaRepository localIglesiaRepo;
    private final IIglesiaRepository cloudIglesiaRepo;
    private final IMensualidadRepository localMensualidadRepo;
    private final IMensualidadRepository cloudMensualidadRepo;

    public StartSyncVerifier(ISocioRepository localSocioRepo, ISocioRepository cloudSocioRepo, IIglesiaRepository localIglesiaRepo, IIglesiaRepository cloudIglesiaRepo, IMensualidadRepository localMensualidadRepo, IMensualidadRepository cloudMensualidadRepo) {
        this.localSocioRepo = localSocioRepo;
        this.cloudSocioRepo = cloudSocioRepo;
        this.localIglesiaRepo = localIglesiaRepo;
        this.cloudIglesiaRepo = cloudIglesiaRepo;
        this.localMensualidadRepo = localMensualidadRepo;
        this.cloudMensualidadRepo = cloudMensualidadRepo;
    }
    
    public void verificarDatosLocales() {
        try {
            System.out.println("Verificando bases de datos local...");
            verificarIglesias();
            verificarSocios();
            verificarMensualidades();
            System.out.println("Sincronizacion completada.");
        }catch(Exception e) {
            System.out.println("Error durante la restauracion inicial: " + e.getMessage());
        }
    }
    
    private void verificarSocios() throws Exception {
        if(localSocioRepo.obtenerTodos().isEmpty()) {
            System.out.println("Base de datos de Socios local vacia. Restaurando desde Firebase...");
            List<Socio> sociosNube = cloudSocioRepo.obtenerTodos();
            
            for (Socio socio : sociosNube) {
                localSocioRepo.guardar(socio);
            }
        }
    }
    
    private void verificarIglesias() throws Exception {
        if(localSocioRepo.obtenerTodos().isEmpty()) {
            System.out.println("Base de datos de Iglesias local vacia. Restaurando desde Firebase...");
            List<Iglesia> iglesiasNube = cloudIglesiaRepo.obtenerTodos();
            
            for (Iglesia iglesia : iglesiasNube) {
                localIglesiaRepo.guardar(iglesia);
            }
        }
    }
     
    private void verificarMensualidades() throws Exception {
        if(localSocioRepo.obtenerTodos().isEmpty()) {
            System.out.println("Base de datos de Socios local vacia. Restaurando desde Firebase...");
            List<Mensualidad> mensualidadNube = cloudMensualidadRepo.obtenerTodos();
            
            for (Mensualidad mensualidad : mensualidadNube) {
                localMensualidadRepo.guardar(mensualidad);
            }
        }
    }
 
}
