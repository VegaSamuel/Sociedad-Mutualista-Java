package vs.sociemutuadto.mapper;

import java.sql.Date;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.NombreCompleto;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuadto.dto.SocioDTO;
import vs.sociemutuadto.util.NameSeparator;

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
    
    public static Socio toEntity(SocioDTO dto) {
        if(dto == null) return null;
        
        NameSeparator separador = new NameSeparator(dto.getNombreCompleto());
        Socio entity = new Socio();
        
        Iglesia iglesiaId = new Iglesia();
        iglesiaId.setId(dto.getIglesiaId());
        
        if(dto.getId() != null) entity.setId(dto.getId());
        
        entity.setNombreCompleto(new NombreCompleto(
            separador.getNombres(), 
            separador.getApellidoPaterno(),
            separador.getApellidoMaterno())
        );
        entity.setIglesia(iglesiaId);
        entity.setPaga(dto.isPaga());
        entity.setFechaIngreso(new Date(0));
        
        return entity;
    }
}
