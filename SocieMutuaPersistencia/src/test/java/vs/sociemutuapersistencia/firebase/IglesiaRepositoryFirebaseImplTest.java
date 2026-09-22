package vs.sociemutuapersistencia.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import vs.sociemutuadominio.models.Iglesia;

/**
 * Clase de Test para la interaccion de Iglesias con la base de datos en la nube.
 * @author Samuel Vega
 */
public class IglesiaRepositoryFirebaseImplTest {
    @Mock private Firestore firestoreMock;
    @Mock private CollectionReference collectionMock;
    @Mock private DocumentReference documentMock;
    @Mock private ApiFuture<WriteResult> apiFutureMock;

    /**
     * Test of guardar method, of class IglesiaRepositoryFirebaseImpl.
     */
    
    public void testGuardar() {
        System.out.println("Probando guardar una Iglesia en la nube...");
        
        // Arrange
        Iglesia iglesia = new Iglesia(9L, "Primera Iglesia", 50000d, "Miguel Diaz");
        
        try(MockedStatic<FirestoreClient> clientMock = mockStatic(FirestoreClient.class)) {
            clientMock.when(FirestoreClient::getFirestore).thenReturn(firestoreMock);
            
            when(firestoreMock.collection("iglesias")).thenReturn(collectionMock);
            when(collectionMock.document("9")).thenReturn(documentMock);
            
            ArgumentCaptor<Map<String, Object>> mapCaptor = ArgumentCaptor.forClass(Map.class);
            when(documentMock.set(mapCaptor.capture())).thenReturn(apiFutureMock);
            when(apiFutureMock.get()).thenReturn(null);
            
            IglesiaRepositoryFirebaseImpl instance = new IglesiaRepositoryFirebaseImpl();
            
            // Action
            instance.guardar(iglesia);
            
            // Assert
            Map<String, Object> capturado = mapCaptor.getValue();
            assertEquals(9L, capturado.get("id_local"));
            assertEquals("Primera Iglesia", capturado.get("nombre"));
            assertEquals(50000d, capturado.get("saldo"));
            assertEquals("Miguel Diaz", capturado.get("pastor"));
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al guardar la Iglesia en la nube");
        }
    }

    /**
     * Test of eliminar method, of class IglesiaRepositoryFirebaseImpl.
     */
    
    public void testEliminar() {
        System.out.println("Probando eliminar una Iglesia en la nube...");
        
        try(MockedStatic<FirestoreClient> clientMock = mockStatic(FirestoreClient.class)) {
            // Arrange
            clientMock.when(FirestoreClient::getFirestore).thenReturn(firestoreMock);
            
            when(firestoreMock.collection("iglesias")).thenReturn(collectionMock);
            when(collectionMock.document("15")).thenReturn(documentMock);
            
            IglesiaRepositoryFirebaseImpl instance = new IglesiaRepositoryFirebaseImpl();
            
            // Action
            instance.eliminar(15L);
            
            // Assert
            verify(documentMock, times(1)).delete();
        }
    }
    
}
