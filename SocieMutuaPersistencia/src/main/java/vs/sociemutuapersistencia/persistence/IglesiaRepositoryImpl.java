package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuapersistencia.connection.ConnectionManager;
import vs.sociemutuapersistencia.mappers.PersistenceMapper;

/**
 * Clase encargada de la persistencia de las iglesias.
 * @author Samuel Vega
 */
public class IglesiaRepositoryImpl implements IIglesiaRepository {
    private final Connection connection;
    private final PersistenceMapper pm;
    
    public IglesiaRepositoryImpl() {
        this.connection = ConnectionManager.getConnection();
        this.pm = PersistenceMapper.getManager();
    }

    @Override
    public void guardar(Iglesia iglesia) throws PersistenceException {
        String sql = "INSERT INTO iglesias(nombre, saldo, pastor) VALUES (?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, iglesia.getNombre());
            stmt.setDouble(2, iglesia.getSaldo());
            stmt.setString(3, iglesia.getPastor());
            stmt.executeUpdate();
        }catch(Exception e) {
            throw new PersistenceException("Error al guardar una iglesia");
        }
    }

    @Override
    public Iglesia buscarPorId(Long id) throws PersistenceException {
        String sql = "SELECT * FROM iglesias WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next())
                    return pm.mapearIglesia(rs);
            }
        } catch(Exception e) {
            throw new PersistenceException("Error buscar la iglesia con ID: " + id);
        }
        
        return null;
    }

    @Override
    public List<Iglesia> obtenerTodos() throws PersistenceException {
        String sql = "SELECT * FROM iglesias";
        List<Iglesia> socios = new ArrayList<>();
        
        try(
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while(rs.next()) {
                socios.add(pm.mapearIglesia(rs));
            }
        } catch(Exception e) {
            throw new PersistenceException("Error al consultar la lista de iglesias");
        }
        
        return socios;
    }

    @Override
    public void actualizar(Iglesia iglesia) throws PersistenceException {
        String sql = "UPDATE iglesias SET nombre = ?, saldo = ?, pastor = ? WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, iglesia.getNombre());
            stmt.setDouble(2, iglesia.getSaldo());
            stmt.setString(3, iglesia.getPastor());
            
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al actualizar la informacion de la iglesia con ID: " + iglesia.getId());
        }
    }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        String sql = "DELETE FROM iglesias WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al eliminar la iglesia con ID: " + id);
        }
    }
    
}
