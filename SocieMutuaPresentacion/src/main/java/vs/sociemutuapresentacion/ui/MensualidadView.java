package vs.sociemutuapresentacion.ui;

import java.awt.Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.interfaces.ISocioRepository;
import vs.sociemutuadominio.models.Socio;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.MensualidadDTO;
import vs.sociemutuadto.dto.SocioDTO;
import vs.sociemutuadto.mapper.MensualidadDTOMapper;
import vs.sociemutuadto.mapper.SocioDTOMapper;
import vs.sociemutuapersistencia.firebase.MensualidadRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.firebase.SocioRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.persistence.MensualidadRepositoryImpl;
import vs.sociemutuapersistencia.persistence.SocioRepositoryImpl;
import vs.sociemutuapersistencia.sync.persistence.MensualidadRepositorySyncImpl;
import vs.sociemutuapersistencia.sync.persistence.SocioRepositorySyncImpl;
import vs.sociemutuapresentacion.enums.Operations;
import vs.sociemutuapresentacion.utils.FiltroDecimales;
import vs.sociemutuapresentacion.utils.FiltroNumeros;
import vs.sociemutuapresentacion.utils.SocioColorRenderer;

/**
 * Vista para administrar las mensualidades.
 * @author Samuel Vega
 */
public class MensualidadView extends javax.swing.JDialog {
    private final IMensualidadRepository mensualidadRepo;
    private final ISocioRepository socioRepo;
    private final Operations operacion;
    private final MensualidadDTO mensualidad;
    private final DefaultListModel<SocioDTO> sociosList = new DefaultListModel<>();
    private final IglesiaDTO iglesia;
    
    private int nPagan;
    
    /**
     * Creates new form MensualidadView
     * @param parent
     * @param modal
     * @param mensualidad Mensualidad que se administrara.
     * @param iglesia
     * @param operacion Operacion que se realizara en la mensualidad.
     */
    public MensualidadView(Frame parent, boolean modal, MensualidadDTO mensualidad, IglesiaDTO iglesia, Operations operacion) {
        super(parent, modal);
        
        this.mensualidadRepo = new MensualidadRepositorySyncImpl(
            new MensualidadRepositoryImpl(),
            new MensualidadRepositoryFirebaseImpl()
        );
        
        this.socioRepo = new SocioRepositorySyncImpl(
            new SocioRepositoryImpl(),
            new SocioRepositoryFirebaseImpl()
        );
        
        this.iglesia = iglesia;
        this.mensualidad = mensualidad;
        this.mensualidad.setIglesiaId(iglesia.getId());
        this.mensualidad.setCuota(40.00d);
        this.operacion = operacion;
        
        initComponents();
        
        this.configurarDesign();
        this.manejarElementosVisuales();
    }
    
    /**
     * Maneja los elementos visuales de la ventana.
     */
    private void manejarElementosVisuales() {
        if(this.mensualidad.getIglesiaId() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
            String fechaTexto = LocalDate.now().format(formatter);
            
            fechaTexto = fechaTexto.substring(0, 1).toUpperCase() + fechaTexto.substring(1);
            
            this.lblInfo.setText(fechaTexto);
            
            this.lblNPagan.setText("");
            this.txtAbonos.setEnabled(false);
            this.txtDefunciones.setEnabled(false);
            this.btnConfirmacion.setEnabled(false);
            this.lblIglesia.setText("Ninguna iglesia seleccionada");
            return;
        } else {
            this.txtAbonos.setEnabled(true);
            this.txtDefunciones.setEnabled(true);
            this.btnConfirmacion.setEnabled(true);
        }
        
        if(operacion.equals(Operations.AGREGAR)) {
            this.setTitle(this.getTitle() + " | Guardar");
            this.lblIglesia.setText(this.mensualidad.getNombreIglesia());
            this.lblNPagan.setText(String.valueOf(nPagan));
            this.lblCargos.setText("$ 0.00");
            
            this.btnConfirmacion.setText("Registrar");
        }
        
        if(operacion.equals(Operations.ACTUALIZAR)) {
            this.setTitle(this.getTitle() + " | Actualizar");
            
            this.txtAbonos.setText(mensualidad.getAbonos().toString());
            this.txtDefunciones.setText(mensualidad.getDefunciones().toString());
            
            this.lblNPagan.setText(mensualidad.getSociosPagan().toString());
            this.lblCargos.setText(mensualidad.getCargos().toString());
        }
        
        if(operacion.equals(Operations.ELIMINAR)) {
            this.setTitle(this.getTitle() + " | Eliminar");
            
        }
    }
    
    /**
     * Configua el diseno de los componentes de la ventaña.
     */
    private void configurarDesign() {
        // Configuraciones para la lista de socios
        this.lSocios.setCellRenderer(new SocioColorRenderer());
        ((AbstractDocument) this.txtAbonos.getDocument()).setDocumentFilter(new FiltroDecimales());
        ((AbstractDocument) this.txtDefunciones.getDocument()).setDocumentFilter(new FiltroNumeros());
        this.lSocios.setFixedCellHeight(25);
        this.lSocios.setCellRenderer(new SocioColorRenderer());
        this.lSocios.putClientProperty("FlatLaf.style", 
            "selectionArc: 5; " +
            "selectionInsets: 2,2,2,2;"
        );
        this.jScrollPane1.putClientProperty("FlatLaf.style", "arc: 10;");
        this.jScrollPane1.getViewport().setOpaque(false);
        
        this.txtDefunciones.putClientProperty("FlatLaf.style", "arc: 10;");
        this.txtAbonos.putClientProperty("FlatLaf.style", "arc: 10;");
        this.txtAbonos.addFocusListener(new FocusAdapter() {
           @Override
           public void focusLost(FocusEvent e) {
               String texto = txtAbonos.getText().trim();

               if(!texto.isEmpty()) {
                   try {
                       double valor = Double.parseDouble(texto);
                       txtAbonos.setText(String.format(java.util.Locale.US, "%.2f", valor));
                   } catch(NumberFormatException ex) {
                       System.out.println("Valor ingresado no es un numero valido");
                   }
               }
           }
        });
        
        this.btnSearchIglesia.putClientProperty("JButton.buttonType", "toolBarButton");
    }
    
    /**
     * Actualiza la vista conforme a nuevos datos que se obtengan de una iglesia.
     */
    private void actualizarDatosIglesia() {       
        this.sociosList.clear();
        this.nPagan = 0;
        
        for (Socio socio : socioRepo.obtenerTodosPorIglesia(iglesia.getId())) {
            this.sociosList.addElement(SocioDTOMapper.toDto(socio));
            nPagan = socio.isPaga() ? nPagan + 1 : nPagan;
        }
        
        this.lblNPagan.setText(String.valueOf(nPagan));
        
        this.mensualidad.setIglesiaId(iglesia.getId());
        this.mensualidad.setNombreIglesia(iglesia.getNombre());
    }
    
    private boolean camposCorrectos() {
        if(this.txtAbonos.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(
                this, 
                "La Mensualidad debe tener abonos.",
                "Mensualidad | Campos incompletos",
                JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
        
        if(this.txtDefunciones.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(
                this, 
                "La Mensualidad debe tener defunciones.",
                "Mensualidad | Campos incompletos",
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
            "Mensualidad | " + motivo,
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
                "Mensualidad | " + motivo,
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

        btnConfirmacion = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lSocios = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblNPagan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAbonos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDefunciones = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblCargos = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        btnSearchIglesia = new javax.swing.JButton();
        lblIglesia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mensualidades");
        setResizable(false);

        btnConfirmacion.setText("Actualizar");
        btnConfirmacion.addActionListener(this::btnConfirmacionActionPerformed);

        lSocios.setModel(sociosList);
        jScrollPane1.setViewportView(lSocios);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Socios");

        jLabel3.setText("Socios que pagan");

        lblNPagan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNPagan.setText("##");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Abonos");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Defunciones");

        txtDefunciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDefuncionesKeyReleased(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cargos");

        lblCargos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCargos.setText("0.00");

        jPanel1.setBackground(new java.awt.Color(204, 51, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(12, 12));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );

        jLabel8.setText("No pagan");

        jLabel9.setText("Pagan");

        lblInfo.setText("fecha");

        btnSearchIglesia.setText("...");
        btnSearchIglesia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearchIglesia.addActionListener(this::btnSearchIglesiaActionPerformed);

        lblIglesia.setText("Iglesia");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDefunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCargos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtAbonos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNPagan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConfirmacion)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblIglesia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchIglesia)
                        .addGap(5, 5, 5))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInfo)
                    .addComponent(btnSearchIglesia)
                    .addComponent(lblIglesia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAbonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNPagan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDefunciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel9))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnConfirmacion))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDefuncionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDefuncionesKeyReleased
        if(this.txtDefunciones.getText().equalsIgnoreCase("")) {
            this.lblCargos.setText("$ 0.00");
            return;
        }
        
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        
        Double cargos = this.mensualidad.getCuota() * Long.valueOf(this.txtDefunciones.getText());
        this.lblCargos.setText(formatoMoneda.format(cargos));
    }//GEN-LAST:event_txtDefuncionesKeyReleased

    private void btnConfirmacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmacionActionPerformed
        if(!this.camposCorrectos()) return;
        
        if(this.operacion.equals(Operations.AGREGAR)) {
            MensualidadDTO mDto = new MensualidadDTO();
            
            String[] fecha = this.lblInfo.getText().split(" ");
            
            mDto.setMes(fecha[0]);
            mDto.setAnio(fecha[1]);
            mDto.setCuota(this.mensualidad.getCuota());
            mDto.setAbonos(Double.valueOf(this.txtAbonos.getText()));
            mDto.setFechaCreacion(new Date(System.currentTimeMillis()));
            mDto.setSociosPagan(Long.valueOf(this.lblNPagan.getText()));
            mDto.setDefunciones(Long.valueOf(this.txtDefunciones.getText()));
            mDto.setCargos(Double.valueOf(this.lblCargos.getText().substring(1)));
            mDto.setIglesiaId(this.iglesia.getId());

            MensualidadDTO mGuardada = MensualidadDTOMapper.toDto(this.mensualidadRepo.guardar(MensualidadDTOMapper.toEntity(mDto)));
            this.mensualidad.setId(mGuardada.getId());
            
            this.mostrarInfo("Mensualidad guardada exitosamente.", "Mensualidad Guardada");
        }
    }//GEN-LAST:event_btnConfirmacionActionPerformed

    private void btnSearchIglesiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchIglesiaActionPerformed
        IglesiaSearcherView isv = new IglesiaSearcherView(JOptionPane.getFrameForComponent(this), true, this.iglesia);
        isv.setVisible(true);
        if(this.iglesia.getId() != null) {
            this.actualizarDatosIglesia();
            this.manejarElementosVisuales();
        }
    }//GEN-LAST:event_btnSearchIglesiaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmacion;
    private javax.swing.JButton btnSearchIglesia;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<SocioDTO> lSocios;
    private javax.swing.JLabel lblCargos;
    private javax.swing.JLabel lblIglesia;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblNPagan;
    private javax.swing.JTextField txtAbonos;
    private javax.swing.JTextField txtDefunciones;
    // End of variables declaration//GEN-END:variables
}
