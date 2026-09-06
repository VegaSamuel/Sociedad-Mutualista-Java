package vs.sociemutuapersistencia.mappers;

import java.sql.ResultSet;
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
    
    public static PersistenceMapper getManager() {
        if(pm == null) pm = new PersistenceMapper();
        
        return pm;
    }
    
    public Socio mapearSocio(ResultSet rs) throws java.sql.SQLException {
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
    
    public Iglesia mapearIglesia(ResultSet rs) throws java.sql.SQLException {
        return new Iglesia(
            rs.getLong("id"),
            rs.getString("nombre"),
            rs.getDouble("saldo"),
            rs.getString("pastor")
        );
    }
    
    public Mensualidad mapearMensualidad(ResultSet rs) throws java.sql.SQLException {
        Iglesia iglesia = new Iglesia();
        iglesia.setId(rs.getLong("iglesia_id"));
        
        return new Mensualidad(
            rs.getLong("id"),
            rs.getString("mes"),
            rs.getDouble("cuota"),
            rs.getDouble("abonos"),
            rs.getDate("fecha_creacion"),
            rs.getInt("socios_pagan"),
            rs.getInt("defunciones"),
            rs.getDouble("cargos"),
            iglesia
        );
    }
}
