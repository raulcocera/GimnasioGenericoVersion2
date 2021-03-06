/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMCompra;
import BD.ConexionBD;
import static BD.ConexionBD.abrirBase;
import Interfaces.CompraGui;
import Interfaces.PrincipalGui;
import Interfaces.RealizarPagoGui;
import busqueda.Busqueda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import Modelos.Articulo;
import Modelos.Compra;
import Modelos.Proveedor;
import Utiles.DatosGenericos;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorCompra implements ActionListener, CellEditorListener {

    private Busqueda busqueda;
    private ABMCompra abmCompra;
    private RealizarPagoGui realizarPagoGui;
    private CompraGui compraGui;
    private PrincipalGui apgui;
    //private ComprasRealizadasControlador controladorCompras;
    private Integer idCompraAModificar;
    private JTextField textnom;
    private DefaultTableModel tablaProveedores;
    private DefaultTableModel tablaProd;
    private JTable tablafac;
    private JTable tablaprov;
    private JTable tablaprod;
    private JTextField textCodProd;
    private JTextField textProvCompra;
    private List prodlista;
    private List provlista;
    private JTextField txtCodigoBus;

    public ControladorCompra(CompraGui compraGui, PrincipalGui apgui) {
        this.busqueda = new Busqueda();
        this.abmCompra = new ABMCompra();
        prodlista = new LinkedList<Articulo>();
        provlista = new LinkedList<Proveedor>();
        abmCompra = new ABMCompra();
        this.compraGui = compraGui;
        this.compraGui.setActionListener(this);
        this.apgui = apgui;
        this.idCompraAModificar = 0;
        textnom = compraGui.getNombreProveedor();
        textnom.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaProveedorKeyReleased(evt);
            }
        });
        txtCodigoBus= compraGui.getTxtCodigoBusqueda();
        txtCodigoBus.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaPorCodigo(evt);
            }
        });
        tablaprov = compraGui.getTablaProveedores();
        tablaprov.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProvMouseClicked(evt);
            }
        });
        tablaprod = compraGui.getTablaArticulos();
        tablaprod.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProdMouseClicked(evt);
            }
        });


        tablafac = compraGui.getTablaCompra();
        tablafac.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablafacMouseClicked(evt);
            }
        });
        textCodProd = compraGui.getCodigo();
        textCodProd.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaProductoKeyReleased(evt);
            }
        });
        textProvCompra = compraGui.getProveedorCompra();
        textProvCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                provFacMouseClicked(evt);
            }
        });
        tablaProveedores = compraGui.getTablaProveedoresDefault();
        tablaProd = compraGui.getTablaArticulosDefault();
        provlista = busqueda.filtroProveedor("", "");
        actualizarListaProveedor();
        actualizarListaProd();
    }

    private void busquedaPorCodigo(java.awt.event.KeyEvent evt){
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Base.openTransaction();
        Articulo p = Articulo.findFirst("codigo = ?", txtCodigoBus.getText());
        
                    
                    Base.commitTransaction();
                    if(p!=null){
                        if (!existeProdFacc(p.getInteger("id"))) {
                    Object cols[] = new Object[8];
                    BigDecimal bd = new BigDecimal(1);
                    cols[0] = p.get("id");
                    cols[1] = p.get("codigo");
                    cols[2] = BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING);
                    cols[3] = p.get("articulo");
                    cols[4] = p.get("descripcion");
                    cols[5] = BigDecimal.valueOf(p.getFloat("precio_compra")).setScale(2, RoundingMode.CEILING);
                    cols[6] = BigDecimal.valueOf(p.getFloat("precio")).setScale(2, RoundingMode.CEILING);
                    cols[7] = BigDecimal.valueOf(p.getFloat("precio_compra")).setScale(2, RoundingMode.CEILING);
                    compraGui.getTablaCompraDefault().addRow(cols);
                    this.setCellEditor();
                    actualizarPrecio();
                    txtCodigoBus.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(compraGui, "codigo de articulo no encontrado o articulo ya cargado", "codigo inexistente", JOptionPane.INFORMATION_MESSAGE);
                    }
        }
    }
    }
    public void busquedaProveedorKeyReleased(java.awt.event.KeyEvent evt) {
        actualizarListaProveedor();
    }

    private void busquedaProductoKeyReleased(KeyEvent evt) {
        actualizarListaProd();
    }

    private void tablaProvMouseClicked(MouseEvent evt) {
        int row = compraGui.getTablaProveedores().getSelectedRow();
        if (row > -1) {
            String id = (String) tablaprov.getValueAt(row, 0);
            String nom = (String) tablaprov.getValueAt(row, 1);
            compraGui.getProveedorCompra().setText(id + " " + nom);
        }
    }

    private void tablaProdMouseClicked(MouseEvent evt) {
        int[] rows = compraGui.getTablaArticulos().getSelectedRows();
        if (rows.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                if (!existeProdFacc(Integer.valueOf((String) tablaprod.getValueAt(rows[i], 0)))) {
                    Base.openTransaction();
                    Articulo p = Articulo.findFirst("id = ?", (tablaprod.getValueAt(rows[i], 0)));
                    Base.commitTransaction();
                    Object cols[] = new Object[8];
                    BigDecimal bd = new BigDecimal(1);
                    cols[0] = p.get("id");
                    cols[1] = p.get("codigo");
                    cols[2] = BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING);
                    cols[3] = p.get("articulo");
                    cols[4] = p.get("descripcion");
                    cols[5] = BigDecimal.valueOf(p.getFloat("precio_compra")).setScale(2, RoundingMode.CEILING);
                    cols[6] = BigDecimal.valueOf(p.getFloat("precio")).setScale(2, RoundingMode.CEILING);
                    cols[7] = BigDecimal.valueOf(p.getFloat("precio_compra")).setScale(2, RoundingMode.CEILING);
                    compraGui.getTablaCompraDefault().addRow(cols);
                    setCellEditor();
                    actualizarPrecio();
                } 
            }
        }
    }

    public void cargarTodos() {
        abrirBase();
        tablaProveedores.setRowCount(0);
        provlista = busqueda.filtroProveedor("", "");
        Iterator<Proveedor> it = provlista.iterator();
        while (it.hasNext()) {
            Proveedor a = it.next();
            String row[] = new String[2];
            row[0] = a.getId().toString();
            row[1] = a.getString("nombre");
            tablaProveedores.addRow(row);
        }
        tablaProd.setRowCount(0);
        Base.openTransaction();
        prodlista = Articulo.findAll();
        Base.commitTransaction();
        Iterator<Articulo> it2 = prodlista.iterator();
        while (it2.hasNext()) {
            Articulo a = it2.next();
            String rowArray[] = new String[4];
            rowArray[0] = a.getId().toString();
            rowArray[1] = a.getString("codigo");
            rowArray[2] = a.getString("articulo");
            rowArray[3] = a.getString("descripcion");
            tablaProd.addRow(rowArray);
        }
    }

    private void provFacMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            compraGui.getProveedorCompra().setText("");
        }
    }

    public void actualizarListaProveedor() {
        tablaProveedores.setRowCount(0);
        provlista = busqueda.filtroProveedor(textnom.getText(), "");
        Iterator<Proveedor> it = provlista.iterator();
        while (it.hasNext()) {
            Proveedor a = it.next();
            String row[] = new String[2];
            row[0] = a.getId().toString();
            row[1] = a.getString("nombre");
            tablaProveedores.addRow(row);
        }
    }

    public void actualizarListaProd() {
        tablaProd.setRowCount(0);
        Base.openTransaction();
        prodlista = Articulo.where(" (codigo like ? or descripcion like ? or id like ? or articulo like ? or descripcion like ?)", "%" + textCodProd.getText() + "%", "%" + textCodProd.getText() + "%", "%" + textCodProd.getText() + "%", "%" + textCodProd.getText() + "%", "%" + textCodProd.getText() + "%");
        Base.commitTransaction();
        Iterator<Articulo> it = prodlista.iterator();
        while (it.hasNext()) {
            Articulo a = it.next();
            String rowArray[] = new String[4];
            rowArray[0] = a.getId().toString();
            rowArray[1] = a.getString("codigo");
            rowArray[2] = a.getString("articulo");
            rowArray[3] = a.getString("descripcion");
            tablaProd.addRow(rowArray);
        }
    }

    private void tablafacMouseClicked(MouseEvent evt) {
        if (tablafac.isEditing()) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == compraGui.getBorrarArticulosSeleccionados()) {//boton borrar articulos seleccionados
            int[] rows = compraGui.getTablaCompra().getSelectedRows();
            if (rows.length > 0) {
                Integer[] idABorrar = new Integer[rows.length];
                for (int i = 0; i < rows.length; i++) {
                    idABorrar[i] = (Integer) tablafac.getValueAt(rows[i], 0);
                }
                int i = 1;
                int cantABorrar = 0;
                while (cantABorrar < rows.length) {
                    while (i < compraGui.getTablaCompra().getRowCount()) {
                        if ((Integer) compraGui.getTablaCompra().getValueAt(i, 0) == idABorrar[cantABorrar]) {
                            compraGui.getTablaCompraDefault().removeRow(i);
                            cantABorrar++;
                        }
                        i++;
                    }
                    i = 0;
                }
                actualizarPrecio();
            }
        }

        if (e.getSource() == compraGui.getRealizarCompra()) {//Boton realizar compra
            if (compraGui.getCalendarioFacturaText().getText().equals("") || compraGui.getTablaCompra().getRowCount() == 0) {
                JOptionPane.showMessageDialog(compraGui, "Falta la fecha o no hay productos cargados", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                if (compraGui.getProveedorCompra().getText().equals("")) {
                    JOptionPane.showMessageDialog(compraGui, "Proveedor no seleccionado", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                     
                    Compra v = new Compra();
                    LinkedList<Pair> parDeProductos = new LinkedList();
                    LinkedList<BigDecimal> precioFinales = new LinkedList();
                    String laFecha = compraGui.getCalendarioFacturaText().getText(); //saco la fecha
                    String cliente = compraGui.getProveedorCompra().getText();
                    if (!cliente.equals("")) {
                        Integer idCliente = Integer.valueOf(cliente.split(" ")[0]); //saco el id prov
                        v.set("proveedor_id", idCliente);
                    }
                    for (int i = 0; i < compraGui.getTablaCompra().getRowCount(); i++) {
                        Base.openTransaction();
                        Articulo producto = Articulo.findFirst("id = ?", tablafac.getValueAt(i, 0));
                        Base.commitTransaction();
                        BigDecimal cantidad = ((BigDecimal) tablafac.getValueAt(i, 2)).setScale(2, RoundingMode.CEILING); //saco la cantidad
                        BigDecimal precioFinal = ((BigDecimal) tablafac.getValueAt(i, 5)).setScale(2, RoundingMode.CEILING);
                        BigDecimal precioVentaFinal = ((BigDecimal) tablafac.getValueAt(i, 6)).setScale(2, RoundingMode.CEILING);
                        producto.set("precio_compra", precioFinal);
                        producto.set("precio",precioVentaFinal);
                        producto.saveIt();
                        precioFinales.add(precioFinal);
                        Pair par = new Pair(producto, cantidad); //creo el par
                        parDeProductos.add(par); //meto el par a la lista
                    }
                    v.set("fecha", laFecha);
                    v.setProductos(parDeProductos);
                    v.setPreciosFinales(precioFinales);
                    if (compraGui.getAbonaSi().isSelected()) {
                        v.set("pago", true);
                    } else {
                        v.set("pago", false);
                    }
                    BigDecimal bd = new BigDecimal(compraGui.getTotalCompra().getText());
                    v.set("monto", bd);
                    if (abmCompra.alta(v)) {
                        JOptionPane.showMessageDialog(apgui, "Compra realizada con exito.");
                        compraGui.limpiarVentana();
                        Base.openTransaction();
                        Proveedor prov = Proveedor.findById(v.get("proveedor_id"));
                        Base.commitTransaction();
                        if (!(prov == null) && (compraGui.getAbonaSi().isSelected())) {
                            BigDecimal cuentaCorriente = prov.getBigDecimal("cuenta_corriente").subtract(v.getBigDecimal("monto"));
                            prov.set("cuenta_corriente", cuentaCorriente);
                            Base.openTransaction();
                            v = Compra.findById(ABMCompra.idCompraAlta);
                            Base.commitTransaction();
                            
                            realizarPagoGui = new RealizarPagoGui(apgui, true, prov, v);
                            realizarPagoGui.setLocationRelativeTo(compraGui);
                            realizarPagoGui.setVisible(true);
                        } else if (!(prov == null)) {
                            BigDecimal cuentaCorriente = prov.getBigDecimal("cuenta_corriente").subtract(v.getBigDecimal("monto"));
                             
                            prov.set("cuenta_corriente", cuentaCorriente);
                            prov.saveIt();
                             
                        }
                         
                    } else {
                         
                        JOptionPane.showMessageDialog(apgui, "Ocurrió un error inesperado, compra no realizada");
                    }
                }
            }
        }

        if (e.getSource() == compraGui.getCompraNueva()) {
            compraGui.limpiarVentana();
            compraGui.paraVerCompra(false);
            compraGui.getRealizarCompra().setEnabled(true);
        }
       
        
    }

    private boolean existeProdFacc(int id) {
        boolean ret = false;
        for (int i = 0; i < tablafac.getRowCount() && !ret; i++) {
            if(tablafac.getValueAt(i, 0)!=null)
                ret = (Integer) tablafac.getValueAt(i, 0) == id;
        }
        return ret;
    }

    public void setCellEditor() {
        for (int i = 0; i < tablafac.getRowCount(); i++) {
            tablafac.getCellEditor(i, 1).addCellEditorListener(this);
            tablafac.getCellEditor(i, 5).addCellEditorListener(this);
            tablafac.getCellEditor(i, 6).addCellEditorListener(this);
        }
    }

    public void actualizarPrecio() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < tablafac.getRowCount(); i++) {
            if(tablafac.getValueAt(i,0)!=null){
            importe = ((BigDecimal) tablafac.getValueAt(i, 2)).multiply((BigDecimal) compraGui.getTablaCompra().getValueAt(i, 5)).setScale(2, RoundingMode.CEILING);
            tablafac.setValueAt(importe, i, 7);
            }
        }
        for (int i = 0; i < tablafac.getRowCount(); i++) {
                        if(tablafac.getValueAt(i,0)!=null){
            total = total.add((BigDecimal) tablafac.getValueAt(i, 7)).setScale(2, RoundingMode.CEILING);
                        }
        }
        compraGui.getTotalCompra().setText(total.toString());
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        actualizarPrecio();
//        for (int i = 0; i < tablafac.getRowCount(); i++) {
//            Base.openTransaction();
//            Articulo a = Articulo.findById(tablafac.getValueAt(i, 0));
//            Base.commitTransaction();
//            if (!(a.getBigDecimal("precio_compra").equals(tablafac.getValueAt(i, 4)))) {
//                a.setBigDecimal("precio_compra", tablafac.getValueAt(i, 4));
//                a.saveIt();
//            }
//            if (!(a.getBigDecimal("precio_venta").equals(tablafac.getValueAt(i, 5)))) {
//                a.setBigDecimal("precio_venta", tablafac.getValueAt(i, 5));
//                a.saveIt();
//            }
       // }
   
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CompraGui getCompraGui() {
        return compraGui;
    }
    
    
}
