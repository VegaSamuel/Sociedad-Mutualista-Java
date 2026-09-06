package vs.sociemutuapersistencia.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de la conexion con la base de datos.
 * @author Samuel Vega
 */
public class ConnectionManager {
    private static Connection con;
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:mysql://localhost:3306/sociedad_mutualista?useSSL=false&serverTimeZone=UTC";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");
    
    private ConnectionManager() {}
    
    /**
     * Singleton que crea o regresa una conexion para interactuar con la base de datos.
     * @return Instancia unica de la conexion con la base de datos.
     */
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion establecida con exito");
            } catch(SQLException sqle) {
                System.err.println("Error al conectarse con la base de datos: " + sqle.getMessage());
                throw new RuntimeException(sqle);
            }
        }
        return con;
    }
}
