package vs.sociemutuapersistencia.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Socio;

/**
 * Esta clase se encarga de la persistencia de los socios en la base de datos en Firebase.
 * @author samue
 */
public class SocioRepositoryFirebaseImpl implements ISocioRepository {

    @Override
    public void guardar(Socio socio) throws PersistenceException {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> docData = new HashMap<>();
        
        docData.put("id_local", socio.getId());
        docData.put("nombres", socio.getNombreCompleto().getNombres());
        docData.put("apellido_paterno", socio.getNombreCompleto().getApellidoPaterno());
        docData.put("apellido_materno", socio.getNombreCompleto().getApellidoMaterno());
        docData.put("fecha_ingreso", socio.getFechaIngreso());
        docData.put("paga", socio.isPaga());
        docData.put("iglesia_id", socio.getIglesia().getId());
        
        try {
            db.collection("socios").document(String.valueOf(socio.getId())).set(docData).get();
        } catch(Exception e) {
            throw new PersistenceException("Error de conexion con Firebase");
        }
    }

    @Override
    public Socio buscarPorId(Long id) throws PersistenceException { return null; }

    @Override
    public List<Socio> obtenerTodos() throws PersistenceException { return null; }

    @Override
    public void actualizar(Socio socio) throws PersistenceException { guardar(socio); }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        FirestoreClient.getFirestore().collection("socios").document(String.valueOf(id)).delete();
    }
    
}
