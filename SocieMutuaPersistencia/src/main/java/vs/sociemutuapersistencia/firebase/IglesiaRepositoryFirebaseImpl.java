package vs.sociemutuapersistencia.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.models.Iglesia;

/**
 * Esta clase se encarga de la persistencia de las iglesias en la base de datos en Firebase.
 * @author Samuel Vega
 */
public class IglesiaRepositoryFirebaseImpl implements IIglesiaRepository {

    @Override
    public void guardar(Iglesia iglesia) throws PersistenceException {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> docData = new HashMap<>();
        
        docData.put("id_local", iglesia.getId());
        docData.put("nombre", iglesia.getNombre());
        docData.put("saldo", iglesia.getSaldo());
        docData.put("pastor", iglesia.getPastor());
        
        try {
            db.collection("iglesias").document(String.valueOf(iglesia.getId())).set(docData).get();
        } catch(Exception e) {
            throw new PersistenceException("Error de conexion con Firebase");
        }
    }

    @Override
    public Iglesia buscarPorId(Long id) throws PersistenceException { return null; }

    @Override
    public List<Iglesia> obtenerTodos() throws PersistenceException { return null; }

    @Override
    public void actualizar(Iglesia iglesia) throws PersistenceException { guardar(iglesia); }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        FirestoreClient.getFirestore().collection("iglesias").document(String.valueOf(id)).delete();
    }
    
}
