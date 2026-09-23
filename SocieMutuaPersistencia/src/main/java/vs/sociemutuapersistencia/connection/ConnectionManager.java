package vs.sociemutuapersistencia.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que se encarga de la conexion con la base de datos.
 * @author Samuel Vega
 */
public class ConnectionManager {
    private static Connection con;
    private static final Dotenv env = Dotenv.configure().directory("../SocieMutuaPersistencia").load();
    private static final String URL = env.get("DB_URL");
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
                
                inicializarEstructura(con);
            } catch(SQLException sqle) {
                System.err.println("Error al conectarse con la base de datos: " + sqle.getMessage());
                throw new RuntimeException(sqle);
            }
        }
        return con;
    }
    
    private static void inicializarEstructura(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS iglesias (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(255) NOT NULL, " +
                    "saldo DOUBLE, " +
                    "pastor VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS socios (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombres VARCHAR(255), " +
                    "apellido_paterno VARCHAR(255), " +
                    "apellido_materno VARCHAR(255), " +
                    "fecha_ingreso DATE, " +
                    "paga BOOLEAN, " +
                    "iglesia_id BIGINT, " +
                    "FOREIGN KEY (iglesia_id) REFERENCES iglesias(id) ON DELETE SET NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS mensualidades (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "mes VARCHAR(50), " +
                    "anio VARCHAR(4), " +
                    "cuota DOUBLE, " +
                    "abonos DOUBLE, " +
                    "fecha_creacion DATE, " +
                    "socios_pagan INT, " +
                    "defunciones INT, " +
                    "cargos DOUBLE, " +
                    "iglesia_id BIGINT, " +
                    "FOREIGN KEY (iglesia_id) REFERENCES iglesias(id) ON DELETE SET NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS tabla_sync (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "entidad VARCHAR(50), " +
                    "entidad_id BIGINT, " +
                    "accion VARCHAR(10))");

            System.out.println("Estructura de tablas verificada/creada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear la estructura de tablas: " + e.getMessage());
        }
    }
}
