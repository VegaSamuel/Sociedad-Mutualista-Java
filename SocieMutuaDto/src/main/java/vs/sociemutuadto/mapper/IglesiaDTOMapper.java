package vs.sociemutuadto.mapper;

import java.util.ArrayList;
import java.util.List;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.SocioDTO;

/**
 * Se encarga de mapear las Iglesias a sus DTOs y viceversa.
 * @author Samuel Vega
 */
public class IglesiaDTOMapper {
    
    /**
     * Convierte una Iglesia en un DTO.
     * @param iglesia Iglesia a convertir.
     * @return El DTO resultante de la conversion
     */
    public static IglesiaDTO toDto(Iglesia iglesia) {
        if(iglesia == null) return null;
        
        IglesiaDTO dto = new IglesiaDTO();
        dto.setId(iglesia.getId());
        
        dto.setNombre(iglesia.getNombre());
        dto.setSaldo(iglesia.getSaldo());
        dto.setPastor(iglesia.getPastor());
        dto.setSocios(convertSociosToDTOList(iglesia.getSocios()));
        
        return dto;
    }
    
    /**
     * Convierte un DTO a una Iglesia.
     * @param dto DTO a convertir.
     * @return La Iglesia resultante de la conversion.
     */
    public static Iglesia toEntity(IglesiaDTO dto) {
        if(dto == null) return null;
        
        Iglesia entity = new Iglesia();
        entity.setId(dto.getId());
        
        entity.setNombre(dto.getNombre());
        entity.setPastor(dto.getPastor());
        entity.setSaldo(dto.getSaldo());
        entity.setSocios(convertSociosToEntityList(dto.getSocios()));
        
        return entity;
    }
    
    /**
     * Convierte la lista de los Socios de la Iglesia a sus DTOs
     * @param socios Socios que pertenecen a la Iglesia.
     * @return Lista de DTOs de Socios.
     */
    public static List<SocioDTO> convertSociosToDTOList(List<Socio> socios) {
        if(socios == null) return null;
        List<SocioDTO> dtos = new ArrayList();
        
        for (Socio socio : socios) {
            dtos.add(SocioDTOMapper.toDto(socio));
        }
         
        return dtos;
    }
    
    /**
     * Convierte la lista de DTOs a Socios que pertenecen a la Iglesia.
     * @param dtos DTOs a convertir.
     * @return Lista de Socios que pertecen a la Iglesia.
     */
    public static List<Socio> convertSociosToEntityList(List<SocioDTO> dtos) {
        if(dtos == null) return null;
        List<Socio> socios = new ArrayList();
        
        for (SocioDTO dto : dtos) {
            socios.add(SocioDTOMapper.toEntity(dto));
        }
         
        return socios;
    }
}
