package vs.sociemutuapersistencia.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuapersistencia.connection.ConnectionManager;

/**
 * Clase de Test para la interaccion de Mensualidades en la base de datos local.
 * @author Samuel Vega
 */
public class MensualidadRepositoryImplTest {
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement statementMock;
    @Mock
    private ResultSet resultSetMock;
    
    private MensualidadRepositoryImpl instance;
    
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
    }

    /**
     * Test of guardar method, of class MensualidadRepositoryImpl.
     */
    @Test
    public void testGuardar() {
        System.out.println("Probando guardar una Mensualidad...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            instance = new MensualidadRepositoryImpl();
            
            Iglesia iglesia = new Iglesia();
            iglesia.setId(10L);
            Mensualidad mensualidad = new Mensualidad(
                null,
                "Diciembre",
                "2020",
                5000d,
                7500d,
                new java.sql.Date(50000),
                5,
                2,
                6000d,
                iglesia
            );
            
            // Action
            instance.guardar(mensualidad);
            
            // Assert
            verify(statementMock).setString(1, "Diciembre");
            verify(statementMock).setString(2, "2020");
            verify(statementMock).setDouble(4, 7500d);
            verify(statementMock).setInt(7, 2);
            verify(statementMock).setLong(9, 10L);
            
            verify(statementMock, times(1)).executeUpdate();
        }catch(SQLException e) {
            throw new PersistenceException("Error al guardar una Mensualidad");
        }
    }

    /**
     * Test of buscarPorId method, of class MensualidadRepositoryImpl.
     */
    @Test
    public void testBuscarPorId() {
        System.out.println("Probando buscar una Mensualidad por ID...");
        
        // Arrange
        Iglesia iglesia = new Iglesia();
        iglesia.setId(10L);
        Mensualidad mensualidadEsperada = new Mensualidad(
            1L,
            "Agosto",
            "2020",
            5000d,
            7500d,
            new java.sql.Date(50000),
            5,
            2,
            6000d,
            iglesia
        );
        
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(1L);
            when(resultSetMock.getString("mes")).thenReturn("Agosto");
            when(resultSetMock.getString("anio")).thenReturn("2020");
            when(resultSetMock.getDouble("cuota")).thenReturn(5000d);
            when(resultSetMock.getDouble("abonos")).thenReturn(4000d);
            when(resultSetMock.getDate("fecha_creacion")).thenReturn(new java.sql.Date(10000));
            when(resultSetMock.getInt("socios_pagan")).thenReturn(2);
            when(resultSetMock.getInt("defunciones")).thenReturn(5);
            when(resultSetMock.getDouble("cargos")).thenReturn(2500d);
            when(resultSetMock.getLong("iglesia_id")).thenReturn(10L);
            
            instance = new MensualidadRepositoryImpl();
            
            //Action
            Mensualidad mensualidadResultado = instance.buscarPorId(1L);
            
            //Assert
            verify(statementMock).setLong(1, 1L);
            assertEquals(mensualidadEsperada.getId(), mensualidadResultado.getId());
            assertEquals(mensualidadEsperada.getMes(), mensualidadResultado.getMes());
            assertEquals(mensualidadEsperada.getIglesia().getId(), mensualidadResultado.getIglesia().getId());
        } catch (Exception ex) {
            System.out.println("Error al buscar una Mensualidad");
        }
    }

    /**
     * Test of obtenerTodos method, of class MensualidadRepositoryImpl.
     */
    @Test
    public void testObtenerTodos() {
        System.out.println("Probando obtener todas las Mensualidades...");
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true, false);
            
            when(resultSetMock.getLong("id")).thenReturn(15L);
            when(resultSetMock.getString("mes")).thenReturn("Agosto");
            when(resultSetMock.getString("anio")).thenReturn("2020");
            when(resultSetMock.getDouble("cuota")).thenReturn(5000d);
            when(resultSetMock.getDouble("abonos")).thenReturn(4000d);
            when(resultSetMock.getDate("fecha_creacion")).thenReturn(new java.sql.Date(10000));
            when(resultSetMock.getInt("socios_pagan")).thenReturn(2);
            when(resultSetMock.getInt("defunciones")).thenReturn(5);
            when(resultSetMock.getDouble("cargos")).thenReturn(2500d);
            when(resultSetMock.getLong("iglesia_id")).thenReturn(15L);
            
            instance = new MensualidadRepositoryImpl();
            
            // Action
            List<Mensualidad> result = instance.obtenerTodos();
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(15L, result.get(0).getId());
            assertEquals("Agosto", result.get(0).getMes());
            verify(connectionMock).prepareStatement("SELECT * FROM mensualidades");
            verify(statementMock).executeQuery();
        }catch(Exception ex) {
            System.out.println("Error al obtener las Mensualidades");
        }
    }

    /**
     * Test of actualizar method, of class MensualidadRepositoryImpl.
     */
    @Test
    public void testActualizar() {
        System.out.println("Probando actualizar una Mensualidad...");
        
        Iglesia iglesia = new Iglesia();
        iglesia.setId(20L);
        Mensualidad mensualidad = new Mensualidad(
            4L,
            "Diciembre",
            "2020",
            5000d,
            7500d,
            new java.sql.Date(50000),
            5,
            2,
            6000d,
            iglesia
        );
        
        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            // Arrange
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(statementMock.executeQuery()).thenReturn(resultSetMock);
            
            instance = new MensualidadRepositoryImpl();
            
            // Action
            instance.actualizar(mensualidad);
            
            // Assert
            verify(statementMock).setString(1, "Diciembre");
            verify(statementMock).setString(2, "2020");
            verify(statementMock).setDouble(3, 5000d);
            verify(statementMock).setDouble(4, 7500d);
            verify(statementMock).setDate(5, new java.sql.Date(50000));
            verify(statementMock).setInt(6, 5);
            verify(statementMock).setInt(7, 2);
            verify(statementMock).setDouble(8, 6000d);
            verify(statementMock).setLong(9, 20L);
            verify(statementMock).setLong(10, 4L);
            
            verify(statementMock, times(1)).executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al actualizar una Mensualidad");
        }
    }

    /**
     * Test of eliminar method, of class MensualidadRepositoryImpl.
     */
    @Test
    public void testEliminar() {
        System.out.println("Probando eliminar una Mensualidad...");
        
        // Arrange
        Long idEliminar = 15L;

        try(MockedStatic<ConnectionManager> managerMock = mockStatic(ConnectionManager.class)) {
            managerMock.when(ConnectionManager::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
            
            instance = new MensualidadRepositoryImpl();
            
            // Action
            instance.eliminar(idEliminar);
            
            // Assert
            verify(statementMock).setLong(1, idEliminar);
            verify(statementMock, times(1)).executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al eliminar una Mensualidad");
        }
    }
    
}
