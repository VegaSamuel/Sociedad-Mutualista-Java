package vs.sociemutuadominio.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Samuel Vega
 */
@Embeddable
public class NombreCompleto {
    @Column(name = "nombre_completo")
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public NombreCompleto() {}

    public NombreCompleto(String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

}
