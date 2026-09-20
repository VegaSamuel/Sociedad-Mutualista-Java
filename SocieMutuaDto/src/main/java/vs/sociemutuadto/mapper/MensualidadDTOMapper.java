package vs.sociemutuadto.mapper;

import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuadto.dto.MensualidadDTO;

/**
 *
 * @author Samuel Vega
 */
public class MensualidadDTOMapper {
    
    public static MensualidadDTO toDto(Mensualidad mensualidad) {
        if(mensualidad == null) return null;
        
        MensualidadDTO dto = new MensualidadDTO();
        dto.setId(mensualidad.getId());
        dto.setMes(mensualidad.getMes());
        dto.setAnio(mensualidad.getAnio());
        dto.setCuota(mensualidad.getCuota());
        dto.setAbonos(mensualidad.getAbonos());
        dto.setFechaCreacion(mensualidad.getFechaCreacion());
        dto.setSociosPagan(mensualidad.getSociosPagan());
        dto.setDefunciones(mensualidad.getDefunciones());
        dto.setCargos(mensualidad.getCargos());
        
        if(mensualidad.getIglesia() != null) dto.setNombreIglesia(mensualidad.getIglesia().getNombre());
        
        return dto;
    }
}
