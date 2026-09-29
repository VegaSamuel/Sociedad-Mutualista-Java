package vs.sociemutuadto.dto;

import java.util.List;
import java.util.Objects;

/**
 * Clase DTO de Iglesia para mostrar informacion en la UI.
 * @author Samuel Vega
 */
public class IglesiaDTO {
    private Long id;
    private String nombre;
    private Double saldo;
    private String pastor;
    private List<SocioDTO> socios;
    
    public IglesiaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getPastor() {
        return pastor;
    }

    public void setPastor(String pastor) {
        this.pastor = pastor;
    }

    public List<SocioDTO> getSocios() {
        return socios;
    }

    public void setSocios(List<SocioDTO> socios) {
        this.socios = socios;
    }

    public void addSocio(SocioDTO socio) {
        this.socios.add(socio);
    }
    
    public void removeSocio(SocioDTO socio) {       
        this.socios.remove(socio);
    }
    
    public boolean hasSocios() {
        if(this.socios != null) {
            return !this.socios.isEmpty();
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IglesiaDTO other = (IglesiaDTO) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nombre;
    }

}
