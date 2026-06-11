package vs.sociemutuadominio.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Samuel Vega
 */
@Entity
@Table(name = "mensualidades")
public class Mensualidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cuota", nullable = false)
    private Double cuota;
    
    @Column(name = "abonos", nullable = false)
    private Double abonos;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "socios_pagan", nullable = false)
    private Integer sociosPagan;
    
    @Column(name = "defunciones", nullable = false)
    private Integer defunciones;
    
    @Column(name = "cargos", nullable = false)
    private Double cargos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_iglesia", nullable = false)
    private Iglesia iglesia;

    public Mensualidad() {}

    public Mensualidad(Long id, Double cuota, Double abonos, LocalDateTime fechaCreacion, Integer sociosPagan, Integer defunciones, Double cargos, Iglesia iglesia) {
        this.id = id;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fecha_creacion) {
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
