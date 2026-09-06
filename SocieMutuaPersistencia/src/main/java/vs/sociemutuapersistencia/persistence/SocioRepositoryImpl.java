package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuapersistencia.connection.ConnectionManager;
import vs.sociemutuapersistencia.mappers.PersistenceMapper;

/**
 * Clase encargada de la persistencia de los socios.
 * @author Samuel Vega
 */
public class SocioRepositoryImpl implements ISocioRepository {
    private final Connection connection;
    private final PersistenceMapper pm;
    
    /**
     * Constructor de la clase.
     */
    public SocioRepositoryImpl() {
        this.connection = ConnectionManager.getConnection();
        this.pm = PersistenceMapper.getManager();
    }

    @Override
    public void guardar(Socio socio) throws PersistenceException {
        String sql = "INSERT INTO socios(nombres, apellido_paterno, apellido_materno, fecha_ingreso, paga, iglesia_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, socio.getNombreCompleto().getNombres());
            stmt.setString(2, socio.getNombreCompleto().getApellidoPaterno());
            stmt.setString(3, socio.getNombreCompleto().getApellidoMaterno());
            stmt.setDate(4, socio.getFechaIngreso());
            stmt.setBoolean(5, socio.isPaga());
            stmt.setLong(6, socio.getIglesia().getId());
            stmt.executeUpdate();
        }catch(Exception e) {
            throw new PersistenceException("Error al guardar un socio");
        }
    }

    @Override
    public Socio buscarPorId(Long id) throws PersistenceException {
        String sql = "SELECT * FROM socios WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next())
                    return pm.mapearSocio(rs);
            }
        } catch(Exception e) {
            throw new PersistenceException("Error buscar el socio con ID: " + id);
        }
        
        return null;
    }

    @Override
    public List<Socio> obtenerTodos() throws PersistenceException {
        String sql = "SELECT * FROM socios";
        List<Socio> socios = new ArrayList<>();
        
        try(
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while(rs.next()) {
                socios.add(pm.mapearSocio(rs));
            }
        } catch(Exception e) {
            throw new PersistenceException("Error al consultar la lista de socios");
        }
        
        return socios;
    }

    @Override
    public void actualizar(Socio socio) throws PersistenceException {
        String sql = "UPDATE socios SET nombres = ?, apellido_paterno = ?, apellido_materno = ?, fecha_ingreso = ?, paga = ?, iglesia_id = ? WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, socio.getNombreCompleto().getNombres());
            stmt.setString(2, socio.getNombreCompleto().getApellidoPaterno());
            stmt.setString(3, socio.getNombreCompleto().getApellidoMaterno());
            stmt.setDate(4, socio.getFechaIngreso());
            stmt.setBoolean(5, socio.isPaga());
            stmt.setLong(6, socio.getIglesia().getId());
            stmt.setLong(7, socio.getId());
            
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al actualizar la informacion del socio con ID: " + socio.getId());
        }
    }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        String sql = "DELETE FROM socios WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al eliminar al socio con ID: " + id);
        }
    }
    
}
