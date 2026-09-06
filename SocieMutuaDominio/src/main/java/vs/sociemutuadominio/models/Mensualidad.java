package vs.sociemutuadominio.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Esta clase define los atributos y funciones de las mensualidades.
 * @author Samuel Vega
 */
public class Mensualidad {

    private Long id;
    private String mes;
    private Double cuota;
    private Double abonos;
    private Date fechaCreacion;
    private Integer sociosPagan;
    private Integer defunciones;
    private Double cargos;
    private Iglesia iglesia;

    /**
     * Constructor por omision.
     */
    public Mensualidad() {}

    /**
     * Constructor completo.
     * @param id ID de la mensualidad.
     * @param mes Mes al que pertenece la mensualidad.
     * @param cuota Cuota que se realiza al momento del objeto.
     * @param abonos Abonos que se realizaron a la mensualidad.
     * @param fechaCreacion Creacion de la mensualidad.
     * @param sociosPagan Cantidad de socios que pagan.
     * @param defunciones Cantidad de defunciones que hubo en el mes.
     * @param cargos Cargos correspondientes a las defunciones y cuota.
     * @param iglesia Iglesia que debe tal mensualidad.
     */
    public Mensualidad(Long id, String mes, Double cuota, Double abonos, Date fechaCreacion, Integer sociosPagan, Integer defunciones, Double cargos, Iglesia iglesia) {
        this.id = id;
        this.mes = mes;
        this.cuota = cuota;
        this.abonos = abonos;
        this.fechaCreacion = fechaCreacion;
        this.sociosPagan = sociosPagan;
        this.defunciones = defunciones;
        this.cargos = cargos;
        this.iglesia = iglesia;
    }

    public Mensualidad(String mes, Double cuota, Double abonos, Date fechaCreacion, Integer sociosPagan, Integer defunciones, Double cargos, Iglesia iglesia) {
        this.mes = mes;
        this.cuota = cuota;
        this.abonos = abonos;
        this.fechaCreacion = fechaCreacion;
        this.sociosPagan = sociosPagan;
        this.defunciones = defunciones;
        this.cargos = cargos;
        this.iglesia = iglesia;
    }

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

    public void setFechaCreacion(Date fecha_creacion) {
        this.fechaCreacion = fecha_creacion;
    }

    public Integer getSociosPagan() {
        return sociosPagan;
    }

    public void setSociosPagan(Integer socios_pagan) {
        this.sociosPagan = socios_pagan;
    }

    public Integer getDefunciones() {
        return defunciones;
    }

    public void setDefunciones(Integer defunciones) {
        this.defunciones = defunciones;
    }

    public Double getCargos() {
        return cargos;
    }

    public void setCargos(Double cargos) {
        this.cargos = cargos;
    }

    public Iglesia getIglesia() {
        return iglesia;
    }

    public void setIglesia(Iglesia iglesia) {
        this.iglesia = iglesia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Mensualidad other = (Mensualidad) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Mensualidad{" + "id=" + id + ", cuota=" + cuota + ", abonos=" + abonos + ", fechaCreacion=" + fechaCreacion + ", sociosPagan=" + sociosPagan + ", defunciones=" + defunciones + ", cargos=" + cargos + ", iglesia=" + iglesia + '}';
    }

}
