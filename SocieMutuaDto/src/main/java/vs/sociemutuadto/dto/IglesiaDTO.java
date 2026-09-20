package vs.sociemutuadto.dto;

/**
 * Clase DTO de Iglesia para mostrar informacion en la UI.
 * @author Samuel Vega
 */
public class IglesiaDTO {
    private Long id;
    private String nombre;
    private Double saldo;
    private String pastor;
    
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

}
