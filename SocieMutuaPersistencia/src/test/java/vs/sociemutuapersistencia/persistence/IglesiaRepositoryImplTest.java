package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Clase de Test para la interaccion de Iglesias en la base de datos local.
 * @author Samuel Vega
 */
public class IglesiaRepositoryImplTest {
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement statementMock;
    @Mock
    private ResultSet resultSetMock;
    
    private IglesiaRepositoryImpl instance;
    
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
    }
    
    /**
     * Test of guardar method, of class IglesiaRepositoryImpl.
     */
    @Test
    public void testGuardar() {
        System.out.println("Probando guardar una Iglesia...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            instance = new IglesiaRepositoryImpl();
            
            Iglesia iglesia = new Iglesia(
                null,
                "Primera Iglesia",
                10000d,
                "Gildardo Fierro"
            );
            
            // Action
            instance.guardar(iglesia);
            
            // Assert
            verify(statementMock).setString(1, "Primera Iglesia");
            verify(statementMock).setDouble(2, 10000d);
            verify(statementMock).setString(3, "Gildardo Fierro");
            
            verify(statementMock, times(1)).executeUpdate();
        }catch(SQLException ex) {
            System.out.println("Error al guardar una Iglesia");
        }
    }
    
    /**
     * Test of buscarPorId method, of class IglesiaRepositoryImpl.
     */
    @Test
    public void testBuscarPorId() {
        System.out.println("Probando buscar una Iglesia por ID...");
        
        // Arrange
        Iglesia iglesiaEsperada = new Iglesia(
            1L,
            "Primera Iglesia",
            10000d,
            "Gildardo Fierro"
        );
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(1L);
            when(resultSetMock.getString("nombre")).thenReturn("Primera Iglesia");
            when(resultSetMock.getDouble("saldo")).thenReturn(10000d);
            when(resultSetMock.getString("pastor")).thenReturn("Gildardo Fierro");
            
            instance = new IglesiaRepositoryImpl();
            
            // Action
            Iglesia iglesiaResultado = instance.buscarPorId(1L);
            
            // Assert
            verify(statementMock).setLong(1, 1L);
            assertEquals(iglesiaEsperada.getId(), iglesiaResultado.getId());
            assertEquals(iglesiaEsperada.getNombre(), iglesiaResultado.getNombre());
        } catch (Exception ex) {
            System.out.println("Error al buscar una Iglesia");
        }
    }

    /**
     * Test of obtenerTodos method, of class IglesiaRepositoryImpl.
     */
    @Test
    public void testObtenerTodos() {
        System.out.println("Probando obtener todas las Iglesias...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(1L);
            when(resultSetMock.getString("nombre")).thenReturn("Cuarta Iglesia");
            when(resultSetMock.getDouble("saldo")).thenReturn(15000d);
            when(resultSetMock.getString("pastor")).thenReturn("Mario Manjarrez");
            
            instance = new IglesiaRepositoryImpl();
            
            // Action
            List<Iglesia> result = instance.obtenerTodos();
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1L, result.get(0).getId());
            assertEquals("Cuarta Iglesia", result.get(0).getNombre());
            verify(connectionMock).prepareStatement("SELECT * FROM iglesias");
            verify(statementMock).executeQuery();
        }catch(Exception ex) {
            System.out.println("Error al obtener todas las Iglesias");
        }
    }
    
    /**
     * Test of eliminar method, of class IglesiaRepositoryImpl.
     */
    @Test
    public void testEliminar() {
        System.out.println("Probando eliminar una Iglesia...");
        
        // Arrange
        Long idEliminar = 5L;

        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            
            instance = new IglesiaRepositoryImpl();
            
            // Action
            instance.eliminar(idEliminar);
            
            // Assert
            verify(statementMock).setLong(1, idEliminar);
            verify(statementMock, times(1)).executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al buscar una Iglesia");
        }
    }
}
