package vs.sociemutuadto.util;

/**
 * Esta clase se encarga de separar el nombre completo en nombres y apellidos;
 * @author Samuel Vega
 */
public class NameSeparator {
    private final String nombre;
    
    public NameSeparator(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombres() {
        return nombre;
    }
    
    public String getApellidoPaterno() {
        return "";
    }
    
    public String getApellidoMaterno() {
        return "";
    }
}
