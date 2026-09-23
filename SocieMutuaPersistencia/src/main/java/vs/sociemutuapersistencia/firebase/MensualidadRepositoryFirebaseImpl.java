package vs.sociemutuapersistencia.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.models.Iglesia;
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
        docData.put("anio", mensualidad.getAnio());
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
    public List<Mensualidad> obtenerTodos() throws PersistenceException {
        Firestore db = FirestoreClient.getFirestore();
        List<Mensualidad> mensualidades = new ArrayList<>();
        
        try {
            List<QueryDocumentSnapshot> documents = db.collection("mensualidades").get().get().getDocuments();
            
            for(QueryDocumentSnapshot doc : documents) {
                Mensualidad mensualidad = new Mensualidad();
                
                if(doc.getLong("id_local") != null) mensualidad.setId(doc.getLong("id_local"));
                mensualidad.setMes(doc.getString("mes"));
                mensualidad.setAnio(doc.getString("anio"));
                mensualidad.setCuota(doc.getDouble("cuota"));
                mensualidad.setAbonos(doc.getDouble("abonos"));
                mensualidad.setFechaCreacion((Date) doc.getDate("fecha_ingreso"));
                mensualidad.setSociosPagan(doc.getLong("socios_pagan"));
                mensualidad.setDefunciones(doc.getLong("defunciones"));
                mensualidad.setCargos(doc.getDouble("cargos"));
                if(doc.getLong("iglesia_id") != null) {
                    Iglesia iglesia = new Iglesia();
                    iglesia.setId(doc.getLong("iglesia_id"));
                    mensualidad.setIglesia(iglesia);
                }

                mensualidades.add(mensualidad);
            }
            
            return mensualidades;
        }catch(Exception e) {
            throw new PersistenceException("Error al descargar el respaldo de Socios: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Mensualidad mensualidad) throws PersistenceException { guardar(mensualidad); }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        FirestoreClient.getFirestore().collection("mensualidades").document(String.valueOf(id)).delete();
    }
    
}
