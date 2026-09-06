package vs.sociemutuapersistencia.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.models.Mensualidad;

/**
 * Esta clase se encarga de la persistencia de las mensualidades en la base de datos en Firebase.
 * @author Samuel Vega
 */
public class MensualidadRepositoryFirebaseImpl implements IMensualidadRepository {

    @Override
    public void guardar(Mensualidad mensualidad) throws PersistenceException {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> docData = new HashMap<>();
        
        docData.put("id_local", mensualidad.getId());
        docData.put("mes", mensualidad.getMes());
        docData.put("cuota", mensualidad.getCuota());
        docData.put("abonos", mensualidad.getAbonos());
        docData.put("fecha_creacion", mensualidad.getFechaCreacion());
        docData.put("socios_pagan", mensualidad.getSociosPagan());
        docData.put("defunciones", mensualidad.getDefunciones());
        docData.put("cargos", mensualidad.getCargos());
        docData.put("iglesia_id", mensualidad.getIglesia().getId());
        
        try {
            db.collection("mensualidades").document(String.valueOf(mensualidad.getId())).set(docData).get();
        } catch(Exception e) {
            throw new PersistenceException("Error de conexion con Firebase");
        }
    }

    @Override
    public Mensualidad buscarPorId(Long id) throws PersistenceException { return null; }

    @Override
    public List<Mensualidad> obtenerTodos() throws PersistenceException { return null; }

    @Override
    public void actualizar(Mensualidad mensualidad) throws PersistenceException { guardar(mensualidad); }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        FirestoreClient.getFirestore().collection("mensualidades").document(String.valueOf(id)).delete();
    }
    
}
