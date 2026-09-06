package vs.sociemutuadominio.exceptions;

/**
 * Clase para manejar excepciones referentes a la persistencia
 * @author Samuel Vega
 */
public class PersistenceException extends RuntimeException {
    
    public PersistenceException() {}
    
    public PersistenceException(String msg) {
        super(msg);
    }
}
