package vs.sociemutuadominio.models;

/**
 * Esta clase define los establece el nombre completo de un socio.
 * @author Samuel Vega
 */
public class NombreCompleto {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;

    /**
     * Constructor por omision.
     */
    public NombreCompleto() {}

    /**
     * Constructor completo.
     * @param nombres Nombres del socio.
     * @param apellidoPaterno Apellido paterno del socio.
     * @param apellidoMaterno Apellido materno del socio.
     */
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
