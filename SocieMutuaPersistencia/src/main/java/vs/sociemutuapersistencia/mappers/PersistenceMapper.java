package vs.sociemutuapersistencia.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuadominio.models.NombreCompleto;
import vs.sociemutuadominio.models.Socio;

/**
 * Clase que se encarga de mapear los resultados de las consultas de la base
 * de datos para facilitar lectura de las entidades.
 * @author Samuel Vega
 */
public class PersistenceMapper {
    private static PersistenceMapper pm;
    
    private PersistenceMapper() {}
    
    /**
     * Metodo Singleton para obtner un unico mapeador.
     * @return Mapeador de las entidades de persistencia.
     */
    public static PersistenceMapper getManager() {
        if(pm == null) pm = new PersistenceMapper();
        
        return pm;
    }
    
    /**
     * Mapea un Socio traido de la base de datos.
     * @param rs Resultado de la base de datos.
     * @return Un Socio con todos sus atributos.
     * @throws java.sql.SQLException Fallo de formato.
     */
    public Socio mapearSocio(ResultSet rs) throws SQLException {
        NombreCompleto nombre = new NombreCompleto(
            rs.getString("nombres"),
            rs.getString("apellido_paterno"),
            rs.getString("apellido_materno")
        );
        
        Iglesia iglesia = new Iglesia();
        iglesia.setId(rs.getLong("iglesia_id"));
        
        return new Socio(
            rs.getLong("id"),
            nombre,
            rs.getDate("fecha_ingreso"),
            rs.getBoolean("paga"),
            iglesia
        );
    }
    
    /**
     * Mapea una Iglesia traido de la base de datos.
     * @param rs Resultado de la base de datos.
     * @return Una Iglesia con todos sus atributos.
     * @throws java.sql.SQLException Fallo de formato.
     */
    public Iglesia mapearIglesia(ResultSet rs) throws SQLException {
        return new Iglesia(
            rs.getLong("id"),
            rs.getString("nombre"),
            rs.getDouble("saldo"),
            rs.getString("pastor")
        );
    }
    
    /**
     * Mapea una Mensualidad traido de la base de datos.
     * @param rs Resultado de la base de datos.
     * @return Una Mensualidad con todos sus atributos.
     * @throws java.sql.SQLException Fallo de formato.
     */
    public Mensualidad mapearMensualidad(ResultSet rs) throws SQLException {
        Iglesia iglesia = new Iglesia();
        iglesia.setId(rs.getLong("iglesia_id"));
        
        return new Mensualidad(
            rs.getLong("id"),
            rs.getString("mes"),
            rs.getString("anio"),
            rs.getDouble("cuota"),
            rs.getDouble("abonos"),
            rs.getDate("fecha_creacion"),
            rs.getLong("socios_pagan"),
            rs.getLong("defunciones"),
            rs.getDouble("cargos"),
            iglesia
        );
    }
}
