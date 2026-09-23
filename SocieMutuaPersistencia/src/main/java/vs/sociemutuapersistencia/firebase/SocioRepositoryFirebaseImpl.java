package vs.sociemutuapersistencia.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vs.sociemutuadominio.exceptions.PersistenceException;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.NombreCompleto;
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
    public List<Socio> obtenerTodos() throws PersistenceException {
        Firestore db = FirestoreClient.getFirestore();
        List<Socio> socios = new ArrayList<>();
        
        try {
            List<QueryDocumentSnapshot> documents = db.collection("socios").get().get().getDocuments();
            
            for(QueryDocumentSnapshot doc : documents) {
                Socio socio = new Socio();
                
                if(doc.getLong("id_local") != null) socio.setId(doc.getLong("id_local"));
                socio.setNombreCompleto(new NombreCompleto(
                        doc.getString("nombres"),
                        doc.getString("apellido_paterno"),
                        doc.getString("apellido_materno")
                ));
                socio.setFechaIngreso((Date) doc.getDate("fecha_ingreso"));
                socio.setPaga(doc.getBoolean("paga"));
                if(doc.getLong("iglesia_id") != null) {
                    Iglesia iglesia = new Iglesia();
                    iglesia.setId(doc.getLong("iglesia_id"));
                    socio.setIglesia(iglesia);
                }

                socios.add(socio);
            }
            
            return socios;
        }catch(Exception e) {
            throw new PersistenceException("Error al descargar el respaldo de Socios: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Socio socio) throws PersistenceException { guardar(socio); }

    @Override
    public void eliminar(Long id) throws PersistenceException {
        FirestoreClient.getFirestore().collection("socios").document(String.valueOf(id)).delete();
    }
    
}
