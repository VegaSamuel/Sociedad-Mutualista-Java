package vs.sociemutuapresentacion.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import vs.sociemutuapersistencia.sync.StartSyncVerifier;
import vs.sociemutuapresentacion.godly.GodView;

/**
 * Esta clase se encarga de realizar y mostrar sincronizacion de la base local con la nube.
 * @author Samuel Vega
 */
public class InitialLoader extends JDialog {
    
    public InitialLoader(StartSyncVerifier verificator) {
        setUndecorated(true);
        setSize(300, 100);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#1E1F22"));
        
        JLabel lblMensaje = new JLabel("Descargando respaldo de la nube...", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.WHITE);
        add(lblMensaje, BorderLayout.CENTER);
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                 verificator.verificarDatosLocales();
                 return null;
            }
            
            @Override
            protected void done() {
                dispose();
                
                java.awt.EventQueue.invokeLater(() -> new GodView().setVisible(true));
            }
        };
        
        worker.execute();
    }
    
}
