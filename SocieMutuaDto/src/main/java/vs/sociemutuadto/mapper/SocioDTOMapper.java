package vs.sociemutuadto.mapper;

import vs.sociemutuadominio.models.Socio;
import vs.sociemutuadto.dto.SocioDTO;

/**
 * Mapeador convertidor de entidades y DTOs de Socio.
 * @author Samuel Vega
 */
public class SocioDTOMapper {
    
    public static SocioDTO toDto(Socio socio) {
        if(socio == null) return null;
        
        SocioDTO dto = new SocioDTO();
        dto.setId(socio.getId());
        
        if(socio.getNombreCompleto() != null) {
            dto.setNombreCompleto(
                socio.getNombreCompleto().getNombres() +
                socio.getNombreCompleto().getApellidoPaterno() +
                socio.getNombreCompleto().getApellidoMaterno()
            );
        }
        
        if(socio.getIglesia() != null) dto.setNombreIglesia(socio.getIglesia().getNombre());
        dto.setPaga(socio.isPaga());
        
        return dto;
    }
}
