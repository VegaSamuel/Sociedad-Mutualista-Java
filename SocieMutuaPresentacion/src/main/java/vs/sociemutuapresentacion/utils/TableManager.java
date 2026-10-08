package vs.sociemutuapresentacion.utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.MensualidadDTO;
import vs.sociemutuadto.dto.SocioDTO;

/**
 * Esta clase se encarga de elaborar las tablas correspondientes a las entidades.
 * @author Samuel Vega
 */
public class TableManager {
    
    /**
     * Modifica una tabla conforme a lo brindado.
     * @param tabla Tabla que se modificara.
     * @param entidad Entidad que se muestra en la tabla.
     * @param datos Datos que se reflejaran en la tabla.
     */
    public void cargarDatosEnTabla(JTable tabla, String entidad, List<?> datos) {
        NumberFormat formater = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        
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
                        formater.format(iglesia.getSaldo()),
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
        
        switch(entidad.toLowerCase()) {
            case "socios" -> {
                TableColumnModel columnas = tabla.getColumnModel();
                
                columnas.getColumn(0).setPreferredWidth(40);
                columnas.getColumn(0).setMaxWidth(60);
                
                columnas.getColumn(1).setPreferredWidth(250);
                
                columnas.getColumn(2).setPreferredWidth(150);
                
                columnas.getColumn(3).setPreferredWidth(60);
                columnas.getColumn(3).setMaxWidth(80);
            }
            case "iglesias" -> {
                TableColumnModel columnas = tabla.getColumnModel();
                
                columnas.getColumn(0).setPreferredWidth(40);
                columnas.getColumn(0).setMaxWidth(60);
                
                columnas.getColumn(1).setPreferredWidth(150);
                
                columnas.getColumn(2).setPreferredWidth(120);
                columnas.getColumn(2).setMaxWidth(140);
                
                columnas.getColumn(3).setPreferredWidth(250);
                
            }  
            case "mensualidades" -> {
                 TableColumnModel columnas = tabla.getColumnModel();
                
                columnas.getColumn(0).setPreferredWidth(40);
                columnas.getColumn(0).setMaxWidth(60);
                
                columnas.getColumn(1).setPreferredWidth(250);
                
                columnas.getColumn(2).setPreferredWidth(150);
                
                columnas.getColumn(3).setPreferredWidth(60);
                columnas.getColumn(3).setMaxWidth(80);
                
                columnas.getColumn(4).setPreferredWidth(60);
                columnas.getColumn(4).setMaxWidth(80);
            }
        }
    }
}
