/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author A
 */
public class GuiEjercicios extends javax.swing.JInternalFrame {

    private DefaultTableModel tablaEjerciciosDefault;
    
    public GuiEjercicios() {
        initComponents();
        
        tablaEjerciciosDefault = (DefaultTableModel) tablaEjercicios.getModel();
        
        tablaEjercicios.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaEjercicios.getColumnModel().getColumn(1).setPreferredWidth(300);
        tablaEjercicios.getColumnModel().getColumn(2).setPreferredWidth(100);
    }

     public void ApreteBtnNuevoModificar(){
        txtEjercicio.setEnabled(true);
        boxGrupo.setEnabled(true);
        modificarBtn.setEnabled(false);
        nuevoBtn.setText("Guardar");
        eliminarBtn.setText("Cancelar");
        eliminarBtn.setEnabled(true);
    }
    
    public void EstadoInicial(){
        txtEjercicio.setEnabled(false);
        boxGrupo.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
    }
    
    public void reClick(){
        EstadoInicial();
        LimpiarCampos();
    }
    
    public void EstadoLuegoDeModificar(){
        txtEjercicio.setEnabled(false);
        boxGrupo.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(true);
        modificarBtn.setEnabled(true);
    }
    
    public void EstadoEjercicioSeleccionado(){
        txtEjercicio.setEnabled(false);
        boxGrupo.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(true);
        modificarBtn.setEnabled(true);
    }
    
    public void LimpiarCampos(){
        txtEjercicio.setText("");
        boxGrupo.setSelectedIndex(0);
    }
    
    public void  setActionListener(ActionListener al){
        nuevoBtn.addActionListener(al);
        modificarBtn.addActionListener(al);
        eliminarBtn.addActionListener(al);
    }

    public DefaultTableModel getTablaEjerciciosDefault() {
        return tablaEjerciciosDefault;
    }

    public JComboBox getBoxGrupo() {
        return boxGrupo;
    }

    public JButton getEliminarBtn() {
        return eliminarBtn;
    }

    public JButton getModificarBtn() {
        return modificarBtn;
    }

    public JButton getNuevoBtn() {
        return nuevoBtn;
    }

    public JTable getTablaEjercicios() {
        return tablaEjercicios;
    }

    public JTextField getTxtEjercicio() {
        return txtEjercicio;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEjercicios = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEjercicio = new javax.swing.JTextField();
        boxGrupo = new javax.swing.JComboBox();
        nuevoBtn = new javax.swing.JButton();
        modificarBtn = new javax.swing.JButton();
        eliminarBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Gestion de ejercicios");

        tablaEjercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Grupo muscular"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEjercicios);

        jLabel1.setText("Ejercicio");

        jLabel2.setText("Grupo muscular");

        txtEjercicio.setEnabled(false);

        boxGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "ABDOMINALES", "ANTEBRAZOS", "BÍCEPS", "CUÁDRICEPS", "ESPALDA", "FEMORALES", "GEMELOS", "GLÚTEOS", "HOMBROS", "MUÑECAS (ENDURECER)", "PECTORALES", "TRÍCEPS", "AEROBICO" }));
        boxGrupo.setEnabled(false);

        nuevoBtn.setText("Nuevo");
        nuevoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBtnActionPerformed(evt);
            }
        });

        modificarBtn.setText("Modificar");
        modificarBtn.setEnabled(false);

        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(false);

        jLabel3.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(nuevoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(modificarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(eliminarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boxGrupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEjercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEjercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(boxGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eliminarBtn)
                    .addComponent(nuevoBtn)
                    .addComponent(modificarBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxGrupo;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificarBtn;
    private javax.swing.JButton nuevoBtn;
    private javax.swing.JTable tablaEjercicios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtEjercicio;
    // End of variables declaration//GEN-END:variables
}
