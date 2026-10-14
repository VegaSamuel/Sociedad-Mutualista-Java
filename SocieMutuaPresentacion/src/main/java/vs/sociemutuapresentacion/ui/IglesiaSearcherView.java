package vs.sociemutuapresentacion.ui;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.models.Iglesia;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.mapper.IglesiaDTOMapper;
import vs.sociemutuapersistencia.persistence.IglesiaRepositoryImpl;

/**
 * Ventana encargada de buscar Iglesias con un buscador especial.
 * @author Samuel Vega
 */
public class IglesiaSearcherView extends javax.swing.JDialog {
    private final IIglesiaRepository iglesiaRepo;
    
    private final IglesiaDTO iglesia;
    private final IglesiaDTO prevIglesia;
    private JPopupMenu menuSugerencias;
    private JList<IglesiaDTO> sugerencias;
    private DefaultListModel<IglesiaDTO> modeloSugerencias;
    private Timer debounceTimer;
    private boolean seleccionAutomatica;

    /**
     * Creates new form NewJFrame
     * @param parent Ventana que abre esta ventana.
     * @param modal Si se enfoca o no.
     * @param iglesia Iglesia que se manipula con esta nueva ventana.
     */
    public IglesiaSearcherView(Frame parent, boolean modal, IglesiaDTO iglesia) {
        super(parent, modal);
        this.iglesiaRepo = new IglesiaRepositoryImpl();
        this.iglesia = iglesia;
        this.seleccionAutomatica = false;
        
        // Se hace de esta manera para que cree uno nuevo y no apunte al mismo objeto que se modifica con la ventana.
        this.prevIglesia = new IglesiaDTO();
        this.prevIglesia.setId(iglesia.getId());
        this.prevIglesia.setSaldo(iglesia.getSaldo());
        this.prevIglesia.setPastor(iglesia.getPastor());
        this.prevIglesia.setNombre(iglesia.getNombre());
        
        initComponents();
        
        this.configurarBuscador();
    }
    
    /**
     * Configura el buscador tipo Google que busca iglesias.
     */
    private void configurarBuscador() {
        // Le da estilo al campo de texto
        this.txtSearch.putClientProperty("JTextField.placeholderText", "Buscar Iglesia...");
        this.txtSearch.putClientProperty("JTextField.showClearButton", true);
        this.txtSearch.putClientProperty("FlatLaf.style", "arc: 10;");
        
        // Crea el menu de sugerencias
        menuSugerencias = new JPopupMenu();
        menuSugerencias.setFocusable(false);
        
        // Le define una lista al menu de sugerencias
        modeloSugerencias = new DefaultListModel<>();
        sugerencias = new JList<>(modeloSugerencias);
        sugerencias.setFocusable(false);
        
        // Define los espacios entre objetos de la lista
        sugerencias.setFixedCellHeight(30);
        JScrollPane scrollSugerencias = new JScrollPane(sugerencias);
        menuSugerencias.add(scrollSugerencias);
        menuSugerencias.setPopupSize(this.txtSearch.getWidth(), 120);
        
        // Cada cierto se realiza la busqueda
        debounceTimer = new Timer(300, e -> ejecutarBusqueda(this.txtSearch.getText()));
        debounceTimer.setRepeats(false);
        
        // Reinicia el contador cada que termina de escribir
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { if(!seleccionAutomatica) debounceTimer.restart(); }
            @Override
            public void removeUpdate(DocumentEvent e) { if(!seleccionAutomatica) debounceTimer.restart(); }
            @Override
            public void changedUpdate(DocumentEvent e) { if(!seleccionAutomatica) debounceTimer.restart(); }
        });
        
        // Maneja lo que pasa cuando se da clic a una iglesia de la lista
        sugerencias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Si hay seleccion
                if(e.getClickCount() == 1 && !sugerencias.isSelectionEmpty()) {
                    // Obtiene la iglesia seleccionada
                    IglesiaDTO seleccion = sugerencias.getSelectedValue();
                    
                    // Modifica el objeto que se obtuvo al llegar a esta ventana
                    iglesia.setId(seleccion.getId());
                    iglesia.setNombre(seleccion.getNombre());
                    iglesia.setSaldo(seleccion.getSaldo());
                    iglesia.setPastor(seleccion.getPastor());
                    
                    // Para que el popup no vuelva a aparecer despues de seleccionar
                    seleccionAutomatica = true;
                    
                    // Pone el valor seleccionado en el campo de texto
                    txtSearch.setText(seleccion.getNombre());
                    
                    // Esconde de nuevo el popup.
                    seleccionAutomatica = false;
                    menuSugerencias.setVisible(false);
                }
            }
        });
    }
    
    /**
     * Realiza la busqueda de la iglesia conforme a lo escrito en el campo de texto.
     * @param iglesia Iglesia a buscar.
     */
    private void ejecutarBusqueda(String iglesia) {
        // Si no hay nada escrito, esconde el popup
        if(iglesia.trim().isEmpty()) {
            menuSugerencias.setVisible(false);
            return;
        }
        
        // Vacia la lista antes de llenarla con los nuevos datos
        modeloSugerencias.clear();
        
        // Recorre todas las iglesias en la base de datos y las suma a la lista
        for(Iglesia ig : this.iglesiaRepo.obtenerPorNombre(iglesia)) {
            modeloSugerencias.addElement(IglesiaDTOMapper.toDto(ig));
        }
        
        // Y si la lista no esta vacia
        if(!modeloSugerencias.isEmpty()) {
            // Muestra el popup con los resultados de la busqueda.
            menuSugerencias.show(this.txtSearch, 0, this.txtSearch.getHeight());
            this.txtSearch.requestFocus();
        } else menuSugerencias.setVisible(false);
    }
    
    /**
     * Verifica si la iglesia que busca el usuario existe.
     * @param iglesia Iglesia a verificar si existe.
     * @return True si existe, Flase en caso contrario.
     */
    private boolean existeIglesia(String iglesia) {
        for(Iglesia ig : this.iglesiaRepo.obtenerPorNombre(iglesia)) {
            if(ig.getNombre().equalsIgnoreCase(iglesia)) return true;
        }
        
        return false;
    }
    
    /**
     * Muestra un dialogo que pide confirmacion al usuario.
     * @param mensaje Mensaje que vendra en el dialogo.
     * @param motivo Motivo de la peticion.
     * @return La respuesta del usuario.
     */
    private int pedirConfirmacion(String mensaje, String motivo) {
        int response = JOptionPane.showConfirmDialog(
            this, 
            mensaje, 
            "Iglesia | " + motivo, 
            JOptionPane.YES_NO_OPTION
        );
        
        return response;
    }
    
    /**
     * Este metodo verifica que el usuario haya confirmado una peticion.
     * @param peticion Valor recibido de la peticion.
     * @return True si confirmo, False en caso contrario.
     */
    private boolean estaConfirmada(int peticion) {
        return peticion == 0;
    }
    
    /**
     * Regresa la iglesia a sus valores originales antes de llegar a esta ventana.
     */
    private void restaurarIglesia() {
        this.iglesia.setId(prevIglesia.getId());
        this.iglesia.setNombre(prevIglesia.getNombre());
        this.iglesia.setSaldo(prevIglesia.getSaldo());
        this.iglesia.setPastor(prevIglesia.getPastor());
    }
    
    /**
     * Muestra un mensaje de error en la pantalla.
     * @param info Mensaje a mostrar.
     * @param motivo De donde viene el error (Se muestra en cabecera).
     */
    private void mostrarError(String info, String motivo) {
        JOptionPane.showMessageDialog(
            this, 
            info,
            "Buscador de Iglesias | " + motivo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscador de Iglesia");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if(estaConfirmada(this.pedirConfirmacion("¿Seguro que desea cancelar esta operacion?", "Cancelar selección de Iglesia"))) {
            this.restaurarIglesia();
            dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(!existeIglesia(txtSearch.getText())) {
            this.mostrarError("Esa iglesia no existe.", "Iglesia inexistente");
            return;
        }
        
        if(estaConfirmada(this.pedirConfirmacion("¿Seguro que desea seleccionar esta iglesia?", "Iglesia Seleccionada"))) {
            dispose();
        } else this.restaurarIglesia();
    }//GEN-LAST:event_btnAceptarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
