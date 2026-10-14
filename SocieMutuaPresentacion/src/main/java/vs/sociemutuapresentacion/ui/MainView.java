package vs.sociemutuapresentacion.ui;

import java.awt.Color;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import vs.sociemutuadominio.interfaces.IIglesiaRepository;
import vs.sociemutuadominio.interfaces.IMensualidadRepository;
import vs.sociemutuadominio.models.Mensualidad;
import vs.sociemutuadto.dto.IglesiaDTO;
import vs.sociemutuadto.dto.MensualidadDTO;
import vs.sociemutuadto.mapper.IglesiaDTOMapper;
import vs.sociemutuadto.mapper.MensualidadDTOMapper;
import vs.sociemutuapersistencia.firebase.IglesiaRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.firebase.MensualidadRepositoryFirebaseImpl;
import vs.sociemutuapersistencia.persistence.IglesiaRepositoryImpl;
import vs.sociemutuapersistencia.persistence.MensualidadRepositoryImpl;
import vs.sociemutuapersistencia.sync.persistence.IglesiaRepositorySyncImpl;
import vs.sociemutuapersistencia.sync.persistence.MensualidadRepositorySyncImpl;
import vs.sociemutuapresentacion.enums.Operations;

/**
 * Ventana principal de todo el programa.
 * @author Samuel Vega
 */
public class MainView extends javax.swing.JFrame {
    private final IMensualidadRepository mensualidadRepo;
    private final IIglesiaRepository iglesiaRepo;
    private final DefaultListModel<IglesiaDTO> iglesias = new DefaultListModel<>();
    private final DefaultListModel<MensualidadDTO> meses = new DefaultListModel<>();
    
    private List<String> anios;
    private MensualidadDTO actualMensualidad;
    private int indexAnios;
    private final Double cuota;
    
    /**
     * Creates new form MainView
     */
    public MainView() {
        this.mensualidadRepo = new MensualidadRepositorySyncImpl(
            new MensualidadRepositoryImpl(),
            new MensualidadRepositoryFirebaseImpl()
        );
        
        this.iglesiaRepo = new IglesiaRepositorySyncImpl(
            new IglesiaRepositoryImpl(),
            new IglesiaRepositoryFirebaseImpl()
        );
        
        this.cuota = 50.0d;
        this.actualMensualidad = null;
        
        initComponents();
        
        if(this.lMeses.isSelectionEmpty()) this.esconderEtiquetas();
        
        this.configurarDesign();
        this.manejarElementosVisuales();
    }
    
    private void manejarElementosVisuales() {
        this.anios = this.mensualidadRepo.obtenerAnios();
        Date hoy = new Date(System.currentTimeMillis());
        this.indexAnios = anios.indexOf(String.valueOf(hoy.toLocalDate().getYear()));
        
        if(!anios.isEmpty()) actualizarAnioYMeses(); else this.lblAnio.setText("");
    }
    
    private void llenarListaIglesias(String mes) {
        this.iglesias.clear();
        for(Mensualidad mensualidad : this.mensualidadRepo.obtenerPorMes(mes)) {
            this.iglesias.addElement(IglesiaDTOMapper.toDto(this.iglesiaRepo.buscarPorId(mensualidad.getIglesia().getId())));
            //this.cuota = mensualidad.getCuota();
        }
    }
    
    private void llenarPropiedadesIglesia(IglesiaDTO dto) {       
        if(this.lIglesias.isSelectionEmpty()) {
            this.esconderEtiquetas();
        } else {
            this.lblPastor.setText(dto.getPastor());
            this.lblNPagan.setText(String.valueOf(this.actualMensualidad.getSociosPagan()));
            this.lblDefunciones.setText(String.valueOf(this.actualMensualidad.getDefunciones()));
            
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
            this.lblCargos.setText(formatoMoneda.format(this.actualMensualidad.getCargos()));
            
            this.revelarEtiquetas();
        }
    }
    
    private void actualizarMensualidadActual(IglesiaDTO dto) {
        for (Mensualidad men : this.mensualidadRepo.obtenerPorMes(this.lMeses.getSelectedValue().getMes())) {
            if(men.getIglesia().getId().equals(dto.getId())) {
                this.actualMensualidad = MensualidadDTOMapper.toDto(men);
            }
        }
    }
    
    private void configurarDesign() {
        // Configuraciones para la lista de meses
        this.lMeses.setFixedCellHeight(25);
        this.lMeses.setOpaque(false);
        this.lMeses.setBackground(new Color(30, 41, 34, 0));
        this.lMeses.putClientProperty("FlatLaf.style", 
            "selectionArc: 5; " +
            "selectionInsets: 2,2,2,2;"
        );
        
        this.lIglesias.setFixedCellHeight(25);
        this.lIglesias.putClientProperty("FlatLaf.style", 
            "selectionArc: 5; " +
            "selectionInsets: 2,2,2,2;"
        );
        this.jScrollPane1.putClientProperty("FlatLaf.style", "arc: 10;");
        this.jScrollPane1.getViewport().setOpaque(false);
        
        this.btnNext.putClientProperty("JButton.buttonType", "toolBarButton");
        this.btnPrevious.putClientProperty("JButton.buttonType", "toolBarButton");
        
        this.propertiesPanel.putClientProperty("FlatLaf.style", "arc: 10;");
        this.resumePanel.putClientProperty("FlatLaf.style", "arc: 10;");
    }
    
    private void actualizarAnioYMeses() {
        if(indexAnios == 0) { 
            this.btnPrevious.setEnabled(false);
            this.btnPrevious.setText("");
        } else {
            this.btnPrevious.setEnabled(true);
            this.btnPrevious.setText("<");
        }
        
        if(indexAnios == (this.anios.size() - 1)) { 
            this.btnNext.setEnabled(false);
            this.btnNext.setText("");
        } else {
            this.btnNext.setEnabled(true);
            this.btnNext.setText(">");
        }
        
        this.lblAnio.setText(this.anios.get(indexAnios));
        
        this.esconderEtiquetas();
        this.iglesias.clear();
        this.meses.clear();
        
        for (Mensualidad mes : this.mensualidadRepo.obtenerPorAnio(this.anios.get(indexAnios))) {
            this.meses.addElement(MensualidadDTOMapper.toDto(mes));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        lblAnio = new javax.swing.JLabel();
        lMeses = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lIglesias = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        propertiesPanel = new javax.swing.JPanel();
        etPastor = new javax.swing.JLabel();
        lblPastor = new javax.swing.JLabel();
        etCargos = new javax.swing.JLabel();
        lblCargos = new javax.swing.JLabel();
        etNPagan = new javax.swing.JLabel();
        lblNPagan = new javax.swing.JLabel();
        etDefunciones = new javax.swing.JLabel();
        lblDefunciones = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        resumePanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnAgregarMensualidad = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        adminMenu = new javax.swing.JMenu();
        IglesiaAdminMenu = new javax.swing.JMenuItem();
        cuotaAdminMenu = new javax.swing.JMenuItem();
        tablesMenu = new javax.swing.JMenu();
        iglesiasTableMenu = new javax.swing.JMenuItem();
        utilMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sociedad Mutualista IAFCJ v 1.0");
        setResizable(false);

        lblAnio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnio.setText("Anio");

        lMeses.setModel(meses);
        lMeses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lMesesMouseClicked(evt);
            }
        });

        lIglesias.setModel(iglesias);
        lIglesias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lIglesiasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lIglesias);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Iglesias");

        propertiesPanel.setBackground(new java.awt.Color(51, 51, 51));

        etPastor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etPastor.setText("Pastor");

        lblPastor.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPastor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPastor.setText("jLabel2");

        etCargos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etCargos.setText("Cargos");
        etCargos.setToolTipText("");

        lblCargos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCargos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCargos.setText("jLabel1");

        etNPagan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etNPagan.setText("Socios que pagan");

        lblNPagan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblNPagan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNPagan.setText("jLabel1");

        etDefunciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etDefunciones.setText("Defunciones");

        lblDefunciones.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDefunciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDefunciones.setText("jLabel1");

        javax.swing.GroupLayout propertiesPanelLayout = new javax.swing.GroupLayout(propertiesPanel);
        propertiesPanel.setLayout(propertiesPanelLayout);
        propertiesPanelLayout.setHorizontalGroup(
            propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etPastor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etNPagan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPastor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNPagan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(propertiesPanelLayout.createSequentialGroup()
                        .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etDefunciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDefunciones, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCargos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(etCargos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(6, 6, 6))
        );
        propertiesPanelLayout.setVerticalGroup(
            propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertiesPanelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(lblPastor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etPastor, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCargos)
                    .addGroup(propertiesPanelLayout.createSequentialGroup()
                        .addComponent(lblDefunciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etCargos)
                    .addComponent(etDefunciones))
                .addGap(18, 18, 18)
                .addComponent(lblNPagan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etNPagan)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Propiedades");

        resumePanel.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout resumePanelLayout = new javax.swing.GroupLayout(resumePanel);
        resumePanel.setLayout(resumePanelLayout);
        resumePanelLayout.setHorizontalGroup(
            resumePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resumePanelLayout.setVerticalGroup(
            resumePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Resumen");

        btnPrevious.setText("<");
        btnPrevious.addActionListener(this::btnPreviousActionPerformed);

        btnNext.setText(">");
        btnNext.addActionListener(this::btnNextActionPerformed);

        btnAgregarMensualidad.setText("Agregar Mes");
        btnAgregarMensualidad.addActionListener(this::btnAgregarMensualidadActionPerformed);

        fileMenu.setText("Archivo");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setText("Salir");
        exitMenuItem.setToolTipText("");
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        adminMenu.setText("Administrar");

        IglesiaAdminMenu.setText("Añadir Iglesia");
        IglesiaAdminMenu.addActionListener(this::IglesiaAdminMenuActionPerformed);
        adminMenu.add(IglesiaAdminMenu);

        cuotaAdminMenu.setMnemonic('a');
        cuotaAdminMenu.setText("Actualizar Cuota");
        cuotaAdminMenu.addActionListener(this::cuotaAdminMenuActionPerformed);
        adminMenu.add(cuotaAdminMenu);

        tablesMenu.setText("Mostrar Tabla");

        iglesiasTableMenu.setText("Iglesias");
        tablesMenu.add(iglesiasTableMenu);

        adminMenu.add(tablesMenu);

        menuBar.add(adminMenu);

        utilMenu.setText("Utilidades");

        cutMenuItem.setText("Cambio Pastoral");
        utilMenu.add(cutMenuItem);

        copyMenuItem.setText("Reportes");
        utilMenu.add(copyMenuItem);

        menuBar.add(utilMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAnio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lMeses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(propertiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(resumePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnio)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resumePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(propertiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lMeses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarMensualidad)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void btnAgregarMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMensualidadActionPerformed
        MensualidadView mw = new MensualidadView(this, true, new MensualidadDTO(), new IglesiaDTO(), Operations.AGREGAR);
        mw.setVisible(true);
        //this.manejarElementosVisuales();
    }//GEN-LAST:event_btnAgregarMensualidadActionPerformed

    private void IglesiaAdminMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IglesiaAdminMenuActionPerformed
        IglesiaView iv = new IglesiaView(this, true, new IglesiaDTO(), Operations.AGREGAR);
        iv.setVisible(true);
    }//GEN-LAST:event_IglesiaAdminMenuActionPerformed

    private void lIglesiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lIglesiasMouseClicked
        this.actualizarMensualidadActual(this.lIglesias.getSelectedValue());
        this.llenarPropiedadesIglesia(this.lIglesias.getSelectedValue());
    }//GEN-LAST:event_lIglesiasMouseClicked

    private void lMesesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lMesesMouseClicked
        this.llenarListaIglesias(this.lMeses.getSelectedValue().getMes());
    }//GEN-LAST:event_lMesesMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.indexAnios = indexAnios + 1;
        this.actualizarAnioYMeses();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        this.indexAnios = indexAnios - 1;
        this.actualizarAnioYMeses();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void cuotaAdminMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuotaAdminMenuActionPerformed
        JOptionPane.showInputDialog(this, "Escribe la nueva cuota.", this.cuota);
    }//GEN-LAST:event_cuotaAdminMenuActionPerformed

    private void esconderEtiquetas() {       
        this.etPastor.setVisible(false);
        this.lblPastor.setVisible(false);
        this.etCargos.setVisible(false);
        this.lblCargos.setVisible(false);
        this.etNPagan.setVisible(false);
        this.lblNPagan.setVisible(false);
        this.etDefunciones.setVisible(false);
        this.lblDefunciones.setVisible(false);
    }
    
    private void revelarEtiquetas() {
        this.etPastor.setVisible(true);
        this.lblPastor.setVisible(true);
        this.etCargos.setVisible(true);
        this.lblCargos.setVisible(true);
        this.etNPagan.setVisible(true);
        this.lblNPagan.setVisible(true);
        this.etDefunciones.setVisible(true);
        this.lblDefunciones.setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem IglesiaAdminMenu;
    private javax.swing.JMenu adminMenu;
    private javax.swing.JButton btnAgregarMensualidad;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cuotaAdminMenu;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JLabel etCargos;
    private javax.swing.JLabel etDefunciones;
    private javax.swing.JLabel etNPagan;
    private javax.swing.JLabel etPastor;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem iglesiasTableMenu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JList<IglesiaDTO> lIglesias;
    private javax.swing.JList<MensualidadDTO> lMeses;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblCargos;
    private javax.swing.JLabel lblDefunciones;
    private javax.swing.JLabel lblNPagan;
    private javax.swing.JLabel lblPastor;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPanel propertiesPanel;
    private javax.swing.JPanel resumePanel;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenu tablesMenu;
    private javax.swing.JMenu utilMenu;
    // End of variables declaration//GEN-END:variables

}
