package vs.sociemutuadominio.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Samuel Vega
 */
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private NombreCompleto nombreCompleto;
    
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;
    
    @Column(name = "paga", nullable = false)
    private Boolean paga;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iglesia_id", nullable = false)
    private Iglesia iglesia;

    public Socio() {}

    public Socio(Long id, NombreCompleto nombreCompleto, LocalDateTime fechaIngreso, boolean paga, Iglesia iglesia) {
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

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fecha_ingreso) {
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
