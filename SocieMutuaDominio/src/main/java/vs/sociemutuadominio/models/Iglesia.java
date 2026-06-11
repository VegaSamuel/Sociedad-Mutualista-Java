package vs.sociemutuadominio.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Samuel Vega
 */
@Entity
@Table(name = "iglesias")
public class Iglesia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="nombre", nullable = false)
    private String nombre;
    
    @Column(name="saldo", nullable = false)
    private Double saldo;
    
    @Column(name="pastor", nullable = false)
    private String pastor;
    
    @OneToMany(mappedBy = "iglesia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "socios", nullable = false)
    private List<Socio> socios;
    
    @OneToMany(mappedBy = "iglesia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mensualidades", nullable = true)
    private List<Mensualidad> mensualidades;

    public Iglesia() {}

    public Iglesia(Long id, String nombre, Double saldo, String pastor) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.pastor = pastor;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Iglesia other = (Iglesia) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Iglesia{" + "id=" + id + ", nombre=" + nombre + ", saldo=" + saldo + ", pastor=" + pastor + '}';
    }

}
