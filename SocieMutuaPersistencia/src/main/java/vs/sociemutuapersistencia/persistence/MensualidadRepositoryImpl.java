package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuapersistencia.connection.ConnectionManager;
import vs.sociemutuapersistencia.mappers.PersistenceMapper;

/**
 * Clase encargada de la persistencia de las mensualidades
 * @author Samuel Vega
 */
public class MensualidadRepositoryImpl implements IMensualidadRepository {
    private final Connection connection;
    private final PersistenceMapper pm;
    
    public MensualidadRepositoryImpl() {
        this.connection = ConnectionManager.getConnection();
        this.pm = PersistenceMapper.getManager();
    }
    
    @Override
    public void guardar(Mensualidad mensualidad) throws PersistenceException {
        String sql = "INSERT INTO mensualidades(mes, cuota, abonos, fecha_creacion, socios_pagan, defunciones, cargos, id_iglesia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mensualidad.getMes());
            stmt.setDouble(2, mensualidad.getCuota());
            stmt.setDouble(3, mensualidad.getAbonos());
            stmt.setDate(4, mensualidad.getFechaCreacion());
            stmt.setInt(5, mensualidad.getSociosPagan());
            stmt.setInt(6, mensualidad.getDefunciones());
            stmt.setDouble(7, mensualidad.getCargos());
            stmt.setLong(8, mensualidad.getIglesia().getId());
            stmt.executeUpdate();
        }catch(Exception e) {
            throw new PersistenceException("Error al guardar una mensualidad");
        }}

    @Override
    public Mensualidad buscarPorId(Long id) throws PersistenceException {
        String sql = "SELECT * FROM mensualidades WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next())
                    return pm.mapearMensualidad(rs);
            }
        } catch(Exception e) {
            throw new PersistenceException("Error buscar la mensualidad con ID: " + id);
        }
        
        return null;}

    @Override
    public List<Mensualidad> obtenerTodos() throws PersistenceException {
        String sql = "SELECT * FROM mensualidades";
        List<Mensualidad> socios = new ArrayList<>();
        
        try(
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while(rs.next()) {
                socios.add(pm.mapearMensualidad(rs));
            }
        } catch(Exception e) {
            throw new PersistenceException("Error al consultar la lista de mensualidades");
        }
        
        return socios;
    }

    @Override
    public void actualizar(Mensualidad mensualidad) throws PersistenceException {
        String sql = "UPDATE mensualidades SET mes = ?, cuota = ?, abonos = ?, fecha_creacion = ?, socios_pagan = ?, defunciones = ?, cargos = ?, iglesia_id = ? WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mensualidad.getMes());
            stmt.setDouble(2, mensualidad.getCuota());
            stmt.setDouble(3, mensualidad.getAbonos());
            stmt.setDate(4, mensualidad.getFechaCreacion());
            stmt.setInt(5, mensualidad.getSociosPagan());
            stmt.setInt(6, mensualidad.getDefunciones());
            stmt.setDouble(7, mensualidad.getCargos());
            stmt.setLong(8, mensualidad.getIglesia().getId());
            stmt.setLong(9, mensualidad.getId());
            
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al actualizar la informacion de la mensualidad con ID: " + mensualidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        String sql = "DELETE FROM mensualidades WHERE id = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch(Exception e) {
            throw new PersistenceException("Error al eliminar la mensualidad con ID: " + id);
        }
    }
    
}
