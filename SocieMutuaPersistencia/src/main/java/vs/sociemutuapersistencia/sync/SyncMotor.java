package vs.sociemutuapersistencia.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Esta clase se encarga de la sincronizacion con la base de datos de Firebase.
 * @author Samuel Vega
 */
public class SyncMotor {
    private final ScheduledExecutorService plan = Executors.newScheduledThreadPool(1);
    private final ISocioRepository localSocioRepo;
    private final ISocioRepository cloudSocioRepo;
    private final IIglesiaRepository localIglesiaRepo;
    private final IIglesiaRepository cloudIglesiaRepo;
    private final IMensualidadRepository localMensualidadRepo;
    private final IMensualidadRepository cloudMensualidadRepo;

    public SyncMotor(ISocioRepository localSocioRepo, ISocioRepository cloudSocioRepo, IIglesiaRepository localIglesiaRepo, IIglesiaRepository cloudIglesiaRepo, IMensualidadRepository localMensualidadRepo, IMensualidadRepository cloudMensualidadRepo) {
        this.localSocioRepo = localSocioRepo;
        this.cloudSocioRepo = cloudSocioRepo;
        this.localIglesiaRepo = localIglesiaRepo;
        this.cloudIglesiaRepo = cloudIglesiaRepo;
        this.localMensualidadRepo = localMensualidadRepo;
        this.cloudMensualidadRepo = cloudMensualidadRepo;
    }
    
    public void iniciarSincronizacionAutomatica() {
        Connection connection = ConnectionManager.getConnection();
        
        plan.scheduleAtFixedRate(() -> {
            try(
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tabla_sync");
                ResultSet rs = stmt.executeQuery()
            ) {
                while(rs.next()) {
                    long idSync = rs.getLong("id");
                    String entidad = rs.getString("entidad");
                    long entidadId = rs.getLong("entidad_id");
                    String accion = rs.getString("accion");
                    
                    boolean exito = procesarSincronizacion(entidad, entidadId, accion);
                    
                    if(exito) {
                        try(PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM tabla_sync WHERE id = ?")) {
                            deleteStmt.setLong(1, idSync);
                            deleteStmt.executeUpdate();
                        }
                    }
                }
            }catch(SQLException e) {
                System.out.println("Error procesando la cola: " + e.getMessage());
            }

            System.out.println("Revisando operaciones pendientes...");
        }, 0, 1, TimeUnit.MINUTES);
    }
    
    private boolean procesarSincronizacion(String entidad, long entidadId, String accion) {
        try {
            if(entidad.equalsIgnoreCase("socio")) {
                if(accion.equalsIgnoreCase("INSERT") || accion.equalsIgnoreCase("UPDATE")) {
                    Socio socioLocal = localSocioRepo.buscarPorId(entidadId);
                    if(socioLocal != null) cloudSocioRepo.guardar(socioLocal);
                }else if(accion.equalsIgnoreCase("DELETE")) cloudSocioRepo.eliminar(entidadId);
            }
            
            if(entidad.equalsIgnoreCase("iglesia")) {
                if(accion.equalsIgnoreCase("INSERT") || accion.equalsIgnoreCase("UPDATE")) {
                    Iglesia iglesiaLocal = localIglesiaRepo.buscarPorId(entidadId);
                    if(iglesiaLocal != null) cloudIglesiaRepo.guardar(iglesiaLocal);
                }else if(accion.equalsIgnoreCase("DELETE")) cloudIglesiaRepo.eliminar(entidadId);
            }
            
            if(entidad.equalsIgnoreCase("mensualidad")) {
                if(accion.equalsIgnoreCase("INSERT") || accion.equalsIgnoreCase("UPDATE")) {
                    Mensualidad mensualidadLocal = localMensualidadRepo.buscarPorId(entidadId);
                    if(mensualidadLocal != null) cloudMensualidadRepo.guardar(mensualidadLocal);
                }else if(accion.equalsIgnoreCase("DELETE")) cloudMensualidadRepo.eliminar(entidadId);
            }
            
            return true;
        } catch(PersistenceException e) {
            return false;
        }
    }
}
