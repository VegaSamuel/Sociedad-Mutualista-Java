package vs.sociemutuapresentacion.ui;

import javax.swing.JOptionPane;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.SocioDTO;
import vs.sociemutuadto.mapper.IglesiaDTOMapper;
import vs.sociemutuadto.mapper.SocioDTOMapper;
import vs.sociemutuapersistencia.firebase.IglesiaRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.firebase.SocioRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.persistence.IglesiaRepositoryImpl;
import vs.sociemutuapersistencia.persistence.SocioRepositoryImpl;
import vs.sociemutuapersistencia.sync.persistence.IglesiaRepositorySyncImpl;
import vs.sociemutuapersistencia.sync.persistence.SocioRepositorySyncImpl;
import vs.sociemutuapresentacion.enums.Operations;

/**
 * Vista para administrar a las iglesias.
 * @author Samuel Vega
 */
public class IglesiaView extends javax.swing.JDialog {
    private final IIglesiaRepository iglesiaRepo;
    private final ISocioRepository socioRepo;
    private final Operations operacion;
    private final IglesiaDTO iglesia;

    /**
     * Creates new form IglesiaView
     * @param parent Ventana principal que la llamo.
     * @param modal Define si bloquea la interaccion con mas ventanas.
     * @param iglesia Iglesia que se utilizara en la ventana.
     * @param operacion La operacion que se va a realizar en la ventana actual.
     */
    public IglesiaView(java.awt.Frame parent, boolean modal, IglesiaDTO iglesia, Operations operacion) {
        super(parent, modal);
        
        this.iglesiaRepo = new IglesiaRepositorySyncImpl(
            new IglesiaRepositoryImpl(),
            new IglesiaRepositoryFirebaseImpl()
        );
        this.socioRepo = new SocioRepositorySyncImpl(
            new SocioRepositoryImpl(),
            new SocioRepositoryFirebaseImpl()
        );
        this.iglesia = iglesia;
        this.operacion = operacion;
        
        initComponents();
        
        this.manejarElementosVisuales();
    }
    
    private void manejarElementosVisuales() {
        if(operacion.equals(Operations.GUARDAR) || operacion.equals(Operations.ASIGNACION)) {
            this.setTitle(this.getTitle() + " | Guardar");
            this.btnAceptar.setText("Guardar");
            this.btnRestaurar.setEnabled(false);
        }
        
        if(operacion.equals(Operations.ACTUALIZAR)) {
            this.setTitle(this.getTitle() + " | Actualizar");
            this.btnAceptar.setText("Actualizar");
            this.rescatarInformacion();
        }
        
        if(operacion.equals(Operations.ELIMINAR)) {
            this.setTitle(this.getTitle() + " | Eliminar");
            this.btnAceptar.setText("Eliminar");
            this.btnRestaurar.setVisible(false);
            
            this.rescatarInformacion();
            
            this.txtPastor.setEnabled(false);
            this.txtSaldo.setEnabled(false);
            this.txtNombre.setEnabled(false);
        }
    }
    
    private void rescatarInformacion() {
        this.txtNombre.setText(iglesia.getNombre());
        this.txtPastor.setText(iglesia.getPastor());
        this.txtSaldo.setText(String.valueOf(iglesia.getSaldo()));
    }
    
    private boolean verificarCampos() {
        if(this.txtNombre.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(
                this, 
                "La iglesia debe tener un nombre.",
                "Iglesia | Campos incompletos",
                JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
        
        if(this.txtPastor.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(
                this, 
                "La iglesia debe tener un pastor.",
                "Iglesia | Campos incompletos",
                JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
        
        if(this.txtSaldo.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(
                this, 
                "La iglesia debe tener un saldo inicial.",
                "Iglesia | Campos incompletos",
                JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
        
        return true;
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
            "Iglesia | " + motivo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Muestra informacion en la pantalla.
     * @param info Mensaje a mostrar.
     * @param motivo De donde viene la informacion (Se muestra en cabecera).
     */
    private void mostrarInfo(String info, String motivo) {
        JOptionPane.showMessageDialog(
                this, 
                info,
                "Iglesia | " + motivo,
                JOptionPane.INFORMATION_MESSAGE
            );
    }

    /**
     * 
     * @param mensaje
     * @param motivo
     * @return True si confirma, False en caso contrario
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
     * 
     * @param peticion
     * @return 
     */
    private boolean estaConfirmada(int peticion) {
        return peticion == 0;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIglesia = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtPastor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        btnEditSocios = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnRestaurar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iglesias");

        lblIglesia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIglesia.setText("Iglesia");

        jLabel1.setText("Pastor:");

        jLabel2.setText("Saldo:  $");

        txtSaldo.setText("0.00");

        btnEditSocios.setText("Editar Socios...");
        btnEditSocios.addActionListener(this::btnEditSociosActionPerformed);

        btnAceptar.setText("Actualizar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        jLabel3.setText("Nombre:");

        btnRestaurar.setText("Restaurar");
        btnRestaurar.addActionListener(this::btnRestaurarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPastor)
                            .addComponent(txtSaldo)
                            .addComponent(txtNombre)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditSocios, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRestaurar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAceptar)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIglesia)
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIglesia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPastor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditSocios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar)
                    .addComponent(btnRestaurar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Iglesia | Cancelar", JOptionPane.YES_NO_OPTION);
        
        if(response == 0) dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "¿Seguro que desea restaurar los valores?", "Iglesia | Restaurar", JOptionPane.YES_NO_OPTION);
        
        if(response == 0) this.rescatarInformacion();
    }//GEN-LAST:event_btnRestaurarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // Verifica que no falten campos para actuar
        if(!verificarCampos()) return;
        
        // Si hay que guardar
        if(operacion.equals(Operations.GUARDAR)) {
            IglesiaDTO iDto = new IglesiaDTO();
            iDto.setNombre(txtNombre.getText());
            iDto.setPastor(txtPastor.getText());
            iDto.setSaldo(Double.valueOf(txtSaldo.getText()));
            iDto.setSocios(iglesia.getSocios());
            
            IglesiaDTO iGuardada = IglesiaDTOMapper.toDto(this.iglesiaRepo.guardar(IglesiaDTOMapper.toEntity(iDto)));
            iglesia.setId(iGuardada.getId());
            
            if(iDto.hasSocios()) {
                for (SocioDTO socio : iglesia.getSocios()) {
                    socio.setIglesiaId(iglesia.getId());
                    this.socioRepo.guardar(SocioDTOMapper.toEntity(socio));
                }
            }
            
            this.mostrarInfo("La iglesia se guardo exitosamente.", "Iglesia guardada");
            dispose();
        }
        
        // Si hay que actualizar
        if(operacion.equals(Operations.ACTUALIZAR)) {
            IglesiaDTO iDto = new IglesiaDTO();
            iDto.setId(iglesia.getId());
            iDto.setNombre(txtNombre.getText());
            iDto.setPastor(txtPastor.getText());
            iDto.setSaldo(Double.valueOf(txtSaldo.getText()));
            
            this.iglesiaRepo.actualizar(IglesiaDTOMapper.toEntity(iDto));
            
            if(iglesia.hasSocios()) {
                for (SocioDTO socio : iglesia.getSocios()) {
                    socio.setIglesiaId(iglesia.getId());
                    this.socioRepo.actualizar(SocioDTOMapper.toEntity(socio));
                }
            }
            
            this.mostrarInfo("La iglesia se actualizo exitosamente.", "Iglesia actualizada");
            dispose();
        }
        
        // Si hay que eliminar
        if(operacion.equals(Operations.ELIMINAR)) {
            int peticion = this.pedirConfirmacion("¿Seguro que desea eliminar la iglesia: \"" + iglesia.getNombre() + "\"?", "Eliminar");
            if(this.estaConfirmada(peticion)) {
                this.iglesiaRepo.eliminar(iglesia.getId());
                this.mostrarInfo("La iglesia:  \"" + iglesia.getNombre() + "\" se elimino exitosamente.", "Iglesia eliminada");
                dispose();
            }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnEditSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSociosActionPerformed
        if(txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(
                this, 
                "La iglesia debe tener al menos el nombre para asignarle socios en primera instancia",
                "Iglesias | Sin informacion",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        this.iglesia.setNombre(this.txtNombre.getText());
        SociosIglesiaView siv = new SociosIglesiaView(JOptionPane.getFrameForComponent(this), true, iglesia, Operations.ASIGNACION);
        siv.setVisible(true);
    }//GEN-LAST:event_btnEditSociosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditSocios;
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblIglesia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPastor;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}
