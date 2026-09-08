package vs.sociemutuapersistencia.sync.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Esta clase se encarga de la sincronizacion de las bases de datos sobre la entidad Mensualidad.
 * @author Samuel Vega
 */
public class MensualidadRepositorySyncImpl implements IMensualidadRepository {
    private final IMensualidadRepository localRepo;
    private final IMensualidadRepository cloudRepo;
    private final Connection connection;
    
    public MensualidadRepositorySyncImpl(IMensualidadRepository localRepo, IMensualidadRepository cloudRepo) {
        this.localRepo = localRepo;
        this.cloudRepo = cloudRepo;
        this.connection = ConnectionManager.getConnection();
    }

    @Override
    public void guardar(Mensualidad mensualidad) throws PersistenceException {
        localRepo.guardar(mensualidad);
        
        new Thread(() -> {
            try {
                cloudRepo.guardar(mensualidad);
            }catch(PersistenceException e) {
                this.guardarEnColaDeSincronizacion(mensualidad.getId(), "INSERT");
            }
        }).start();
    }

    @Override
    public Mensualidad buscarPorId(Long id) throws PersistenceException { return localRepo.buscarPorId(id); }

    @Override
    public List<Mensualidad> obtenerTodos() throws PersistenceException { return localRepo.obtenerTodos(); }

    @Override
    public void actualizar(Mensualidad mensualidad) throws PersistenceException {
        localRepo.actualizar(mensualidad);
        
        new Thread(() -> {
            try {
                cloudRepo.actualizar(mensualidad);
            }catch(PersistenceException e) {
                this.guardarEnColaDeSincronizacion(mensualidad.getId(), "UPDATE");
            }
        }).start();
    }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        localRepo.eliminar(id);
        
        new Thread(() -> {
            try {
                cloudRepo.eliminar(id);
            }catch(PersistenceException e) {
                this.guardarEnColaDeSincronizacion(id, "DELETE");
            }
        }).start();
    }
    
    private void guardarEnColaDeSincronizacion(Long entidadId, String accion) {
        System.out.println("Guardado en cola para reintento: " + accion + " - ID: " + entidadId);
        
        String sql = "INSERT INTO tabla_sync(entidad, entidad_id, accion) VALUES ('mensualidad', ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, entidadId);
            stmt.setString(2, accion);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
