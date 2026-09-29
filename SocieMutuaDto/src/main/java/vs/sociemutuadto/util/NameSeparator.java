package vs.sociemutuadto.util;

/**
 * Esta clase se encarga de separar el nombre completo en nombres y apellidos;
 * @author Samuel Vega
 */
public class NameSeparator {
    private String nombres = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    
    public NameSeparator(String nombreCompleto) {
        if(nombreCompleto == null || nombreCompleto.trim().isEmpty()) return;
        
        String[] partes = nombreCompleto.trim().split("\\s+");
        int cantidad = partes.length;
        
        switch (cantidad) {
            case 1:
                this.nombres = partes[0];
                break;
            case 2:
                this.nombres = partes[0];
                this.apellidoPaterno = partes[1];
                break;
            default:
                this.apellidoMaterno = partes[cantidad - 1];
                this.apellidoPaterno = partes[cantidad - 2];
                StringBuilder nombresBuilder = new StringBuilder();
                for (int i = 0; i < cantidad - 2; i++) {
                    nombresBuilder.append(partes[i]).append(" ");
                }   
                this.nombres = nombresBuilder.toString().trim();
                break;
        }
    }
    
    public String getNombres() { return nombres; }
    
    public String getApellidoPaterno() { return apellidoPaterno; }
    
    public String getApellidoMaterno() { return apellidoMaterno; }
}
