/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import BD.ConexionBD;
import static BD.ConexionBD.abrirBase;
import Utiles.DatosGenericos;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author nico
 */
public class ControladorJReport {

    private JasperReport reporte;
    private String ruta;

    public ControladorJReport(String jasper) throws JRException, ClassNotFoundException, SQLException {
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reporte/" + jasper));//cargo el reporte
        ruta = getClass().getResource("/Reporte/" + jasper).getPath();
    }

    //listado de clientes productos y proveedores.
    public void mostrarReporte() throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer.viewReport(jasperPrint, false);
        connection.close();
    }

    //listado de clientes productos y proveedores.
    public void mostrarReporteGastos(Date desde, Date hasta) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer.viewReport(jasperPrint, false);
        connection.close();
    }

    public void mostrarTicketMembresia(Integer idPagoMemb) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        parametros.put("direccion_gimnasio", DatosGenericos.direccionGimnasio);
        parametros.put("id_pago", idPagoMemb);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer.viewReport(jasperPrint, false);
        connection.close();
    }

    //listado de clientes productos y proveedores.
    public void mostrarDieta(Integer idSocio, Integer idDieta) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("id_socio", idSocio);
        parametros.put("id_dieta", idDieta);
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

        jasperViewer.setTitle("Impresión de dieta");

        jasperViewer.toFront();
        jasperViewer.setVisible(true);

    }

    //listado de clientes productos y proveedores.
    public String obtenerDieta(Integer idSocio, Integer idDieta) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("id_socio", idSocio);
        parametros.put("id_dieta", idDieta);
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        String rutaDestino = System.getProperty("user.dir");

        try {
            System.err.println(new File(".").getCanonicalFile());
        } catch (IOException ex) {
            Logger.getLogger(ControladorJReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.err.println(new File(".").getParentFile());

        rutaDestino += "/dieta.pdf";
//         }
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaDestino);

        return rutaDestino;

    }

    public void mostrarRutina(Integer idRutina) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();

        parametros.put("rutina_id", idRutina);
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);

        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setTitle("Impresión de rutina");
        jasperViewer.toFront();
        jasperViewer.setVisible(true);
    }

    public String obtenerRutina(Integer idRutina) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("rutina_id", idRutina);
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        String rutaDestino = System.getProperty("user.dir");
//         int i=0;
//         for( i=rutaDestino.length()-2;i>0 && rutaDestino.charAt(i)!='/';i-- ){
//             rutaDestino= rutaDestino.substring(0, i);
//             
//         }
        // if(rutaDestino.charAt(i)=='/'){
        rutaDestino += "/rutina.pdf";
        //}
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaDestino);

        return rutaDestino;

    }

    public void mostrarTicketVenta(Integer idVenta) throws ClassNotFoundException, SQLException, JRException {
        Class.forName(ConexionBD.driver);
        Connection connection = abrirBase();
        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("id_venta", idVenta);
        parametros.put("nombre_gimnasio", DatosGenericos.nombreGimnasio);
        parametros.put("direccion_gimnasio", DatosGenericos.direccionGimnasio);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer.viewReport(jasperPrint, false);
        connection.close();
    }

}
