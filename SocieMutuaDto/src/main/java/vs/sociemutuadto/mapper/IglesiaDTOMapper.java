package vs.sociemutuadto.mapper;

import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadto.dto.IglesiaDTO;

/**
 *
 * @author Samuel Vega
 */
public class IglesiaDTOMapper {
    
    public static IglesiaDTO toDto(Iglesia iglesia) {
        if(iglesia == null) return null;
         
        IglesiaDTO dto = new IglesiaDTO();
        dto.setId(iglesia.getId());
        
        dto.setNombre(iglesia.getNombre());
        dto.setSaldo(iglesia.getSaldo());
        dto.setPastor(iglesia.getPastor());
        
        return dto;
    }
}
