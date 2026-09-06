package vs.sociemutuadominio.interfaces;

import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.models.Socio;

/**
 * Interfaz que define las acciones de persistencia para un socio.
 * @author Samuel Vega
 */
public interface ISocioRepository {
    
    /**
     * Guarda un socio en la base de datos.
     * @param socio Socio a guardar.
     */
    void guardar(Socio socio) throws PersistenceException;
    
    /**
     * Busca un socio en la base de datos.
     * @param id ID del socio a buscar.
     * @return Socio encontrado.
     */
    Socio buscarPorId(Long id) throws PersistenceException;
    
    /**
     * Recupera todos los socios existentes en la base de datos.
     * @return Todos los socios que hay en la base de datos.
     */
    List<Socio> obtenerTodos() throws PersistenceException;
    
    /**
     * Actualiza un socio en la base de datos.
     * @param socio Socio actualizado.
     */
    void actualizar(Socio socio) throws PersistenceException;
    
    /**
     * Elimina un socio de la base de datos.
     * @param id ID del socio a eliminar.
     */
    void eliminar(Long id) throws PersistenceException;
    
}
