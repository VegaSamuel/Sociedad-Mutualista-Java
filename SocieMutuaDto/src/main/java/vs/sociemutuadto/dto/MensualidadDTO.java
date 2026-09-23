package vs.sociemutuadto.dto;

import java.sql.Date;

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

}
