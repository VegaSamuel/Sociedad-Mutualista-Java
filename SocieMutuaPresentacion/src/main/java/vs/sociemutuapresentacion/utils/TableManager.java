package vs.sociemutuapresentacion.utils;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.MensualidadDTO;
import vs.sociemutuadto.dto.SocioDTO;

/**
 * Esta clase se encarga de elaborar las tablas correspondientes a las entidades.
 * @author Samuel Vega
 */
public class TableManager {
    
    public void cargarDatosEnTabla(JTable tabla, String entidad, List<?> datos) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        switch(entidad.toLowerCase()) {
            case "socios" -> {
                modelo.setColumnIdentifiers(new String[]{"ID", "Nombre Completo", "Iglesia", "Paga"});
                for(Object item : datos) {
                    SocioDTO socio = (SocioDTO) item;
                    modelo.addRow(new Object[]{
                        socio.getId(),
                        socio.getNombreCompleto(),
                        socio.getNombreIglesia(),
                        socio.isPaga() ? "Si" : "No"
                    });
                }
            }
            case "iglesias" -> {
                modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Saldo", "Pastor"});
                for(Object item : datos) {
                    IglesiaDTO iglesia = (IglesiaDTO) item;
                    modelo.addRow(new Object[]{
                        iglesia.getId(),
                        iglesia.getNombre(),
                        iglesia.getSaldo(),
                        iglesia.getPastor()
                    });
                }
            }
            case "mensualidades" -> {
                modelo.setColumnIdentifiers(new String[]{"ID", "Mes", "Año", "Cuota", "Iglesia"});
                for(Object item : datos) {
                    MensualidadDTO mensualidad = (MensualidadDTO) item;
                    modelo.addRow(new Object[]{
                        mensualidad.getId(),
                        mensualidad.getMes(),
                        mensualidad.getAnio(),
                        mensualidad.getCuota(),
                        mensualidad.getNombreIglesia()
                    });
                }
            }
        }

        tabla.setModel(modelo);
    }
}
