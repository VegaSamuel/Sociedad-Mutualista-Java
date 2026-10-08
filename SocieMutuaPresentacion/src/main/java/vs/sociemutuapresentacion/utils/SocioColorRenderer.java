package vs.sociemutuapresentacion.utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import vs.sociemutuadto.dto.SocioDTO;

/**
 * Se encarga del renderizado de las listas.
 * @author Samuel Vega
 */
public class SocioColorRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if(value instanceof SocioDTO) {
            SocioDTO socio = (SocioDTO) value;
            setText(socio.getNombreCompleto());
            
            if(!isSelected) {
                if(socio.isPaga()) {
                    c.setForeground(new Color(153, 204, 0));
                } else {
                    c.setForeground(new Color(255, 51, 51));
                }
            } else c.setForeground(Color.BLACK);
        }
        
        return c;
    }
    
}
