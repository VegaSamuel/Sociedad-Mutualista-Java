package vs.sociemutuapersistencia.sync.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Esta clase se encarga de la sincronizacion de las bases de datos sobre la entidad Socios.
 * @author Samuel Vega
 */
public class SocioRepositorySyncImpl implements ISocioRepository {
    private final ISocioRepository localRepo;
    private final ISocioRepository cloudRepo;
    private final Connection connection;
    
    public SocioRepositorySyncImpl(ISocioRepository localRepo, ISocioRepository cloudRepo) {
        this.localRepo = localRepo;
        this.cloudRepo = cloudRepo;
        this.connection = ConnectionManager.getConnection();
    }

    @Override
    public void guardar(Socio socio) throws PersistenceException {
        localRepo.guardar(socio);
        
        new Thread(() -> {
            try {
                cloudRepo.guardar(socio);
            }catch(PersistenceException e) {
                this.guardarEnColaDeSincronizacion(socio.getId(), "INSERT");
            }
        }).start();
    }

    @Override
    public Socio buscarPorId(Long id) throws PersistenceException { return localRepo.buscarPorId(id); }

    @Override
    public List<Socio> obtenerTodos() throws PersistenceException { return localRepo.obtenerTodos(); }

    @Override
    public void actualizar(Socio socio) throws PersistenceException {
        localRepo.actualizar(socio);
        
        new Thread(() -> {
            try {
                cloudRepo.actualizar(socio);
            }catch(PersistenceException e) {
                this.guardarEnColaDeSincronizacion(socio.getId(), "UPDATE");
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
        
        String sql = "INSERT INTO tabla_sync(entidad, entidad_id, accion) VALUES ('socio', ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, entidadId);
            stmt.setString(2, accion);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
