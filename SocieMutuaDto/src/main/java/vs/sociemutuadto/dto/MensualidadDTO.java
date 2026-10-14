package vs.sociemutuadto.dto;

import java.sql.Date;
import java.util.Objects;

/**
 * Clase DTO de Mensualidad para mostrar informacion en la UI.
 * @author Samuel Vega
 */
public class MensualidadDTO {
    private Long id;
    private String mes;
    private String anio;
    private Double cuota;
    private Double abonos;
    private Date fechaCreacion;
    private Long sociosPagan;
    private Long defunciones;
    private Double cargos;
    private String nombreIglesia;
    private Long iglesiaId;
    
    public MensualidadDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public Double getAbonos() {
        return abonos;
    }

    public void setAbonos(Double abonos) {
        this.abonos = abonos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getSociosPagan() {
        return sociosPagan;
    }

    public void setSociosPagan(Long sociosPagan) {
        this.sociosPagan = sociosPagan;
    }

    public Long getDefunciones() {
        return defunciones;
    }

    public void setDefunciones(Long defunciones) {
        this.defunciones = defunciones;
    }

    public Double getCargos() {
        return cargos;
    }

    public void setCargos(Double cargos) {
        this.cargos = cargos;
    }

    public String getNombreIglesia() {
        return nombreIglesia;
    }

    public void setNombreIglesia(String nombreIglesia) {
        this.nombreIglesia = nombreIglesia;
    }

    public Long getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(Long iglesiaId) {
        this.iglesiaId = iglesiaId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final MensualidadDTO other = (MensualidadDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return mes;
    }

}
