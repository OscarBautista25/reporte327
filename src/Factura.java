import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Factura {
    Connection conexion;
    PreparedStatement preparar;
    ResultSet resultado;

    public Connection conectar(){
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/reporte", "root", "1234");

        }catch (SQLException e){
            System.out.println("Error al conectar la base de datos " +e.getMessage());

        }
        return conexion;
    }

    public Factura(){
        conectar();
    }

    private String obtenerNombreUsuario(String factura){
        String usuario = null;
        try {
            String query ="Select usuario from facturas where id_factura = ?";
            preparar = conexion.prepareStatement(query);
            preparar.setString(1, factura);
            resultado = preparar.executeQuery();
            if(resultado.next()){
                usuario = resultado.getString("usuario");
            }

        }catch (SQLException e){
            System.out.println("Error al obtener el nombre del usuario " + e.getMessage());

        }
        return usuario;
    }

    private String obtenerValorVenta(String factura){
        String valorVenta = null;
        try {
            String query ="Select valor_venta from facturas where id_factura = ?";
            preparar = conexion.prepareStatement(query);
            preparar.setString(1, factura);
            resultado = preparar.executeQuery();
            if(resultado.next()){
                valorVenta = resultado.getString("valor_venta");
            }

        }catch (SQLException e){
            System.out.println("Error al obtener el valor de la venta " + e.getMessage());

        }
        return valorVenta;
    }
    private String obtenerNombreVendedor(String factura){
        String vendedor = null;
        try {
            String query ="Select nombre_vendedor from facturas where id_factura = ?";
            preparar = conexion.prepareStatement(query);
            preparar.setString(1, factura);
            resultado = preparar.executeQuery();
            if(resultado.next()){
                vendedor = resultado.getString("nombre_vendedor");
            }

        }catch (SQLException e){
            System.out.println("Error al obtener el nombre del vendedor " + e.getMessage());

        }
        return vendedor;
    }
    public void generarReporte(String factura){
        try {
            String usuario = obtenerNombreUsuario(factura);
            String valorVenta = obtenerValorVenta(factura);
            String vendedor = obtenerNombreVendedor(factura);

            Document documentoFactura = new Document();
            PdfWriter.getInstance(documentoFactura, new FileOutputStream("Factura.pdf"));
            documentoFactura.open();
            documentoFactura.add(new Paragraph("FACTURA DE COMPRA"));
            List list = new List();
                    list.setSymbolIndent(12);
                    list.setListSymbol("\u2022");

// Add ListItem objects
            list.add(new ListItem("Nombre del comprador:  " +usuario));
            list.add(new ListItem("Valor de la venta:  " +valorVenta));
            list.add(new ListItem("Nombre del vendedor:  " +vendedor));

//// Add the list
            documentoFactura.add(list);

            documentoFactura.close();

            System.out.println("El certificado ha sido generado satisfactoriamiente y se encuentra alojado en la carpeta del proyecto");

        }catch (DocumentException | IOException e){
            System.out.println("Error al generar el certificado " + e.getMessage());

        }


        }
     public static void main(String[] args) {
        Factura factura1 = new Factura();
        factura1.generarReporte("1001");



        }





}
