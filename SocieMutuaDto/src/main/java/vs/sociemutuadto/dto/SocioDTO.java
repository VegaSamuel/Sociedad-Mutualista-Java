package vs.sociemutuadto.dto;

/**
 *
 * @author Samuel Vega
 */
public class SocioDTO {
    private Long id;
    private String nombreCompleto;
    private String nombreIglesia;
    private boolean paga;
    
    public SocioDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreIglesia() {
        return nombreIglesia;
    }

    public void setNombreIglesia(String nombreIglesia) {
        this.nombreIglesia = nombreIglesia;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

}
