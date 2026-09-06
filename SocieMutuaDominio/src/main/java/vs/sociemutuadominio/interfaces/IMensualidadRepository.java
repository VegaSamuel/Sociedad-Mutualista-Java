package vs.sociemutuadominio.interfaces;

import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.models.Mensualidad;

/**
 * Interfaz que define las acciones de persistencia para una mensualidad.
 * @author Samuel Vega
 */
public interface IMensualidadRepository {
    
    /**
     * Guarda una mensualidad en la base de datos.
     * @param mensualidad Mensualidad a guardar.
     */
    void guardar(Mensualidad mensualidad) throws PersistenceException;
    
    /**
     * Busca una mensualidad en la base de datos.
     * @param id ID del mensualidad a buscar.
     * @return Mensualidad encontrada.
     */
    Mensualidad buscarPorId(Long id) throws PersistenceException;
    
    /**
     * Recupera todas las mensualidades existentes en la base de datos.
     * @return Todas las mensualidades que hay en la base de datos.
     */
    List<Mensualidad> obtenerTodos() throws PersistenceException;
    
    /**
     * Actualiza una mensualidad en la base de datos.
     * @param mensualidad Mensualidad actualizada.
     */
    void actualizar(Mensualidad mensualidad) throws PersistenceException;
    
    /**
     * Elimina una mensualidad de la base de datos.
     * @param id ID de la mensualidad a eliminar.
     */
    void eliminar(Long id) throws PersistenceException;
    
}
