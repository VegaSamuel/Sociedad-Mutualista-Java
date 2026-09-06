package vs.sociemutuadominio.interfaces;

import java.util.List;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.models.Iglesia;

/**
 * Interfaz que define las acciones de persistencia para una iglesia.
 * @author Samuel Vega
 */
public interface IIglesiaRepository {
    
    /**
     * Guarda una iglesia en la base de datos.
     * @param iglesia Iglesia a guardar.
     */
    void guardar(Iglesia iglesia) throws PersistenceException;
    
    /**
     * Busca una iglesia en la base de datos.
     * @param id ID de la iglesia a buscar.
     * @return Iglesia encontrada.
     */
    Iglesia buscarPorId(Long id) throws PersistenceException;
    
    /**
     * Recupera todas las iglesias existentes en la base de datos.
     * @return Todas las iglesias que hay en la base de datos.
     */
    List<Iglesia> obtenerTodos() throws PersistenceException;
    
    /**
     * Actualiza una iglesia en la base de datos.
     * @param iglesia Iglesia actualizada.
     */
    void actualizar(Iglesia iglesia) throws PersistenceException;
    
    /**
     * Elimina una iglesia de la base de datos.
     * @param id ID del iglesia a eliminar.
     */
    void eliminar(Long id) throws PersistenceException;
    
}
