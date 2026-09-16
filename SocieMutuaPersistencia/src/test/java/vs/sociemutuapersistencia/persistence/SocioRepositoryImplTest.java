package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.NombreCompleto;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Clase de Test para la interaccion de Socios en la base de datos local.
 * @author samue
 */
public class SocioRepositoryImplTest {
    @Mock
    private Connection connectionMock;
    @Mock 
    private PreparedStatement statementMock;
    @Mock 
    private ResultSet resultSetMock;
    
    private SocioRepositoryImpl instance;
    
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
    }

    /**
     * Test of guardar method, of class SocioRepositoryImpl.
     */
    @Test
    public void testGuardarValido() {
        System.out.println("Probando guardar un Socio valido...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            instance = new SocioRepositoryImpl();
            
            Iglesia iglesia = new Iglesia();
            iglesia.setId(10L);
            NombreCompleto nombre = new NombreCompleto("Miguel", "Diaz", "Morelos");
            Socio socio = new Socio(
                    null,
                    nombre,
                    new java.sql.Date(System.currentTimeMillis()),
                    true,
                    iglesia
            );
            
            //Action
            instance.guardar(socio);
            
            //Assert
            verify(statementMock).setString(1, "Miguel");
            verify(statementMock).setString(2, "Diaz");
            verify(statementMock).setBoolean(5, true);
            verify(statementMock).setLong(6, 10L);
            
            verify(statementMock, times(1)).executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al guardar un Socio");
        }
    }

    /**
     * Test of buscarPorId method, of class SocioRepositoryImpl.
     */
    @Test
    public void testBuscarPorId() {
        System.out.println("Probando buscar un Socio por ID...");
        
        // Arrange
        Iglesia iglesia = new Iglesia();
        iglesia.setId(10L);
        NombreCompleto nombre = new NombreCompleto("Miguel", "Diaz", "Morelos");
        Socio socioEsperado = new Socio(
                1L,
                nombre,
                new java.sql.Date(500000),
                true,
                iglesia
        );
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(1L);
            when(resultSetMock.getString("nombres")).thenReturn("Miguel");
            when(resultSetMock.getString("apellido_paterno")).thenReturn("Diaz");
            when(resultSetMock.getString("apellido_materno")).thenReturn("Morelos");
            when(resultSetMock.getDate("fecha_ingreso")).thenReturn(new java.sql.Date(500000));
            when(resultSetMock.getObject("paga")).thenReturn(true);
            when(resultSetMock.getBoolean("paga")).thenReturn(true);
            when(resultSetMock.getLong("iglesia_id")).thenReturn(10L);
            
            instance = new SocioRepositoryImpl();
            
            //Action
            Socio socioResultado = instance.buscarPorId(1L);
            
            //Assert
            verify(statementMock).setLong(1, 1L);
            assertEquals(socioEsperado.getId(), socioResultado.getId());
            assertEquals(socioEsperado.getNombreCompleto().getNombres(), socioResultado.getNombreCompleto().getNombres());
            assertEquals(socioEsperado.getIglesia().getId(), socioResultado.getIglesia().getId());
        } catch (Exception ex) {
            System.out.println("Error al buscar un Socio");
        }
    }

    /**
     * Test of obtenerTodos method, of class SocioRepositoryImpl.
     */
    @Test
    public void testObtenerTodos() {
        System.out.println("Probando obtener todos los Socios...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(100L);
            when(resultSetMock.getString("nombres")).thenReturn("Dania");
            when(resultSetMock.getString("apellido_paterno")).thenReturn("Rios");
            when(resultSetMock.getString("apellido_materno")).thenReturn("Mendoza");
            when(resultSetMock.getDate("fecha_ingreso")).thenReturn(new java.sql.Date(10000));
            when(resultSetMock.getObject("paga")).thenReturn(true);
            when(resultSetMock.getBoolean("paga")).thenReturn(true);
            when(resultSetMock.getLong("iglesia_id")).thenReturn(5L);
            
            instance = new SocioRepositoryImpl();
            
            // Action
            List<Socio> result = instance.obtenerTodos();
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(100L, result.get(0).getId());
            assertEquals("Dania", result.get(0).getNombreCompleto().getNombres());
            verify(connectionMock).prepareStatement("SELECT * FROM socios");
            verify(statementMock).executeQuery();
        }catch(Exception ex) {
            System.out.println("Error al obtener los Socios");
        }
    }

    /**
     * Test of actualizar method, of class SocioRepositoryImpl.
     */
    @Test
    public void testActualizar() {
        System.out.println("Probando actualizar un Socio...");
        
        Iglesia iglesia = new Iglesia();
        iglesia.setId(20L);
        NombreCompleto nombres = new NombreCompleto("Samuel", "Vega", "Gomez");
        Socio socio = new Socio(
            5L,
            nombres,
            new java.sql.Date(500000),
            true,
            iglesia
        );
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            
            instance = new SocioRepositoryImpl();
            
            // Action
            instance.actualizar(socio);
            
            // Assert
            verify(statementMock).setString(1, "Samuel");
            verify(statementMock).setString(2, "Vega");
            verify(statementMock).setString(3, "Gomez");
            verify(statementMock).setDate(4, socio.getFechaIngreso());
            verify(statementMock).setBoolean(5, true);
            verify(statementMock).setLong(6, 20L);
            verify(statementMock).setLong(7, 5L);
            
            verify(statementMock, times(1)).executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al actualizar un Socio");
        }
    }
    
    /**
     * Test of exception on actualizar method
     * @throws Exception PersistenceException
     */
    @Test
    public void testActualizar_lanzaPersistenceException() throws Exception {
        System.out.println("Probando lanzamiento de excepcion al actualizar un Socio...");
        
        // Arrange
        Iglesia iglesia = new Iglesia();
        iglesia.setId(10L);
        Socio socio = new Socio(1L, new NombreCompleto("A", "B", "C"), new java.sql.Date(100), false, iglesia);
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenThrow(new SQLException("Caida de red simulada"));
            
            instance = new SocioRepositoryImpl();
            
            // Assert
            assertThrows(PersistenceException.class, () -> {
                instance.actualizar(socio);
            });
        }
    }

    /**
     * Test of eliminar method, of class SocioRepositoryImpl.
     */
    @Test
    public void testEliminar() {
        System.out.println("Probando eliminar un socio...");
        
        // Arrange
        Long idEliminar = 15L;

        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            
            instance = new SocioRepositoryImpl();
            
            // Action
            instance.eliminar(idEliminar);
            
            // Assert
            verify(statementMock).setLong(1, idEliminar);
            verify(statementMock, times(1)).executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al eliminar un Socio");
        }
    }
    
}
