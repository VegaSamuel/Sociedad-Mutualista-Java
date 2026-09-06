package vs.sociemutuadominio.models;

import java.sql.Date;
import java.util.Objects;

/**
 * Esta clase contiene los atributos y funciones de un socio.
 * @author Samuel Vega
 */
public class Socio {
    
    private Long id;
    private NombreCompleto nombreCompleto;
    private Date fechaIngreso;
    private Boolean paga;
    private Iglesia iglesia;

    /**
     * Constructor por omision.
     */
    public Socio() {}

    /**
     * Constructor completo.
     * @param id Id del socio.
     * @param nombreCompleto Nombre completo del socio.
     * @param fechaIngreso Fecha en la ingreso.
     * @param paga Indica si el socio paga o no paga las mensualidades.
     * @param iglesia Iglesia a la que pertenece el socio.
     */
    public Socio(Long id, NombreCompleto nombreCompleto, Date fechaIngreso, boolean paga, Iglesia iglesia) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fechaIngreso = fechaIngreso;
        this.paga = paga;
        this.iglesia = iglesia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(NombreCompleto nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fecha_ingreso) {
        this.fechaIngreso = fecha_ingreso;
    }

    public Boolean isPaga() {
        return paga;
    }

    public void setPaga(Boolean paga) {
        this.paga = paga;
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
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Socio other = (Socio) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Socio{" + "id=" + id + ", nombreCompleto=" + nombreCompleto + ", fechaIngreso=" + fechaIngreso + ", paga=" + paga + ", iglesia=" + iglesia + '}';
    }

}
