package vs.sociemutuadominio.models;

import java.util.List;
import java.util.Objects;

/**
 * Esta clase define los atributos y funciones de una iglesia.
 * @author Samuel Vega
 */
public class Iglesia {

    private Long id;
    private String nombre;
    private Double saldo;
    private String pastor;
    private List<Socio> socios;
    private List<Mensualidad> mensualidades;

    /**
     * Constructor por omision.
     */
    public Iglesia() {}

    /**
     * Constructor completo sin listas.
     * @param id ID de la iglesia.
     * @param nombre Nombre de la iglesia.
     * @param saldo Saldo que tiene la iglesia.
     * @param pastor Pastor que la dirige.
     */
    public Iglesia(Long id, String nombre, Double saldo, String pastor) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.pastor = pastor;
    }

    /**
     * Constructor completo sin listas.
     * @param id ID de la iglesia.
     * @param nombre Nombre de la iglesia.
     * @param saldo Saldo que tiene la iglesia.
     * @param pastor Pastor que la dirige.
     * @param socios Socios que tiene la iglesia.
     * @param mensualidades Mensualidades que estan presentes en la iglesia.
     */
    public Iglesia(Long id, String nombre, Double saldo, String pastor, List<Socio> socios, List<Mensualidad> mensualidades) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.pastor = pastor;
        this.socios = socios;
        this.mensualidades = mensualidades;
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

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }

    public List<Mensualidad> getMensualidades() {
        return mensualidades;
    }

    public void setMensualidades(List<Mensualidad> mensualidades) {
        this.mensualidades = mensualidades;
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
        return "Iglesia{" + "id=" + id + ", nombre=" + nombre + ", saldo=" + saldo + ", pastor=" + pastor + ", socios=" + socios + ", mensualidades=" + mensualidades + '}';
    }

}
