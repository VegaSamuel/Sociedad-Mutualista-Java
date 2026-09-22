package vs.sociemutuapresentacion.main;

import com.formdev.flatlaf.FlatDarkLaf;
import vs.sociemutuapresentacion.godly.GodView;

/**
 * Clase principal que inicia el programa, configura todo y abre la ventana principal
 * @author Samuel Vega
 */
public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        
        GodView god = new GodView();
        god.setVisible(true);
    }
}
