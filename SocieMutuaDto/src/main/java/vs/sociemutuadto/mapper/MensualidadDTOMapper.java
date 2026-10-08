package vs.sociemutuadto.mapper;

import vs.sociemutuadominio.models.Iglesia;
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
    
    public static Mensualidad toEntity(MensualidadDTO dto) {
        if(dto == null) return null;
        
        Mensualidad entity = new Mensualidad();
        
        Iglesia iglesiaId = new Iglesia();
        iglesiaId.setId(dto.getIglesiaId());
        iglesiaId.setNombre(dto.getNombreIglesia());
        
        if(dto.getId() != null) entity.setId(dto.getId());
        entity.setMes(dto.getMes());
        entity.setAnio(dto.getAnio());
        entity.setCuota(dto.getCuota());
        entity.setAbonos(dto.getAbonos());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setSociosPagan(dto.getSociosPagan());
        entity.setDefunciones(dto.getDefunciones());
        entity.setCargos(dto.getCargos());
        entity.setIglesia(iglesiaId);
        
        return entity;
    }
}
